package com.spring.asset_craft.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if (password == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Password is required")
                    .addConstraintViolation();
            return false;
        }

        if (password.length() < 8) {
            context.buildConstraintViolationWithTemplate("Password must be at least 6 characters long")
                    .addConstraintViolation();
            valid = false;
        }

        if (!password.matches(".*[a-z].*")) {
            context.buildConstraintViolationWithTemplate("Password must include at least 1 lowercase letter")
                    .addConstraintViolation();
            valid = false;
        }

        if (!password.matches(".*[A-Z].*")) {
            context.buildConstraintViolationWithTemplate("Password must include at least 1 uppercase letter")
                    .addConstraintViolation();
            valid = false;
        }

        if (!password.matches(".*\\d.*")) {
            context.buildConstraintViolationWithTemplate("Password must contain at least 1 digit")
                    .addConstraintViolation();
            valid = false;
        }

        if (!password.matches(".*[@#$%^&+=!].*")) {
            context.buildConstraintViolationWithTemplate("Password must contain at least 1 special character")
                    .addConstraintViolation();
            valid = false;
        }
        return valid;
    }
}
