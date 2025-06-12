package com.example.hotelManagementBackend.dto;

import java.util.List;

public class GuestReservationsResponse {
    private ReservationDetailsResponse.GuestDetails guest;
    private List<ReservationSummaryDTO> reservations;
    private List<String> serviceNames;

    public List<String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(List<String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    // Getters and Setters
    public ReservationDetailsResponse.GuestDetails getGuest() { return guest; }
    public void setGuest(ReservationDetailsResponse.GuestDetails guest) { this.guest = guest; }
    public List<ReservationSummaryDTO> getReservations() { return reservations; }
    public void setReservations(List<ReservationSummaryDTO> reservations) { this.reservations = reservations; }
}
