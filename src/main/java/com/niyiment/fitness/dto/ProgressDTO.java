package com.niyiment.fitness.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ProgressDTO {
    private LocalDate date;
    private Double weight;
    private Double bodyFat;
}
