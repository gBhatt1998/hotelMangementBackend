package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.ReservationDetailsResponse;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Service;
import com.example.hotelManagementBackend.repositories.GuestRepository;
import com.example.hotelManagementBackend.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

@org.springframework.stereotype.Service
public class ReservationDetailsResponseService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private GuestRepository guestRepository;

    // ReservationServices.java
    public List<ReservationDetailsResponse> getAllReservationDetails(String roomType) {
        List<Reservation> reservations = reservationRepository.findAllWithDetailsFiltered(
                (roomType == null || roomType.isEmpty()) ? null : roomType
        );

        return reservations.stream()
                .map(this::mapToDetailsResponse)
                .toList();
    }


    /* made again  in guest services */

//    public List<ReservationDetailsResponse> getReservationsByGuest(String email) {
//
//        int guestId = guestRepository.findByEmail(email)
//                .map(guest ->  guest.getId())
//                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"Guest not found"));
//        List<Reservation> reservations = reservationRepository.findByGuestIdWithDetails(guestId);
//        return reservations.stream()
//                .map(this::mapToDetailsResponse)
//                .toList();
//    }

    private ReservationDetailsResponse mapToDetailsResponse(Reservation reservation) {
        ReservationDetailsResponse response = new ReservationDetailsResponse();
        response.setReservationId((long) reservation.getReservationId());
        response.setCheckInDate(reservation.getCheckInDate());
        response.setCheckOutDate(reservation.getCheckOutDate());
        response.setTotalPrice(reservation.getTotalPrice());

        // Room details
        response.setRoomNumber(reservation.getRoom().getRoomNo());
        response.setRoomTypeName(reservation.getRoom().getRoomTypeId().getType());

        // Service names
        response.setServiceNames(reservation.getGuest().getServices().stream()
                .map(Service::getName)
                        .distinct()
                .toList());

        // Guest details
        Guest guest = reservation.getGuest();
        ReservationDetailsResponse.GuestDetails guestDetails =
                new ReservationDetailsResponse.GuestDetails();
        guestDetails.setId(guest.getId());
        guestDetails.setName(guest.getName());
        guestDetails.setEmail(guest.getEmail());
        guestDetails.setPhone(guest.getPhone());
        response.setGuest(guestDetails);

        return response;
    }
}
