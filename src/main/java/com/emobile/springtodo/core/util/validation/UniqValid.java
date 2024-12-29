package com.emobile.springtodo.core.util.validation;

import com.emobile.springtodo.core.annotation.UniqTitle;
import com.emobile.springtodo.core.repository.TaskRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqValid implements ConstraintValidator<UniqTitle, String> {
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return taskRepository.findByTitle(value).isEmpty();
    }
}