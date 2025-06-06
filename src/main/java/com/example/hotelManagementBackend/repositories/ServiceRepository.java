package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Integer> {
}
