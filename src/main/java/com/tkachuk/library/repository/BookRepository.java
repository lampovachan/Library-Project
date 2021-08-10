package com.tkachuk.library.repository;

import java.util.Optional;

import com.tkachuk.library.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book, String> {
    @Query(value = "{'isbn' : ?0}")
    Optional<Book> findByISBN(String isbn);
}