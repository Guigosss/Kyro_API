package com.bulk.kyro.api.controllers;

import com.bulk.kyro.api.models.workoutsession.requests.WorkoutSessionRequest;
import com.bulk.kyro.api.models.workoutsession.responses.WorkoutSessionResponse;
import com.bulk.kyro.bll.services.WorkoutSessionService;
import com.bulk.kyro.dl.entities.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
public class WorkoutSessionController {

    private final WorkoutSessionService workoutSessionService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<Page<WorkoutSessionResponse>> find(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @AuthenticationPrincipal UserEntity user
    ) {
        Page<WorkoutSessionResponse> response = workoutSessionService.findSessions(user.getId(), PageRequest.of(page, size));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<WorkoutSessionResponse> findById(@PathVariable Integer id, @AuthenticationPrincipal UserEntity user) {
        WorkoutSessionResponse response = workoutSessionService.findSession(id, user.getId());
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody WorkoutSessionRequest request, @AuthenticationPrincipal UserEntity user) {
        WorkoutSessionResponse response = workoutSessionService.createSession(request, user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody WorkoutSessionRequest request, @AuthenticationPrincipal UserEntity user) {
        workoutSessionService.updateSession(id, request, user);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id, @AuthenticationPrincipal UserEntity user) {
        workoutSessionService.deleteSession(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}
