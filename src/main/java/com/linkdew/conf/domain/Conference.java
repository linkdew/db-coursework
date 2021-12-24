package com.linkdew.conf.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@EnableAutoConfiguration
@Table(name = "conferences")
public final class Conference {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String lecturer;

    private String info;

    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "conference", fetch = FetchType.LAZY)
    private List<Meeting> meetings;

    public Conference(String name, String lecturer, String info, String description) {
        this.name = name;
        this.lecturer = lecturer;
        this.info = info;
        this.description = description;
        this.meetings = new ArrayList<>();
    }
}
