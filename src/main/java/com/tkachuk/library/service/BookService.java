package com.tkachuk.library.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.tkachuk.library.dto.BookDto;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    private final MongoTemplate mongoTemplate;

    @Autowired
    public BookService(BookRepository bookRepository, MongoTemplate mongoTemplate) {
        this.bookRepository = bookRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooks(String genre, String author, boolean asc, boolean desc) {
        Query query = new Query();
        if (genre!=null) {
            query.addCriteria(Criteria.where("genre").is(genre));
            return mongoTemplate.find(query, Book.class);
        }
        if (author!=null) {
            query.addCriteria(Criteria.where("author").is(author));
            return mongoTemplate.find(query, Book.class);
        }
        if (asc) {
            return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "created"));
        }
        if (desc) {
            return bookRepository.findAll(Sort.by(Sort.Direction.DESC, "created"));
        }
        else {
            return bookRepository.findAll();
        }
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
}
