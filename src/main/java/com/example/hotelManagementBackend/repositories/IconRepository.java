package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Icon;
import com.example.hotelManagementBackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IconRepository extends JpaRepository<Icon, Long> {
}
