package com.niyiment.fitness.repository;

import com.niyiment.fitness.entity.Exercise;
import org.springframework.data.jpa.domain.Specification;

public class ExerciseSpecification {

    public static Specification<Exercise> isActive() {
       return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    public static Specification<Exercise> filterByName(String name) {
        return (root, query, cb) -> {
            if (name == null) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
}
