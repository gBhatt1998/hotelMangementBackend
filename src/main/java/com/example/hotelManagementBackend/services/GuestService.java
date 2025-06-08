package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.GuestReservationsResponse;
import com.example.hotelManagementBackend.dto.ReservationDetailsResponse;
import com.example.hotelManagementBackend.dto.ReservationSummaryDTO;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.repositories.GuestRepository;
import com.example.hotelManagementBackend.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public GuestReservationsResponse getReservationsByGuest(String email) {
        // Get the guest by email
        Guest guest = guestRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Guest not found"));

        // Get all their reservations
        List<Reservation> reservations = reservationRepository.findByGuestIdWithDetails(guest.getId());

        // Map each reservation
        List<ReservationSummaryDTO> reservationList = new ArrayList<>();
        for (Reservation reservation : reservations) {
            ReservationSummaryDTO dto = new ReservationSummaryDTO();
            dto.setReservationId(reservation.getReservationId());
            dto.setCheckInDate(reservation.getCheckInDate());
            dto.setCheckOutDate(reservation.getCheckOutDate());
            dto.setTotalPrice(reservation.getTotalPrice());
            dto.setRoomNumber(reservation.getRoom().getRoomNo());
            dto.setRoomTypeName(reservation.getRoom().getRoomTypeId().getType());

            // Get services from this reservation (not the guest to avoid duplicates)
//            List<String> serviceNames = reservation.getGuest().getServices()
//                    .stream()
//                    .map(service -> service.getName())
//                    .collect(Collectors.toList());
//            dto.setServiceNames(serviceNames);


            reservationList.add(dto);
        }

        //  guest details
        ReservationDetailsResponse.GuestDetails guestDto = new ReservationDetailsResponse.GuestDetails();
        guestDto.setId(guest.getId());
        guestDto.setName(guest.getName());
        guestDto.setEmail(guest.getEmail());
        guestDto.setPhone(guest.getPhone());

        //get service name
        List<String> serviceNames=guest.getServices().stream().map(service -> service.getName()).toList();

        // save everything
        GuestReservationsResponse response = new GuestReservationsResponse();
        response.setGuest(guestDto);
        response.setReservations(reservationList);
        response.setServiceNames(serviceNames);

        return response;
    }

}
