package com.example.hotelManagementBackend.controllers;


import com.example.hotelManagementBackend.dto.RoomTypeRequestDTO;
import com.example.hotelManagementBackend.dto.RoomTypeResponseDTO;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.mapper.RoomTypeMapper;
import com.example.hotelManagementBackend.services.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin/room-types")
public class AdminRoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @GetMapping
    public ResponseEntity<List<RoomTypeResponseDTO>> getAll() {
        List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
        List<RoomTypeResponseDTO> dtoList = roomTypes.stream()
                .map(RoomTypeMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping
    public ResponseEntity<RoomTypeResponseDTO> create(@RequestBody RoomTypeRequestDTO dto) {
        RoomType created = roomTypeService.createRoomType(RoomTypeMapper.toEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(RoomTypeMapper.toResponseDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomTypeResponseDTO> update(@PathVariable int id, @RequestBody RoomTypeRequestDTO dto) {
        RoomType updated = roomTypeService.updateRoomType(id, dto);
        return ResponseEntity.ok(RoomTypeMapper.toResponseDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.noContent().build();
    }
}
