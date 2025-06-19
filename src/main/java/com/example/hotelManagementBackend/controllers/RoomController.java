package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.RoomRequestDTO;
import com.example.hotelManagementBackend.dto.RoomResponseDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.mapper.RoomMapper;
import com.example.hotelManagementBackend.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/rooms")
public class RoomController {
    @Autowired private RoomService service;

    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms(@RequestParam(required = false) String roomType) {
        List<RoomResponseDTO> rooms;
        if (roomType == null || roomType.isEmpty()) {
            rooms = service.getAllRoomsWithDeleteFlag();
        } else {
            rooms = service.getRoomsByRoomType(roomType);
        }
        return ResponseEntity.ok(rooms);
    }


    @PostMapping
    public ResponseEntity<RoomResponseDTO> create(@RequestBody RoomRequestDTO dto) {
        return new ResponseEntity<>(RoomMapper.toResponseDTO(service.createRoom(dto), true), HttpStatus.CREATED);
    }

    @DeleteMapping("/{roomNo}")
    public ResponseEntity<Void> delete(@PathVariable int roomNo) {
//        System.out.println(" delte called for roomNo: " + roomNo);

        try {
            service.deleteRoom(roomNo);
            return ResponseEntity.noContent().build();
        }catch  (DataIntegrityViolationException ex) {
            throw new CustomException( HttpStatus.CONFLICT,"Cannot delete room " + roomNo + " because it has reservations.");
        }
    }


    @GetMapping("/suggest-next/{roomTypeId}")
    public ResponseEntity<Integer> suggestNextRoomNumber(@PathVariable int roomTypeId) {
        return ResponseEntity.ok(service.suggestNextRoomNumber(roomTypeId));
    }

    @GetMapping("/suggest-room-numbers")
    public ResponseEntity<List<Integer>> suggestRoomNumbers(
            @RequestParam int roomTypeId,
            @RequestParam int baseRoomNo,
            @RequestParam int count
    ) {
        List<Integer> suggestions = service.suggestRoomNumbers(roomTypeId, baseRoomNo, count);
        return ResponseEntity.ok(suggestions);
    }


}
