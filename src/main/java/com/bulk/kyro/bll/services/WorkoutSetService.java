package com.bulk.kyro.bll.services;

import com.bulk.kyro.dl.entities.WorkoutSetEntity;

import java.util.List;

public interface WorkoutSetService {

    void saveAll(List<WorkoutSetEntity> sets);
    List<WorkoutSetEntity> findBySessionId(Integer sessionId);
    void deleteAllBySessionId(Integer sessionId);
}
