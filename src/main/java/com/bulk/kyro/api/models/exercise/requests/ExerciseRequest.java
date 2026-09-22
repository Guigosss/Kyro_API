package com.bulk.kyro.api.models.exercise.requests;

import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.MuscleGroupEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record ExerciseRequest(
        @NotBlank String name,
        @NotEmpty Set<Integer> muscleGroupIds
) {
    public ExerciseEntity toEntity(Set<MuscleGroupEntity> muscleGroups){
        ExerciseEntity exercise = new ExerciseEntity(name);
        exercise.setMuscleGroups(muscleGroups);
        return exercise;
    }
}
