package com.bulk.kyro.bll.exceptions.workout;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class DuplicateExerciseInSessionException extends WorkoutException {

    public DuplicateExerciseInSessionException(Integer exerciseId) {
        super(HttpStatus.CONFLICT, new HashMap<String, String>(
                Map.of("exercises", "Exercise with id " + exerciseId + " appears more than once in the session.")
        ));
    }

    public DuplicateExerciseInSessionException(Map<String, String> body) {
        super(HttpStatus.CONFLICT, body);
    }
}