package com.linkdew.conf.interf;

import com.linkdew.conf.domain.Conference;
import java.util.List;

public interface ConferenceInterface {
    Conference add(String name, String director, String info, String description);

    List<Conference> findAll();

    Conference findById(Long id);

    List<Conference> findByName(String name);

    void deleteById(Long id);
}
