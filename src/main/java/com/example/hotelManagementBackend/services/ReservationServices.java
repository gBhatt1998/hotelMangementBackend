package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.entities.Room;
import com.example.hotelManagementBackend.entities.RoomType;
import com.example.hotelManagementBackend.repositories.RoomRepository;
import com.example.hotelManagementBackend.repositories.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServices {
    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private RoomTypeRepository roomTypeRepo;


    public RoomType createRoomType( RoomType roomType){
      return roomTypeRepo.save(roomType);
    }

//    public Room createRoom(Room room){
//
//        if (room == null) {
//            throw new IllegalArgumentException("RoomType must not be null");
//        }
//
//
//
//        Integer roomTypeId = room.getRoomTypeId().getId();
//        RoomType roomType =roomTypeRepo.findById(roomTypeId)
//                .orElseThrow(()->new RuntimeException("Invalid RoomType Id"));
//        room.setRoomTypeId(roomType);
//        return roomRepo.save(room);
//    }

    public Room addRoom(int id, Room room){
        Optional<RoomType> r = roomTypeRepo.findById(id);
        if(r.isPresent()){
            RoomType roomType = r.get();
            room.setRoomTypeId(roomType);
            return roomRepo.save(room);
        }else {
            throw new RuntimeException("Invalid Room");
        }
    }

    public List<Room> isAvailableRoom(){
        return roomRepo.findAllByAvailability(true);
    }

}
