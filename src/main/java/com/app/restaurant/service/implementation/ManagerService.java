package com.app.restaurant.service.implementation;

import com.app.restaurant.model.users.Manager;
import com.app.restaurant.repository.ManagerRepository;
import com.app.restaurant.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ManagerService implements IManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager findOne(Integer id) {
        return null;
    }

    @Override
    public Manager save(Manager manager) {
        return managerRepository.save(manager);
    }
}
