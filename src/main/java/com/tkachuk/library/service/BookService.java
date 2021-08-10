package com.tkachuk.library.service;

import java.util.List;
import java.util.Optional;

import com.tkachuk.library.dto.BookDto;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }


    public Book getSpecificBook(String id) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    public Book createBook(BookDto bookDTO) {
        Book book = new Book(bookDTO);
        return this.bookRepository.save(book);
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

        foundBook.setIsbn(bookDTO.getIsbn());
        foundBook.setTitle(bookDTO.getTitle());
        foundBook.setAuthor(bookDTO.getAuthor());
        foundBook.setPages(bookDTO.getPages());
        foundBook.setImage(bookDTO.getImage());

        return this.bookRepository.save(foundBook);
    }
}
