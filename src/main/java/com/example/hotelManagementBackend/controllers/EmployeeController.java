package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.dto.EmployeeRequestDTO;
import com.example.hotelManagementBackend.dto.EmployeeResponseDTO;
import com.example.hotelManagementBackend.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/employees")
@PreAuthorize("hasRole('ADMIN')")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeRequestDTO request) {
        String result = employeeService.createEmployee(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee(@PathVariable int id, @Valid @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
