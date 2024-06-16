package com.example.demo.repository;

import com.example.demo.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findById(Long id);
    List<Employee> findAll(int pageNumber, int pageSize);
    Optional<Employee> findByIdCard(String idCard);
    Employee create(Employee employee);
    boolean update(Long id,Employee employee);
    boolean delete(Long id);
}
