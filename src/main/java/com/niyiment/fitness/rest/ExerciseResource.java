package com.niyiment.fitness.rest;

import com.niyiment.fitness.dto.ExerciseDTO;
import com.niyiment.fitness.dto.ExerciseResponseDTO;
import com.niyiment.fitness.service.ExerciseService;
import com.niyiment.fitness.utility.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exercises")
public class ExerciseResource {
    private final ExerciseService exerciseService;

    /**
     * Retrieve the exercise with pagination and optional filter by name
     *
     * @param name   the name of the exercise
     * @param page   the page to retrieve
     * @param size   page size
     * @param sortBy sort by name
     * @return Paginated exercise response dto
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ExerciseResponseDTO>>> getAllExercise(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, sortBy);
        Page<ExerciseResponseDTO> exercisePage = exerciseService.getAllExercises(name, pageRequest);
        return ResponseEntity.ok(ApiResponse.success("Exercises retrieved successfully", exercisePage));
    }

    /**
     * Retrieve exercise by id
     *
     * @param id exercise identifier
     * @return exercise information
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExerciseResponseDTO>> getExerciseById(@PathVariable UUID id) {
        ExerciseResponseDTO exercise = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(ApiResponse.success("Exercise retrieved successfully", exercise));
    }

    /**
     * Create new exercise
     *
     * @param dto exercise information
     * @return exercise information with created id
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ExerciseResponseDTO>> createExercise(@Valid @RequestBody ExerciseDTO dto) {
        ExerciseResponseDTO createdExercise = exerciseService.createExercise(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Exercise created successfully", createdExercise));
    }

    /**
     * Update existing exercise
     *
     * @param id   exercise identifier
     * @param dto   exercise information
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExerciseResponseDTO>> updateExercise(@PathVariable UUID id, @Valid @RequestBody ExerciseDTO dto) {
        ExerciseResponseDTO updatedExercise = exerciseService.updateExercise(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Exercise updated successfully", updatedExercise));
    }

    /**
     * Delete exercise by id
     *
     * @param id   exercise identifier
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExercise(@PathVariable UUID id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.ok(ApiResponse.success("Exercise deleted successfully", null));
    }

}
