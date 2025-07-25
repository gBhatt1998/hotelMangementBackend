package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    Optional<Department> findByNameIgnoreCase(String name);



}
