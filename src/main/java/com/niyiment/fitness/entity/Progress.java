package com.niyiment.fitness.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "ft_progress")
@Data
public class Progress extends BaseAuditActivatableEntity {
    private LocalDate date;
    private Double weight;
    private Double bodyFat;
}
