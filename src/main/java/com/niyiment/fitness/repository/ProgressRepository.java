package com.niyiment.fitness.repository;

import com.niyiment.fitness.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface ProgressRepository extends JpaRepository<Progress, UUID>, JpaSpecificationExecutor<Progress> {
}