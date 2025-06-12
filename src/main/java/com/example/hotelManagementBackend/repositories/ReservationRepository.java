package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

//    @Query("SELECT r FROM Room r WHERE r.availability = true AND NOT EXISTS (" +
//            "SELECT rs FROM Reservation rs WHERE rs.room = r. AND " +
//            "rs.checkInDate < :checkOut AND rs.checkOutDate > :checkIn)")
//    List<Room> findAvailableRoomsByDateRange(
//            @Param("checkIn") Date checkIn,
//            @Param("checkOut") Date checkOut);

    boolean existsByGuestId(int guestId); // checks if guest has other reservations

    long countByGuestId(int guestId);

//    // getting reservations for admin
//    @Query("""
//    SELECT DISTINCT r FROM Reservation r
//    JOIN FETCH r.room rm
//    JOIN FETCH rm.roomType rt
//    JOIN FETCH r.guest g
//    LEFT JOIN FETCH g.services s
//    """)
//    List<Reservation> findAllWithRoomAndGuest();
//
//    //getting reservation for guest
//    @Query("SELECT r FROM Reservation r JOIN FETCH r.room rm JOIN FETCH r.guest g WHERE g.id = :guestId")
//    List<Reservation> findByGuestIdWithRoom(@Param("guestId") int guestId);

    // For admin - get all reservations with all relationships
    @Query("SELECT  r FROM Reservation r " +
            "JOIN FETCH r.guest g " +
            "JOIN FETCH r.room rm " +
            "JOIN FETCH rm.roomType rt " +
            "LEFT JOIN FETCH g.services s")
    List<Reservation> findAllWithDetails();

    // For guest - get reservations by guest ID with all relationships
    @Query("SELECT  r FROM Reservation r " +
            "JOIN FETCH r.guest g " +
            "JOIN FETCH r.room rm " +
            "JOIN FETCH rm.roomType rt " +
            "LEFT JOIN FETCH  g.services s " +
            "WHERE g.id = :guestId")
    List<Reservation> findByGuestIdWithDetails(@Param("guestId") int guestId);

}