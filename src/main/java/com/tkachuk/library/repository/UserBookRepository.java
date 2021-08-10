package com.tkachuk.library.repository;

import java.util.List;

import com.tkachuk.library.model.UserBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserBookRepository extends MongoRepository<UserBook, String> {
    @Query(value = "{'userId' : ?0}")
    List<UserBook> findByUserId(String userId);
}