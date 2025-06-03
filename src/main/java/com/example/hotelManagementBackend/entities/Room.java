package com.example.hotelManagementBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
//@Data
@Entity
public class Room {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY
    @Column(name = "roomNo")
    int roomNo;

    @Column(name = "availability")
    Boolean availability;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservations=new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomTypeId", nullable = false)
    @JsonManagedReference
    @JsonIgnore
    private RoomType roomType;

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public RoomType getRoomTypeId() {
        return roomType;
    }

    public void setRoomTypeId(RoomType roomTypeId) {
        this.roomType = roomTypeId;
    }
}
