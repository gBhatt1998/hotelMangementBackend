package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {

    List<Room> findAllByAvailability(Boolean availability);


}
