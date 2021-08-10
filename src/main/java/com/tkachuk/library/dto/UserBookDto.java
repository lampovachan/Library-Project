package com.tkachuk.library.dto;

import com.tkachuk.library.model.Book;
import com.tkachuk.library.model.enums.BookLocationStatus;
import com.tkachuk.library.model.enums.BookProgressStatus;

public class UserBookDto {
    private String id;
    private String userId;
    private Book book;
    private BookLocationStatus locationStatus;
    private BookProgressStatus progressStatus;
    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookLocationStatus getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(BookLocationStatus locationStatus) {
        this.locationStatus = locationStatus;
    }

    public BookProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(BookProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}