package com.example.demo.repository;

import com.example.demo.entities.Benefits;

import java.util.Optional;

public interface BenefitsRepository {
    Optional<Benefits> findByEmployeeId(Long id);
    Optional<Benefits> findById(Long id);
    Benefits create(Benefits benefits);
    boolean update(Long id,Benefits benefits);
    boolean delete(Long id);
}
