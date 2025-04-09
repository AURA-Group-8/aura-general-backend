package com.aura8.general_backend.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    private String name;
    private String description;
    private Integer expectedDurationMinutes;
    private Double price;
    private Boolean deleted = false;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime modifiedAt = LocalDateTime.now();
}

