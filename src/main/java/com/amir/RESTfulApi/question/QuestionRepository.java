package com.amir.RESTfulApi.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    // Find questions by matiere name
    List<Question> findByMatiereName(String matiereName);

    // Find questions by school year
    List<Question> findByAnneeScolaire(Integer anneeScolaire);

    // Find questions by school year and trimester
    List<Question> findByAnneeScolaireAndTrimestre(Integer anneeScolaire, Integer trimestre);

    // Find questions by school year, trimester and unit
    List<Question> findByAnneeScolaireAndTrimestreAndUnitNumber(
            Integer anneeScolaire, Integer trimestre, Integer unitNumber);

    // Find questions by question type
    List<Question> findByType(Integer type);

    // Search questions by content (case insensitive)
    @Query("SELECT q FROM Question q WHERE LOWER(q.contentQuestion) LIKE LOWER(CONCAT('%', :content, '%'))")
    List<Question> findByContentQuestionContainingIgnoreCase(@Param("content") String content);

    // Count questions by matiere name
    Long countByMatiereName(String matiereName);

    // Get questions by matiere name, year and trimester
    List<Question> findByMatiereNameAndAnneeScolaireAndTrimestre(
            String matiereName, Integer anneeScolaire, Integer trimestre);

    // Get questions ordered by year, trimester, and unit
    @Query("SELECT q FROM Question q ORDER BY q.anneeScolaire DESC, q.trimestre DESC, q.unitNumber ASC")
    List<Question> findAllOrderedByYearTrimesterAndUnit();

    // Check if question exists by content and matiere name
    boolean existsByContentQuestionAndMatiereName(String contentQuestion, String matiereName);

    // Get questions by matiere name and year
    List<Question> findByMatiereNameAndAnneeScolaire(String matiereName, Integer anneeScolaire);

    // Get questions by matiere name and trimester
    List<Question> findByMatiereNameAndTrimestre(String matiereName, Integer trimestre);

}