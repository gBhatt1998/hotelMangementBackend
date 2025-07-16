package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.dto.CreateServiceTaskDTO;
import com.example.hotelManagementBackend.entities.ServiceTask;
import com.example.hotelManagementBackend.services.ServiceTaskService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service-tasks")
//@RequiredArgsConstructor
public class ServiceTaskController {

    @Autowired
    private  ServiceTaskService taskService;

    @Operation(summary = "Create Service Task  ")
    @PostMapping
    public ResponseEntity<ServiceTask> createTask(
            @Valid @RequestBody CreateServiceTaskDTO dto,
            Authentication auth
    ) {
        String createdBy = (auth != null) ? auth.getName() : "SYSTEM";
        ServiceTask created = taskService.createTask(dto, createdBy);
        return ResponseEntity.ok(created);
    }

    // You can add other endpoints here later:
    // - GET /api/service-tasks
    // - PUT /api/service-tasks/{id}
    // - DELETE /api/service-tasks/{id}
}
