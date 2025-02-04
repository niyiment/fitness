package com.niyiment.fitness.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
public class BaseDTO implements Serializable {
    private UUID id;
}
