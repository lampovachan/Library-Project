package com.tkachuk.library.model.elastic;

import com.tkachuk.library.dto.BookDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

@Document(indexName = "book", type = "book")
public class EsBook implements Serializable {
    private static final long serialVersionUID = -3442422877407279457L;

    @Id
    private String id;

    @Field
    private String author;

    @Field
    private String title;

    @Field
    private String description;

    public EsBook() {}

    public EsBook(String author, String title, String description) {
        this.author = author;
        this.title = title;
        this.description = description;
    }

    public EsBook(BookDto book) {
        this.author = book.getAuthor();
        this.title = book.getTitle();
        this.description = book.getDescription();
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
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
