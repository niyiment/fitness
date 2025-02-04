package com.niyiment.fitness.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
public class ExerciseResponseDTO extends BaseAuditDTO {
    private String name;
    private String description;
    private String videoUrl;
    private UUID workout;
    private String workoutType;
    private LocalDateTime workoutDate;
    private boolean active;
}
