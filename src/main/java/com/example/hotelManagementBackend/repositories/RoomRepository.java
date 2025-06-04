package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    List<Room> findAllByAvailability(Boolean availability);

    @Query("SELECT r FROM Room r " +
            "LEFT JOIN FETCH r.roomType " +
            "LEFT JOIN FETCH r.reservations res " +
            "LEFT JOIN FETCH res.guest " +
            "WHERE r.availability = :availability")
    List<Room> findByAvailabilityWithDetails(@Param("availability") boolean availability);
}
