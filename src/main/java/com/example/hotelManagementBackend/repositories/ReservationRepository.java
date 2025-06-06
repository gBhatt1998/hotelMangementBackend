package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

//    @Query("SELECT r FROM Room r WHERE r.availability = true AND NOT EXISTS (" +
//            "SELECT rs FROM Reservation rs WHERE rs.room = r. AND " +
//            "rs.checkInDate < :checkOut AND rs.checkOutDate > :checkIn)")
//    List<Room> findAvailableRoomsByDateRange(
//            @Param("checkIn") Date checkIn,
//            @Param("checkOut") Date checkOut);


}