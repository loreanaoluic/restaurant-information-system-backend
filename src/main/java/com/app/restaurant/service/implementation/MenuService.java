package com.app.restaurant.service.implementation;

import com.app.restaurant.model.Menu;
import com.app.restaurant.repository.MenuRepository;
import com.app.restaurant.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    @Override
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Override
    public Menu findOne(Integer id) {
        return menuRepository.findById(id).orElse(null);
    }

    @Override
    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }
}
