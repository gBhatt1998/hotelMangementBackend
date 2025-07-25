package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.Auth.GuestDetails;
import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.ApiResponse;
import com.example.hotelManagementBackend.dto.ReservationDetailsResponse;
import com.example.hotelManagementBackend.services.AdminService;
import com.example.hotelManagementBackend.services.ReservationDetailsResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller", description = "Admin operations like managing reservations, employees, departments")

public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReservationDetailsResponseService reservationDetailsResponseService;

    @Operation(summary = "Delete a reservation by ID")
    @DeleteMapping("/{reservationId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteReservation(@PathVariable int reservationId) throws CustomException {
        adminService.deleteReservationById(reservationId);
        return ResponseEntity.ok(
                new ApiResponse<>("success", "Reservation deleted successfully.", null)
        );
    }


    @Operation(summary = "Get All Reservation Details By Filtering")
    @GetMapping("/filtered")
    public ResponseEntity<List<ReservationDetailsResponse>> getFilteredReservations(
            @RequestParam(required = false) String roomTypeName,
            @RequestParam String dateFilter,
            @RequestParam int month,
            @RequestParam int year
    ) {
        List<ReservationDetailsResponse> response = reservationDetailsResponseService.getFilteredReservations(
                roomTypeName, dateFilter, month, year
        );
        return ResponseEntity.ok(response);
    }


}
