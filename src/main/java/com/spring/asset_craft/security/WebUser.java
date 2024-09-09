package com.spring.asset_craft.security;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WebUser {

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    private String username;

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    private String email;
    @NotNull(message="is required")
    @Size(min=1, message="is required")
    private String password;


    public WebUser() {
    }

    public WebUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public WebUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
