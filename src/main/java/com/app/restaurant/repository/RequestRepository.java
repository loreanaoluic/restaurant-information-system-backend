package com.app.restaurant.repository;

import com.app.restaurant.model.Request;
import com.app.restaurant.model.users.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {

    @Query("select request from Request request where request.deleted = false")
    List<Request> findAllNotDeleted();
}
