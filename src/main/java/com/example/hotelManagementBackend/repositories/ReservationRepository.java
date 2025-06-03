package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {



}
