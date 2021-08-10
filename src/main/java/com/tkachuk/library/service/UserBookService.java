package com.tkachuk.library.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.tkachuk.library.dto.UserBookDto;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.model.UserBook;
import com.tkachuk.library.repository.BookRepository;
import com.tkachuk.library.repository.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserBookService {
    private static final Logger LOG = Logger.getLogger(UserBookService.class.getName());

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserBookRepository userBookRepository;

    public List<UserBook> getUserBooks() {
        return this.userBookRepository.findAll();
    }

    public UserBook getSpecificUserBook(String id) {
        Optional<UserBook> userBookOptional = this.userBookRepository.findById(id);
        return userBookOptional.orElse(null);
    }

    public UserBook createUserBook(UserBookDto userBookDTO) {
        Book book = null;

        if (userBookDTO.getBook().getIsbn() != null) {
            Optional<Book> bookOptional = this.bookRepository.findByISBN(userBookDTO.getBook().getIsbn());
            book = bookOptional.orElse(null);
        }

        if (book == null) {
            book = this.bookRepository.save(userBookDTO.getBook());
        }

        UserBook userBook = new UserBook(userBookDTO);
        userBook.setBook(book);

        return this.userBookRepository.save(userBook);
    }

    public boolean deleteUserBook(String id) {
        Optional<UserBook> userBookOptional = this.userBookRepository.findById(id);
        UserBook userBook = userBookOptional.orElse(null);

        if (userBook == null) {
            return false;
        }

        this.userBookRepository.deleteById(id);

        return true;
    }

    public UserBook editUserBook(String id, UserBookDto userBookDTO) {
        Optional<UserBook> userBookOptional = this.userBookRepository.findById(id);
        UserBook foundUserBook = userBookOptional.orElse(null);

        if (foundUserBook == null) {
            return null;
        }

        foundUserBook.setUserId(userBookDTO.getUserId());
        foundUserBook.setBook(userBookDTO.getBook());
        foundUserBook.setLocationStatus(userBookDTO.getLocationStatus());
        foundUserBook.setProgressStatus(userBookDTO.getProgressStatus());
        foundUserBook.setComment(userBookDTO.getComment());

        return this.userBookRepository.save(foundUserBook);
    }

    public Iterable<UserBook> getUserBooksForUser(String userId) {
        return this.userBookRepository.findByUserId(userId);
    }
}
