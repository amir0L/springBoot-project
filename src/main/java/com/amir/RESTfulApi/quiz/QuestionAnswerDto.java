package com.amir.RESTfulApi.quiz;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class QuestionAnswerDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionAnswerRequest {
        private String matiereName;
        private Integer anneeScolaire;
        private Integer trimestre;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnswerInfo {
        private Integer answerId;
        private String contentAnswer;
        private Boolean isCorrect;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionWithAnswersResponse {
        private Integer questionId;
        private String contentQuestion;
        private Integer type;
        private Integer anneeScolaire;
        private Integer trimestre;
        private Integer unitNumber;
        private String matiereName;
        private List<AnswerInfo> answers;
        private Long totalAnswers;
        private Long correctAnswers;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionsAnswersResponse {
        private String matiereName;
        private Integer anneeScolaire;
        private Integer trimestre;
        private Long totalQuestions;
        private List<QuestionWithAnswersResponse> questions;
    }
}