package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class ConnectEveryRoomTypeWithDTO {



    private final AnalyzeReservation analyzeReservation;
    private final PopulateEveryRoomType populateEveryRoomType;

    @Autowired
    public ConnectEveryRoomTypeWithDTO(AnalyzeReservation analyzeReservation, PopulateEveryRoomType populateEveryRoomType) {
        this.analyzeReservation = analyzeReservation;
        this.populateEveryRoomType = populateEveryRoomType;
    }

    public List<RoomTypeWithSingleRoomDTO> getRoomTypesWithOneRoom() {
        List<RoomTypeWithSingleRoomDTO> result = new ArrayList<>();
        Map<Integer, Map<String, Object>> roomTypeDataMap = analyzeReservation.getRoomTypeDataMap();
        List<Integer> roomNumbers = populateEveryRoomType.getOneRoomPerTypeAndPop();

        // Map room numbers to room types
        for (Integer roomNumber : roomNumbers) {

            Integer roomTypeId = roomNumber / 100;
            Map<String, Object> details = roomTypeDataMap.get(roomTypeId);

            if (details != null) {
                RoomTypeWithSingleRoomDTO dto = new RoomTypeWithSingleRoomDTO(
                        (Integer) details.get("id"),
                        (String) details.get("type"),
                        (String) details.get("description"),
                        (Float) details.get("pricePerNight"),
                        roomNumber
                );
                result.add(dto);
            }
        }
        result.sort((a, b) -> a.getId().compareTo(b.getId()));


        return result;
    }
}