package com.bulk.kyro.bll.services;

import com.bulk.kyro.api.models.exerciseworkout.requests.ExerciseWorkoutRequest;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;

import java.util.List;

public interface WorkoutSessionService {

    WorkoutSessionEntity createSession(WorkoutSessionEntity session, List<ExerciseWorkoutRequest> exercises);
}
