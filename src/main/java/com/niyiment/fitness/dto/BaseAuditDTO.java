package com.niyiment.fitness.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BaseAuditDTO extends BaseDTO{
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
