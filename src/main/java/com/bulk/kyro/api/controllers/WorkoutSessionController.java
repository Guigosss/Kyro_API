package com.bulk.kyro.api.controllers;

import com.bulk.kyro.api.models.workoutsession.requests.WorkoutSessionRequest;
import com.bulk.kyro.bll.services.WorkoutSessionService;
import com.bulk.kyro.dl.entities.UserEntity;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Void> save(
            @Valid @RequestBody WorkoutSessionRequest workoutSessionRequest,
            @AuthenticationPrincipal UserEntity user
    ) {
        WorkoutSessionEntity workoutSession = workoutSessionRequest.toEntity(user);

        WorkoutSessionEntity response = workoutSessionService.createSession(workoutSession, workoutSessionRequest.exercises());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
}
