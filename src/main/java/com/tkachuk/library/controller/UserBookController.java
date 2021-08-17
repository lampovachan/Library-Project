package com.tkachuk.library.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.model.elastic.EsBook;
import com.tkachuk.library.service.BookService;
import com.tkachuk.library.service.PhotoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(value = "User Book Controller")
@CrossOrigin(
        origins = "*",
        methods = { RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequestMapping("/api/userbooks")
@RestController
public class UserBookController {
    private final BookService bookService;
    private final PhotoService photoService;

    @Autowired
    public UserBookController(BookService bookService, PhotoService photoService) {
        this.bookService = bookService;
        this.photoService = photoService;
    }

    @ApiOperation(value = "Fetches all Books by criteria or without it.", authorizations = { @Authorization(value="jwtToken") })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping()
    public Iterable<Book> getBooks(@RequestParam(required = false) String genre, @RequestParam(required = false) String author,
                                   @RequestParam(required = false) String order) {
        return bookService.getBooks(genre, author, order);
    }

    @ApiOperation(value = "Fetch a specific Book.", authorizations = { @Authorization(value="jwtToken") })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/{id}")
    public Book getSpecificBook(@PathVariable("id") String id) {
        return bookService.getSpecificBook(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getFile")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> getFile(@RequestParam String bookId) {
        Optional<Book> book = bookService.findById(bookId);
        if (book.isEmpty())
            return ResponseEntity.notFound().build();
        String fileUrl = book.get().getFileId();
        S3Object object = photoService.getBookFileFromS3(fileUrl.split("/")[1]);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + book.get().getFileId().split("/")[1])
                .body(new InputStreamResource(object.getObjectContent()));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/getPhoto")
    @ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
    public ResponseEntity<?> getPhoto(@RequestParam String bookId) {
        Optional<Book> book = bookService.findById(bookId);
        if (book.isEmpty())
            return ResponseEntity.notFound().build();
        String fileUrl = book.get().getFileId();

        S3Object object = photoService.getPhotoFromS3(fileUrl.split("/")[1]);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + book.get().getFileId().split("/")[1])
                .body(new InputStreamResource(object.getObjectContent()));
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/findBook")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public Iterable<EsBook> findBooks(@RequestParam(required = false) String author, @RequestParam(required = false) String title,
                                      @RequestParam(required = false) String description) {
        return bookService.searchForBooks(author, title, description);
    }
}
