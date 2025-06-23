package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.GuestReservationsResponse;
import com.example.hotelManagementBackend.dto.ReservationDetailsResponse;
import com.example.hotelManagementBackend.dto.ReservationSummaryDTO;
import com.example.hotelManagementBackend.dto.SignupRequestDTO;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.repositories.GuestRepository;
import com.example.hotelManagementBackend.repositories.ReservationRepository;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public Guest registerGuest(SignupRequestDTO signupRequest) {
        if (guestRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Email already exists.");
        }

        Guest guest = new Guest();
        guest.setName(signupRequest.getName());
        guest.setEmail(signupRequest.getEmail());
        guest.setPhone(signupRequest.getPhone());
        guest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        guest.setRole("ROLE_" + signupRequest.getRole().toUpperCase());

        return guestRepository.save(guest);
    }






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
            dto.setCanDelete(reservation.getCheckOutDate().toLocalDate().isAfter(LocalDate.now()));


            reservationList.add(dto);
        }

        //  guest details
        ReservationDetailsResponse.GuestDetails guestDto = new ReservationDetailsResponse.GuestDetails();
//        guestDto.setId(guest.getId());
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

    public void deleteReservationByGuest(int reservationId, String email) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Reservation not found"));

        if (!reservation.getGuest().getEmail().equals(email)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "You can only delete your own reservations");
        }

        Room room = reservation.getRoom();
        if (room != null) {
            room.setAvailability(true);
            roomRepository.save(room);
        }

        reservationRepository.delete(reservation);

        long count = reservationRepository.countByGuestId(reservation.getGuest().getId());
        if (count == 0) {
            guestRepository.deleteGuestServicesByGuestId(reservation.getGuest().getId());
        }
    }


}
