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
    import java.util.stream.Collectors;

    @Component
    public class AnalyzeReservation {

        @Autowired
        private RoomTypeRepository roomTypeRepo;

        @Autowired
        private RoomRepository roomRepo;

        private Map<Integer, Map<String, Object>> roomTypeDataMap;

        @PostConstruct
        public void initializeRoomTypeMap() {
            List<RoomType> roomTypes = roomTypeRepo.findAll();

            roomTypeDataMap = new HashMap<>();

            for (RoomType roomType : roomTypes) {
                Map<String, Object> roomTypeDetails = new HashMap<>();
                roomTypeDetails.put("id", roomType.getId());
                roomTypeDetails.put("type", roomType.getType());
                roomTypeDetails.put("description", roomType.getDescription());
                roomTypeDetails.put("pricePerNight", roomType.getPricePerNight());
                roomTypeDetails.put("imageUrl",roomType.getImageUrl());

                roomTypeDataMap.put(roomType.getId(), roomTypeDetails);
            }

            System.out.println("RoomType data map initialized:");
            System.out.println(roomTypeDataMap);

            System.out.println("list of room number");
            System.out.println(roomRepo.findAllAvailableRoom());
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