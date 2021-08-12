package com.tkachuk.library.model;

import com.tkachuk.library.dto.BookDto;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "books")
public class Book {
    @Id
    @ApiModelProperty(required = false, hidden = true)
    private String id;
    private String author;
    private String title;
    private List<String> genres;
    @ApiModelProperty(required = false, hidden = true)
    private String image;
    private String description;
    @ApiModelProperty(required = false, hidden = true)
    private String fileId;
    private Date created;

    public Book(BookDto bookDTO, Date created) {
        this.author = bookDTO.getAuthor();
        this.title = bookDTO.getTitle();
        this.image = bookDTO.getImage();
        this.created = created;
    }

    public Book() {

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

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}