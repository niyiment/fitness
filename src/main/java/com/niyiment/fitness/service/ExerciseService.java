package com.niyiment.fitness.service;


import com.niyiment.fitness.dto.ExerciseDTO;
import com.niyiment.fitness.dto.ExerciseResponseDTO;
import com.niyiment.fitness.entity.Exercise;
import com.niyiment.fitness.entity.Workout;
import com.niyiment.fitness.exception.ResourceNotFoundException;
import com.niyiment.fitness.repository.ExerciseRepository;
import com.niyiment.fitness.repository.ExerciseSpecification;
import com.niyiment.fitness.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository repository;
    private final WorkoutRepository workoutRepository;
    private static final String RECORD_NOT_FOUND = "Record not found";

    /**
     * Retrieve exercise with optional filter by name and pagination
     * @param name name of exercise
     * @param pageable Pagination pageable
     * @return Paged ExerciseResponseDTO
     */
    public Page<ExerciseResponseDTO> getAllExercises(String name, Pageable pageable) {
        Specification<Exercise> spec = ExerciseSpecification.isActive();
        if (name != null && !name.isEmpty()) {
            spec.and(ExerciseSpecification.filterByName(name));
        }
        Page<Exercise> exercises = repository.findAll(spec, pageable);

        return exercises.map(this::toDTO);
    }

    /**
     * Retrieve exercise by id
     * @param id exercise identifier
     * @return ExerciseResponseDTO
     */
    public ExerciseResponseDTO getExerciseById(UUID id) {
        Exercise exercise = repository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        return toDTO(exercise);
    }

    /**
     * Create new Exercise
     * @param dto New exercise request dto
     * @return ExerciseResponseDTO
     */
    public ExerciseResponseDTO createExercise(ExerciseDTO dto) {
        Exercise exercise = new Exercise();
        toEntity(exercise, dto);

        return toDTO(repository.save(exercise));
    }

    /**
     * Update existing exercise
     * @param id exercise identifier
     * @param dto exercise request dto
     * @return ExerciseResponseDTO
     */
    public ExerciseResponseDTO updateExercise(UUID id, ExerciseDTO dto) {
        Exercise exercise = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RECORD_NOT_FOUND));
        toEntity(exercise, dto);

        return toDTO(repository.save(exercise));
    }

    /**
     * Delete exercise by id
     * @param id exercise identifier
     */
    public void deleteExercise(UUID id) {
        repository.deleteById(id);
    }

    /**
     * Map Exercise entity to ExerciseResponseDTO
     * @param exercise entity
     * @return ExerciseResponseDTO
     */
    private ExerciseResponseDTO toDTO(Exercise exercise) {
        ExerciseResponseDTO result = new ExerciseResponseDTO();
        Workout workout = workoutRepository.findById(exercise.getWorkout().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id " + exercise.getWorkout().getId()));
        result.setId(exercise.getId());
        result.setName(exercise.getName());
        result.setDescription(exercise.getDescription());
        result.setVideoUrl(exercise.getVideoUrl());
        result.setWorkout(workout.getId());
        result.setWorkoutType(workout.getWorkoutType());
        result.setWorkoutDate(workout.getWorkoutDate());
        result.setActive(exercise.isActive());
        result.setCreatedAt(exercise.getCreatedAt());
        result.setUpdatedAt(exercise.getUpdatedAt());

        return result;
    }

    /**
     * Map ExerciseDTO to Exercise entity
     * @param exercise entity
     * @param dto exercise request dto
     */
    private void toEntity(Exercise exercise, ExerciseDTO dto) {

        Workout workout = workoutRepository.findById(dto.getWorkout())
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id " + dto.getWorkout()));
        exercise.setName(dto.getName());
        exercise.setDescription(dto.getDescription());
        exercise.setVideoUrl(dto.getVideoUrl());
        exercise.setWorkout(workout);
        exercise.setActive(true);
    }
}
