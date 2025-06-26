package com.example.hotelManagementBackend.controllers;


import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.ApiResponse;
import com.example.hotelManagementBackend.dto.ReservationRequest;
import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.entities.Service;
import com.example.hotelManagementBackend.repositories.ReservationRepository;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import com.example.hotelManagementBackend.services.AvailableServices;
import com.example.hotelManagementBackend.services.ConfirmReservationService;
//import com.example.hotelManagementBackend.services.PopulateEveryRoomType;
import com.example.hotelManagementBackend.services.ReservationServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.processing.Generated;
import java.security.Principal;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationServices rs;

    @Autowired
    private RoomTypeRepository roomTypeRepository;


    @Autowired
    private ConfirmReservationService confirmReservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private AvailableServices availableServices;




    @Operation(summary = "Booking Successfully Saved In DB  ")
    @PostMapping("/confirmed")
    public ResponseEntity<?> createReservation(@Valid
                                               @RequestBody ReservationRequest request , Principal principal) {

        String email = principal.getName();
        Reservation reservation = confirmReservationService.createReservation(request,email);


        String message = "Reservation confirmed for room " + reservation.getRoom().getRoomNo();

        return ResponseEntity.ok(new ApiResponse<>("success", message, null));
    }

    @Operation(summary = "Services Displayed On the Drop Down For Selection By User")
    @GetMapping("/services")
    public ResponseEntity<List<Service>> allAvailableServices(){
        return ResponseEntity.ok(availableServices.getAllService());
    }



}