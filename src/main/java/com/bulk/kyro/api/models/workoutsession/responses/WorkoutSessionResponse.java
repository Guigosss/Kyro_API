package com.bulk.kyro.api.models.workoutsession.responses;

import com.bulk.kyro.api.models.exerciseworkout.responses.ExerciseWorkoutResponse;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;

import java.time.LocalDate;
import java.util.List;

public record WorkoutSessionResponse(
        Integer id,
        String nom,
        LocalDate date,
        String notes,
        Integer userId,
        List<ExerciseWorkoutResponse> exercises
) {
    public static WorkoutSessionResponse fromEntity(WorkoutSessionEntity workoutSession,
                                                    List<ExerciseWorkoutResponse> exercises) {
        return new WorkoutSessionResponse(
          workoutSession.getId(),
          workoutSession.getName(),
          workoutSession.getDate(),
          workoutSession.getNotes(),
          workoutSession.getUser().getId(),
          exercises
        );
    }
}
