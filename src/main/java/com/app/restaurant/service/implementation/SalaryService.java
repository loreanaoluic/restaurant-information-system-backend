package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Receipt;
import com.app.restaurant.model.ReceiptItem;
import com.app.restaurant.model.Salary;
import com.app.restaurant.repository.SalaryRepository;
import com.app.restaurant.service.ISalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public double calculateValue(List<Salary> salaries, long start_date, long end_date) {
        double value = 0;


        for (Salary salary : salaries) {
            Date date_start_date = new Date(start_date);
            Date date_end_date = new Date(end_date);
            Date d = new Date(salary.getStartDate());
            Date d2 = new Date(salary.getEndDate());
            long broj_dana = calculateDayNumber(date_start_date, date_end_date, d, d2);
            long dnevnica = (long) (salary.getValue()/25);
            value += (dnevnica*broj_dana);
        }
        return value;
    }

    public long calculateDayNumber(Date date_start_date, Date date_end_date, Date begin, Date end){

        if(date_start_date.after(begin)){
            if(date_end_date.after(end)){
                long diffInMillies = Math.abs(date_start_date.getTime() - end.getTime()) + 1000;

                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                return diff;
            }else{//1
                long diffInMillies = Math.abs(date_start_date.getTime() - date_end_date.getTime()) + 1000;

                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);


                return diff;
            }

        }else{
            if(date_end_date.before(end)){
                long diffInMillies = Math.abs(begin.getTime() - date_end_date.getTime()) + 1000;

                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                return diff;
            }else{//1
                long diffInMillies = Math.abs(begin.getTime() - end.getTime()) + 1000;

                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                return diff;
            }

        }

    }
}
