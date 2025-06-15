package com.amir.RESTfulApi.question;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionId")
    private Integer questionId;

    @Column(name = "contentQuestion", columnDefinition = "MEDIUMTEXT")
    private String contentQuestion;

    @Column(name = "type")
    private Integer type;

    @Column(name = "anneeScolaire")
    private Integer anneeScolaire;

    @Column(name = "trimestre")
    private Integer trimestre;

    @Column(name = "unitNumber")
    private Integer unitNumber;

    @Column(name = "matiereName")
    private String matiereName;
}
