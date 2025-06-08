package com.example.hotelManagementBackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class EmployeeRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Position is required")
    private String position;

    @NotNull(message = "Hire date is required")
    private Date hireDate;

    @NotNull(message = "Department IDs are required")
    private List<Integer> departmentIds;

    public @NotBlank(message = "Name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Name is required") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Position is required") String getPosition() {
        return position;
    }

    public void setPosition(@NotBlank(message = "Position is required") String position) {
        this.position = position;
    }

    public @NotNull(message = "Hire date is required") Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(@NotNull(message = "Hire date is required") Date hireDate) {
        this.hireDate = hireDate;
    }

    public @NotNull(message = "Department IDs are required") List<Integer> getDepartmentIds() {
        return departmentIds;
    }

    public void setDepartmentIds(@NotNull(message = "Department IDs are required") List<Integer> departmentIds) {
        this.departmentIds = departmentIds;
    }
}
