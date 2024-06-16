package com.example.demo.controller;

import com.example.demo.dto.request.EmployeeDTO;
import com.example.demo.dto.request.Paginate;
import com.example.demo.dto.response.EmployeeResponseDto;
import com.example.demo.entities.Employee;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.impl.EmployeeServiceImpl;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {

    public EmployeeService employeeService = new EmployeeServiceImpl();

    @GetMapping("/{id}")
    public EmployeeResponseDto getById(@PathVariable("id") Long id){
        return employeeService.findById(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody EmployeeDTO employee){
        return employeeService.create(employee);
    }

    @GetMapping("/get-all")
    public List<EmployeeResponseDto> getAll(@RequestParam int pageSize, int limit){
        return employeeService.getAll(pageSize,limit);
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id")Long id,@RequestBody EmployeeDTO employeeDto){
        return employeeService.update(id,employeeDto);
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id")Long id){
        return employeeService.delete(id);
    }

    @PostMapping("/change-position/{id}")
    public String changePosition(@PathVariable("id") Long id,@RequestParam String position, Long departmentId){
        return employeeService.changePosition(id,position,departmentId);
    }
}
