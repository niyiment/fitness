package com.niyiment.fitness.rest;

import com.niyiment.fitness.dto.ProgressDTO;
import com.niyiment.fitness.dto.ProgressResponseDTO;
import com.niyiment.fitness.service.ProgressService;
import com.niyiment.fitness.utility.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/progress")
public class ProgressResource {
    private final ProgressService service;

    /**
     * Retrieve all active progress records with optional filtering and pagination
     * @param minWeight optional minimum weight filter
     * @param maxWeight optional maximum weight filter
     * @param startDate optional start date filter
     * @param endDate optional end date filter
     * @param page optional page
     * @param size page size
     * @param sortBy optional sort by weight
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ProgressResponseDTO>>> getAllProgress(
            @RequestParam(required = false) Double minWeight, @RequestParam(required = false) Double maxWeight,
            @RequestParam(required = false) LocalDate startDate, @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "weight") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, sortBy);
        Page<ProgressResponseDTO> result = service.getAllProgress(minWeight, maxWeight, startDate, endDate, pageRequest);
        return ResponseEntity.ok(ApiResponse.success("Progress retrieved successfully", result));
    }

    /**
     * Retrieve progress by ID
     * @param id progress identifier
     * @return custom API response of ProgressResponseDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProgressResponseDTO>> getProgressById(@PathVariable UUID id) {
        ProgressResponseDTO result = service.getProgressById(id);
        return ResponseEntity.ok(ApiResponse.success("Progress retrieved successfully", result));
    }

    /**
     * Record progress
     * @param dto Progress request dto
     * @return custom API response of ProgressResponseDTO
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProgressResponseDTO>> saveProgress(@Valid @RequestBody ProgressDTO dto) {
        ProgressResponseDTO result = service.saveProgress(dto);
        return ResponseEntity.ok(ApiResponse.success("Progress saved successfully", result));
    }

    /**
     * Update progress by ID
     * @param id progress identifier
     * @param dto progress request dto
     * @return custom API response of ProgressResponseDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProgressResponseDTO>> updateProgress(@PathVariable UUID id, @RequestBody ProgressDTO dto) {
        ProgressResponseDTO result = service.updateProgress(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Progress updated successfully", result));
    }


}
