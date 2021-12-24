package com.linkdew.conf.controller;

import com.linkdew.conf.interf.UserInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping
public class AuthController {
    private final UserInterface userInterface;

    public AuthController(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @GetMapping
    public String login() {
        return "redirect:/conferences";
    }

    @GetMapping("/register")
    public String registration(Model model) {
        model.addAttribute("isAuthenticated", false);
        model.addAttribute("isAdmin", false);
        return "registration";
    }

    @PostMapping("/register")
    public String register(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "email") String email,
                           @RequestParam(value = "birthDate") String birthDate,
                           Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date parsed = null;
        try {
            parsed = formatter.parse(birthDate);
        } catch (ParseException e) {
            model.addAttribute("dateMessage", "please, enter correct date");
        }
        if(userInterface.isUnique(username) && parsed != null) {
            userInterface.add(username, password, name, email, parsed);
            return "redirect:/login";
        }
        model.addAttribute("isAuthenticated", false);
        model.addAttribute("isAdmin", false);
        model.addAttribute("usernameMessage", "username must be unique");
        return "registration";
    }

}
