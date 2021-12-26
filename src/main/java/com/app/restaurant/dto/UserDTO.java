package com.app.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String dtype;
    private String name;
    private String lastName;
    private String emailAddress;
    private String username;
    private String password;
    private double salary;
    private Boolean deleted;
}
