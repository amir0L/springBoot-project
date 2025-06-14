package com.amir.RESTfulApi.answer;

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
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answerId")
    private Integer answerId;

    @Column(name = "questionId", nullable = false)
    private Integer questionId;

    @Column(name = "contentAnswer", length = 500)
    private String contentAnswer;

    @Column(name = "isCorrect",columnDefinition = "TINYINT(1)")
    private Boolean isCorrect;
}
