package com.bulk.kyro.bll.exceptions.workout;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class EmptyWorkoutSessionException extends WorkoutException {

    public EmptyWorkoutSessionException() {
        super(HttpStatus.BAD_REQUEST, new HashMap<String, String>(
                Map.of("exercises", "A workout session must contain at least one exercise.")
        ));
    }

    public EmptyWorkoutSessionException(Map<String, String> body) {
        super(HttpStatus.BAD_REQUEST, body);
    }
}