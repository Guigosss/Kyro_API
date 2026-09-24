package com.bulk.kyro.bll.services.impls;

import com.bulk.kyro.api.models.exerciseworkout.requests.ExerciseWorkoutRequest;
import com.bulk.kyro.bll.services.ExerciseService;
import com.bulk.kyro.bll.services.WorkoutSessionService;
import com.bulk.kyro.bll.services.WorkoutSetService;
import com.bulk.kyro.dal.repositories.WorkoutSessionRepository;
import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import com.bulk.kyro.dl.entities.WorkoutSetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutSessionServiceImpl implements WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final ExerciseService exerciseService;
    private final WorkoutSetService workoutSetService;

    @Override
    @Transactional
    public WorkoutSessionEntity createSession(WorkoutSessionEntity session, List<ExerciseWorkoutRequest> exercises) {

        WorkoutSessionEntity savedSession = workoutSessionRepository.save(session);

        List<WorkoutSetEntity> sets = new ArrayList<>();

        for (ExerciseWorkoutRequest exerciseWorkout : exercises) {
            ExerciseEntity exercise = exerciseService.findById(exerciseWorkout.exerciseId());

            sets.addAll(exerciseWorkout.toEntities(session, exercise));
        }
        workoutSetService.saveAll(sets);

        return savedSession;
    }
}
