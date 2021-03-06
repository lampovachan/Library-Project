package com.tkachuk.library.service.elastic;

import java.util.List;
import java.util.Optional;

import com.tkachuk.library.model.Book;
import com.tkachuk.library.model.elastic.EsBook;
import com.tkachuk.library.repository.elastic.EsBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EsBookService {

    private final EsBookRepository bookRepository;

    @Autowired
    public EsBookService(EsBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public EsBook save(EsBook book) {
        return bookRepository.save(book);
    }

    public void delete(EsBook book) {
        bookRepository.delete(book);
    }

    public Optional<EsBook> findOne(String id) {
        return bookRepository.findById(id);
    }

    public Iterable<EsBook> findAll() {
        return bookRepository.findAll();
    }

    public List<EsBook> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<EsBook> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<EsBook> findByDescription(String description) {
        return bookRepository.findByDescription(description);
    }
}

