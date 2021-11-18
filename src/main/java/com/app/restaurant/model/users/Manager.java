package com.app.restaurant.model.users;

import com.app.restaurant.dto.ManagerDTO;
import com.app.restaurant.dto.UserDTO;
import com.app.restaurant.model.Role;
import com.app.restaurant.model.Salary;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter @Setter
@PrimaryKeyJoinColumn(name = "user")
public class Manager extends User {
    public Manager(Manager man) {
        super(man.getId(), man.getName(), man.getLastName(), man.getUsername(), man.getEmailAddress(), man.getPassword(), man.getDeleted(), man.getSalary(), man.getRole());
    }


    public Manager(ManagerDTO man) {
        super(man.getId(), man.getName(), man.getLastName(), man.getUsername(), man.getEmailAddress(), man.getPassword(), man.getDeleted(), null, null);
    }


    public Manager() {

    }
}
