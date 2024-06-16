package com.example.demo.dto;

import com.example.demo.annotation.Column;

public class BenefitsDto {
    public Double basicSalary;
    public Double salaryNet;
    public Double insurance;

    public BenefitsDto() {
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
}
