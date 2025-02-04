package com.niyiment.fitness.service;


import com.niyiment.fitness.dto.WorkoutDTO;
import com.niyiment.fitness.dto.WorkoutResponseDTO;
import com.niyiment.fitness.entity.Workout;
import com.niyiment.fitness.exception.ResourceNotFoundException;
import com.niyiment.fitness.repository.WorkoutRepository;
import com.niyiment.fitness.repository.WorkoutSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository repository;
    private static final String RECORD_NOT_FOUND = "Workout not found";


    /**
     * Get all workouts
     * @param filter
     * @param startDate
     * @param endDate
     * @param pageable
     * @return paginated WorkoutResponseDTO
     */
    public Page<WorkoutResponseDTO> getAllWorkouts(String filter, LocalDateTime startDate,
                                                   LocalDateTime endDate, Pageable pageable) {
        log.info("Getting all workouts with filter: {}, start date: {}, end date: {}", filter, startDate, endDate);
        Specification<Workout> spec = WorkoutSpecification.isActive();
        if (filter != null) {
            spec.and(WorkoutSpecification.filterByWorkoutType(filter));
        } else if (startDate != null && endDate != null) {
            spec.and(WorkoutSpecification.filterByWorkoutDate(startDate, endDate));
        }

        Page<Workout> workouts = repository.findAll(spec, pageable);

        return workouts.map(this::toDTO);
    }

    /**
     * Get workout by id
     * @param id
     * @return WorkoutResponseDTO
     */
    public WorkoutResponseDTO getWorkoutById(UUID id) {
        Workout workout = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        return toDTO(workout);
    }

    /**
     * Create a new workout
     * @param workout
     * @return WorkoutResponseDTO
     */
    public WorkoutResponseDTO createWorkout(WorkoutDTO workout) {
        Workout workoutEntity = new Workout();
        fromDTO(workoutEntity, workout);

        return toDTO(repository.save(workoutEntity));
    }

    /**
     * Update an existing workout
     * @param id
     * @param workout
     * @return WorkoutResponseDTO
     */
    public WorkoutResponseDTO updateWorkout(UUID id, WorkoutDTO workout) {
        Workout existingWorkout = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        fromDTO(existingWorkout, workout);

        return toDTO(repository.save(existingWorkout));
    }

    /**
     * Delete an existing Workout
     * @param id
     */
    public void deleteWorkout(UUID id) {
        repository.deleteById(id);
    }

    /**
     * Toggle workout active status
     * @param id
     * @return WorkoutResponseDTO
     */
    public WorkoutResponseDTO toggleWorkoutActiveStatus(UUID id) {
        Workout workout = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        workout.setActive(!workout.isActive());

        return toDTO(repository.save(workout));
    }

    /**
     * @description map workout entity to workoutResponseDTO
     * @param workout
     * @return
     */

    public WorkoutResponseDTO toDTO(Workout workout) {
        WorkoutResponseDTO result = new WorkoutResponseDTO();
        result.setId(workout.getId());
        result.setWorkoutDate(workout.getWorkoutDate());
        result.setWorkoutType(workout.getWorkoutType());
        result.setActive(workout.isActive());

        return result;
    }

    /**
     * map WorkoutDTO to Workout entity
     * @param workout
     * @param dto
     */

    public void fromDTO(Workout workout, WorkoutDTO dto) {
        workout.setWorkoutDate(dto.getWorkoutDate());
        workout.setWorkoutType(dto.getWorkoutType());
        workout.setActive(true);
    }

}
