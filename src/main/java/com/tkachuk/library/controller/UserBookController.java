package com.tkachuk.library.controller;

import com.tkachuk.library.dto.UserBookDto;
import com.tkachuk.library.model.UserBook;
import com.tkachuk.library.service.CustomUserDetailsService;
import com.tkachuk.library.service.UserBookService;
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
import org.springframework.web.bind.annotation.RestController;

@Api(value = "UserBook Controller")
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
    @Autowired
    CustomUserDetailsService userService;

    @Autowired
    UserBookService userBookService;

    @ApiOperation(value = "Fetches all UserBooks.")
    @GetMapping()
    public Iterable<UserBook> getUserBooks() {
        return this.userBookService.getUserBooks();
    }

    @ApiOperation(value = "Fetche a specific UserBook.")
    @GetMapping(value = "/{id}")
    public UserBook getSpecificUserBook(@PathVariable("id") String id) {
        return this.userBookService.getSpecificUserBook(id);
    }

    @ApiOperation(value = "Post a UserBook.")
    @PostMapping()
    public UserBook postUserBook(@RequestBody UserBookDto userBookDTO) {
        return this.userBookService.createUserBook(userBookDTO);
    }

    @ApiOperation(value = "Delete a UserBook.")
    @DeleteMapping(value = "/{id}")
    public boolean deleteUserBook(@PathVariable("id") String id) {
        return this.userBookService.deleteUserBook(id);
    }

    @ApiOperation(value = "Edit a UserBook.")
    @PutMapping(value = "/{id}")
    public UserBook putUserBook(@PathVariable("id") String id, @RequestBody UserBookDto userBookDTO) {
        return this.userBookService.editUserBook(id, userBookDTO);
    }

    @ApiOperation(value = "Fetch all UserBooks for a specific User.")
    @GetMapping(value="/user/{userId}")
    public Iterable<UserBook> getUserBooksForUser(@PathVariable("userId") String userId) {
        return this.userBookService.getUserBooksForUser(userId);
    }
}
