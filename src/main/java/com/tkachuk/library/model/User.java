package com.tkachuk.library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String username;
    private String password;
    private boolean enabled;

    private String role;

    public User() {
    }

    public User(String username, String encode) {
        this.username = username;
        this.password = encode;
    }

    public User(String username, String encode, String role, Boolean enabled) {
        this.username = username;
        this.password = encode;
        this.role = role;
        this.enabled = enabled;
    }

    public String getId() {
        return this.id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public String getUsername() {
        return this.username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return this.password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public boolean isEnabled() {
        return this.enabled;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    public String getRole() {
        return this.role;
    }


    public void setRole(String role) {
        this.role = role;
    }
}
