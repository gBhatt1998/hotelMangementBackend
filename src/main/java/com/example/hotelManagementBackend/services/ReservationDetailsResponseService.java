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

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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



    public List<ReservationDetailsResponse> getFilteredReservations(String roomTypeName, String dateFilter, int month, int year) {
        LocalDate today = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;
        boolean applyDateFilter = true;

        switch (dateFilter.toLowerCase()) {
            case "today":
                startDate = today;
                endDate = today;
                break;
            case "week":
                LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() % 7);
                LocalDate endOfWeek = startOfWeek.plusDays(6);
                startDate = startOfWeek;
                endDate = endOfWeek;
                break;
            case "month":
                startDate = LocalDate.of(year, month, 1);
                endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                break;
            case "all":
            default:
                applyDateFilter = false;
                startDate = today;
                endDate = today;
                break;
        }

        Date sqlStart = Date.valueOf(startDate);
        Date sqlEnd = Date.valueOf(endDate);

        List<Reservation> reservations = reservationRepository.findByRoomTypeAndDateRange(
                (roomTypeName == null || roomTypeName.isBlank()) ? null : roomTypeName,
                applyDateFilter,
                sqlStart,
                sqlEnd
        );

        return reservations.stream()
                .map(this::mapToReservationDetailsResponse)
                .collect(Collectors.toList());
    }

    private ReservationDetailsResponse mapToReservationDetailsResponse(Reservation r) {
        ReservationDetailsResponse dto = new ReservationDetailsResponse();
        dto.setReservationId((long) r.getReservationId());
        dto.setCheckInDate(r.getCheckInDate());
        dto.setCheckOutDate(r.getCheckOutDate());
        dto.setTotalPrice(r.getTotalPrice());
        dto.setRoomNumber(r.getRoom().getRoomNo());
        dto.setRoomTypeName(r.getRoom().getRoomTypeId().getType());
        dto.setCanDelete(r.getCheckOutDate().toLocalDate().isAfter(LocalDate.now()));

        // Services
        List<String> serviceNames = r.getServices() != null ?
                r.getServices().stream().map(s -> s.getName()).toList() :
                new ArrayList<>();
        dto.setServiceNames(serviceNames);

        // Guest
        ReservationDetailsResponse.GuestDetails guestDetails = new ReservationDetailsResponse.GuestDetails();
//        guestDetails.setId(r.getGuest().getGuestId());
        guestDetails.setName(r.getGuest().getName());
        guestDetails.setEmail(r.getGuest().getEmail());
        guestDetails.setPhone(r.getGuest().getPhone());
        dto.setGuest(guestDetails);

        return dto;
    }
}
