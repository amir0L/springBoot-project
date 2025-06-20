package com.amir.RESTfulApi.matiere;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Integer> {

    // Find matieres by user ID
    List<Matiere> findByUser_Id(Integer userId);

    // Find matieres by school year
    List<Matiere> findByAnneeScolaire(Integer anneeScolaire);

    // Find matieres by school year and trimester
    List<Matiere> findByAnneeScolaireAndTrimestre(Integer anneeScolaire, Integer trimestre);

    // Find matieres by user ID and school year
    List<Matiere> findByUser_IdAndAnneeScolaire(Integer userId, Integer anneeScolaire);

    // Find matieres by user ID, school year and trimester
    List<Matiere> findByUser_IdAndAnneeScolaireAndTrimestre(Integer userId, Integer anneeScolaire, Integer trimestre);

    // Find matieres by name (case insensitive)
    List<Matiere> findByNameContainingIgnoreCase(String name);

    // Find matieres by name and user ID (case insensitive)
    List<Matiere> findByNameContainingIgnoreCaseAndUser_Id(String name, Integer userId);

    // Custom query to get overall average grades (all three grades)
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 3) " +
            "FROM Matiere m")
    Double getOverallAverageGrades();

    // Custom query to get overall average grades for a specific user (all three grades)
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 3) " +
            "FROM Matiere m WHERE m.user.id = :userId")
    Double getOverallAverageGradesByUserId(@Param("userId") Integer userId);

    // Custom query to get overall average grades with only one homework (noteDevoir1)
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0)) / 2) " +
            "FROM Matiere m")
    Double getOverallAverageGradesOneHomework();

    // Custom query to get overall average grades for a specific user with only one homework (noteDevoir1)
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0)) / 2) " +
            "FROM Matiere m WHERE m.user.id = :userId")
    Double getOverallAverageGradesOneHomeworkByUserId(@Param("userId") Integer userId);

    // Custom query to get overall average grades with two homeworks (noteDevoir1 and noteDevoir2)
    @Query("SELECT AVG((COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 2) " +
            "FROM Matiere m")
    Double getOverallAverageGradesTwoHomeworks();

    // Custom query to get overall average grades for a specific user with two homeworks (noteDevoir1 and noteDevoir2)
    @Query("SELECT AVG((COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 2) " +
            "FROM Matiere m WHERE m.user.id = :userId")
    Double getOverallAverageGradesTwoHomeworksByUserId(@Param("userId") Integer userId);

    // Check if matiere exists by name, year, trimester and user
    boolean existsByNameAndAnneeScolaireAndTrimestreAndUser_Id(String name, Integer anneeScolaire, Integer trimestre, Integer userId);

    // Get matieres with grade calculations
    @Query("SELECT m FROM Matiere m ORDER BY m.anneeScolaire DESC, m.trimestre DESC")
    List<Matiere> findAllOrderedByYearAndTrimester();

    // Get matieres by user ID with grade calculations
    @Query("SELECT m FROM Matiere m WHERE m.user.id = :userId ORDER BY m.anneeScolaire DESC, m.trimestre DESC")
    List<Matiere> findByUserIdOrderedByYearAndTrimester(@Param("userId") Integer userId);

    // Custom query to get average grade for a specific matiere by user ID and matiere name (all three grades)
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 3) " +
            "FROM Matiere m WHERE m.user.id = :userId AND LOWER(m.name) = LOWER(:matiereName)")
    Double getAverageGradeForSpecificMatiereByUser(@Param("userId") Integer userId, @Param("matiereName") String matiereName);

    // Custom query to get average grade for a specific matiere by user ID and matiere name (exam + homework1)
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0)) / 2) " +
            "FROM Matiere m WHERE m.user.id = :userId AND LOWER(m.name) = LOWER(:matiereName)")
    Double getAverageGradeForSpecificMatiereByUserOneHomework(@Param("userId") Integer userId, @Param("matiereName") String matiereName);

    // Custom query to get average grade for a specific matiere by user ID and matiere name (homework1 + homework2)
    @Query("SELECT AVG((COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 2) " +
            "FROM Matiere m WHERE m.user.id = :userId AND LOWER(m.name) = LOWER(:matiereName)")
    Double getAverageGradeForSpecificMatiereByUserTwoHomeworks(@Param("userId") Integer userId, @Param("matiereName") String matiereName);

    // Custom query to get average grade for a specific matiere by user ID, matiere name, and school year (all three grades)
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 3) " +
            "FROM Matiere m WHERE m.user.id = :userId AND LOWER(m.name) = LOWER(:matiereName) AND m.anneeScolaire = :anneeScolaire")
    Double getAverageGradeForSpecificMatiereByUserAndYear(@Param("userId") Integer userId, @Param("matiereName") String matiereName, @Param("anneeScolaire") Integer anneeScolaire);

    // Custom query to get average grade for a specific matiere by user ID, matiere name, school year, and trimester (all three grades)
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 3) " +
            "FROM Matiere m WHERE m.user.id = :userId AND LOWER(m.name) = LOWER(:matiereName) AND m.anneeScolaire = :anneeScolaire AND m.trimestre = :trimestre")
    Double getAverageGradeForSpecificMatiereByUserYearAndTrimester(@Param("userId") Integer userId, @Param("matiereName") String matiereName, @Param("anneeScolaire") Integer anneeScolaire, @Param("trimestre") Integer trimestre);
}