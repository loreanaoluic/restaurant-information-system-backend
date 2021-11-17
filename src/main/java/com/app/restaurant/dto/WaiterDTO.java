package com.app.restaurant.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WaiterDTO {
    private Integer id;
    private String name;
    private String lastName;
    private String emailAddress;
    private String username;
    private String password;
    private Boolean deleted;
}
