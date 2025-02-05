package com.niyiment.fitness.external;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExternalApiSettingRepository extends JpaRepository<ExternalApiSetting, UUID> {
    Optional<ExternalApiSetting> findByApiName(String apiName);
}
