package com.emobile.springtodo.core.util.validation;

import com.emobile.springtodo.core.annotation.UniqTitle;
import com.emobile.springtodo.core.dao.Dao;
import com.emobile.springtodo.core.entity.Task;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqValid implements ConstraintValidator<UniqTitle, String> {
    @Autowired
    private Dao<Task, Long> dao;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return dao.findByTitle(value).isEmpty();
    }
}