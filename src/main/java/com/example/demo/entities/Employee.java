package com.example.demo.entities;



import com.example.demo.annotation.Column;
import com.example.demo.annotation.Table;

import java.util.Date;

@Table(name = "employees")
public class Employee {

    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "bod")
    private Date bod;
    @Column(name = "address")
    private String address;
    @Column(name = "tel")
    private String tel;
    @Column(name = "id_card")
    private String idCard;
    @Column(name = "position")
    private String position;
    @Column(name = "department_id")
    private Long departmentId;
    @Column(name = "status")
    private boolean status;
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

    public Date getBod() {
        return bod;
    }

    public void setBod(Date bod) {
        this.bod = bod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Employee(String name, Date bod, String address, String tel, String idCard, String position, Long departmentId) {
        this.name = name;
        this.bod = bod;
        this.address = address;
        this.tel = tel;
        this.idCard = idCard;
        this.position = position;
        this.departmentId = departmentId;

    }

    public Employee(Long id, String name, Date bod, String address, String tel, String idCard, String position, Long departmentId) {
        this.id = id;
        this.name = name;
        this.bod = bod;
        this.address = address;
        this.tel = tel;
        this.idCard = idCard;
        this.position = position;
        this.departmentId = departmentId;
    }

    public Employee(){}
}
