package com.tkachuk.library.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.tkachuk.library.dto.BookDto;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.model.elastic.EsBook;
import com.tkachuk.library.repository.BookRepository;
import com.tkachuk.library.service.elastic.EsBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    private final MongoTemplate mongoTemplate;

    private final EsBookService esBookService;

    @Autowired
    public BookService(BookRepository bookRepository, MongoTemplate mongoTemplate, EsBookService esBookService) {
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
        this.esBookService = esBookService;
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooks(String genre, String author, String order) {
        Query query = new Query();
        if (genre!=null) {
            query.addCriteria(Criteria.where("genre").is(genre));
            return mongoTemplate.find(query, Book.class);
        }
        if (author!=null) {
            query.addCriteria(Criteria.where("author").is(author));
            return mongoTemplate.find(query, Book.class);
        }
        if (order!=null && order.equals("asc")) {
            return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "created"));
        }
        else if (order!=null && order.equals("desc")) {
            return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "created"));
        }
        else {
            return bookRepository.findAll();
        }
    }

    public Iterable<EsBook> searchForBooks(String author, String title, String description, PageRequest pageRequest) {
        if (author!=null) {
            return esBookService.findByAuthor(author, pageRequest);
        }

        if (title!=null) {
            return esBookService.findByTitle(title);
        }

        if (description!=null) {
            return esBookService.findByDescription(description, pageRequest);
        }
        else {
            return esBookService.findAll();
        }
    }

    public Book getSpecificBook(String id) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    public Book createBook(BookDto bookDTO) {
        Book book = new Book(bookDTO, new Date());
        EsBook esBook = new EsBook(bookDTO);
        esBookService.save(esBook);
        return bookRepository.save(book);
    }

    public boolean deleteBook(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Optional <EsBook> esBookOptional = esBookService.findOne(id);
        EsBook esBook = esBookOptional.orElse(null);
        Book book = bookOptional.orElse(null);

        if (book == null) {
            return false;
        }

        bookRepository.deleteById(id);
        esBookService.delete(esBook);

        return true;
    }

    public Book editBook(String id, BookDto bookDTO) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Optional<EsBook> esBookOptional = esBookService.findOne(id);
        Book foundBook = bookOptional.orElse(null);
        EsBook esBook = esBookOptional.orElse(null);

        if (foundBook == null) {
            return null;
        }
        foundBook.setTitle(bookDTO.getTitle());
        foundBook.setAuthor(bookDTO.getAuthor());
        foundBook.setGenres(bookDTO.getGenres());
        foundBook.setDescription(bookDTO.getDescription());
        foundBook.setFileId(bookDTO.getFileId());
        foundBook.setImage(bookDTO.getImage());

        esBook.setAuthor(esBook.getAuthor());
        esBook.setTitle(esBook.getTitle());
        esBook.setDescription(esBook.getDescription());

        esBookService.save(esBook);
        return bookRepository.save(foundBook);
    }
}
