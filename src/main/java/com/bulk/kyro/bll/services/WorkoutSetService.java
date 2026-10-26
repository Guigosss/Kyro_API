package com.bulk.kyro.bll.services;

import com.bulk.kyro.dl.entities.WorkoutSetEntity;

import java.util.List;

public interface WorkoutSetService {

    void save(WorkoutSetEntity set);
    List<WorkoutSetEntity> findBySessionId(Integer sessionId);
    void deleteAll(List<WorkoutSetEntity> sets);
}
