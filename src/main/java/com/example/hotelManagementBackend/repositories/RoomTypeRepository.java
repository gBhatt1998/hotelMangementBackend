package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
import com.example.hotelManagementBackend.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType,Integer> {



    @Query("SELECT new com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO(" +
            "rt.id, rt.type, rt.description, rt.pricePerNight, MIN(r.roomNo), rt.imageUrl) " +
            "FROM Room r " +
            "JOIN r.roomType rt " +
            "WHERE NOT EXISTS (" +
            "SELECT res FROM Reservation res " +
            "WHERE res.room = r " +
            "AND res.checkInDate <= :checkOutDate " +
            "AND res.checkOutDate >= :checkInDate" +
            ") " +
            "GROUP BY rt.id, rt.type, rt.description, rt.pricePerNight, rt.imageUrl")
    List<RoomTypeWithSingleRoomDTO> findRoomTypesWithAvailableRoomByDateRange(
            @Param("checkInDate") Date checkInDate,
            @Param("checkOutDate") Date checkOutDate
    );


    @Query("SELECT new com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO(" +
            "rt.id, rt.type, rt.description, rt.pricePerNight, MIN(r.roomNo), rt.imageUrl) " +
            "FROM Room r " +
            "JOIN r.roomType rt " +
            "WHERE r.availability = true " + // <-- add space at the end
            "GROUP BY rt.id, rt.type, rt.description, rt.pricePerNight, rt.imageUrl")
    List<RoomTypeWithSingleRoomDTO> getAllRoomTypesWithMinimumRoomNo();


}
