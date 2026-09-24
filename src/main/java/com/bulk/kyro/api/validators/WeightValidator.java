package com.bulk.kyro.api.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WeightValidator implements ConstraintValidator<MinMaxWeight, Double> {

    private double min;
    private double max;

    @Override
    public void initialize(MinMaxWeight constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Double weight, ConstraintValidatorContext context) {
        if (weight == null) {
            return true;
        }

        return weight >= min && weight <= max;
    }
}
