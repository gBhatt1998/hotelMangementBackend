package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.Service;
import com.example.hotelManagementBackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
public class AdminService {

    @Autowired
    private ReservationRepository reservationRepo;

    @Autowired
    private ServiceRepository serviceRepo;

    @Autowired
    private GuestRepository guestRepo;

    @Autowired
    private GuestRepository guestRepos;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoomRepository roomRepository;

//    private Guest

    public void deleteReservationById(int reservationId) throws CustomException {
        Reservation reservation = reservationRepo.findById(reservationId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Reservation not found with ID: " + reservationId));


        //get guest id to soft delete / cancel the rservartion
        String role=reservation.getGuest().getRole();

        Guest guest =reservation.getGuest();
        int guestId= guest.getId();

        Room room = reservation.getRoom();
        if (room != null) {
            room.setAvailability(true);
            roomRepository.save(room);  // Save updated room availability
        }

        reservationRepo.delete(reservation);

        long count = reservationRepo.countByGuestId(guestId);

        if (count == 0) {
            //remove guest if the service and guest

            guestRepo.deleteGuestServicesByGuestId(guestId);
            // Do not delete guest if they are admin
            if (!"ADMIN".equalsIgnoreCase(role)) {
                guestRepo.delete(guest);
            } // Also delete guest if no other reservation
        }


    }




}
