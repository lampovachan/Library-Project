package com.tkachuk.library.model;

import com.tkachuk.library.dto.BookDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {
    @Id
    private String id;

    private String isbn;
    private String title;
    private String author;
    private int pages;
    private String image;

    public Book() {
    }

    public Book(String isbn, String title, String author, int pages, String image) {
        super();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.image = image;
    }


    public Book(BookDto bookDTO) {
        super();
        this.isbn = bookDTO.getIsbn();
        this.title = bookDTO.getTitle();
        this.author = bookDTO.getAuthor();
        this.pages = bookDTO.getPages();
        this.image = bookDTO.getImage();
    }


    public String getId() {
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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


    public int getPages() {
        return this.pages;
    }


    public void setPages(int pages) {
        this.pages = pages;
    }


    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}