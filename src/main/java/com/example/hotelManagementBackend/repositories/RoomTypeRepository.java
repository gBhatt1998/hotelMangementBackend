package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomTypeRepository extends JpaRepository<RoomType,Integer> {



}
