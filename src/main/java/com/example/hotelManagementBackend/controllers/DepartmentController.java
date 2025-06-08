package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.dto.DepartmentRequestDTO;
import com.example.hotelManagementBackend.dto.DepartmentResponseDTO;
import com.example.hotelManagementBackend.services.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/departments")
@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<String> createDepartment( @Valid @RequestBody DepartmentRequestDTO request) {
        String result = departmentService.createDepartment(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        List<DepartmentResponseDTO> list = departmentService.getAllDepartments();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartment(@PathVariable int id, @Valid @RequestBody DepartmentRequestDTO request) {
        String result = departmentService.updateDepartment(id, request);
        if (result.equals("Department not found")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable int id) {
        String result = departmentService.deleteDepartment(id);
        if (result.equals("Department not found")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
