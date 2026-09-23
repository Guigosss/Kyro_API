package com.bulk.kyro.bll.services;

import com.bulk.kyro.dl.entities.ExerciseEntity;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface ExerciseService {

    Page<ExerciseEntity> find(Pageable pageable);
    ExerciseEntity findById(Integer id);
    ExerciseEntity save(ExerciseEntity exercise);
    void update(Integer id, ExerciseEntity exercise);
    void delete(Integer id);
}
