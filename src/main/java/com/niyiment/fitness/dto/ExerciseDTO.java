package com.niyiment.fitness.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExerciseDTO {
    private String name;
    private String description;
    private String videoUrl;
    private UUID workout;
}
