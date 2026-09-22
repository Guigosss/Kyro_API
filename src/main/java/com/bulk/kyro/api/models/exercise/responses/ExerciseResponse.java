package com.bulk.kyro.api.models.exercise.responses;

import com.bulk.kyro.dl.entities.ExerciseEntity;

public record ExerciseResponse(
        Integer id,
        String name
) {
    public static ExerciseResponse fromEntity(ExerciseEntity exercise) {
        return new ExerciseResponse(
                exercise.getId(),
                exercise.getName()
        );
    }
}
