package com.emobile.springtodo.core.annotation;

import com.emobile.springtodo.core.util.validation.UniqValid;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqValid.class)
@Documented
public @interface UniqTitle {
    String message() default "task with same title already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}