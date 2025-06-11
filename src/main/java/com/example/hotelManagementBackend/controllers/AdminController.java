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


    @GetMapping("/allreservation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationDetailsResponse>> getGuestReservations(
            @AuthenticationPrincipal GuestDetails guestDetails) {
        String email = guestDetails.getUsername(); // email is used as username
        System.out.println("Authenticated user: " + guestDetails.getUsername());
        System.out.println("Authorities: " + guestDetails.getAuthorities());
        // Your CustomGuestDetails should expose guest ID
        List<ReservationDetailsResponse> reservations = reservationDetailsResponseService.getAllReservationDetails();
        return ResponseEntity.ok(reservations);
    }
}
