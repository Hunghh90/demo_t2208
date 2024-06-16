package com.example.demo.entities;


import com.example.demo.annotation.Column;
import com.example.demo.annotation.Table;

import java.util.Date;

@Table(name = "benefits")
public class Benefits {

    @Column(name = "id")
    private Long id;
    @Column(name = "basic_salary")
    private Double basicSalary;
    @Column(name = "salary_net")
    private Double salaryNet;
    @Column(name = "insurance")
    private Double insurance;
    @Column(name = "employee_id")
    private Long employeeId;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Double getSalaryNet() {
        return salaryNet;
    }

    public void setSalaryNet(Double salaryNet) {
        this.salaryNet = salaryNet;
    }

    public Double getInsurance() {
        return insurance;
    }

    public void setInsurance(Double insurance) {
        this.insurance = insurance;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Benefits(){}

    public Benefits(Long id, Double basicSalary, Double salaryNet, Double insurance, Long employeeId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.basicSalary = basicSalary;
        this.salaryNet = salaryNet;
        this.insurance = insurance;
        this.employeeId = employeeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Benefits(Double basicSalary, Double salaryNet, Double insurance, Long employeeId, Date createdAt, Date updatedAt) {
        this.basicSalary = basicSalary;
        this.salaryNet = salaryNet;
        this.insurance = insurance;
        this.employeeId = employeeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
