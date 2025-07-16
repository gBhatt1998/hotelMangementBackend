package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.ServiceTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTaskRepository extends JpaRepository<ServiceTask, Long> {
    List<ServiceTask> findByActiveTrue();
}
