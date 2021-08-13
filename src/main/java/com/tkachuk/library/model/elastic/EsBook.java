package com.tkachuk.library.model.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "book", type = "book")
public class EsBook implements Serializable {
    private static final long serialVersionUID = -3442422877407279457L;

    @Id
    private String id;

    private String author;

    private String title;

    private String description;

    public EsBook(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public EsBook(String author, String title, String description) {
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
}
