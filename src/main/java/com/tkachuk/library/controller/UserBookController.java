package com.tkachuk.library.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.repository.BookRepository;
import com.tkachuk.library.service.BookService;
import com.tkachuk.library.security.services.UserDetailsServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
    private final UserDetailsServiceImpl userDetailsService;
    private final BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    public UserBookController(UserDetailsServiceImpl userDetailsService, BookService bookService) {
        this.userDetailsService = userDetailsService;
        this.bookService = bookService;
    }

    @ApiOperation(value = "Fetches all Books.", authorizations = { @Authorization(value="jwtToken") })
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping()
    public Iterable<Book> getBooks() {
        return bookService.getBooks();
    }

    @ApiOperation(value = "Find Books by a specific author.", authorizations = { @Authorization(value = "jwtToken")})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/by-author")
    public Iterable<Book> filterByAuthor(@RequestParam String author) {
        return bookService.getBooksByAuthor(author);
    }

    @ApiOperation(value = "Sort Books by date ascending.", authorizations = { @Authorization(value = "jwtToken")})
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "by-date")
    public Iterable<Book> sortByDate() {
        return bookService.sortBooksByDateASC();
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
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty())
            return ResponseEntity.notFound().build();
        String fileUrl = book.get().getFileId();

        S3Object object = bookService.getBookFileFromS3(fileUrl.split("/")[1]);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .cacheControl(CacheControl.noCache())
                .header("Content-Disposition", "attachment; filename=" + book.get().getFileId().split("/")[1])
                .body(new InputStreamResource(object.getObjectContent()));
    }

}
