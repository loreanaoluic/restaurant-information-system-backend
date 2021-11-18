package com.app.restaurant.dto;


import com.app.restaurant.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManagerDTO {
    private Integer id;
    private String name;
    private String lastName;
    private String emailAddress;
    private String username;
    private String password;
    private Boolean deleted;
}
