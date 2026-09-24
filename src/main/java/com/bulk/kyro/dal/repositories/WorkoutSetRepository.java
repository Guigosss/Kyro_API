package com.bulk.kyro.dal.repositories;

import com.bulk.kyro.dl.entities.WorkoutSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutSetRepository extends JpaRepository<WorkoutSetEntity, Integer> {

    List<WorkoutSetEntity> findBySessionId(Integer sessionId);
}
