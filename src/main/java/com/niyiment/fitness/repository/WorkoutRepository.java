package com.niyiment.fitness.repository;

import com.niyiment.fitness.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, UUID>, JpaSpecificationExecutor<Workout> {
    List<Workout> findByWorkoutType(String workflowType);
}
