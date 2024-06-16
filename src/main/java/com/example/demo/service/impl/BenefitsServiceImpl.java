package com.example.demo.service.impl;

import com.example.demo.entities.Benefits;
import com.example.demo.helper.TransferValuesIfNull;
import com.example.demo.repository.BenefitsRepository;
import com.example.demo.repository.impl.BenefitsRepositoryImpl;
import com.example.demo.service.BenefitsService;

import java.util.Optional;

public class BenefitsServiceImpl implements BenefitsService {
    BenefitsRepository repository = new BenefitsRepositoryImpl(Benefits.class);
    @Override
    public Benefits getByEmployeeId(Long id) {
        try{
            Optional<Benefits> benefits = repository.findByEmployeeId(id);
            return benefits.orElse(null);
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Benefits create(Benefits benefits) {
        try{
            Benefits b = repository.create(benefits);
            if(b==null){
                return null;
            }
            return b;
        }catch (Exception e){
            System.err.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Long id, Benefits benefits) {
        try{
            Optional<Benefits> ben = repository.findById(id);
            if(!ben.isPresent()){
                return false;
            }
            benefits = TransferValuesIfNull.transferValuesIfNull(ben.get(),benefits);
            boolean b = repository.update(id,benefits);
            if(!b){
                return false;
            }
            return true;
        }catch (Exception e){
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
