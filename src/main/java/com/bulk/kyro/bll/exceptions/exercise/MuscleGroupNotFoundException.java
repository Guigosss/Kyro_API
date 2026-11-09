package com.bulk.kyro.bll.exceptions.exercise;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MuscleGroupNotFoundException extends ExerciseException {

    public MuscleGroupNotFoundException(Set<Integer> missingIds) {
        super(HttpStatus.NOT_FOUND, new HashMap<String, String>(
                Map.of("muscleGroupIds", "Muscle groups not found: " + missingIds)
        ));
    }

    public MuscleGroupNotFoundException(Map<String, String> body) {
        super(HttpStatus.NOT_FOUND, body);
    }
}