package com.bulk.kyro.dal.repositories;

import com.bulk.kyro.dl.entities.WorkoutSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSessionEntity, Integer> {
}
