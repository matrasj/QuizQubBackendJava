package com.example.quizqubbackendjava.model.entity;

import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    @OneToMany(cascade = ALL, mappedBy = "question")
    private Set<Option> options = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "correct_option_id", referencedColumnName = "id")
    private Option correctOption;

}
