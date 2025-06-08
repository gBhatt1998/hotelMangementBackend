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

    public String createDepartment(DepartmentRequestDTO request) {
        Optional<Department> existing = departmentRepository.findByName(request.getName());
        if (existing.isPresent()) {
            throw new CustomException(HttpStatus.CONFLICT,"Department Already Exist");
        }
        Department department = new Department();
        department.setName(request.getName());
        departmentRepository.save(department);
        return "Department created";
    }

    public List<DepartmentResponseDTO> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDTO> responseList = new ArrayList<>();

        for (Department d : departments) {
            DepartmentResponseDTO dto = new DepartmentResponseDTO();
            dto.setId(d.getDepartmentId());
            dto.setName(d.getName());
            responseList.add(dto);
        }
        return responseList;
    }

    public String updateDepartment(int id, DepartmentRequestDTO request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No such Department"));

        department.setName(request.getName());
        departmentRepository.save(department);
        return "Department updated";
    }

    public String deleteDepartment(int id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "No such Department"));
        departmentRepository.delete(department);
        return "Department deleted";
    }
}
