package com.example.hotelManagementBackend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int reservationId;

    Date checkInDate;

    Date checkOutDate;

    float totalPrice;

    @ManyToOne
    @JoinColumn(name = "RoomId", nullable = false)
    @JsonIgnore
    private Room room;


    @ManyToOne
    @JoinColumn(name="GuestId")
    @JsonIgnore
    private Guest guest;
}
