package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    List<Room> findAllByAvailability(Boolean availability);

    @Query("SELECT r FROM Room r " +
            "LEFT JOIN FETCH r.roomType " +
            "LEFT JOIN FETCH r.reservations res " +
            "LEFT JOIN FETCH res.guest " +
            "WHERE r.availability = :availability")
    List<Room> findByAvailabilityWithDetails(@Param("availability") boolean availability);


    @Query("SELECT r.roomNo FROM Room r "+
    "WHERE r.availability = true")
    List<Integer> findAllAvailableRoom();

    @Query("SELECT r.roomNo FROM Room r WHERE r.availability = false AND NOT EXISTS " +
            "(SELECT res FROM Reservation res WHERE res.room = r AND " +
            "(res.checkInDate <= :checkOutDate) AND (res.checkOutDate >= :checkInDate))")
    List<Integer> findAvailableRoomsByDateRange(
            @Param("checkInDate") Date checkInDate,
            @Param("checkOutDate") Date checkOutDate
    );

//    @Transactional
//    @Modifying
//    @Query("UPDATE  Room r SET r.availability=false WHERE r.roomNO= :roomNum ")
//    void confirmRoom(@Param("roomNum") Integer roomNum);
//


    @Query("SELECT MIN(r.roomNo) FROM Room r WHERE r.availability=true GROUP BY r.roomType ")
    List<Integer> findOneRoomNoPerRoomType();

    @Query("SELECT new map(rt.type as roomType, " +
            "SUM(CASE WHEN r.availability = false THEN 1 ELSE 0 END) as occupied, COUNT(r) as total) " +
            "FROM Room r JOIN r.roomType rt GROUP BY rt.type")
    List<Map<String, Object>> getRoomOccupancyByRoomType();



}
