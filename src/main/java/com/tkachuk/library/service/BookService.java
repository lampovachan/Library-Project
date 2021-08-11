package com.tkachuk.library.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.tkachuk.library.dto.BookDto;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.model.User;
import com.tkachuk.library.repository.BookRepository;
import com.tkachuk.library.repository.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookService {
    private final BookRepository bookRepository;

    private final MongoTemplate mongoTemplate;

    private final AmazonS3 configure;

    @Autowired
    public BookService(BookRepository bookRepository, MongoTemplate mongoTemplate, AmazonS3 configure) {
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
        this.configure = configure;
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public List<Book> getBooksByAuthor(String author) {
        Query query = new Query();
        query.addCriteria(Criteria.where("author").is(author));
        return mongoTemplate.find(query, Book.class);
    }

    public List<Book> sortBooksByDateASC() {
        return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "created"));
    }

    public Book getSpecificBook(String id) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    public Book createBook(BookDto bookDTO) {
        Book book = new Book(bookDTO, new Date());
        return bookRepository.save(book);
    }

    public boolean deleteBook(String id) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        Book book = bookOptional.orElse(null);

        if (book == null) {
            return false;
        }

        this.bookRepository.deleteById(id);

        return true;
    }

    public Book editBook(String id, BookDto bookDTO) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        Book foundBook = bookOptional.orElse(null);

        if (foundBook == null) {
            return null;
        }
        foundBook.setTitle(bookDTO.getTitle());
        foundBook.setAuthor(bookDTO.getAuthor());
        foundBook.setGenres(bookDTO.getGenres());
        foundBook.setDescription(bookDTO.getDescription());
        foundBook.setFileId(bookDTO.getFileId());
        foundBook.setImage(bookDTO.getImage());

        return this.bookRepository.save(foundBook);
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String uploadFileTos3bucket(String fileName, File file) {
        if (!configure.doesBucketExist("book-user")) {
            configure.createBucket("book-user");
        }
        try {
            configure.putObject(new PutObjectRequest("book-user", fileName, file));
        } catch (AmazonServiceException e) {
            return "uploadFileTos3bucket().Uploading failed :" + e.getMessage();
        }
        return "Uploading Successfull -> ";
    }

    private File getFile(MultipartFile multipartFile) throws IOException {
        return convertMultiPartToFile(multipartFile);
    }

    private String getFilename(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename();
    }

    private String uploadWrapper(MultipartFile multipartFile) throws IOException {
        File file = getFile(multipartFile);
        String fileName = getFilename(multipartFile);
        return uploadFileTos3bucket(fileName, file);
    }

    private String saveBookFile(MultipartFile multipartFile, String id) {
        String fileName = getFilename(multipartFile);
        String fileUrl = "";
        fileUrl = "book-user" + "/" + fileName;
        Optional<Book> book = bookRepository.findById(id);
        book.get().setFileId(fileUrl);
        bookRepository.save(book.get());
        return fileUrl;
    }

    public String uploadBookFile(MultipartFile multipartFile, String id) throws IOException {
        File file = getFile(multipartFile);
        String status = uploadWrapper(multipartFile);
        String fileUrl = saveBookFile(multipartFile, id);

        file.delete();
        return status + " " + fileUrl;
    }

    public S3Object getBookFileFromS3(String fileUrl) {
        return configure.getObject("book-user", fileUrl);
    }
}
