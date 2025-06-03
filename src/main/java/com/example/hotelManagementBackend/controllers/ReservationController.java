package com.example.hotelManagementBackend.controllers;


import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import com.example.hotelManagementBackend.services.ReservationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.processing.Generated;
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
        return rs.isAvailableRoom();
    }
}
