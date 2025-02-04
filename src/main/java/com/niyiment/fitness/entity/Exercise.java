package com.niyiment.fitness.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "ft_exercise")
@Data
public class Exercise extends  BaseAuditActivatableEntity {
    @NotBlank(message = "Name is required")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String videoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;
}