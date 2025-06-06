package com.example.hotelManagementBackend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "email",nullable = false,unique = true)
    String email;

    @Column(name="password")
    String password;

    @Column(name = "phone")
    String  phone;

    @Column(name="role")
    String role;


    @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL)
    private List<Reservation> reservations=new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "guest_service", // Join table name
            joinColumns = @JoinColumn(name = "guest_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services = new ArrayList<>();

    public void addService(Service service) {
        if (!this.services.contains(service)) {
            this.services.add(service);
            service.getGuests().add(this);
        }
    }

//    public void removeService(Service service) {
//        services.remove(service);
//        service.getGuests().remove(this);
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
