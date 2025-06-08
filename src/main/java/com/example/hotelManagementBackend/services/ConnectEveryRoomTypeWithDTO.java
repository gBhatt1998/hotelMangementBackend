package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Logger;

@Component
public class ConnectEveryRoomTypeWithDTO {



    private final AnalyzeReservation analyzeReservation;

    public ConnectEveryRoomTypeWithDTO(AnalyzeReservation analyzeReservation) {
        this.analyzeReservation = analyzeReservation;
    }

    public List<RoomTypeWithSingleRoomDTO> getRoomTypesWithOneRoom(Collection<Integer> roomNumbers) {
        List<RoomTypeWithSingleRoomDTO> result = new ArrayList<>();
        Map<Integer, Map<String, Object>> roomTypeDataMap = analyzeReservation.getRoomTypeDataMap();

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

        result.sort(Comparator.comparing(RoomTypeWithSingleRoomDTO::getId));
        return result;
    }
}