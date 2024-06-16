package com.example.demo.service;

import com.example.demo.entities.Department;

public interface DepartmentService {
    Department getById(Long id);
    boolean update(Long id, Department department);
}
