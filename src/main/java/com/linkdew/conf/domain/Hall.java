package com.linkdew.conf.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@EnableAutoConfiguration
@Table(name = "lecturehalls")
public final class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "blank name")
    private String name;

    private int capacity;

    @Enumerated(EnumType.STRING)
    private LectureHallPlace lectureHallPlace;

    @JsonBackReference
    @OneToMany(mappedBy = "hall", fetch = FetchType.EAGER)
    private List<Meeting> meetings;

    public Hall(@NotBlank(message = "blank name") String name, int capacity, LectureHallPlace lectureHallPlace) {
        this.name = name;
        this.capacity = capacity;
        this.lectureHallPlace = lectureHallPlace;
        meetings = new ArrayList<>();
    }
}
