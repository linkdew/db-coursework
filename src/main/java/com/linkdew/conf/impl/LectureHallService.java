package com.linkdew.conf.impl;

import com.linkdew.conf.domain.Hall;
import com.linkdew.conf.repository.LectureHallRepository;
import com.linkdew.conf.interf.LectureHallInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureHallService implements LectureHallInterface {

    private final LectureHallRepository lectureHallRepository;

    public LectureHallService(LectureHallRepository lectureHallRepository) {
        this.lectureHallRepository = lectureHallRepository;
    }

    @Override
    public Hall add(Hall hall) {
        return lectureHallRepository.save(hall);
    }

    @Override
    public List<Hall> findAll() {
        return lectureHallRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        lectureHallRepository.deleteById(id);
    }
}
