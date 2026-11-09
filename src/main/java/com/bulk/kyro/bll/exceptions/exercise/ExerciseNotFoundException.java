package com.bulk.kyro.bll.exceptions.exercise;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ExerciseNotFoundException extends ExerciseException {

    public ExerciseNotFoundException(Integer id) {
        super(HttpStatus.NOT_FOUND, new HashMap<String, String>(
                Map.of("id", "Exercise with id " + id + " does not exist.")
        ));
    }

    public ExerciseNotFoundException(Map<String, String> body) {
        super(HttpStatus.NOT_FOUND, body);
    }
}