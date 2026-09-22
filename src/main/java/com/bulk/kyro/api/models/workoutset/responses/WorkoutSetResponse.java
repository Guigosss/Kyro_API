package com.bulk.kyro.api.models.workoutset.responses;

import com.bulk.kyro.dl.entities.WorkoutSetEntity;

public record WorkoutSetResponse(
        Integer id,
        Double weight,
        Integer reps,
        Integer orderIndex
) {
    public static WorkoutSetResponse fromEntity(WorkoutSetEntity workoutSet) {
        return new WorkoutSetResponse(
          workoutSet.getId(),
          workoutSet.getWeight(),
          workoutSet.getReps(),
          workoutSet.getOrderIndex()
        );
    }
}
