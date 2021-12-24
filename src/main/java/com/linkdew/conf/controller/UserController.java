package com.linkdew.conf.controller;

import com.linkdew.conf.interf.UserInterface;
import com.linkdew.conf.domain.Role;
import com.linkdew.conf.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserInterface userInterface;

    public UserController(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @GetMapping
    public String all(Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        List<User> users = userInterface.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("{/user}")
    public String byId(@PathVariable User user, Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        model.addAttribute("user", user);
        return "user-info";
    }

    @GetMapping("/roles/{id}")
    public String changeRoles(@PathVariable Long id,
                              @RequestParam(required = false) boolean isAdmin,
                              @RequestParam(required = false) boolean isUser){
        List<Role> roles = new ArrayList<>();
        if(isAdmin)
            roles.add(Role.ADMIN);
        if(isUser)
            roles.add(Role.USER);
        System.out.println(roles);
        userInterface.changeRoles(id, roles);
        return "redirect:/users";
    }
}
