package com.app.restaurant.model.users;

import com.app.restaurant.model.Role;
import com.app.restaurant.model.Salary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public abstract class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "userSeqGen", sequenceName = "userSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email_address", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;

    @OneToMany
    private List<Salary> salary;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(this.role);
    }

    @Override
    public String getUsername() {
        return getEmailAddress();
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
