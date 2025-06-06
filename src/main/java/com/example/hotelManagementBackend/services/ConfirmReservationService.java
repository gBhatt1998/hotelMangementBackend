package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.dto.ReservationRequest;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.repositories.GuestRepository;
import com.example.hotelManagementBackend.repositories.ReservationRepository;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import com.example.hotelManagementBackend.repositories.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
import com.example.hotelManagementBackend.entities.Service;
import java.util.List;
import java.util.Map;

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
//    private final PasswordEncoder passwordEncoder;

    public Reservation createReservation(ReservationRequest request) {
        Guest guest = processGuest(request.getGuestDetails());

        //get services
        List<Service> selectedServices = serviceRepo.findAllById(request.getServiceIds());
        //add guest to service
        selectedServices.forEach(service -> {
            if (!guest.getServices().contains(service)) {
                guest.getServices().add(service);
                service.getGuests().add(guest);
            }
        });
        // Get room and update availability
        Room room = roomRepo.findById(request.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        // Update room status
        room.setAvailability(false);
        roomRepo.save(room); //

        //add details of reservation
        Reservation reservation = new Reservation();
        reservation.setCheckInDate(request.getCheckInDate());
        reservation.setCheckOutDate(request.getCheckOutDate());
        reservation.setRoom(room);
        reservation.setTotalPrice(request.getTotalPrice());

//        roomRepo.confirmRoom(request.getRoomId());
        reservation.setGuest(guest);
        return reservationRepo.save(reservation);
    }


private Guest processGuest(ReservationRequest.GuestDetails details) {

    Guest newGuest = new Guest();
    newGuest.setName(details.getName());
    newGuest.setEmail(details.getEmail());
    newGuest.setPassword(details.getPassword());
    newGuest.setPhone(details.getPhone());
    newGuest.setRole(details.getRole());
    return guestRepo.save(newGuest);
}

}
