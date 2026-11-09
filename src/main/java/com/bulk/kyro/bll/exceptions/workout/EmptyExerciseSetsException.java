package com.bulk.kyro.bll.exceptions.workout;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class EmptyExerciseSetsException extends WorkoutException {

    public EmptyExerciseSetsException(Integer exerciseId) {
        super(HttpStatus.BAD_REQUEST, new HashMap<String, String>(
                Map.of("sets", "Exercise with id " + exerciseId + " must contain at least one set.")
        ));
    }

    public EmptyExerciseSetsException(Map<String, String> body) {
        super(HttpStatus.BAD_REQUEST, body);
    }
}