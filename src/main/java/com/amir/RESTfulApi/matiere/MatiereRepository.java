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

    // Custom query to get overall average grades
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 3) " +
            "FROM Matiere m")
    Double getOverallAverageGrades();

    // Custom query to get overall average grades for a specific user
    @Query("SELECT AVG((COALESCE(m.noteExam, 0) + COALESCE(m.noteDevoir1, 0) + COALESCE(m.noteDevoir2, 0)) / 3) " +
            "FROM Matiere m WHERE m.user.id = :userId")
    Double getOverallAverageGradesByUserId(@Param("userId") Integer userId);

    // Check if matiere exists by name, year, trimester and user
    boolean existsByNameAndAnneeScolaireAndTrimestreAndUser_Id(String name, Integer anneeScolaire, Integer trimestre, Integer userId);

    // Get matieres with grade calculations
    @Query("SELECT m FROM Matiere m ORDER BY m.anneeScolaire DESC, m.trimestre DESC")
    List<Matiere> findAllOrderedByYearAndTrimester();

    // Get matieres by user ID with grade calculations
    @Query("SELECT m FROM Matiere m WHERE m.user.id = :userId ORDER BY m.anneeScolaire DESC, m.trimestre DESC")
    List<Matiere> findByUserIdOrderedByYearAndTrimester(@Param("userId") Integer userId);
}