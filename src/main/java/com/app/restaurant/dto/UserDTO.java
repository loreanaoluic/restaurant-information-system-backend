package com.app.restaurant.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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
