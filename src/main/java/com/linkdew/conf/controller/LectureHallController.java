package com.linkdew.conf.controller;

import com.linkdew.conf.domain.Hall;
import com.linkdew.conf.domain.LectureHallPlace;
import com.linkdew.conf.domain.User;
import com.linkdew.conf.interf.LectureHallInterface;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LectureHallController {
    private final LectureHallInterface lectureHallInterface;

    public LectureHallController(LectureHallInterface lectureHallInterface) {
        this.lectureHallInterface = lectureHallInterface;
    }

    @GetMapping("/halls")
    public String all(@AuthenticationPrincipal User user, Model model) {
        checkUser(user, model);
        List<Hall> halls = lectureHallInterface.findAll();
        model.addAttribute("halls", halls);
        return "lecturehalls";
    }

    @GetMapping("/add/hall")
    public String addPage(Model model, @AuthenticationPrincipal User user) {
        checkUser(user, model);
        return "lecturehall-addition";
    }

    @PostMapping("/add/hall")
    public String add(@RequestParam(value = "name") String name,
                    @RequestParam(value = "capacity") int capacity,
                    @RequestParam(value = "lectureHallPlace") LectureHallPlace lectureHallPlace) {
            Hall hall = new Hall(name, capacity, lectureHallPlace);
            lectureHallInterface.add(hall);
        return "redirect:/halls";
    }

    @GetMapping("/delete/hall/{hall}")
    public String delete(@PathVariable Hall hall){
        if(hall.getMeetings().isEmpty())
            lectureHallInterface.deleteById(hall.getId());
        return "redirect:/halls";
    }

    private void checkUser(@AuthenticationPrincipal User user, Model model) {
        boolean isAuthenticated = false;
        boolean isAdmin = false;
        if(user != null) {
            isAuthenticated = true;
            if (user.isAdmin())
                isAdmin = true;
        }
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAdmin);
    }
}
