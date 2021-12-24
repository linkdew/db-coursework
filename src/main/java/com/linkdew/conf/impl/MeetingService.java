package com.linkdew.conf.impl;

import com.linkdew.conf.domain.Conference;
import com.linkdew.conf.domain.LectureHallPlace;
import com.linkdew.conf.domain.Meeting;
import com.linkdew.conf.repository.MeetingRepository;
import com.linkdew.conf.interf.MeetingInterface;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MeetingService implements MeetingInterface {

    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public Meeting add(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> findAll(String start, String end, LectureHallPlace lectureHallPlace) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
        Date startDate;
        Date endDate;
        if(start == null && end == null && lectureHallPlace == null)
            return meetingRepository.findAll();
        if(start == null || start.isBlank())
            startDate = calendar.getTime();
        else startDate = formatter.parse(start);
        if(end == null || end.isBlank()) {
            calendar.add(Calendar.DATE, 10);
            endDate = calendar.getTime();
        }
        else endDate = formatter.parse(end);
        if(lectureHallPlace == null)
            return meetingRepository.findAllByDateBetween(startDate, endDate);
        List<Meeting> meetings = findByDateAndHallType(startDate, endDate, lectureHallPlace);
        Collections.sort(meetings);
        return meetings;
    }

    @Override
    public List<Meeting> findByConference(Conference conference) {
        List<Meeting> meetings = meetingRepository.findAllByConference(conference);
        Collections.sort(meetings);
        return meetings;
    }

    public List<Meeting> findByDateAndHallType(Date start, Date end, LectureHallPlace lectureHallPlace) {
        List<Meeting> meetings = meetingRepository.findAllByDateBetween(start, end);
        List<Meeting> matchingMeetings = new ArrayList<>();
        for (Meeting meeting :
                meetings) {
            if (meeting.getHall().getLectureHallPlace() == lectureHallPlace)
                matchingMeetings.add(meeting);
        }
        return matchingMeetings;
    }

    @Override
    public void deleteById(Long id) {
        meetingRepository.deleteById(id);
    }

    @Override
    public void deleteAllByConference(Conference conference) {
        meetingRepository.deleteAllByConference(conference);
    }

}
