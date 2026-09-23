package com.bulk.kyro.bll.services;

import com.bulk.kyro.dl.entities.MuscleGroupEntity;

import java.util.Set;

public interface MuscleGroupService {

    Set<MuscleGroupEntity> findAllByIds(Set<Integer> muscleGroupIds);
}
