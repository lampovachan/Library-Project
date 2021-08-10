package com.tkachuk.library.controller;

import java.util.HashMap;
import java.util.Map;

import com.tkachuk.library.dto.UserDto;
import com.tkachuk.library.model.User;
import com.tkachuk.library.repository.UserRepository;
import com.tkachuk.library.security.jwt.JwtTokenProvider;
import com.tkachuk.library.service.CustomUserDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Authentication Controller")
@CrossOrigin(
        origins = "*",
        methods = { RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository users;

    @Autowired
    private CustomUserDetailsService userService;

    @ApiOperation(value = "Login to the application.")
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody UserDto userDTO) {
        try {
            String username = userDTO.getUsername();
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userDTO.getPassword()));
            User foundUser = this.users.findByUsername(username);
            String token = this.jwtTokenProvider.createToken(username, foundUser.getRoles());
            Map<String, String> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("userId", foundUser.getId());
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Login failed. Nice try.", HttpStatus.UNAUTHORIZED);
        }
    }

    @ApiOperation(value = "Register to the application.")
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody UserDto userDTO) {
        User userExists = this.userService.findUserByUsername(userDTO.getUsername());
        if (userExists != null) {
            return new ResponseEntity<>("This username has already been taken!", HttpStatus.CONFLICT);
        }
        this.userService.saveUser(new User(userDTO));
        Map<String, String> model = new HashMap<>();
        model.put("message", "User registered successfully");
        return ResponseEntity.ok(model);
    }

    @ApiOperation(value = "Deletes a User")
    @DeleteMapping(value = "/{id}")
    public boolean deleteUser(@PathVariable("id") String id) {
        return this.userService.deleteUser(id);
    }
}
