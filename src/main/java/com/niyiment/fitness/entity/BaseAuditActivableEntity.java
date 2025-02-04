package com.niyiment.fitness.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public abstract class BaseAuditActivableEntity extends BaseAuditEntity{
    @Column(nullable = false)
    private boolean active = true;
}
