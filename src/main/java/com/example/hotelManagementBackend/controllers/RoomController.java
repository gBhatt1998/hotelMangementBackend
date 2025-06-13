package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAll() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PostMapping
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.createRoom(room));
    }

    @PutMapping("/{roomNo}")
    public ResponseEntity<Room> update(@PathVariable int roomNo, @RequestBody Room room) {
        return ResponseEntity.ok(roomService.updateRoom(roomNo, room));
    }

    @DeleteMapping("/{roomNo}")
    public ResponseEntity<Void> delete(@PathVariable int roomNo) {
        roomService.deleteRoom(roomNo);
        return ResponseEntity.noContent().build();
    }
}
