package com.app.restaurant.repository;

import com.app.restaurant.model.users.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

}
