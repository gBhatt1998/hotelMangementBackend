package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Reservation;
import com.example.hotelManagementBackend.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {



    long countByGuestId(int guestId);



    //  get reservations by guest ID with all relationships
    @Query("SELECT  r FROM Reservation r " +
            "JOIN FETCH r.guest g " +
            "JOIN FETCH r.room rm " +
            "JOIN FETCH rm.roomType rt " +
            "LEFT JOIN FETCH  r.services s " +
            "WHERE g.id = :guestId "+
            "ORDER BY r.reservationId DESC")
    List<Reservation> findByGuestIdWithDetails(@Param("guestId") int guestId);


    boolean existsByRoom(Room room);

    @Query(value = """
    SELECT DATE_FORMAT(check_out_date, '%Y-%m') AS period,
           SUM(total_price) AS totalRevenue
    FROM reservation
    GROUP BY period
    ORDER BY period
""", nativeQuery = true)
    List<Object[]> getMonthlyRevenue();


    @Query(value = """
    SELECT YEARWEEK(check_out_date, 1) AS period,
           SUM(total_price) AS totalRevenue
    FROM reservation
    GROUP BY period
    ORDER BY period
""", nativeQuery = true)
    List<Object[]> getWeeklyRevenue();



    @Query("""
SELECT DISTINCT r FROM Reservation r
JOIN FETCH r.guest g
JOIN FETCH r.room rm
JOIN FETCH rm.roomType rt
LEFT JOIN FETCH r.services s
WHERE (:roomType IS NULL OR LOWER(rt.type) = LOWER(:roomType))
AND (
  (:applyDateFilter = false OR (
    r.checkInDate <= :dateRangeEnd AND
    r.checkOutDate >= :dateRangeStart
  ))
)
ORDER BY  r.reservationId DESC
""")
    List<Reservation> findByRoomTypeAndDateRange(
            @Param("roomType") String roomType,
            @Param("applyDateFilter") boolean applyDateFilter,
            @Param("dateRangeStart") Date dateRangeStart,
            @Param("dateRangeEnd") Date dateRangeEnd
    );





}