package com.linkdew.conf.interf;

import com.linkdew.conf.domain.Role;
import com.linkdew.conf.domain.User;

import java.util.Date;
import java.util.List;

public interface UserInterface {
    List<User> findAll();

    User add(User user);

    User add(String username, String password, String name, String email, Date birthDate);

    void deleteById(Long id);

    void changeRoles(Long id, List<Role> roles);

    boolean isUnique(String username);
}
