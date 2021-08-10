package com.tkachuk.library.model;

import java.util.Set;

import com.tkachuk.library.dto.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true, direction = IndexDirection.DESCENDING)
    private String username;
    private String password;
    private boolean enabled;

    @DBRef
    private Set<Role> roles;

    public User() {
    }


    public User(UserDto userDTO) {
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
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


    public Set<Role> getRoles() {
        return this.roles;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
