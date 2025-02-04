package com.niyiment.fitness.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ProgressResponseDTO extends BaseAuditDTO{
    private LocalDate date;
    private Double weight;
    private Double bodyFat;
    private boolean active;
}
