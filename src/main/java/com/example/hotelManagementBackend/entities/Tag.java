package com.example.hotelManagementBackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private boolean active=true;

    @CreationTimestamp
    private LocalDateTime createdDate;
}
