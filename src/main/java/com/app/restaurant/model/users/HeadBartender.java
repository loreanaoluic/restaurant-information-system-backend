package com.app.restaurant.model.users;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "user")
public class HeadBartender extends User {

}
