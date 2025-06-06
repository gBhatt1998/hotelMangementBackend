package com.example.hotelManagementBackend.controllers;


import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import com.example.hotelManagementBackend.services.ReservationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.processing.Generated;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationServices rs;

    @Autowired
    private RoomTypeRepository roomTypeRepository;


    @GetMapping("/test")
    public String test(){
        System.out.print("controller working");
        return "reservation controller working";
    }


    @PostMapping("/createRoomType")
    public ResponseEntity<String> createRoomType (@RequestBody RoomType rt){
        rs.createRoomType(rt);
        return new ResponseEntity<>("roomType created", HttpStatus.OK);
    }

    @PostMapping("/createRoom/{id}/room")
    public ResponseEntity<String> createRoom(@PathVariable int id ,@RequestBody Room r){
        System.out.println(r);
        rs.addRoom(id,r);
        return new ResponseEntity<>("room created", HttpStatus.OK);
    }

//    @GetMapping("/getRoomCreated")
//    public RequestBody<Room> getCreatedRoom( )


//      System.out.print("controller working");

    @GetMapping("/isAvailable")
    public List<Room> isAvailable(){
        return rs.findByAvailabilityWithDetails();
    }

    @GetMapping("/allReservation")
    public List<Reservation> getAllReservations() {
        return rs.getAllReservations();
    }

    @GetMapping("/allAvailableRooms")
    public List<RoomTypeWithSingleRoomDTO> getRoomTypesWithSingleRoom() {
        return rs.getAvailableRoom();
    }

    @GetMapping("/availableRoomsByDate")
    public List<RoomTypeWithSingleRoomDTO> getAvailableRoomsByDate(
            @RequestParam("checkIn") String checkInStr,
            @RequestParam("checkOut") String checkOutStr) {
        try {
            Date checkIn = Date.valueOf(checkInStr); // Parses "yyyy-MM-dd"
            Date checkOut = Date.valueOf(checkOutStr);
            return rs.getAvailableRoomOutsideDateRange(checkIn, checkOut);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Use yyyy-MM-dd.");
        }
    }
}
