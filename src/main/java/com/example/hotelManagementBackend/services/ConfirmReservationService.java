package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.InvalidDateRangeException;
import com.example.hotelManagementBackend.Exception.RoomNotFoundException;
import com.example.hotelManagementBackend.Exception.WrongPasswordException;
import com.example.hotelManagementBackend.dto.ReservationRequest;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.repositories.GuestRepository;
import com.example.hotelManagementBackend.repositories.ReservationRepository;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import com.example.hotelManagementBackend.repositories.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
import com.example.hotelManagementBackend.entities.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Component
public class ConfirmReservationService {

    @Autowired
    private  ReservationRepository reservationRepo;
    @Autowired
    private  GuestRepository guestRepo;
    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private  ServiceRepository serviceRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Reservation createReservation(ReservationRequest request, String email) {

        if (request.getCheckInDate().after(request.getCheckOutDate())) {
            throw new InvalidDateRangeException("Check-out date must be after check-in date");
        }

        Guest guest = guestRepo.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Guest not found with email: " + email));

        //Populate the guest details
//        Guest guest = processGuest(request.getGuestDetails());

        //Get services
        // 💼 Fetch selected services
        List<Service> selectedServices = new ArrayList<>();
        if (request.getServiceIds() != null && !request.getServiceIds().isEmpty()) {
            selectedServices = serviceRepo.findAllById(request.getServiceIds());

            // Optional: link guest to services if needed (not required for reservation)
            for (Service service : selectedServices) {
                if (!guest.getServices().contains(service)) {
                    guest.getServices().add(service);
                    service.getGuests().add(guest);
                }
            }
        }


        // Get room and update availability
        Room room = roomRepo.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found with ID: " + request.getRoomId()));
        room.setAvailability(false);
        roomRepo.save(room);

        //Add details of reservation
        Reservation reservation = new Reservation();
        reservation.setCheckInDate(Date.valueOf(request.getCheckInDate().toString()));
        reservation.setCheckOutDate(Date.valueOf(request.getCheckOutDate().toString()));
        reservation.setRoom(room);
        reservation.setGuest(guest);
        reservation.setTotalPrice(request.getTotalPrice());
        reservation.setServices(selectedServices); //

        return reservationRepo.save(reservation);
    }




}
