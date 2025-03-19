package com.aura8.general_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Feedback {
    @Id
    private Integer service_id;
    @Id
    private Integer user_id;

    private String comment;
    private Integer rate;
}
