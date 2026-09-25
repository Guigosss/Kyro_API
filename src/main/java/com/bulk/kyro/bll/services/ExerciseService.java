package com.bulk.kyro.bll.services;

import com.bulk.kyro.api.models.exercise.requests.ExerciseRequest;
import com.bulk.kyro.api.models.exercise.responses.ExerciseResponse;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface ExerciseService {

    Page<ExerciseResponse> find(Pageable pageable);
    ExerciseResponse findById(Integer id);
    ExerciseResponse save(ExerciseRequest exercise);
    ExerciseResponse update(Integer id, ExerciseRequest exerciseRequest);
    void delete(Integer id);
}
