package com.app.restaurant.model.users;

import com.app.restaurant.model.Role;
import com.app.restaurant.model.Salary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString(exclude = "salary")
public abstract class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email_address", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @OneToOne(cascade = CascadeType.ALL)
    private Salary salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(this.role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
