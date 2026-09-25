package com.bulk.kyro.bll.services;

import com.bulk.kyro.api.models.workoutsession.requests.WorkoutSessionRequest;
import com.bulk.kyro.api.models.workoutsession.responses.WorkoutSessionResponse;
import com.bulk.kyro.dl.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkoutSessionService {
    Page<WorkoutSessionResponse> findSessions(Integer userId, Pageable pageable);
    WorkoutSessionResponse findSession(Integer sessionId, Integer userId);
    WorkoutSessionResponse createSession(WorkoutSessionRequest request, UserEntity user);
    void updateSession(Integer sessionId, WorkoutSessionRequest request, UserEntity user);
    void deleteSession(Integer id, Integer userId);
}
