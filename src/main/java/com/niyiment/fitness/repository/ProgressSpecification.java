package com.niyiment.fitness.repository;

import com.niyiment.fitness.entity.Progress;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ProgressSpecification {

    public static Specification<Progress> isActive() {
        return (root, query, cb) -> cb.isTrue(root.get("active"));
    }

    public static Specification<Progress> filterByWeight(Double minWeight, Double maxWeight) {
        return (root, query, cb) -> {
            return cb.between(root.get("weight"), minWeight, maxWeight);
        };
    }

    public static Specification<Progress> filterByDate(LocalDate startDate, LocalDate endDate) {
        return (root, query, cb) -> {
            return cb.between(root.get("date"), startDate, endDate);
        };
    }
}
