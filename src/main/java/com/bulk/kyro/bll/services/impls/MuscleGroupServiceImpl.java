package com.bulk.kyro.bll.services.impls;

import com.bulk.kyro.bll.services.MuscleGroupService;
import com.bulk.kyro.dal.repositories.MuscleGroupRepository;
import com.bulk.kyro.dl.entities.MuscleGroupEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MuscleGroupServiceImpl implements MuscleGroupService {

    private final MuscleGroupRepository muscleGroupRepository;

    @Override
    public Set<MuscleGroupEntity> findAllByIds(Set<Integer> muscleGroupIds) {
        return new HashSet<>(muscleGroupRepository.findAllById(muscleGroupIds));
    }
}
