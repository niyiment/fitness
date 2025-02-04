package com.niyiment.fitness.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WorkoutResponseDTO extends BaseAuditDTO {
    private LocalDateTime workoutDate;
    private String workoutType;
    private boolean active;
}
