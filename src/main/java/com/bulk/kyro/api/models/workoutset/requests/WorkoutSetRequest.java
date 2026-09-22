package com.bulk.kyro.api.models.workoutset.requests;

import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import com.bulk.kyro.dl.entities.WorkoutSetEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record WorkoutSetRequest(
        @NotNull @PositiveOrZero Double weight,
        @NotNull @Positive Integer reps
) {
    public WorkoutSetEntity toEntity(Integer orderIndex,
                                     WorkoutSessionEntity session,
                                     ExerciseEntity exercise) {
        return new WorkoutSetEntity(weight, reps, orderIndex, session, exercise);
    }
}
