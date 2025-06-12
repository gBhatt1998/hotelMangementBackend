package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.RoomNotAvailableException;
import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.*;

@Component
public class PopulateEveryRoomType {

    private final RoomRepository roomRepo;
    private final ConnectEveryRoomTypeWithDTO connectDTO;

    public PopulateEveryRoomType(RoomRepository roomRepo, ConnectEveryRoomTypeWithDTO connectDTO) {
        this.roomRepo = roomRepo;
        this.connectDTO = connectDTO;
    }

    // Get one available room per room type without date filter
    public List<RoomTypeWithSingleRoomDTO> getAvailableRoom() {
        List<Integer> availableRoomNumbers = roomRepo.findOneRoomNoPerRoomType();

        if (availableRoomNumbers.isEmpty()) {
            throw new RoomNotAvailableException("No available rooms found at all.");
        }

        return connectDTO.getRoomTypesWithOneRoom(availableRoomNumbers);
    }

    // Get one available room per room type considering date range
    public List<RoomTypeWithSingleRoomDTO> getAvailableRoomOutsideDateRange(Date checkIn, Date checkOut) {
        // Step 1: Get available rooms without date filter
        List<RoomTypeWithSingleRoomDTO> defaultRoomList = getAvailableRoom();

        // Step 2: Store already covered room type IDs
        Set<Integer> coveredRoomTypeIds = new HashSet<>();
        for (RoomTypeWithSingleRoomDTO room : defaultRoomList) {
            coveredRoomTypeIds.add(room.getId());
        }

        // Step 3: Get available room numbers for the given date range
        List<Integer> roomNumbersInRange = roomRepo.findAvailableRoomsByDateRange(checkIn, checkOut);

        if (roomNumbersInRange.isEmpty()) {
            return defaultRoomList; // ✅ No new rooms, return default list
        }

        // Step 4: Choose the smallest room number per new room type
        Map<Integer, Integer> oneRoomPerNewType = new HashMap<>();
        for (Integer roomNumber : roomNumbersInRange) {
            int roomTypeId = roomNumber / 100;
            if (!coveredRoomTypeIds.contains(roomTypeId)) {
                if (!oneRoomPerNewType.containsKey(roomTypeId) || roomNumber < oneRoomPerNewType.get(roomTypeId)) {
                    oneRoomPerNewType.put(roomTypeId, roomNumber);
                }
            }
        }

        // Step 5: Convert new room numbers to DTOs
        List<Integer> newRoomNumbers = new ArrayList<>(oneRoomPerNewType.values());
        List<RoomTypeWithSingleRoomDTO> newRoomDTOs = connectDTO.getRoomTypesWithOneRoom(newRoomNumbers);

        // Step 6: Merge both lists
        defaultRoomList.addAll(newRoomDTOs);

        // Step 7: Sort by room type ID
        defaultRoomList.sort(Comparator.comparingInt(RoomTypeWithSingleRoomDTO::getId));

        return defaultRoomList;
    }
}
