package com.bulk.kyro.api.models.exercise.requests;

import com.bulk.kyro.dl.entities.ExerciseEntity;
import jakarta.validation.constraints.NotBlank;

public record ExerciseRequest(
        @NotBlank String name
) {
    public ExerciseEntity toExercise(){
        return new ExerciseEntity(name);
    }
}
