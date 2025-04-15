package com.system.pos.entities;

import com.system.pos.entities.base.BaseSoftDeleteEntity;
import com.system.pos.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User extends BaseSoftDeleteEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    @Column(name = "username")
    private String userName;

    private String email;

    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String cpf;

    private Boolean active;

    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getUsername() {
        return userName;
    }

}
