package com.app.restaurant.repository;

import com.app.restaurant.model.users.Manager;
import com.app.restaurant.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select user from User user where user.username = ?1 and user.deleted = false" )
    User findByUsername(String username);
}
