package com.linkdew.conf.interf;

import com.linkdew.conf.domain.Conference;
import com.linkdew.conf.domain.Meeting;
import com.linkdew.conf.domain.LectureHallPlace;

import java.text.ParseException;
import java.util.List;

public interface MeetingInterface {
    Meeting add(Meeting meeting);

    List<Meeting> findByConference(Conference conference);

    List<Meeting> findAll(String start, String end, LectureHallPlace lectureHallPlace) throws ParseException;

    void deleteById(Long id);

    void deleteAllByConference(Conference conference);
}
