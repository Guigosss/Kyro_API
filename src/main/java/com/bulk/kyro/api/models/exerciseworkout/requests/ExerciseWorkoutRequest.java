package com.bulk.kyro.api.models.exerciseworkout.requests;

import com.bulk.kyro.api.models.workoutset.requests.WorkoutSetRequest;
import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import com.bulk.kyro.dl.entities.WorkoutSetEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.IntStream;

public record ExerciseWorkoutRequest(
        @NotNull Integer exerciseId,
        @NotEmpty List<WorkoutSetRequest> sets
) {

    public List<WorkoutSetEntity> toEntities(
            WorkoutSessionEntity session,
            ExerciseEntity exercise
    ) {
        return IntStream.range(0, sets.size())
                .mapToObj(i -> sets.get(i).toEntity(
                        i + 1,
                        session,
                        exercise
                ))
                .toList();
    }
}
