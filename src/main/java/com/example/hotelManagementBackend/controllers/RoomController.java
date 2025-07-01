package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.RoomListRequestDTO;
import com.example.hotelManagementBackend.dto.RoomRequestDTO;
import com.example.hotelManagementBackend.dto.RoomResponseDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.mapper.RoomMapper;
import com.example.hotelManagementBackend.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
@PreAuthorize("hasRole('ADMIN')")
public class RoomController {
    @Autowired private RoomService service;

    @Operation(summary = " Load Room On Admin Dashboard ")
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

    @Operation(summary = "Create  Rooms In Bunch ")
    @PostMapping("/batch")
    public ResponseEntity<List<RoomResponseDTO>> createRooms(@RequestBody RoomListRequestDTO dto) {
        List<Room> savedRooms = service.createRooms(dto.getRooms());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRooms.stream()
                        .map(room -> RoomMapper.toResponseDTO(room, true))
                        .collect(Collectors.toList()));
    }

    @Operation(summary = "Delete Existing Room  ")
    @DeleteMapping("/{roomNo}")
    public ResponseEntity<Void> delete(@PathVariable int roomNo) {

        try {
            service.deleteRoom(roomNo);
            return ResponseEntity.noContent().build();
        }catch  (DataIntegrityViolationException ex) {
            throw new CustomException( HttpStatus.CONFLICT,"Cannot delete room " + roomNo + " because it has reservations.");
        }
    }



@Operation(summary = "Suggest Room Number Based On Requirement")
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
