package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Integer> {

//    @Query("SELECT s FROM Reservation r JOIN r.guest g JOIN g.services s WHERE r.reservationId = :reservationId")
//    List<Service> findServicesByReservationId(@Param("reservationId") int reservationId);

}
