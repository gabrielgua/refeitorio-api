package com.gabrielgua.refeitorio.api.validation.validators;

import com.gabrielgua.refeitorio.api.validation.annotations.NonZero;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class NonZeroValidator implements ConstraintValidator<NonZero, BigDecimal> {

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value.compareTo(BigDecimal.ZERO) != 0;
    }
}