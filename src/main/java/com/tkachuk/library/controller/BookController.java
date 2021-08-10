package com.tkachuk.library.controller;

import com.tkachuk.library.dto.BookDto;
import com.tkachuk.library.model.Book;
import com.tkachuk.library.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Book Controller")
@CrossOrigin(
        origins = "*",
        methods = { RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RequestMapping("/api/books")
@RestController
public class BookController {
    @Autowired
    BookService bookService;

    @ApiOperation(value = "Fetches all Books.")
    @GetMapping()
    public Iterable<Book> getBooks() {
        return this.bookService.getBooks();
    }

    @ApiOperation(value = "Fetch a specific Book.")
    @GetMapping(value = "/{id}")
    public Book getSpecificBook(@PathVariable("id") String id) {
        return this.bookService.getSpecificBook(id);
    }

    @ApiOperation(value = "Post a Book.")
    @PostMapping()
    public Book postBook(@RequestBody BookDto bookDTO) {
        return this.bookService.createBook(bookDTO);
    }

    @ApiOperation(value = "Delete a Book.")
    @DeleteMapping(value = "/{id}")
    public boolean deleteBook(@PathVariable("id") String id) {
        return this.bookService.deleteBook(id);
    }

    @ApiOperation(value = "Edit a Book.")
    @PutMapping(value = "/{id}")
    public Book putBook(@PathVariable("id") String id, @RequestBody BookDto bookDTO) {
        return this.bookService.editBook(id, bookDTO);
    }
}
