package com.niyiment.fitness.repository;


import com.niyiment.fitness.entity.Workout;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;


public class WorkoutSpecification {

    public static Specification<Workout> isActive() {
        return (root, query, cb) -> {
            return cb.isTrue(root.get("active"));
        };
    }

    public static Specification<Workout> filterByWorkoutType(String workflowType) {
        return (root, query, cb) -> {
            return cb.equal(root.get("workoutType"), workflowType);
        };
    }

    public static Specification<Workout> filterByWorkoutDate(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            return cb.between(root.get("workoutDate"), startDate, endDate);
        };
    }
}
