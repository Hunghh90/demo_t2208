package com.example.demo.entities;


import com.example.demo.annotation.Column;
import com.example.demo.annotation.Table;

import java.util.Date;

@Table(name = "departments")
public class Department {

    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "total_employee")
    private Integer totalEmployee;
    @Column(name = "number_deputy")
    private Integer numberDeputy;
    @Column(name = "manager")
    private boolean manager;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalEmployee() {
        return totalEmployee;
    }

    public void setTotalEmployee(Integer totalEmployee) {
        this.totalEmployee = totalEmployee;
    }

    public Integer getNumberDeputy() {
        return numberDeputy;
    }

    public void setNumberDeputy(Integer numberDeputy) {
        this.numberDeputy = numberDeputy;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
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

    public Department(){}

    public Department(Long id, String name, Integer totalEmployee, Integer numberDeputy, boolean manager,Date createdAt,Date updatedAt) {
        this.id = id;
        this.name = name;
        this.totalEmployee = totalEmployee;
        this.numberDeputy = numberDeputy;
        this.manager = manager;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public Department( String name, Integer totalEmployee, Integer numberDeputy, boolean manager,Date createdAt,Date updatedAt) {
        this.name = name;
        this.totalEmployee = totalEmployee;
        this.numberDeputy = numberDeputy;
        this.manager = manager;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
