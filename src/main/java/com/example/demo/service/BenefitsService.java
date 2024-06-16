package com.example.demo.service;

import com.example.demo.entities.Benefits;

public interface BenefitsService {
    Benefits getByEmployeeId(Long id);
    Benefits create(Benefits benefits);
    boolean update(Long id,Benefits benefits);
    boolean delete(Long id);
}
