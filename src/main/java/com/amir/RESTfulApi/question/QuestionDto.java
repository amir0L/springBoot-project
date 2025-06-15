package com.amir.RESTfulApi.question;

import lombok.Builder;
import lombok.Data;

public class QuestionDto {

    @Data
    @Builder
    public static class CreateQuestionRequest {
        private String contentQuestion;
        private Integer type;
        private Integer anneeScolaire;
        private Integer trimestre;
        private Integer unitNumber;
        private String matiereName;
    }

    @Data
    @Builder
    public static class UpdateQuestionRequest {
        private String contentQuestion;
        private Integer type;
        private Integer anneeScolaire;
        private Integer trimestre;
        private Integer unitNumber;
        private String matiereName;
    }

    @Data
    @Builder
    public static class QuestionResponse {
        private Integer questionId;
        private String contentQuestion;
        private Integer type;
        private Integer anneeScolaire;
        private Integer trimestre;
        private Integer unitNumber;
        private String matiereName;
    }
}