package com.bulk.kyro.bll.services.impls;

import com.bulk.kyro.bll.services.WorkoutSetService;
import com.bulk.kyro.dal.repositories.WorkoutSetRepository;
import com.bulk.kyro.dl.entities.WorkoutSetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutSetServiceImpl implements WorkoutSetService {

    private final WorkoutSetRepository workoutSetRepository;

    @Override
    public void saveAll(List<WorkoutSetEntity> sets) {
        workoutSetRepository.saveAll(sets);
    }

    @Override
    public List<WorkoutSetEntity> findBySessionId(Integer sessionId) {
        return workoutSetRepository.findBySessionId(sessionId);
    }

    @Override
    public void deleteAllBySessionId(Integer sessionId) {
        workoutSetRepository.deleteById(sessionId);
    }
}
