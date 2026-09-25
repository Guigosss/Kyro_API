package com.bulk.kyro.bll.services.impls;

import com.bulk.kyro.api.models.exerciseworkout.requests.ExerciseWorkoutRequest;
import com.bulk.kyro.api.models.exerciseworkout.responses.ExerciseWorkoutResponse;
import com.bulk.kyro.api.models.workoutsession.requests.WorkoutSessionRequest;
import com.bulk.kyro.api.models.workoutsession.responses.WorkoutSessionResponse;
import com.bulk.kyro.bll.services.WorkoutSessionService;
import com.bulk.kyro.bll.services.WorkoutSetService;
import com.bulk.kyro.dal.repositories.ExerciseRepository;
import com.bulk.kyro.dal.repositories.WorkoutSessionRepository;
import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.UserEntity;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import com.bulk.kyro.dl.entities.WorkoutSetEntity;
import jakarta.persistence.EntityNotFoundException;
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
    private final ExerciseRepository exerciseRepository;
    private final WorkoutSetService workoutSetService;

    @Override
    public Page<WorkoutSessionResponse> findSessions(Integer userId, Pageable pageable) {
        return workoutSessionRepository.findByUserId(userId, pageable).map(this::toResponse);
    }

    @Override
    public WorkoutSessionResponse findSession(Integer sessionId, Integer userId) {
        return toResponse(findEntity(sessionId, userId));
    }

    @Override
    @Transactional
    public WorkoutSessionResponse createSession(WorkoutSessionRequest request, UserEntity user) {
        WorkoutSessionEntity savedSession = workoutSessionRepository.save(request.toEntity(user));
        saveExerciseWorkouts(savedSession, request.exercises());
        return toResponse(savedSession);
    }

    @Override
    @Transactional
    public void updateSession(Integer sessionId, WorkoutSessionRequest request, UserEntity user) {
        WorkoutSessionEntity existing = findEntity(sessionId, user.getId());

        existing.setName(request.name());
        existing.setDate(request.date());
        existing.setNotes(request.notes());

        workoutSetService.deleteAllBySessionId(sessionId);
        saveExerciseWorkouts(existing, request.exercises());
    }

    @Override
    public void deleteSession(Integer id, Integer userId) {
        if (!workoutSessionRepository.existsByIdAndUserId(id, userId)) {
            throw new EntityNotFoundException("Workout session with id " + id + " does not exist.");
        }
        workoutSessionRepository.deleteById(id);
    }

    private WorkoutSessionEntity findEntity(Integer sessionId, Integer userId) {
        WorkoutSessionEntity session = workoutSessionRepository.findByIdAndUserId(sessionId, userId);
        if (session == null) {
            throw new EntityNotFoundException("Workout session with id " + sessionId + " does not exist.");
        }
        return session;
    }

    private WorkoutSessionResponse toResponse(WorkoutSessionEntity session) {
        List<WorkoutSetEntity> sets = workoutSetService.findBySessionId(session.getId());
        Map<ExerciseEntity, List<WorkoutSetEntity>> byExercise = sets.stream()
                .collect(Collectors.groupingBy(WorkoutSetEntity::getExercise));

        List<ExerciseWorkoutResponse> exercises = byExercise.entrySet().stream()
                .map(e -> ExerciseWorkoutResponse.fromEntity(e.getKey(), e.getValue()))
                .toList();

        return WorkoutSessionResponse.fromEntity(session, exercises);
    }

    private void saveExerciseWorkouts(WorkoutSessionEntity session, List<ExerciseWorkoutRequest> exercises) {
        List<WorkoutSetEntity> sets = new ArrayList<>();
        for (ExerciseWorkoutRequest ew : exercises) {
            ExerciseEntity exercise = exerciseRepository.findById(ew.exerciseId()).orElseThrow();
            sets.addAll(ew.toEntities(session, exercise));
        }
        workoutSetService.saveAll(sets);
    }
}
