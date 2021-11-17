package com.app.restaurant.service.implementation;

import com.app.restaurant.model.users.Manager;
import com.app.restaurant.model.users.Waiter;
import com.app.restaurant.repository.WaiterRepository;
import com.app.restaurant.service.IWaiterService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WaiterService implements IWaiterService {

    private final WaiterRepository waiterRepository;

    public WaiterService(WaiterRepository waiterRepository) {
        this.waiterRepository = waiterRepository;
    }

    @Override
    public void delete(Integer id) {
        Waiter waiter = waiterRepository.getById(id);
        waiterRepository.delete(waiter);
    }

    @Override
    public Waiter create(Waiter entity) throws Exception {

        if (waiterRepository.findByUsername(entity.getUsername()) != null)
            throw new Exception("Waiter already exists.");
        else
            waiterRepository.save(entity);

        return entity;
    }

    @Override
    public Waiter update(Waiter entity) throws Exception {
        Optional<Waiter> man = waiterRepository.findById(entity.getId());
        if(man.isPresent()==true){
            Waiter waiter=man.get();
            waiter.setName(entity.getName());
            waiter.setLastName(entity.getLastName());
            waiter.setEmailAddress(entity.getEmailAddress());
            waiter.setUsername(entity.getUsername());
            waiterRepository.save(waiter);
        }
        return man.get();
    }
}
