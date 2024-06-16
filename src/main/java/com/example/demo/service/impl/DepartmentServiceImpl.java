package com.example.demo.service.impl;

import com.example.demo.entities.Benefits;
import com.example.demo.entities.Department;
import com.example.demo.helper.TransferValuesIfNull;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.impl.DepartmentRepositoryImpl;
import com.example.demo.service.DepartmentService;

import java.util.Optional;

public class DepartmentServiceImpl implements DepartmentService {
    DepartmentRepository repository = new DepartmentRepositoryImpl(Department.class);
    @Override
    public Department getById(Long id) {
        try{
            Optional<Department> department = repository.findById(id);
            return department.orElse(null);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Long id, Department department) {
        try{
            Optional<Department> d = repository.findById(id);
            if(!d.isPresent()){
                return false;
            }
            Department dep = d.get();
            dep = TransferValuesIfNull.transferValuesIfNull(dep,department);
            repository.update(id,dep);
            return true;
        }catch (Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }
}
