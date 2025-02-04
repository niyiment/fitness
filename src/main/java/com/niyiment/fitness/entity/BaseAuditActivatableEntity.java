package com.niyiment.fitness.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
public abstract class BaseAuditActivatableEntity extends BaseAuditEntity{
    @Column(nullable = false)
    private boolean active = true;
}
