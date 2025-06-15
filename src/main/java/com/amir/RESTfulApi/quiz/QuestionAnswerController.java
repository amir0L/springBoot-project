package com.amir.RESTfulApi.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/question-answers")
@RequiredArgsConstructor
public class QuestionAnswerController {

    private final QuestionAnswerService questionAnswerService;

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionAnswerDto.QuestionWithAnswersResponse> getQuestionWithAnswersById(
            @PathVariable Integer questionId,
            @RequestParam String matiereName,
            @RequestParam Integer anneeScolaire,
            @RequestParam Integer trimestre) {

        QuestionAnswerDto.QuestionAnswerRequest request = QuestionAnswerDto.QuestionAnswerRequest.builder()
                .matiereName(matiereName)
                .anneeScolaire(anneeScolaire)
                .trimestre(trimestre)
                .build();

        Optional<QuestionAnswerDto.QuestionWithAnswersResponse> response =
                questionAnswerService.getQuestionWithAnswersById(questionId, request);

        return response.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{questionId}")
    public ResponseEntity<QuestionAnswerDto.QuestionWithAnswersResponse> getQuestionWithAnswersByIdPost(
            @PathVariable Integer questionId,
            @RequestBody QuestionAnswerDto.QuestionAnswerRequest request) {

        Optional<QuestionAnswerDto.QuestionWithAnswersResponse> response =
                questionAnswerService.getQuestionWithAnswersById(questionId, request);

        return response.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<QuestionAnswerDto.QuestionsAnswersResponse> getAllQuestionsWithAnswers(
            @RequestParam String matiereName,
            @RequestParam Integer anneeScolaire,
            @RequestParam Integer trimestre) {

        QuestionAnswerDto.QuestionAnswerRequest request = QuestionAnswerDto.QuestionAnswerRequest.builder()
                .matiereName(matiereName)
                .anneeScolaire(anneeScolaire)
                .trimestre(trimestre)
                .build();

        QuestionAnswerDto.QuestionsAnswersResponse response =
                questionAnswerService.getAllQuestionsWithAnswers(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/all")
    public ResponseEntity<QuestionAnswerDto.QuestionsAnswersResponse> getAllQuestionsWithAnswersPost(
            @RequestBody QuestionAnswerDto.QuestionAnswerRequest request) {

        QuestionAnswerDto.QuestionsAnswersResponse response =
                questionAnswerService.getAllQuestionsWithAnswers(request);

        return ResponseEntity.ok(response);
    }
}