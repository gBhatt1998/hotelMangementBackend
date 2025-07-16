package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.dto.CreateServiceTaskDTO;
import com.example.hotelManagementBackend.entities.Department;
import com.example.hotelManagementBackend.entities.Icon;
import com.example.hotelManagementBackend.entities.ServiceTask;
import com.example.hotelManagementBackend.entities.Tag;
import com.example.hotelManagementBackend.repositories.DepartmentRepository;
import com.example.hotelManagementBackend.repositories.IconRepository;
import com.example.hotelManagementBackend.repositories.ServiceTaskRepository;
import com.example.hotelManagementBackend.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
public class ServiceTaskService {
    @Autowired
    private  ServiceTaskRepository taskRepo;
    @Autowired
    private  IconRepository iconRepo;
    @Autowired
    private  DepartmentRepository departmentRepo;
    @Autowired
    private  TagRepository tagRepo;
    public ServiceTask createTask(CreateServiceTaskDTO dto, String createdBy) {
        ServiceTask task = new ServiceTask();

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCreatedBy(createdBy);

        // Required fields
        task.setPriority(dto.getPriority());
        task.setDurationMinutes(dto.getDurationMinutes());

        // Icon
        Icon icon = iconRepo.findById(dto.getIconId())
                .orElseThrow(() -> new RuntimeException("Icon not found"));
        task.setIcon(icon);

        // Tags
        Set<Tag> tags = new HashSet<>(tagRepo.findAllById(dto.getTagIds()));
        task.setTags(tags);

        // Optional fields
        if (dto.getImageUrl() != null) task.setImageUrl(dto.getImageUrl());
        task.setPaid(Boolean.TRUE.equals(dto.getIsPaid()));
        task.setPrice(dto.getPrice());
        task.setApprovalRequired(Boolean.TRUE.equals(dto.getApprovalRequired()));
        task.setGuestRequestable(Boolean.TRUE.equals(dto.getGuestRequestable()));
        task.setModule(dto.getModule());

        if (dto.getDepartmentId() != 0) {
            Department dept = departmentRepo.findById(dto.getDepartmentId()).orElse(null);
            task.setDepartment(dept);
        }

        return taskRepo.save(task);
    }

}
