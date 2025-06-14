package com.amir.RESTfulApi.Question;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    // Create a new question
    @PostMapping
    public ResponseEntity<QuestionDto.QuestionResponse> createQuestion(
            @RequestBody QuestionDto.CreateQuestionRequest request) {
        try {
            QuestionDto.QuestionResponse question = questionService.createQuestion(request);
            return ResponseEntity.ok(question);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get all questions
    @GetMapping
    public ResponseEntity<List<QuestionDto.QuestionResponse>> getAllQuestions() {
        try {
            List<QuestionDto.QuestionResponse> questions = questionService.getAllQuestions();
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get question by ID
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDto.QuestionResponse> getQuestionById(@PathVariable Integer questionId) {
        try {
            Optional<QuestionDto.QuestionResponse> question = questionService.getQuestionById(questionId);
            return question.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Update question
    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionDto.QuestionResponse> updateQuestion(
            @PathVariable Integer questionId,
            @RequestBody QuestionDto.UpdateQuestionRequest request) {
        try {
            Optional<QuestionDto.QuestionResponse> updatedQuestion =
                    questionService.updateQuestion(questionId, request);
            return updatedQuestion.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Delete question
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Integer questionId) {
        try {
            boolean deleted = questionService.deleteQuestion(questionId);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get questions by matiere name
    @GetMapping("/matiere/{matiereName}")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> getQuestionsByMatiereName(
            @PathVariable String matiereName) {
        try {
            List<QuestionDto.QuestionResponse> questions = questionService.getQuestionsByMatiereName(matiereName);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get questions by school year
    @GetMapping("/year/{anneeScolaire}")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> getQuestionsByYear(
            @PathVariable Integer anneeScolaire) {
        try {
            List<QuestionDto.QuestionResponse> questions = questionService.getQuestionsByYear(anneeScolaire);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get questions by school year and trimester
    @GetMapping("/year/{anneeScolaire}/trimester/{trimestre}")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> getQuestionsByYearAndTrimester(
            @PathVariable Integer anneeScolaire,
            @PathVariable Integer trimestre) {
        try {
            List<QuestionDto.QuestionResponse> questions =
                    questionService.getQuestionsByYearAndTrimester(anneeScolaire, trimestre);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get questions by school year, trimester and unit
    @GetMapping("/year/{anneeScolaire}/trimester/{trimestre}/unit/{unitNumber}")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> getQuestionsByYearTrimesterAndUnit(
            @PathVariable Integer anneeScolaire,
            @PathVariable Integer trimestre,
            @PathVariable Integer unitNumber) {
        try {
            List<QuestionDto.QuestionResponse> questions =
                    questionService.getQuestionsByYearTrimesterAndUnit(anneeScolaire, trimestre, unitNumber);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get questions by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> getQuestionsByType(@PathVariable Integer type) {
        try {
            List<QuestionDto.QuestionResponse> questions = questionService.getQuestionsByType(type);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Search questions by content
    @GetMapping("/search")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> searchQuestionsByContent(
            @RequestParam String content) {
        try {
            List<QuestionDto.QuestionResponse> questions = questionService.searchQuestionsByContent(content);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get questions count by matiere name
    @GetMapping("/count/matiere/{matiereName}")
    public ResponseEntity<Long> getQuestionsCountByMatiereName(@PathVariable String matiereName) {
        try {
            Long count = questionService.getQuestionsCountByMatiereName(matiereName);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get questions by matiere name, year and trimester - EXISTING ENDPOINT
    @GetMapping("/matiere/{matiereName}/year/{anneeScolaire}/trimester/{trimestre}")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> getQuestionsByMatiereNameYearAndTrimester(
            @PathVariable String matiereName,
            @PathVariable Integer anneeScolaire,
            @PathVariable Integer trimestre) {
        try {
            List<QuestionDto.QuestionResponse> questions =
                    questionService.getQuestionsByMatiereNameYearAndTrimester(matiereName, anneeScolaire, trimestre);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get questions by matiere name and year - NEW ENDPOINT
    @GetMapping("/matiere/{matiereName}/year/{anneeScolaire}")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> getQuestionsByMatiereNameAndYear(
            @PathVariable String matiereName,
            @PathVariable Integer anneeScolaire) {
        try {
            List<QuestionDto.QuestionResponse> questions =
                    questionService.getQuestionsByMatiereNameAndYear(matiereName, anneeScolaire);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get questions by matiere name and trimester - NEW ENDPOINT
    @GetMapping("/matiere/{matiereName}/trimester/{trimestre}")
    public ResponseEntity<List<QuestionDto.QuestionResponse>> getQuestionsByMatiereNameAndTrimester(
            @PathVariable String matiereName,
            @PathVariable Integer trimestre) {
        try {
            List<QuestionDto.QuestionResponse> questions =
                    questionService.getQuestionsByMatiereNameAndTrimester(matiereName, trimestre);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}