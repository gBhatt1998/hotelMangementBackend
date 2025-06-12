package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.entities.Service;
import com.example.hotelManagementBackend.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AvailableServices {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<Service> getAllService(){
        List<Service> services=serviceRepository.findAll();
        System.out.print("services"+services);
        return serviceRepository.findAll();
    }
}
