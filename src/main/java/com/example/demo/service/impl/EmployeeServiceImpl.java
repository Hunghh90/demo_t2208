package com.example.demo.service.impl;



import com.example.demo.common.GenericDao;
import com.example.demo.common.GenericDaoImpl;
import com.example.demo.dto.BenefitsDto;
import com.example.demo.dto.request.EmployeeDTO;
import com.example.demo.dto.request.Paginate;
import com.example.demo.dto.response.EmployeeResponseDto;
import com.example.demo.entities.Benefits;
import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;

import com.example.demo.helper.EntityDtoConverter;
import com.example.demo.helper.TransferValuesIfNull;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.impl.EmployeeRepositoryImpl;
import com.example.demo.service.BenefitsService;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.EmployeeService;


import java.util.*;


public class EmployeeServiceImpl  implements EmployeeService {
        EmployeeRepository repository = new EmployeeRepositoryImpl(Employee.class);
        DepartmentService departmentService = new DepartmentServiceImpl();
        BenefitsService benefitsService = new BenefitsServiceImpl();

        private EmployeeResponseDto convertToDto(Employee employee, String departmentName, BenefitsDto benefitsDto){
            EmployeeResponseDto dto = new EmployeeResponseDto();
            dto = EntityDtoConverter.convertToDto(employee,EmployeeResponseDto.class);
            dto.setDepartmentName(departmentName);
            dto.setBenefitsDto(benefitsDto);
            return dto;
        }
    @Override
    public EmployeeResponseDto findById(Long id) {
        try{
            Optional<Employee> employee = repository.findById(id);
            if(!employee.isPresent()){
                return null;
            }
            Employee e = employee.get();
            Department department = departmentService.getById(e.getDepartmentId());
            Benefits benefits = benefitsService.getByEmployeeId(e.getId());
            BenefitsDto benefitsDto = EntityDtoConverter.convertToDto(benefits,BenefitsDto.class);
            EmployeeResponseDto employeeResponseDto = convertToDto(e,department.getName(),benefitsDto);
            return employeeResponseDto;
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String create(EmployeeDTO employeeDTO) {
        try{
            Optional<Employee> isExists = repository.findByIdCard(employeeDTO.getIdCard());
            if(isExists.isPresent()){
                return "Is exists";
            }
            Employee employee = EntityDtoConverter.convertToEntity(employeeDTO,Employee.class);
            employee.setCreatedAt(new Date());
            employee.setUpdatedAt(null);
            employee.setStatus(true);
            Employee create = repository.create(employee);
            if(create == null){
                return "Crate fail";
            }
            Benefits benefits = new Benefits();
            benefits.setEmployeeId(create.getId());
            benefits.setBasicSalary(employeeDTO.getBasicSalary());
            benefits.setSalaryNet(employeeDTO.getSalaryNet());
            benefits.setInsurance(employeeDTO.getInsurance());
            benefits.setCreatedAt(new Date());
            benefits.setUpdatedAt(null);
            Benefits ben = benefitsService.create(benefits);
            if(ben == null){
                repository.delete(employee.getId());
                return "fail";
            }
            Department department = departmentService.getById(employeeDTO.getDepartmentId());
            department.setTotalEmployee(department.getTotalEmployee()+1);
            department.setUpdatedAt(new Date());
            boolean dep = departmentService.update(employeeDTO.getDepartmentId(),department );
            if(!dep){
                benefitsService.delete(ben.getId());
                repository.delete(employee.getId());
                return "fail";
            }
            return "Create success";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    public List<EmployeeResponseDto> getAll(int pageSize,int limit ) {
        try {
            List<Employee> employeeList = repository.findAll(pageSize,limit);
            List<EmployeeResponseDto> employeeResponseDtoList = new ArrayList<>();
            for(Employee e : employeeList){
                Department department = departmentService.getById(e.getDepartmentId());
                Benefits benefits = benefitsService.getByEmployeeId(e.getId());
                BenefitsDto benefitsDto = EntityDtoConverter.convertToDto(benefits,BenefitsDto.class);
                EmployeeResponseDto employeeResponseDto = convertToDto(e,department.getName(),benefitsDto);
                employeeResponseDtoList.add(employeeResponseDto);
            }
            return employeeResponseDtoList;
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public String update(Long id, EmployeeDTO employeeDto) {
        try{
            Optional<Employee> e = repository.findById(id);
            Employee employee = EntityDtoConverter.convertToEntity(employeeDto,Employee.class);
            if(e.isPresent()){
                employee = TransferValuesIfNull.transferValuesIfNull(e.get(),employee);
                employee.setUpdatedAt(new Date());
            }
            boolean update = repository.update(id,employee);
            Benefits benefits = benefitsService.getByEmployeeId(id);
            benefits.setEmployeeId(id);
            benefits.setBasicSalary(employeeDto.getBasicSalary());
            benefits.setSalaryNet(employeeDto.getSalaryNet());
            benefits.setInsurance(employeeDto.getInsurance());
            benefits.setCreatedAt(null);
            benefits.setUpdatedAt(new Date());
            benefitsService.update(benefits.getId(),benefits);
            if(!update){
                return "Update fail";
            }
            return "Update success";
        }catch (Exception e){
            System.err.println(e.getMessage());
            return "Update fail";
        }
    }

    @Override
    public String delete(Long id) {
        try{
            Optional<Employee> e = repository.findById(id);
            if(!e.isPresent()){
                return "Delete fail";
            }
            e.get().setStatus(false);
            boolean update = repository.update(id,e.get());
            if(!update){
                return "Delete fail";
            }
            Department department = departmentService.getById(e.get().getDepartmentId());
            department.setTotalEmployee(department.getTotalEmployee()-1);
            department.setUpdatedAt(new Date());
            departmentService.update(e.get().getDepartmentId(),department );
            return "Delete success";
        }catch (Exception e){
            System.err.println(e.getMessage());
            return "Delete fail";
        }
    }

    @Override
    public String changePosition(Long id, String position, Long departmentId) {
        try {
            Optional<Employee> employeeOpt = repository.findById(id);
            if (!employeeOpt.isPresent()) {
                return "fail";
            }
            Employee employee = employeeOpt.get();
            Department currentDepartment = departmentService.getById(employee.getDepartmentId());
            Department newDepartment = departmentService.getById(departmentId);

            if (position.equalsIgnoreCase("staff")) {
                if (employee.getPosition().equalsIgnoreCase("manager")) {
                    currentDepartment.setManager(false);
                    departmentService.update(currentDepartment.getId(), currentDepartment);
                } else if (employee.getPosition().equalsIgnoreCase("deputy")) {
                    currentDepartment.setNumberDeputy(currentDepartment.getNumberDeputy() - 1);
                    departmentService.update(currentDepartment.getId(), currentDepartment);
                }
            } else if (position.equalsIgnoreCase("manager")) {
                if (newDepartment.isManager()) {
                    return "fail because manager already exists";
                }
                if (employee.getPosition().equalsIgnoreCase("manager") && !employee.getDepartmentId().equals(departmentId)) {
                    currentDepartment.setManager(false);
                    departmentService.update(currentDepartment.getId(), currentDepartment);
                }else if (employee.getPosition().equalsIgnoreCase("deputy")) {
                    currentDepartment.setNumberDeputy(currentDepartment.getNumberDeputy() - 1);
                    departmentService.update(currentDepartment.getId(), currentDepartment);
                }
                newDepartment.setManager(true);
                departmentService.update(newDepartment.getId(), newDepartment);
            } else if (position.equalsIgnoreCase("deputy")) {
                if (newDepartment.getNumberDeputy() > 1) {
                    return "fail because deputy slot is full";
                }
                if (employee.getPosition().equalsIgnoreCase("deputy")&& !employee.getDepartmentId().equals(departmentId)) {
                    currentDepartment.setNumberDeputy(currentDepartment.getNumberDeputy() - 1);
                    departmentService.update(currentDepartment.getId(), currentDepartment);
                }
                if (employee.getPosition().equalsIgnoreCase("manager")) {
                        currentDepartment.setManager(false);
                        departmentService.update(currentDepartment.getId(), currentDepartment);
                }
                newDepartment.setNumberDeputy(newDepartment.getNumberDeputy() + 1);
                departmentService.update(newDepartment.getId(), newDepartment);
            }

            if (!departmentId.equals(employee.getDepartmentId())) {
                newDepartment.setTotalEmployee(newDepartment.getTotalEmployee() + 1);
                departmentService.update(newDepartment.getId(), newDepartment);
                currentDepartment.setTotalEmployee(currentDepartment.getTotalEmployee() - 1);
                departmentService.update(currentDepartment.getId(), currentDepartment);
                employee.setDepartmentId(departmentId);
            }
            employee.setPosition(position);
            employee.setDepartmentId(departmentId);
            repository.update(id, employee);
            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

}
