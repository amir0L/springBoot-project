package com.amir.RESTfulApi.user;



import lombok.Builder;
import lombok.Data;

import java.util.List;

public class UserDto {

    @Data
    @Builder
    public static class CreateUserRequest {
        private String firstname;
        private String lastname;
        private String email;
        private String password;
    }

    @Data
    @Builder
    public static class UpdateUserRequest {
        private String firstname;
        private String lastname;
        private String email;
        private String password;
    }

    @Data
    @Builder
    public static class UserResponse {
        private Integer id;
        private String firstname;
        private String lastname;
        private String email;
        private Integer matieresCount; // Number of matieres associated with this user
    }

    @Data
    @Builder
    public static class UserWithMatieresResponse {
        private Integer id;
        private String firstname;
        private String lastname;
        private String email;
        private List<MatiereBasicInfo> matieres;
    }

    @Data
    @Builder
    public static class MatiereBasicInfo {
        private Integer matiereId;
        private String name;
        private Integer anneeScolaire;
        private Integer trimestre;
        private Double averageGrade;
    }

    @Data
    @Builder
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Data
    @Builder
    public static class LoginResponse {
        private Integer id;
        private String firstname;
        private String lastname;
        private String email;
        private String message;
    }
}
