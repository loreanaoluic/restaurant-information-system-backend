package com.app.restaurant.repository;
import com.app.restaurant.model.Salary;
import com.app.restaurant.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {

}
