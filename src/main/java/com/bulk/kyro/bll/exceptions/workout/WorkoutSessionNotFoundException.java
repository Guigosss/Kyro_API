package com.bulk.kyro.bll.exceptions.workout;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class WorkoutSessionNotFoundException extends WorkoutException {

    public WorkoutSessionNotFoundException(Integer id) {
        super(HttpStatus.NOT_FOUND, new HashMap<String, String>(
                Map.of("id", "Workout session with id " + id + " does not exist.")
        ));
    }

    public WorkoutSessionNotFoundException(Map<String, String> body) {
        super(HttpStatus.NOT_FOUND, body);
    }
}