package com.tkachuk.library.model;

import com.tkachuk.library.dto.UserBookDto;
import com.tkachuk.library.model.enums.BookLocationStatus;
import com.tkachuk.library.model.enums.BookProgressStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userbooks")
public class UserBook {
    @Id
    private String id;

    private String userId;
    @DBRef
    private Book book;
    private BookLocationStatus locationStatus;
    private BookProgressStatus progressStatus;
    private String comment;

    public UserBook() {
        this.locationStatus = BookLocationStatus.OWNED;
        this.progressStatus = BookProgressStatus.UNREAD;
    }


    public UserBook(UserBookDto userBookDTO) {
        this.book = userBookDTO.getBook();
        this.userId = userBookDTO.getUserId();
        this.locationStatus = userBookDTO.getLocationStatus();
        this.progressStatus = userBookDTO.getProgressStatus();
        this.comment = userBookDTO.getComment();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookLocationStatus getLocationStatus() {
        return this.locationStatus;
    }

    public void setLocationStatus(BookLocationStatus locationStatus) {
        this.locationStatus = locationStatus;
    }

    public BookProgressStatus getProgressStatus() {
        return this.progressStatus;
    }

    public void setProgressStatus(BookProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
