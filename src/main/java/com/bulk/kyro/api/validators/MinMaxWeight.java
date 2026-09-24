package com.bulk.kyro.api.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = WeightValidator.class)
public @interface MinMaxWeight {

    double min() default 0;

    double max() default 500;

    String message() default "Weight must be between {min} and {max} kg";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
