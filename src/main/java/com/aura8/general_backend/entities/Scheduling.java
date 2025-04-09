package com.aura8.general_backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    private Integer feedback;

    private String status;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private LocalDateTime canceledAt;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();
}