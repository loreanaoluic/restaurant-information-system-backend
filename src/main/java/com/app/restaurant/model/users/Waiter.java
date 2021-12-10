package com.app.restaurant.model.users;

import com.app.restaurant.dto.ManagerDTO;
import com.app.restaurant.dto.WaiterDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "user")
public class Waiter extends User {
    public Waiter(Waiter man) {
        super(man.getId(), man.getName(), man.getLastName(), man.getUsername(), man.getEmailAddress(), man.getPassword(), man.getDeleted(), man.getSalary(), man.getRole(), man.getLastPasswordResetDate());
    }

    public Waiter(WaiterDTO man) {
        super(man.getId(), man.getName(), man.getLastName(), man.getUsername(), man.getEmailAddress(), man.getPassword(), man.getDeleted(), null, null, null);
    }

    public Waiter() {

    }

}
