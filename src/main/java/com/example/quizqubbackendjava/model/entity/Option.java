package com.example.quizqubbackendjava.model.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = {
            DETACH, MERGE, PERSIST, REFRESH
    })
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;
}
