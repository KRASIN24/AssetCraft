package com.spring.asset_craft.security;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;


    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        try {
            BeanWrapper beanWrapper = new BeanWrapperImpl(value);
            Object firstValue = beanWrapper.getPropertyValue(firstFieldName);
            Object secondValue = beanWrapper.getPropertyValue(secondFieldName);
            boolean isValid = firstValue != null && firstValue.equals(secondValue);

            if (!isValid) {
                // Disable default constraint violation message
                context.disableDefaultConstraintViolation();
                // Add the validation error to the second field (confirmPassword)
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode(secondFieldName)
                        .addConstraintViolation();
            }

            return isValid;
        }catch (Exception e){
            return false;
        }
    }
}
