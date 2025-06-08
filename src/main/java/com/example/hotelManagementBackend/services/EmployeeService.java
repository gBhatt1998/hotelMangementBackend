package com.example.hotelManagementBackend.services;

import com.example.hotelManagementBackend.Exception.CustomException;
import com.example.hotelManagementBackend.dto.EmployeeRequestDTO;
import com.example.hotelManagementBackend.dto.EmployeeResponseDTO;
import com.example.hotelManagementBackend.entities.Department;
import com.example.hotelManagementBackend.entities.Employee;
import com.example.hotelManagementBackend.repositories.DepartmentRepository;
import com.example.hotelManagementBackend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public String createEmployee(EmployeeRequestDTO request) {
        List<Department> departments = departmentRepository.findAllById(request.getDepartmentIds());
        if (departments.isEmpty()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid Department IDs");
        }

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setPosition(request.getPosition());
        employee.setHireDate(request.getHireDate());
        employee.setDepartments(departments);

        employeeRepository.save(employee);
        return "Employee created successfully";
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> responseList = new ArrayList<>();

        for (Employee e : employees) {
            EmployeeResponseDTO dto = new EmployeeResponseDTO();
            dto.setId(e.getEmployeeId());
            dto.setName(e.getName());
            dto.setPosition(e.getPosition());
            dto.setHireDate(e.getHireDate());
            List<String> deptNames = e.getDepartments().stream().map(Department::getName).toList();
            dto.setDepartments(deptNames);
            responseList.add(dto);
        }

        return responseList;
    }

    public String updateEmployee(int id, EmployeeRequestDTO request) {
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

        employeeRepository.save(employee);
        return "Employee updated";
    }

    public String deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "Employee not found"));

        employeeRepository.delete(employee);
        return "Employee deleted";
    }
}
