package com.example.quizqubbackendjava.model.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "session")
public class Session {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne(cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "duration_max_minutes")
    private int minutesMaxDuration;

    @Column(name = "duration_time")
    private String durationTime;

    @Column(name = "finished")
    private boolean finished = false;

    @Column(name = "score")
    private double score;



}
