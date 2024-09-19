package com.spring.asset_craft.dto;

import com.spring.asset_craft.security.FieldMatch;
import com.spring.asset_craft.security.ValidPassword;

@FieldMatch(first = "newPassword", second = "confirmPassword", message = "Passwords do not match")
public class PasswordForm {

    private String oldPassword;
    @ValidPassword
    private String newPassword;
    private String confirmPassword;
    public PasswordForm() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
