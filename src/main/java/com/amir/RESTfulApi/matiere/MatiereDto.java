package com.amir.RESTfulApi.matiere;

import lombok.Builder;
import lombok.Data;

public class MatiereDto {

    @Data
    @Builder
    public static class CreateMatiereRequest {
        private Integer anneeScolaire;
        private Integer trimestre;
        private String name;
        private Double noteExam;
        private Double noteDevoir1;
        private Double noteDevoir2;
        private Integer userId; // Added user ID field
    }

    @Data
    @Builder
    public static class UpdateMatiereRequest {
        private Integer anneeScolaire;
        private Integer trimestre;
        private String name;
        private Double noteExam;
        private Double noteDevoir1;
        private Double noteDevoir2;
        private Integer userId; // Added user ID field
    }

    @Data
    @Builder
    public static class MatiereResponse {
        private Integer matiereId;
        private Integer anneeScolaire;
        private Integer trimestre;
        private String name;
        private Double noteExam;
        private Double noteDevoir1;
        private Double noteDevoir2;
        private Double averageGrade;
        private Integer userId; // Added user ID field
        private String userFirstname; // Added user firstname for better response
        private String userLastname; // Added user lastname for better response
    }
}