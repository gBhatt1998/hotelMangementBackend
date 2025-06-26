package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.ReservationRepository;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReservationServices {
    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private RoomTypeRepository roomTypeRepo;
    @Autowired
    private ReservationRepository reservationRepo;






    public List<RoomTypeWithSingleRoomDTO> getAvailableRoom() {
        System.out.println(/*                     */);
        System.out.print("query room list"+ roomTypeRepo.getAllRoomTypesWithMinimumRoomNo());

        return roomTypeRepo.getAllRoomTypesWithMinimumRoomNo();
    }

    public List<RoomTypeWithSingleRoomDTO> getAvailableRoomOutsideDateRange(Date checkIn, Date checkOut) {



        System.out.println("Date by new  function");

        return roomTypeRepo.findRoomTypesWithAvailableRoomByDateRange(checkIn, checkOut);
    }










}
