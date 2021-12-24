package com.linkdew.conf.impl;

import com.linkdew.conf.domain.Conference;
import com.linkdew.conf.repository.ConferenceRepository;
import com.linkdew.conf.interf.ConferenceInterface;
import com.linkdew.conf.interf.MeetingInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConferenceService implements ConferenceInterface {

    private final ConferenceRepository conferenceRepository;

    private final MeetingInterface meetingInterface;

    public ConferenceService(ConferenceRepository conferenceRepository, MeetingInterface meetingInterface) {
        this.conferenceRepository = conferenceRepository;
        this.meetingInterface = meetingInterface;
    }

    @Override
    public Conference add(String name, String lecturer, String info, String description) {
        Conference conference = new Conference(name, lecturer, info, description);
        return conferenceRepository.save(conference);
    }

    @Override
    public List<Conference> findAll() {
        return conferenceRepository.findAll();
    }

    @Override
    public Conference findById(Long id) {
        return conferenceRepository.findById(id).get();
    }

    @Override
    public List<Conference> findByName(String name) {
        return conferenceRepository.findAllByName(name);
    }

    @Override
    public void deleteById(Long id) {
        Conference conference = conferenceRepository.getOne(id);
        meetingInterface.deleteAllByConference(conference);
        conferenceRepository.deleteById(id);
    }
}
