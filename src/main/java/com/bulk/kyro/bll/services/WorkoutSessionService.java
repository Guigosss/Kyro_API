package com.bulk.kyro.bll.services;

import com.bulk.kyro.api.models.exerciseworkout.requests.ExerciseWorkoutRequest;
import com.bulk.kyro.api.models.workoutsession.responses.WorkoutSessionResponse;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkoutSessionService {

    Page<WorkoutSessionEntity> findByUser(Integer userId, Pageable pageable);
    Page<WorkoutSessionResponse> getSessions(Page<WorkoutSessionEntity> workoutSessions);
    WorkoutSessionEntity createSession(WorkoutSessionEntity session, List<ExerciseWorkoutRequest> exercises);
    void deleteSession(Integer id, Integer userId);
}
