package com.example.hotelManagementBackend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class Icon {

    @Id
    @GeneratedValue
    private Long id;

    private String iconName;
    private boolean active = true;

}
