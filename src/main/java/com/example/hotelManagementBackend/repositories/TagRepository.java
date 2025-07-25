package com.example.hotelManagementBackend.repositories;

import com.example.hotelManagementBackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
