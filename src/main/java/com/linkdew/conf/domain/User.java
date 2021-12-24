package com.linkdew.conf.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.*;


@Entity
@Data
@NoArgsConstructor
@EnableAutoConfiguration
@Table(name = "users")
public final class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "username cannot be empty")
    @Column(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean active;

    @NotBlank(message = "name cannot be empty")
    private String name;

    @Email(message = "invalid email")
    @NotBlank(message = "email cannot be empty")
    private String email;

    private Date birthDate;

    @JsonBackReference
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles;


    public User(@NotBlank(message = "username cannot be empty") String username,
                String password,
                @NotBlank(message = "name cannot be empty") String name,
                @Email(message = "email is not correct")
                @NotBlank(message = "email cannot be empty") String email,
                Date birthDate) {
        roles = new ArrayList<>();
        this.username = username;
        this.password = password;
        active = true;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        roles.add(Role.USER);
    }

    public boolean isUser() {
        return roles.contains(Role.USER);
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
