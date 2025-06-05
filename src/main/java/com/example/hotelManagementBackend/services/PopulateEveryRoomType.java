package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.repositories.RoomRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

@Component
public class PopulateEveryRoomType {


    private final HashMap<Integer, Stack<Integer>> mapOfEveryRoomNoInStack;
    private final RoomRepository roomRepo;

    public PopulateEveryRoomType(RoomRepository roomRepo) {
        this.roomRepo = roomRepo;
        this.mapOfEveryRoomNoInStack = new HashMap<>();
    }

    @PostConstruct
    public void getRoomNumber() {
        List<Integer> allRoomNo = roomRepo.findAllAvailableRoom();
        for (Integer roomNo : allRoomNo) {

            int roomType = roomNo / 100;

            mapOfEveryRoomNoInStack.computeIfAbsent(roomType, k -> new Stack<>()).push(roomNo);
        }

        System.out.print("map of every room number in its type");

        System.out.print(mapOfEveryRoomNoInStack);
    }



}
