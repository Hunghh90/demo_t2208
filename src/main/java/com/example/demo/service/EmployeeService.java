package com.example.demo.service;



import com.example.demo.common.GenericDao;
import com.example.demo.dto.request.EmployeeDTO;
import com.example.demo.dto.request.Paginate;
import com.example.demo.dto.response.EmployeeResponseDto;
import com.example.demo.entities.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService  {

    EmployeeResponseDto findById(Long id);
    String create(EmployeeDTO employeeDTO);
    List<EmployeeResponseDto> getAll(int pageSize,int limit );
    String update(Long id, EmployeeDTO employeeDTO);
    String delete(Long id);
    String changePosition(Long id, String position,Long departmentId);
}
