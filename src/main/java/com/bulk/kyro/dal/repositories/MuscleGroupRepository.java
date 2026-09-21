package com.bulk.kyro.dal.repositories;

import com.bulk.kyro.dl.entities.MuscleGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroupEntity, Integer> {
}
