package com.bulk.kyro.bll.services.impls;

import com.bulk.kyro.bll.services.ExerciseService;
import com.bulk.kyro.dal.repositories.ExerciseRepository;
import com.bulk.kyro.dl.entities.ExerciseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    @Override
    public Page<ExerciseEntity> find(Pageable pageable) {
        return exerciseRepository.findAll(pageable);
    }

    @Override
    public ExerciseEntity findById(Integer id) {
        return exerciseRepository.findById(id).orElseThrow();
    }

    @Override
    public ExerciseEntity save(ExerciseEntity exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public void update(Integer id, ExerciseEntity exercise) {
        ExerciseEntity existing = findById(id);
        existing.setName(exercise.getName());
        existing.setMuscleGroups(exercise.getMuscleGroups());
        exerciseRepository.save(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!exerciseRepository.existsById(id)){
            throw new RuntimeException("Exercise with id " + id + " does not exists.");
        }
        exerciseRepository.deleteById(id);
    }
}
