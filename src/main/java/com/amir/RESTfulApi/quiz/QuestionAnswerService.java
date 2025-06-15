package com.amir.RESTfulApi.quiz;


import com.amir.RESTfulApi.answer.AnswerDto;
import com.amir.RESTfulApi.answer.AnswerService;
import com.amir.RESTfulApi.question.QuestionDto;
import com.amir.RESTfulApi.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionAnswerService {

    private final QuestionService questionService;
    private final AnswerService answerService;

    public Optional<QuestionAnswerDto.QuestionWithAnswersResponse> getQuestionWithAnswersById(
            Integer questionId, QuestionAnswerDto.QuestionAnswerRequest request) {

        // Get the question by ID
        Optional<QuestionDto.QuestionResponse> questionOpt = questionService.getQuestionById(questionId);

        if (questionOpt.isEmpty()) {
            return Optional.empty();
        }

        QuestionDto.QuestionResponse question = questionOpt.get();

        // Validate the question matches the criteria
        if (!question.getMatiereName().equals(request.getMatiereName()) ||
                !question.getAnneeScolaire().equals(request.getAnneeScolaire()) ||
                !question.getTrimestre().equals(request.getTrimestre())) {
            return Optional.empty();
        }

        // Get answers for this question
        List<AnswerDto> answers = answerService.getAnswersByQuestionId(questionId);

        // Convert answers to AnswerInfo
        List<QuestionAnswerDto.AnswerInfo> answerInfos = answers.stream()
                .map(this::convertToAnswerInfo)
                .collect(Collectors.toList());

        // Count correct answers
        long correctAnswers = answers.stream()
                .mapToLong(answer -> answer.getIsCorrect() != null && answer.getIsCorrect() ? 1 : 0)
                .sum();

        return Optional.of(QuestionAnswerDto.QuestionWithAnswersResponse.builder()
                .questionId(question.getQuestionId())
                .contentQuestion(question.getContentQuestion())
                .type(question.getType())
                .anneeScolaire(question.getAnneeScolaire())
                .trimestre(question.getTrimestre())
                .unitNumber(question.getUnitNumber())
                .matiereName(question.getMatiereName())
                .answers(answerInfos)
                .totalAnswers((long) answers.size())
                .correctAnswers(correctAnswers)
                .build());
    }

    public QuestionAnswerDto.QuestionsAnswersResponse getAllQuestionsWithAnswers(
            QuestionAnswerDto.QuestionAnswerRequest request) {

        // Get all questions for the specified matiere, year and trimester
        List<QuestionDto.QuestionResponse> questions = questionService
                .getQuestionsByMatiereNameYearAndTrimester(
                        request.getMatiereName(),
                        request.getAnneeScolaire(),
                        request.getTrimestre());

        // Convert each question to QuestionWithAnswersResponse
        List<QuestionAnswerDto.QuestionWithAnswersResponse> questionsWithAnswers = questions.stream()
                .map(question -> {
                    // Get answers for this question
                    List<AnswerDto> answers = answerService.getAnswersByQuestionId(question.getQuestionId());

                    // Convert answers to AnswerInfo
                    List<QuestionAnswerDto.AnswerInfo> answerInfos = answers.stream()
                            .map(this::convertToAnswerInfo)
                            .collect(Collectors.toList());

                    // Count correct answers
                    long correctAnswers = answers.stream()
                            .mapToLong(answer -> answer.getIsCorrect() != null && answer.getIsCorrect() ? 1 : 0)
                            .sum();

                    return QuestionAnswerDto.QuestionWithAnswersResponse.builder()
                            .questionId(question.getQuestionId())
                            .contentQuestion(question.getContentQuestion())
                            .type(question.getType())
                            .anneeScolaire(question.getAnneeScolaire())
                            .trimestre(question.getTrimestre())
                            .unitNumber(question.getUnitNumber())
                            .matiereName(question.getMatiereName())
                            .answers(answerInfos)
                            .totalAnswers((long) answers.size())
                            .correctAnswers(correctAnswers)
                            .build();
                })
                .collect(Collectors.toList());

        return QuestionAnswerDto.QuestionsAnswersResponse.builder()
                .matiereName(request.getMatiereName())
                .anneeScolaire(request.getAnneeScolaire())
                .trimestre(request.getTrimestre())
                .totalQuestions((long) questions.size())
                .questions(questionsWithAnswers)
                .build();
    }

    private QuestionAnswerDto.AnswerInfo convertToAnswerInfo(AnswerDto answerDto) {
        return QuestionAnswerDto.AnswerInfo.builder()
                .answerId(answerDto.getAnswerId())
                .contentAnswer(answerDto.getContentAnswer())
                .isCorrect(answerDto.getIsCorrect())
                .build();
    }
}
