package com.niyiment.fitness.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class ExerciseResponseDTO extends BaseAuditDTO {
    private String name;
    private String description;
    private String videoUrl;
    private UUID workout;
    private boolean active;
}
