package com.tkachuk.library.model.elastic;

import com.tkachuk.library.dto.BookDto;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName = "book", type = "book")
public class EsBook implements Serializable {
    private static final long serialVersionUID = -3442422877407279457L;

    @Id
    private String id;

    @Field
    private String bookId;

    private String author;

    private String title;

    private String description;

    public EsBook(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public EsBook(String bookId, String author, String title, String description) {
        this.bookId = bookId;
        this.author = author;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return this.title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return this.author;
    }


    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String fileId) {
        this.bookId = bookId;
    }
}
