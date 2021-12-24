package com.linkdew.conf.controller;

import com.linkdew.conf.interf.ConferenceInterface;
import com.linkdew.conf.domain.Conference;
import com.linkdew.conf.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ConferenceController {

    private final ConferenceInterface conferenceInterface;

    public ConferenceController(ConferenceInterface conferenceInterface) {
        this.conferenceInterface = conferenceInterface;
    }

    @GetMapping("/conferences")
    public String all(Model model, @AuthenticationPrincipal User user) {
        checkUser(user, model);
        List<Conference> conferences = conferenceInterface.findAll();
        model.addAttribute("conferences", conferences);
        return "conferences";
    }

    @GetMapping("/conference/{id}")
    public String byId(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", false);
        checkUser(user, model);
        Conference conference = conferenceInterface.findById(id);
        model.addAttribute("conference", conference);
        return "conference-info";
    }

    @GetMapping("/conference/find-by-name")
    public List<Conference> byName(@RequestParam String name, Model model, @AuthenticationPrincipal User user) {
        List<Conference> conferences = conferenceInterface.findByName(name);
        model.addAttribute("conferences", conferences);
        return conferenceInterface.findByName(name);
    }

    @GetMapping("/add/conference")
    public String addPage(Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        return "conference-addition";
    }

    @PostMapping("/add/conference")
    public String add(@RequestParam(value = "name") String name,
                      @RequestParam(value = "lecturer") String lecturer,
                      @RequestParam(value = "info") String info,
                      @RequestParam(value = "description", required = false) String description,
                      Model model
    ) {
        conferenceInterface.add(name, lecturer, info, description);
        return "redirect:/conferences";
    }

    @GetMapping("/delete/conference/{conference}")
    public String delete(@PathVariable Conference conference,
                         Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        if (conference.getMeetings().isEmpty()) {
            conferenceInterface.deleteById(conference.getId());
            return "redirect:/conferences";
        }
        model.addAttribute("message", "cannot be deleted");
        model.addAttribute("conference", conference);
        return "conference-info";
    }

    private void checkUser(@AuthenticationPrincipal User user, Model model) {
        boolean isAuthenticated = false;
        boolean isAdmin = false;
        if(user != null) { isAuthenticated = true;
            if (user.isAdmin())  isAdmin = true;
        }
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAdmin);
    }
}
