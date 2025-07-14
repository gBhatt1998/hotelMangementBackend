package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Guest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest,Integer> {

    Optional<Guest> findByEmail(String email);


    boolean existsById(int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM guest_service WHERE guest_id = :guestId", nativeQuery = true)
    void deleteGuestServicesByGuestId(@Param("guestId") int guestId);

    boolean existsByEmail(String email);
}
