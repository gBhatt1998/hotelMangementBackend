package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.EmployeeRequestDTO;
import com.example.hotelManagementBackend.dto.EmployeeResponseDTO;
import com.example.hotelManagementBackend.entities.Department;
import com.example.hotelManagementBackend.entities.Employee;
import com.example.hotelManagementBackend.repositories.DepartmentRepository;
import com.example.hotelManagementBackend.repositories.EmployeeRepository;
import com.example.hotelManagementBackend.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private GuestRepository guestRepository;

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO request) {
        List<Department> departments = departmentRepository.findAllById(request.getDepartmentIds());
        if (departments.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Department IDs");
        }

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setPosition(request.getPosition());
        employee.setHireDate(request.getHireDate());
        employee.setDepartments(departments);

        employee = employeeRepository.save(employee);
        return convertToResponseDTO(employee);
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    public EmployeeResponseDTO updateEmployee(int id, EmployeeRequestDTO request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Employee not found"));

        List<Department> departments = departmentRepository.findAllById(request.getDepartmentIds());
        if (departments.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Department IDs");
        }

        employee.setName(request.getName());
        employee.setPosition(request.getPosition());
        employee.setHireDate(request.getHireDate());
        employee.setDepartments(departments);

        employee = employeeRepository.save(employee);
        return convertToResponseDTO(employee);
    }

    public void deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Employee not found"));

        employeeRepository.delete(employee);
    }

    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getEmployeeId());
        dto.setName(employee.getName());
        dto.setPosition(employee.getPosition());
        dto.setHireDate(employee.getHireDate());
        dto.setDepartments(
                employee.getDepartments().stream()
                        .map(Department::getName)
                        .toList()
        );
        return dto;
    }

    public String generateEmail(String name) {
        String baseEmail = formatNameToEmail(name);
        String email = baseEmail;
        int counter = 1;

        while (employeeRepository.existsByEmail(email) || guestRepository.existsByEmail(email)) {
            email = baseEmail.replace("@", counter + "@");
            counter++;
        }

        return email;
    }

    public String generateSecurePassword(String name) {
        String base = name.trim().toLowerCase().replaceAll("[^a-z0-9]+", ".");
        return base + "12345";
    }


    private String formatNameToEmail(String name) {
        String clean = name.trim().toLowerCase().replaceAll("[^a-z0-9]+", ".");
        return clean + "@metropolitan.com";
    }
}
