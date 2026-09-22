package com.bulk.kyro.api.models.exerciseworkout.responses;

import com.bulk.kyro.api.models.workoutset.responses.WorkoutSetResponse;
import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.WorkoutSetEntity;

import java.util.List;

public record ExerciseWorkoutResponse(
        Integer exerciseId,
        String exerciseName,
        List<WorkoutSetResponse> sets
) {
    public static ExerciseWorkoutResponse fromEntity(ExerciseEntity exercise,
                                                     List<WorkoutSetEntity> sets) {

        List<WorkoutSetResponse> setsResponses = sets.stream()
                .map(WorkoutSetResponse::fromEntity)
                .toList();

        return new ExerciseWorkoutResponse(
                exercise.getId(),
                exercise.getName(),
                setsResponses
        );
    }
}
