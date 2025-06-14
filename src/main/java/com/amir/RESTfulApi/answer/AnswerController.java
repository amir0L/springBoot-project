package com.amir.RESTfulApi.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public ResponseEntity<AnswerDto> createAnswer(@RequestBody CreateAnswerRequest request) {
        try {
            AnswerDto answer = answerService.createAnswer(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(answer);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAllAnswers() {
        List<AnswerDto> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<AnswerDto>> getAllAnswersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "answerId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Page<AnswerDto> answers = answerService.getAllAnswersPaginated(page, size, sortBy, sortDir);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerDto> getAnswerById(@PathVariable Integer id) {
        return answerService.getAnswerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerDto> updateAnswer(
            @PathVariable Integer id,
            @RequestBody UpdateAnswerRequest request) {
        return answerService.updateAnswer(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Integer id) {
        if (answerService.deleteAnswer(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerDto>> getAnswersByQuestionId(@PathVariable Integer questionId) {
        List<AnswerDto> answers = answerService.getAnswersByQuestionId(questionId);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/question/{questionId}/correct")
    public ResponseEntity<List<AnswerDto>> getCorrectAnswersByQuestionId(@PathVariable Integer questionId) {
        List<AnswerDto> answers = answerService.getCorrectAnswersByQuestionId(questionId);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/question/{questionId}/incorrect")
    public ResponseEntity<List<AnswerDto>> getIncorrectAnswersByQuestionId(@PathVariable Integer questionId) {
        List<AnswerDto> answers = answerService.getIncorrectAnswersByQuestionId(questionId);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/correct")
    public ResponseEntity<List<AnswerDto>> getAllCorrectAnswers() {
        List<AnswerDto> answers = answerService.getAllCorrectAnswers();
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/incorrect")
    public ResponseEntity<List<AnswerDto>> getAllIncorrectAnswers() {
        List<AnswerDto> answers = answerService.getAllIncorrectAnswers();
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AnswerDto>> searchAnswersByContent(@RequestParam String content) {
        List<AnswerDto> answers = answerService.searchAnswersByContent(content);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/question/{questionId}/summary")
    public ResponseEntity<QuestionAnswersDto> getQuestionAnswersSummary(@PathVariable Integer questionId) {
        QuestionAnswersDto summary = answerService.getQuestionAnswersSummary(questionId);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/question/{questionId}/count")
    public ResponseEntity<Long> countAnswersByQuestionId(@PathVariable Integer questionId) {
        Long count = answerService.countAnswersByQuestionId(questionId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/questions")
    public ResponseEntity<List<AnswerDto>> getAnswersByQuestionIds(@RequestParam List<Integer> questionIds) {
        List<AnswerDto> answers = answerService.getAnswersByQuestionIds(questionIds);
        return ResponseEntity.ok(answers);
    }
}