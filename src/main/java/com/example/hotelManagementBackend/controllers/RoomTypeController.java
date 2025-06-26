package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.dto.ApiResponse;
import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
import com.example.hotelManagementBackend.services.ReservationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/room-numbers")
public class RoomTypeController {

    @Autowired
    private ReservationServices rs;

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<RoomTypeWithSingleRoomDTO>>> getAvailableRoomTypesWithOneRoomEach() {
        List<RoomTypeWithSingleRoomDTO> data = rs.getAvailableRoom();
        return ResponseEntity.ok(new ApiResponse<>("success", "Available room types with one room each", data));
    }

    @GetMapping("/available/by-date")
    public ResponseEntity<ApiResponse<List<RoomTypeWithSingleRoomDTO>>> getAvailableRoomsByDate(
            @RequestParam("checkIn") String checkInStr,
            @RequestParam("checkOut") String checkOutStr) {

        try {
            Date checkIn = Date.valueOf(checkInStr); // Accepts yyyy-MM-dd
            Date checkOut = Date.valueOf(checkOutStr);
            List<RoomTypeWithSingleRoomDTO> available = rs.getAvailableRoomOutsideDateRange(checkIn, checkOut);
            return ResponseEntity.ok(new ApiResponse<>("success", "Available rooms in date range", available));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Use yyyy-MM-dd.");
        }
    }


}
