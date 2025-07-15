package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Employee;
import com.example.hotelManagementBackend.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsByEmail(String email);

    Optional<Employee>  findByEmail(String email);
}
