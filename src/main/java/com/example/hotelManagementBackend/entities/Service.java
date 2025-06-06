package com.example.hotelManagementBackend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceId;

    @Column(name = "name")
    private String name;
    @Column(name="description")
    private String description;
    @Column(name = "price")
    private double price;

    @ManyToMany(mappedBy = "services")
    private List<Guest> guests = new ArrayList<>();
    // Constructors, getters, and setters
    public Service() {}

    public Service(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    public void addGuest(Guest guest) {
        guests.add(guest);
        guest.getServices().add(this);
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }
}

