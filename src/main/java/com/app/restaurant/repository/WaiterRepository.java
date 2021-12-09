package com.app.restaurant.repository;

import com.app.restaurant.model.users.Manager;
import com.app.restaurant.model.users.User;
import com.app.restaurant.model.users.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Integer> {

    //and user.role = 'Waiter'
    @Query("select user from User user where user.deleted = false " )
    List<Waiter> findAll();

    @Query("select user from User user where user.username = ?1")
    Waiter findByUsername(String username);
}
