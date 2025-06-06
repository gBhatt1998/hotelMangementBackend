package com.example.hotelManagementBackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employeeId")
    private int employeeId;

    private String name;


    private String position;

    @Column(name = "hireDate")
    private Date hireDate;


    @ManyToMany
    @JoinTable(
            name = "employee_departments",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments;
}