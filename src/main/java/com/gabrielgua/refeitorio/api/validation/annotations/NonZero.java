package com.gabrielgua.refeitorio.api.validation.annotations;

import com.gabrielgua.refeitorio.api.validation.validators.NonZeroValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NonZeroValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NonZero {
    String message() default "Value must not be zero.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}