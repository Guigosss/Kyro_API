package com.bulk.kyro.bll.services.impls;

import com.bulk.kyro.api.models.exercise.requests.ExerciseRequest;
import com.bulk.kyro.api.models.exercise.responses.ExerciseResponse;
import com.bulk.kyro.bll.services.ExerciseService;
import com.bulk.kyro.bll.services.MuscleGroupService;
import com.bulk.kyro.dal.repositories.ExerciseRepository;
import com.bulk.kyro.dl.entities.ExerciseEntity;
import com.bulk.kyro.dl.entities.MuscleGroupEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupService muscleGroupService;

    @Override
    public Page<ExerciseResponse> find(Pageable pageable) {
        return exerciseRepository.findAll(pageable).map(ExerciseResponse::fromEntity);
    }

    @Override
    public ExerciseResponse findById(Integer id) {
        return ExerciseResponse.fromEntity(findEntityById(id));
    }

    @Override
    @Transactional
    public ExerciseResponse save(ExerciseRequest request) {
        Set<MuscleGroupEntity> muscleGroups = muscleGroupService.findAllByIds(request.muscleGroupIds());
        ExerciseEntity saved = exerciseRepository.save(request.toEntity(muscleGroups));
        return ExerciseResponse.fromEntity(saved);
    }

    @Override
    @Transactional
    public ExerciseResponse update(Integer id, ExerciseRequest request) {
        ExerciseEntity existing = findEntityById(id);
        Set<MuscleGroupEntity> muscleGroups = muscleGroupService.findAllByIds(request.muscleGroupIds());

        existing.setName(request.name());
        existing.setMuscleGroups(muscleGroups);

        return ExerciseResponse.fromEntity(existing);
    }

    @Override
    public void delete(Integer id) {
        if (!exerciseRepository.existsById(id)) {
            throw new EntityNotFoundException("Exercise with id " + id + " does not exist.");
        }
        exerciseRepository.deleteById(id);
    }

    private ExerciseEntity findEntityById(Integer id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercise with id " + id + " does not exist."));
    }
}
