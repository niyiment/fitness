package com.niyiment.fitness.rest;


import com.niyiment.fitness.dto.WorkoutDTO;
import com.niyiment.fitness.dto.WorkoutResponseDTO;
import com.niyiment.fitness.service.WorkoutService;
import com.niyiment.fitness.utility.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@RequestMapping("/api/v1/workouts")
@RestController
@RequiredArgsConstructor
public class WorkoutResource {
    private final WorkoutService workoutService;

    /**
     * Endpoint to retrieve workoutResponseDTO with optional filtering and pagination
     * @param workoutType optional workout type filter
     * @param startDate optional LocalDateTime of startDate
     * @param endDate optional LocalDateTime of endDate
     * @param page Page number (0 - based)
     * @param size Page size
     * @param sortBy Property to sort by
     * @return A page of workouts wrapped in the API response
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<WorkoutResponseDTO>>> getAllWorkouts(
            @RequestParam(required = false) String workoutType,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "workoutDate") String sortBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, sortBy);
        Page<WorkoutResponseDTO> workoutsPage = workoutService.getAllWorkouts(workoutType,
                startDate, endDate, pageRequest);

        return ResponseEntity.ok(ApiResponse.success("Workouts retrieved successfully", workoutsPage));
    }

    /**
     *
     * @param id workout identifier
     * @return The workout details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutResponseDTO>> getWorkoutById(@PathVariable UUID id) {
        WorkoutResponseDTO workout = workoutService.getWorkoutById(id);
        return ResponseEntity.ok(ApiResponse.success("Workout retrieved successfully", workout));
    }

    /**
     * Endpoint to log a new workout
     * @param dto WorkoutDTO
     * @return Custom API response with the created workout
     */
    @PostMapping
    public ResponseEntity<ApiResponse<WorkoutResponseDTO>> createWorkout(@Valid @RequestBody WorkoutDTO dto) {
        WorkoutResponseDTO createdWorkout = workoutService.createWorkout(dto);

        return new ResponseEntity<>( ApiResponse.success(
                "Workout created successfully",
                createdWorkout), HttpStatus.CREATED
        );
    }

    /**
     * Endpoint to delete a workout
     * @param id workout identifier
     * @return Custom API response with a success message
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkoutResponseDTO>> updateWorkout(@PathVariable UUID id,
            @RequestBody WorkoutDTO dto) {
        WorkoutResponseDTO updatedWorkout = workoutService.updateWorkout(id, dto);

        return ResponseEntity.ok(ApiResponse.success("Workout updated successfully", updatedWorkout));
    }

}
