package com.example.hotelManagementBackend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int Id;

    String name;

    String password;

    String  phone;

    String role;


    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}
