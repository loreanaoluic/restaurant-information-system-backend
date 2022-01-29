package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.Salary;
import com.app.restaurant.repository.SalaryRepository;
import com.app.restaurant.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryService implements ISalaryService {

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


    @Override
    public List<Salary> findByDates(long start_date, long end_date) {
        return salaryRepository.findByDates(start_date, end_date);
    }

    @Override
    public double calculateValue(List<Salary> salaries) {
        double value = 0;
        for (Salary salary : salaries) {
            value += salary.getValue();
        }
        return value;
    }
}
