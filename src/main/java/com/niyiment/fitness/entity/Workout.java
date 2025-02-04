package com.niyiment.fitness.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ft_workouts")
@Data
@NoArgsConstructor
public class Workout extends BaseAuditActivatableEntity {

    private LocalDateTime workoutDate;

    private String workoutType;

    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Exercise> exercises;
}
