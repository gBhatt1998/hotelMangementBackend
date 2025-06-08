package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Auth.GuestDetails;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class GuestDetailsService implements UserDetailsService {

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Guest guest = guestRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Guest not found with email: " + email));
        return new GuestDetails(guest);
    }
}
