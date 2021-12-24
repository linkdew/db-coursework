package com.linkdew.conf.impl;

import com.linkdew.conf.domain.Role;
import com.linkdew.conf.domain.User;
import com.linkdew.conf.repository.UserRepository;
import com.linkdew.conf.interf.UserInterface;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService, UserInterface {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("not found");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User add(String username,
                    String password,
                    String name,
                    String email,
                    Date birthDate) {

        User user = new User(username,
                        passwordEncoder.encode(password),
                        name,
                        email,
                        birthDate);
        return userRepository.save(user);
    }

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void changeRoles(Long id, List<Role> roles) {
        User user = userRepository.findById(id).get();
        user.getRoles().clear();
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public boolean isUnique(String username) {
        return userRepository.findByUsername(username) == null;
    }
}
