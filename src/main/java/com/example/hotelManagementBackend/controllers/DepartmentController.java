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
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@Valid @RequestBody DepartmentRequestDTO request) {
        DepartmentResponseDTO created = departmentService.createDepartment(request);
        return ResponseEntity.ok(created); // Return full DTO, not String
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        List<DepartmentResponseDTO> list = departmentService.getAllDepartments();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable int id, @Valid @RequestBody DepartmentRequestDTO request) {
        DepartmentResponseDTO updated = departmentService.updateDepartment(id, request);
        if (updated == null) {
            return ResponseEntity.notFound().build(); // 404 if not found
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable int id) {
        boolean deleted = departmentService.deleteDepartment(id);
        if (!deleted) {
            return ResponseEntity.notFound().build(); // 404 if not found
        }
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
