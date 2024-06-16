package com.example.demo.repository;

import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;

import java.util.Optional;

public interface DepartmentRepository {
    Optional<Department> findById(Long id);
    boolean update(Long id, Department department);
}
