package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.DepartmentRequestDTO;
import com.example.hotelManagementBackend.dto.DepartmentResponseDTO;
import com.example.hotelManagementBackend.entities.Department;
import com.example.hotelManagementBackend.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;


    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO request) {
        Optional<Department> existing = departmentRepository.findByNameIgnoreCase(request.getName());
        if (existing.isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT, "Department Already Exists");
        }
        Department department = new Department();
        department.setName(request.getName());
        Department saved = departmentRepository.save(department);

        return mapToResponseDTO(saved);
    }

    public List<DepartmentResponseDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDTO> responseList = new ArrayList<>();

        for (Department d : departments) {
            responseList.add(mapToResponseDTO(d));
        }
        return responseList;
    }


    public DepartmentResponseDTO updateDepartment(int id, DepartmentRequestDTO request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No such Department"));

        department.setName(request.getName());
        Department updated = departmentRepository.save(department);

        return mapToResponseDTO(updated);
    }

    public boolean deleteDepartment(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No such Department"));
        departmentRepository.delete(department);
        return true;
    }

    private DepartmentResponseDTO mapToResponseDTO(Department department) {
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setId(department.getDepartmentId());
        dto.setName(department.getName());
        return dto;
    }
}
