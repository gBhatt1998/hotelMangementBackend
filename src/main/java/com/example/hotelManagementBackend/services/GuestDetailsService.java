package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Auth.EmployeeDetails;
import com.example.hotelManagementBackend.Auth.GuestDetails;
import com.example.hotelManagementBackend.entities.Employee;
import com.example.hotelManagementBackend.entities.Guest;
import com.example.hotelManagementBackend.repositories.EmployeeRepository;
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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Try employee login first
        Employee employee = employeeRepository.findByEmail(email).orElse(null);
        if (employee != null) {
            return new EmployeeDetails(employee);
        }

        // Fall back to guest login
        Guest guest = guestRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

        return new GuestDetails(guest);
    }
}
