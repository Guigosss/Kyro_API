package com.bulk.kyro.bll.services.impls;

import com.bulk.kyro.api.models.exerciseworkout.requests.ExerciseWorkoutRequest;
import com.bulk.kyro.api.models.exerciseworkout.responses.ExerciseWorkoutResponse;
import com.bulk.kyro.api.models.workoutsession.responses.WorkoutSessionResponse;
import com.bulk.kyro.bll.services.ExerciseService;
import com.bulk.kyro.bll.services.WorkoutSessionService;
import com.bulk.kyro.bll.services.WorkoutSetService;
import com.bulk.kyro.dal.repositories.WorkoutSessionRepository;
import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import com.bulk.kyro.dl.entities.WorkoutSetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutSessionServiceImpl implements WorkoutSessionService {

    private final WorkoutSessionRepository workoutSessionRepository;
    private final ExerciseService exerciseService;
    private final WorkoutSetService workoutSetService;

    @Override
    public Page<WorkoutSessionEntity> findByUser(Integer userId, Pageable pageable) {
        return workoutSessionRepository.findByUserId(userId, pageable);
    }

    @Override
    public Page<WorkoutSessionResponse> getSessions(Page<WorkoutSessionEntity> workoutSessions) {
        return workoutSessions.map(this::toResponse);
    }

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

    @Override
    public void deleteSession(Integer id, Integer userId) {
        if (!workoutSessionRepository.existsByIdAndUserId(id, userId)) {
            throw new RuntimeException("Workout session with id " + id + " does not exists.");
        }
        workoutSessionRepository.deleteById(id);
    }

    private WorkoutSessionResponse toResponse(WorkoutSessionEntity session) {

        List<WorkoutSetEntity> sets = workoutSetService.findBySessionId(session.getId());

        //- Bench → [Set 1, Set 2]
        //- Squat → [Set 3, Set 4]
        Map<ExerciseEntity, List<WorkoutSetEntity>> setsByExercise = sets.stream()
                        .collect(Collectors.groupingBy(WorkoutSetEntity::getExercise));

        List<ExerciseWorkoutResponse> exercises = setsByExercise.entrySet()
                        .stream()
                        .map(entry -> ExerciseWorkoutResponse.fromEntity(
                                entry.getKey(),
                                entry.getValue()
                        ))
                        .toList();

        return WorkoutSessionResponse.fromEntity(session, exercises);
    }
}
