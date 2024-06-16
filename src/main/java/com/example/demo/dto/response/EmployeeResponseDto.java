package com.example.demo.dto.response;

import com.example.demo.annotation.Column;
import com.example.demo.dto.BenefitsDto;

import java.util.Date;

public class EmployeeResponseDto {
    public Long id;
    public String name;
    public Date bod;
    public String address;
    public String tel;
    public String idCard;
    public String position;
    public String departmentName;
    public BenefitsDto benefitsDto;
    public Date createdAt;
    public Date updatedAt;

    public EmployeeResponseDto() {
    }

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public BenefitsDto getBenefitsDto() {
        return benefitsDto;
    }

    public void setBenefitsDto(BenefitsDto benefitsDto) {
        this.benefitsDto = benefitsDto;
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
}
