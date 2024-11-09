package com.emobile.springtodo.core.util.validation;

import com.emobile.springtodo.core.annotation.CheckStatus;
import com.emobile.springtodo.core.entity.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class CheckValidator implements ConstraintValidator<CheckStatus, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.stream(Status.values()).anyMatch(x -> x.toString().equals(value));
    }
}