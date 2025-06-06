package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface GuestRepository extends JpaRepository<Guest,Integer> {

    List<String>findByEmail(String email);
}
