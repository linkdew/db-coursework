package com.linkdew.conf.interf;

import com.linkdew.conf.domain.Hall;

import java.util.List;

public interface LectureHallInterface {
    Hall add(Hall hall);

    List<Hall> findAll();

    void deleteById(Long id);
}
