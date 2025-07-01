package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.RoomResponseDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.mapper.RoomMapper;
import com.example.hotelManagementBackend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.example.hotelManagementBackend.dto.RoomRequestDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepo;
    private final RoomTypeRepository roomTypeRepo;
    private final ReservationRepository reservationRepo;

    public RoomService(RoomRepository roomRepo, RoomTypeRepository roomTypeRepo, ReservationRepository reservationRepo) {
        this.roomRepo = roomRepo;
        this.roomTypeRepo = roomTypeRepo;
        this.reservationRepo = reservationRepo;
    }


    public List<RoomResponseDTO> getAllRoomsWithDeleteFlag() {
        return roomRepo.findAll().stream()
                .map(room -> RoomMapper.toResponseDTO(room, !reservationRepo.existsByRoom(room)))
                .collect(Collectors.toList());
    }

    public List<RoomResponseDTO> getRoomsByRoomType(String type) {
        List<Room> filteredRooms = roomRepo.findByRoomType_Type(type);
        return filteredRooms.stream()
                .map(room -> RoomMapper.toResponseDTO(room, !reservationRepo.existsByRoom(room)))
                .collect(Collectors.toList());
    }

    public void deleteRoom(int roomNo) {
        Room room = roomRepo.findById(roomNo)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        if (reservationRepo.existsByRoom(room)) {
            throw new RuntimeException("Cannot delete a room that has reservations.");
        }
        roomRepo.deleteById(roomNo);
    }

public List<Room> createRooms(List<RoomRequestDTO> dtos) {
    List<Room> roomsToSave = new ArrayList<>();

    for (RoomRequestDTO dto : dtos) {
        RoomType type = roomTypeRepo.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "RoomType not found: " + dto.getRoomTypeId()));

        if (roomRepo.existsById(dto.getRoomNo())) {
            throw new CustomException(HttpStatus.CONFLICT, "Room number " + dto.getRoomNo() + " already exists.");
        }

        Room room = new Room();
        room.setRoomNo(dto.getRoomNo());
        room.setRoomTypeId(type);
        room.setAvailability(dto.isAvailability());
        roomsToSave.add(room);
    }

    return roomRepo.saveAll(roomsToSave);
}

    public List<Integer> suggestRoomNumbers(int roomTypeId, int baseRoomNo, int count) {
        if (!roomTypeRepo.existsById(roomTypeId)) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Room type not found");
        }

        //  if baseRoomNo already exists
        if (roomRepo.existsByRoomNo(baseRoomNo)) {
            throw new CustomException(HttpStatus.CONFLICT, "Base room number " + baseRoomNo + " already exists.");
        }

        List<Integer> finalSuggestions = new ArrayList<>();
        int current = baseRoomNo;

        while (finalSuggestions.size() < count) {
            //  next batch
            List<Integer> candidates = new ArrayList<>();
            for (int i = 0; i < (count - finalSuggestions.size()); i++) {
                candidates.add(current + i);
            }

            //  which room number already exist
            List<Integer> existing = roomRepo.findExistingRoomNos(candidates);

            // filter out existing ones
            for (int num : candidates) {
                if (!existing.contains(num)) {
                    finalSuggestions.add(num);
                }
            }

            current += candidates.size();


            if (current > 9999) break;
        }

        if (finalSuggestions.size() < count) {
            throw new CustomException(
                    HttpStatus.CONFLICT,
                    "Unable to find " + count + " available room numbers starting from " + baseRoomNo
            );
        }

        return finalSuggestions;
    }



}