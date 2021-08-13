package com.tkachuk.library.controller;

import com.tkachuk.library.dto.BookDto;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.service.BookService;
import com.tkachuk.library.service.PhotoService;
import com.tkachuk.library.service.elastic.EsBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Api(value = "Admin Book Controller")
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequestMapping("/api/books")
@RestController
public class AdminBookController {
    private final BookService bookService;
    private final PhotoService photoService;

    @Autowired
    public AdminBookController(BookService bookService, PhotoService photoService) {
        this.bookService = bookService;
        this.photoService = photoService;
    }

    @ApiOperation(value = "Post a Book.", authorizations = { @Authorization(value="jwtToken") })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public Book postBook(@RequestBody BookDto bookDTO) {
        return bookService.createBook(bookDTO);
    }

    @ApiOperation(value = "Delete a Book.", authorizations = { @Authorization(value="jwtToken") })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public boolean deleteBook(@PathVariable("id") String id) {
        return bookService.deleteBook(id);
    }

    @ApiOperation(value = "Edit a Book.", authorizations = { @Authorization(value="jwtToken") })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public Book putBook(@PathVariable("id") String id, @RequestBody BookDto bookDTO) {
        return bookService.editBook(id, bookDTO);
    }

    @ApiOperation(value = "Upload a Book.", authorizations = { @Authorization(value="jwtToken") })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile multipartFile, @PathVariable String id) throws IOException {
        return photoService.uploadBookFile(multipartFile, id);
    }

    @ApiOperation(value = "Upload a Photo.", authorizations = { @Authorization(value="jwtToken") })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/uploadPhoto")
    public String uploadPhoto(@RequestPart(value = "file") MultipartFile multipartFile, @PathVariable String id) throws IOException {
        return photoService.uploadPhoto(multipartFile, id);
    }
}
