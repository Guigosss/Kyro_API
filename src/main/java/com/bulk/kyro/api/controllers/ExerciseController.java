package com.bulk.kyro.api.controllers;

import com.bulk.kyro.api.models.exercise.requests.ExerciseRequest;
import com.bulk.kyro.api.models.exercise.responses.ExerciseResponse;
import com.bulk.kyro.bll.services.ExerciseService;
import com.bulk.kyro.bll.services.MuscleGroupService;
import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.MuscleGroupEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exercise")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final MuscleGroupService muscleGroupService;

    @GetMapping
    public ResponseEntity<Page<ExerciseResponse>> find(
        @RequestParam(name = "page", required = false, defaultValue = "0") int page,
        @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ExerciseEntity> exercises = exerciseService.find(pageable);
        Page<ExerciseResponse> response = exercises.map(ExerciseResponse::fromEntity);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponse> findById(@PathVariable Integer id) {
        ExerciseEntity exercise = exerciseService.findById(id);
        ExerciseResponse response = ExerciseResponse.fromEntity(exercise);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ExerciseRequest exerciseRequest) {
        Set<MuscleGroupEntity> muscleGroups = muscleGroupService.findAllByIds(exerciseRequest.muscleGroupIds());
        ExerciseEntity exercise = exerciseRequest.toEntity(muscleGroups);
        ExerciseEntity response = exerciseService.save(exercise);
        
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Integer id,
            @Valid @RequestBody ExerciseRequest exerciseRequest
    ) {
        Set<MuscleGroupEntity> muscleGroups = muscleGroupService.findAllByIds(exerciseRequest.muscleGroupIds());
        ExerciseEntity exercise = exerciseRequest.toEntity(muscleGroups);
        exerciseService.update(id, exercise);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
