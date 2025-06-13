package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AnalyzeReservation {

    @Autowired
    private RoomTypeRepository roomTypeRepo;

    @Autowired
    private RoomRepository roomRepo;

    private Map<Integer, Map<String, Object>> roomTypeDataMap;

    @PostConstruct
    public void initializeRoomTypeMap() {
        this.roomTypeDataMap = buildRoomTypeDataMap();
        System.out.println("RoomType data map initialized:");
        System.out.println(roomTypeDataMap);
        System.out.println("List of available room numbers:");
        System.out.println(roomRepo.findAllAvailableRoom());
    }

    public Map<Integer, Map<String, Object>> getRoomTypeDataMap() {
        return new HashMap<>(roomTypeDataMap); // Return a copy for safety
    }

    public Map<String, Object> getRoomTypeDetailsById(int id) {
        return roomTypeDataMap.get(id);
    }

    public void refreshRoomTypeData() {
        this.roomTypeDataMap = buildRoomTypeDataMap();
    }

    private Map<Integer, Map<String, Object>> buildRoomTypeDataMap() {
        List<RoomType> roomTypes = roomTypeRepo.findAll();
        Map<Integer, Map<String, Object>> dataMap = new HashMap<>();

        for (RoomType roomType : roomTypes) {
            dataMap.put(roomType.getId(), convertToMap(roomType));
        }

        return dataMap;
    }

    private Map<String, Object> convertToMap(RoomType roomType) {
        Map<String, Object> roomTypeDetails = new HashMap<>();
        roomTypeDetails.put("id", roomType.getId());
        roomTypeDetails.put("type", roomType.getType());
        roomTypeDetails.put("description", roomType.getDescription());
        roomTypeDetails.put("pricePerNight", roomType.getPricePerNight());
        roomTypeDetails.put("imageUrl", roomType.getImageUrl());
        return roomTypeDetails;
    }
}
