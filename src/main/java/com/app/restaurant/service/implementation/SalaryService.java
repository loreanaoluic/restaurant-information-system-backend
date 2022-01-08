package com.app.restaurant.service.implementation;

import com.app.restaurant.exception.NotFoundException;
import com.app.restaurant.model.Price;
import com.app.restaurant.model.Salary;
import com.app.restaurant.model.users.User;
import com.app.restaurant.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService  {

    private final SalaryRepository salaryRepository;

    @Autowired
    public SalaryService(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }


    public Salary findOne(Integer id) {
        return this.salaryRepository.findById(id).orElse(null);
    }

    public List<Salary> findAll() {
        return this.salaryRepository.findAll();
    }

    public Salary save(Salary entity) {
        return this.salaryRepository.save(entity);
    }


}
