package com.niyiment.fitness.service;

import com.niyiment.fitness.dto.ProgressDTO;
import com.niyiment.fitness.dto.ProgressResponseDTO;
import com.niyiment.fitness.entity.Progress;
import com.niyiment.fitness.exception.ResourceNotFoundException;
import com.niyiment.fitness.repository.ProgressRepository;
import com.niyiment.fitness.repository.ProgressSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProgressService {
    private final ProgressRepository repository;
    private static final String RECORD_NOT_FOUND = "Record not found";

    public Page<ProgressResponseDTO> getAllProgress(Double minWeight, Double maxWeight,
                                                    LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Specification<Progress> spec = ProgressSpecification.isActive();
        if (minWeight != null && maxWeight != null) {
            spec.and(ProgressSpecification.filterByWeight(minWeight, maxWeight));
        }
        if (startDate != null && endDate != null) {
            spec.and(ProgressSpecification.filterByDate(startDate, endDate));
        }

        Page<Progress> progress = repository.findAll(spec, pageable);
        return  progress.map(this::toDTO);
    }

    public ProgressResponseDTO getProgressById(UUID id) {
        Progress progress = repository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        return toDTO(progress);
    }

    public ProgressResponseDTO saveProgress(ProgressDTO dto) {
        Progress progress = new Progress();
        toEntity(progress, dto);

        return toDTO(repository.save(progress));
    }

    public ProgressResponseDTO updateProgress(UUID id, ProgressDTO dto) {
        Progress progress = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        toEntity(progress, dto);

        return toDTO(repository.save(progress));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private ProgressResponseDTO toDTO(Progress progress) {
        ProgressResponseDTO result = new ProgressResponseDTO();
        result.setId(progress.getId());
        result.setBodyFat(progress.getBodyFat());
        result.setDate(progress.getDate());
        result.setWeight(progress.getWeight());
        result.setActive(progress.isActive());
        result.setCreatedAt(progress.getCreatedAt());
        result.setUpdatedAt(progress.getUpdatedAt());

        return  result;
    }

    private void toEntity(Progress progress, ProgressDTO dto) {
        progress.setBodyFat(dto.getBodyFat());
        progress.setDate(dto.getDate());
        progress.setWeight(dto.getWeight());
    }
}