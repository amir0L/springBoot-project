package com.amir.RESTfulApi.answer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    // Find answers by question ID
    List<Answer> findByQuestionId(Integer questionId);

    // Find correct/incorrect answers by question ID
    List<Answer> findByQuestionIdAndIsCorrect(Integer questionId, Boolean isCorrect);

    // Find all correct/incorrect answers
    List<Answer> findByIsCorrect(Boolean isCorrect);

    // Search answers by content
    @Query("SELECT a FROM Answer a WHERE a.contentAnswer LIKE %:content%")
    List<Answer> findByContentAnswerContaining(@Param("content") String content);

    // Count answers for a specific question
    @Query("SELECT COUNT(a) FROM Answer a WHERE a.questionId = :questionId")
    Long countByQuestionId(@Param("questionId") Integer questionId);

    // Find answers by multiple question IDs
    @Query("SELECT a FROM Answer a WHERE a.questionId IN :questionIds")
    List<Answer> findByQuestionIdIn(@Param("questionIds") List<Integer> questionIds);
}