package com.bulk.kyro.bll.exceptions.workout;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class WorkoutSetNotFoundException extends WorkoutException {

    public WorkoutSetNotFoundException(Integer id) {
        super(HttpStatus.NOT_FOUND, new HashMap<String, String>(
                Map.of("sets", "Workout set with id " + id + " does not exist in this session.")
        ));
    }

    public WorkoutSetNotFoundException(Map<String, String> body) {
        super(HttpStatus.NOT_FOUND, body);
    }
}