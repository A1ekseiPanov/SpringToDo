package com.emobile.springtodo.core.annotation;

import com.emobile.springtodo.core.util.validation.CheckValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckValidator.class)
@Documented
public @interface CheckStatus {
    String message() default "does not correspond to statuses";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}