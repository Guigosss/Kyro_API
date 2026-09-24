package com.bulk.kyro.api.controllers;

import com.bulk.kyro.api.models.workoutsession.requests.WorkoutSessionRequest;
import com.bulk.kyro.api.models.workoutsession.responses.WorkoutSessionResponse;
import com.bulk.kyro.bll.services.WorkoutSessionService;
import com.bulk.kyro.dl.entities.UserEntity;
import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Page<WorkoutSessionResponse>> findByUser(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @AuthenticationPrincipal UserEntity user
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<WorkoutSessionEntity> workoutSessions = workoutSessionService.findByUser(user.getId(), pageable);

        Page<WorkoutSessionResponse> response = workoutSessionService.getSessions(workoutSessions);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserEntity user
    ) {
        workoutSessionService.deleteSession(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
