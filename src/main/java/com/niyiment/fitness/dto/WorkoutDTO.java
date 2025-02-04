package com.niyiment.fitness.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WorkoutDTO {
    @NotNull(message = "Workout date is required")
    private LocalDateTime workoutDate;

    @NotNull(message = "Workout type is required")
    private String workoutType;
}
