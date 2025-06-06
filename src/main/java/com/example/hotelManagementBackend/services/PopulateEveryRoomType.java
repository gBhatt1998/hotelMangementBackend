package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.dto.RoomTypeWithSingleRoomDTO;
import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.repositories.ReservationRepository;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

@Component
public class PopulateEveryRoomType {


    private final HashMap<Integer, List<Integer>> mapOfRoomTypeToRooms;
    private final RoomRepository roomRepo;
    private final ReservationRepository reservationRepo;

    public PopulateEveryRoomType(RoomRepository roomRepo, ReservationRepository reservationRepo) {
        this.roomRepo = roomRepo;
        this.reservationRepo = reservationRepo;
        this.mapOfRoomTypeToRooms = new HashMap<>();
    }
    List<Integer> allRoomNo;

    @PostConstruct
    public void getRoomNumber() {
        allRoomNo = roomRepo.findAllAvailableRoom();


        for (Integer roomNo : allRoomNo) {

            int roomType = roomNo / 100;

            mapOfRoomTypeToRooms.computeIfAbsent(roomType, k -> new ArrayList<>()).add(roomNo);
        }

        System.out.print("map of every room number in its type");

        System.out.print(mapOfRoomTypeToRooms);

        System.out.println("list of room with one type each");

//        System.out.println(getOneRoomPerTypeAndPop());
    }
    public void resetRoomMap() {
        mapOfRoomTypeToRooms.clear();
        getRoomNumber();
    }
    public List<Integer> getOneRoomPerTypeAndPop() {
        return mapOfRoomTypeToRooms.values().stream()
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0)) // Get first room without removing
                .collect(Collectors.toList());
    }

//    private  List<Integer> roomsOutOfStoredDateRange(Date checkIn, Date checkOut){
////        List<Room> availableRooms = roomRepo.findAvailableRoomsByDateRange(checkIn, checkOut);
//
//        // Extract room numbers
//        return availableRooms.stream()
//                .map(Room::getRoomNo)
//                .collect(Collectors.toList());
//    }

    public void updateRooms(Date checkIn, Date checkOut) {
//        refeshRoomNoList();
        List<Integer> additionalRooms = roomRepo.findAvailableRoomsByDateRange(checkIn, checkOut);
        additionalRooms.forEach(roomNo -> {
            int roomType = roomNo / 100;
            List<Integer> typeRooms = mapOfRoomTypeToRooms.computeIfAbsent(roomType, k -> new ArrayList<>());

            if (!typeRooms.contains(roomNo)) {
                typeRooms.add(roomNo);
                System.out.println("Added room " + roomNo + " for type " + roomType);
            }
        });
    }


    }

