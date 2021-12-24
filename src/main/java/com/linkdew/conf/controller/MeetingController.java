package com.linkdew.conf.controller;

import com.linkdew.conf.domain.*;
import com.linkdew.conf.interf.LectureHallInterface;
import com.linkdew.conf.interf.MeetingInterface;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MeetingController {

    private final MeetingInterface meetingInterface;

    private final LectureHallInterface lectureHallInterface;

    public MeetingController(MeetingInterface meetingInterface, LectureHallInterface lectureHallInterface) {
        this.meetingInterface = meetingInterface;
        this.lectureHallInterface = lectureHallInterface;
    }

    @GetMapping("/meetings")
    public String all(@RequestParam(required = false) String startDate,
                      @RequestParam(required = false) String endDate,
                      @RequestParam(required = false) String lectureHallPlace,
                      @AuthenticationPrincipal User user,
                      Model model) throws ParseException {
        checkUser(user, model);
        LectureHallPlace place = null;
        if(lectureHallPlace != null) {
            if (lectureHallPlace.equals("any")) place = null;
            else place = LectureHallPlace.valueOf(lectureHallPlace);
        }
        List<Meeting> meetings = meetingInterface.findAll(startDate, endDate, place);
        model.addAttribute("meetings", meetings);
        return "meetings";
    }

    @GetMapping("meeting/conference/{conference}")
    public String findByConference(@PathVariable Conference conference,
                                     @AuthenticationPrincipal User user,
                                     Model model) {
        checkUser(user, model);
        List<Meeting> meetings = meetingInterface.findByConference(conference);
        model.addAttribute("meetings", meetings);
        return "meetings";
    }

    @GetMapping("/add/meeting/{conference}")
    public String addPage(@PathVariable(value = "conference") Conference conference,
                          Model model,
                          @AuthenticationPrincipal User user) {
        model.addAttribute("halls", lectureHallInterface.findAll());
        model.addAttribute("conference", conference);
        checkUser(user, model);
        return "meeting-addition";
    }

    @PostMapping("/add/meeting/{conference}")
    public String add(@PathVariable(value = "conference") Conference conference,
                      @RequestParam(value = "date") String date,
                      @RequestParam(value = "hall") Hall hall,
                      Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
        Date parsed;
        try {
            parsed = formatter.parse(date);
        } catch (ParseException e) {
            model.addAttribute("message", "invalid date");
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("isAdmin", true);
            model.addAttribute("halls", lectureHallInterface.findAll());
            model.addAttribute("conference", conference);
            return "meeting-addition";
        }
        Meeting meeting = new Meeting(parsed, conference, hall);
        meetingInterface.add(meeting);
        return "redirect:/meetings";
    }

    @GetMapping("/delete/meeting/{id}")
    public String delete(@PathVariable Long id){
        meetingInterface.deleteById(id);
        return "redirect:/meetings";
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
