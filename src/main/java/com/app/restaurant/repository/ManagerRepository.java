package com.app.restaurant.repository;

import com.app.restaurant.model.users.Manager;
import com.app.restaurant.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    @Query("select user from User user where user.deleted = false and user.role = 2")
    List<Manager> findAll();

    @Query("select user from User user where user.username = ?1 and user.role = 2")
    Manager findByUsername(String username);
}
