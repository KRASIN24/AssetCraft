package com.spring.asset_craft.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords do not match")
public class WebUser {

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    private String username;

    @NotNull(message="is required")
    @Size(min=1, message="is required")
    private String email;

//    @Size(min=1, message="is required")
    //@NotBlank(message="is required")
    @ValidPassword
    private String password;

    //@NotNull(message="is required")
    private String confirmPassword;


    public WebUser() {
    }

    public WebUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public WebUser(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
