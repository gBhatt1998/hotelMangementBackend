package com.example.hotelManagementBackend.controllers;


import com.example.hotelManagementBackend.entities.RoomType;
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
    public ResponseEntity<List<RoomType>> getAll() {
        return ResponseEntity.ok(roomTypeService.getAllRoomTypes());
    }

    @PostMapping
    public ResponseEntity<RoomType> create(@RequestBody RoomType roomType) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomTypeService.createRoomType(roomType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomType> update(@PathVariable int id, @RequestBody RoomType roomType) {
        return ResponseEntity.ok(roomTypeService.updateRoomType(id, roomType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        roomTypeService.deleteRoomType(id);
        return ResponseEntity.noContent().build();
    }
}
