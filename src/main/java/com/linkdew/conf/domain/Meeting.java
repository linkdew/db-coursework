package com.linkdew.conf.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@EnableAutoConfiguration
@Table(name = "meetings")
public final class Meeting implements Comparable<Meeting> {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date date;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lecturehall_id")
    private Hall hall;



    public Meeting(Date date, Conference conference, Hall hall) {
        this.date = date;
        this.conference = conference;
        this.hall = hall;
    }

    @Override
    public int compareTo(Meeting m) {
        return this.getDate().compareTo(m.getDate());
    }
}
