package com.example.hotelManagementBackend.controllers;

import com.example.hotelManagementBackend.Auth.GuestDetails;
import com.example.hotelManagementBackend.dto.GuestReservationsResponse;
import com.example.hotelManagementBackend.dto.ReservationDetailsResponse;
import com.example.hotelManagementBackend.services.GuestService;
import com.example.hotelManagementBackend.services.ReservationDetailsResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GuestController {
    @Autowired
    private ReservationDetailsResponseService reservationDetailsResponseService;

    @Autowired
    private GuestService guestService;

    @GetMapping("/guest/reservations")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GuestReservationsResponse > getGuestReservations(
            @AuthenticationPrincipal GuestDetails guestDetails) {
        String email = guestDetails.getUsername(); // email is used as username

        System.out.println("Authenticated user: " + guestDetails.getUsername());
        System.out.println("Authorities: " + guestDetails.getAuthorities());

        // Your CustomGuestDetails should expose guest ID
        GuestReservationsResponse reservations = guestService.getReservationsByGuest(email);
        return ResponseEntity.ok(reservations);
    }
}
