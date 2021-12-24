package com.linkdew.conf.repository;

import com.linkdew.conf.domain.Conference;
import com.linkdew.conf.domain.Hall;
import com.linkdew.conf.domain.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    void deleteAllByHall(Hall hall);
    void deleteAllByConference(Conference conference);
    List<Meeting> findAllByDate(Date date);
    List<Meeting> findAllByDateBetween(Date start, Date end);
    List<Meeting> findAllByConference(Conference conference);
}
