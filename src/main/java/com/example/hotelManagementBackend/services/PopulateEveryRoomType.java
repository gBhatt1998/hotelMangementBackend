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


    private final HashMap<Integer, Stack<Integer>> mapOfEveryRoomNoInStack;
    private final RoomRepository roomRepo;
    private final ReservationRepository reservationRepo;

    public PopulateEveryRoomType(RoomRepository roomRepo, ReservationRepository reservationRepo) {
        this.roomRepo = roomRepo;
        this.reservationRepo = reservationRepo;
        this.mapOfEveryRoomNoInStack = new HashMap<>();
    }
    List<Integer> allRoomNo;

    @PostConstruct
    public void getRoomNumber() {
        allRoomNo = roomRepo.findAllAvailableRoom();


        for (Integer roomNo : allRoomNo) {

            int roomType = roomNo / 100;

            mapOfEveryRoomNoInStack.computeIfAbsent(roomType, k -> new Stack<>()).push(roomNo);
        }

        System.out.print("map of every room number in its type");

        System.out.print(mapOfEveryRoomNoInStack);

        System.out.println("list of room with one type each");

//        System.out.println(getOneRoomPerTypeAndPop());
    }

    public List<Integer> getOneRoomPerTypeAndPop() {
        List<Integer> result = new ArrayList<>();
        for (Stack<Integer> stack : mapOfEveryRoomNoInStack.values()) {
            if (!stack.isEmpty()) {
                result.add(stack.pop());
            }
        }
        return result;
    }

    private  List<Integer> roomsOutOfStoredDateRange(Date checkIn, Date checkOut){
        List<Room> availableRooms = reservationRepo.findAvailableRoomsByDateRange(checkIn, checkOut);

        // Extract room numbers
        return availableRooms.stream()
                .map(Room::getRoomNo)
                .collect(Collectors.toList());
    }

    public void updateRooms(Date checkIn, Date checkOut) {
        List<Integer> additionalRooms = roomsOutOfStoredDateRange(checkIn, checkOut);
        if (!additionalRooms.isEmpty()) {
            additionalRooms.forEach(roomNo -> {
                if (!allRoomNo.contains(roomNo)) { // Avoid duplicates
                    allRoomNo.add(roomNo);
                    int roomType = roomNo / 100;
                    mapOfEveryRoomNoInStack
                            .computeIfAbsent(roomType, k -> new Stack<>())
                            .push(roomNo);
                }
            });
            System.out.println("Updated stack with new rooms: " + mapOfEveryRoomNoInStack);
        }
    }



}
