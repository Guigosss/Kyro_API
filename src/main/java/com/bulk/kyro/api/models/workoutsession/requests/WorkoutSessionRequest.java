package com.bulk.kyro.api.models.workoutsession.requests;

import com.bulk.kyro.api.models.workoutset.requests.WorkoutSetRequest;
import com.bulk.kyro.dl.entities.UserEntity;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record WorkoutSessionRequest(
        @NotBlank String name,
        @NotNull LocalDate date,
        @Size(max = 500) String notes,
        List<WorkoutSetRequest> sets //- WorkoutSetForm
) {
    public WorkoutSessionEntity toEntity(UserEntity user){
        return new WorkoutSessionEntity(name, date, notes, user);
    }
}
