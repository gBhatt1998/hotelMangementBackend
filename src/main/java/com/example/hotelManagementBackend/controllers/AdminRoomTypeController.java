package com.example.hotelManagementBackend.controllers;


import com.example.hotelManagementBackend.dto.RoomTypeRequestDTO;
import com.example.hotelManagementBackend.dto.RoomTypeResponseDTO;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.mapper.RoomTypeMapper;
import com.example.hotelManagementBackend.services.RoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/room-types")
@PreAuthorize("hasRole('ADMIN')")
public class AdminRoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @Operation(summary = "Load Room type on Dashboard")
    @GetMapping
    public ResponseEntity<List<RoomTypeResponseDTO>> getAll() {
        List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
        List<RoomTypeResponseDTO> dtoList = roomTypes.stream()
                .map(RoomTypeMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
    @Operation(summary = "Create New  Room type")
    @PostMapping
    public ResponseEntity<RoomTypeResponseDTO> create(@RequestBody RoomTypeRequestDTO dto) {
        RoomType created = roomTypeService.createRoomType(RoomTypeMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(RoomTypeMapper.toResponseDTO(created));
    }
    @Operation(summary = "Update Existing  Room type ")
    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponseDTO> update(@PathVariable int id, @RequestBody RoomTypeRequestDTO dto) {
        RoomType updated = roomTypeService.updateRoomType(id, dto);
        return ResponseEntity.ok(RoomTypeMapper.toResponseDTO(updated));
    }

    @Operation(summary = "Delete Existing Room type ")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.noContent().build();
    }
}
