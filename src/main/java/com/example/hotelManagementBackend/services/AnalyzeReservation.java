package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AnalyzeReservation {

    @Autowired
    private RoomTypeRepository roomTypeRepo;

    private Map<Integer, Map<String, Object>> roomTypeDataMap;
    @PostConstruct
    public void initializeRoomTypeMap() {
        List<RoomType> roomTypes = roomTypeRepo.findAll();

        roomTypeDataMap = new HashMap<>();

        for (RoomType roomType : roomTypes) {
            Map<String, Object> typeDetails = new HashMap<>();
            typeDetails.put("id", roomType.getId());
            typeDetails.put("type", roomType.getType());
            typeDetails.put("description", roomType.getDescription());
            typeDetails.put("pricePerNight", roomType.getPricePerNight());

            roomTypeDataMap.put(roomType.getId(), typeDetails);
        }

        System.out.println("RoomType data map initialized:");
        System.out.println(roomTypeDataMap);
    }

    public Map<Integer, Map<String, Object>> getRoomTypeDataMap() {
        return roomTypeDataMap;
    }

    public Map<String, Object> getRoomTypeDetailsById(int id) {
        return roomTypeDataMap.get(id);
    }

    public void refreshRoomTypeData() {
        initializeRoomTypeMap();
    }
}