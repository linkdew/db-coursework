package com.linkdew.conf.repository;

import com.linkdew.conf.domain.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureHallRepository extends JpaRepository<Hall, Long> {

}
