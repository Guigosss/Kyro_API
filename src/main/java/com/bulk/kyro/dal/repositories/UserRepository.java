package com.bulk.kyro.dal.repositories;

import com.bulk.kyro.dl.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);

    @Query("select u from UserEntity u join fetch u.role where u.username ilike :username")
    Optional<UserEntity> findByUsername(String username);
}
