package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
