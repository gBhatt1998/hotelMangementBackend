package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT r FROM Room r WHERE " +
            "NOT EXISTS (SELECT res FROM Reservation res WHERE res.room = r) OR " +
            "NOT EXISTS (SELECT res FROM Reservation res WHERE res.room = r AND " +
            "           (res.checkInDate BETWEEN :checkIn AND :checkOut OR " +
            "            res.checkOutDate BETWEEN :checkIn AND :checkOut OR " +
            "            (res.checkInDate <= :checkIn AND res.checkOutDate >= :checkOut)))")
    List<Room> findAvailableRoomsByDateRange(
            @Param("checkIn") Date checkIn,
            @Param("checkOut") Date checkOut);
}