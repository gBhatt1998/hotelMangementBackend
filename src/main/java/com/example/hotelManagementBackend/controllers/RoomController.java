package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.dto.RoomRequestDTO;
import com.example.hotelManagementBackend.dto.RoomResponseDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.mapper.RoomMapper;
import com.example.hotelManagementBackend.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/rooms")
public class RoomController {
    @Autowired private RoomService service;

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        return ResponseEntity.ok(service.getAllRoomsWithDeleteFlag());
    }

    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@RequestBody RoomRequestDTO dto) {
        return new ResponseEntity<>(RoomMapper.toResponseDTO(service.createRoom(dto), true), HttpStatus.CREATED);
    }

    @DeleteMapping("/{roomNo}")
    public ResponseEntity<Void> delete(@PathVariable int roomNo) {
        service.deleteRoom(roomNo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/suggest-next/{roomTypeId}")
    public ResponseEntity<Integer> suggestNextRoomNumber(@PathVariable int roomTypeId) {
        return ResponseEntity.ok(service.suggestNextRoomNumber(roomTypeId));
    }

}
