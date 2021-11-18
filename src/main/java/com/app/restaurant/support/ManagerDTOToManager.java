package com.app.restaurant.support;

import com.app.restaurant.dto.ManagerDTO;
import com.app.restaurant.dto.MenuItemDTO;
import com.app.restaurant.model.MenuItem;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.users.Manager;
import com.app.restaurant.service.IManagerService;
import com.app.restaurant.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManagerDTOToManager {

    private final IManagerService managerService;

    @Autowired
    public ManagerDTOToManager(IManagerService managerService) {
        this.managerService = managerService;
    }


    public Manager convert(ManagerDTO managerDTO) {
            return new Manager(managerDTO);

    }
}
