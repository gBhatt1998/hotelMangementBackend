package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
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




    List<Room> findByRoomType_Type(String type);


    @Query("SELECT r.roomNo FROM Room r WHERE r.roomNo IN :roomNos")
    List<Integer> findExistingRoomNos(List<Integer> roomNos);

    boolean existsByRoomNo(int roomNo);


}
