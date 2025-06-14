package com.amir.RESTfulApi.answer;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public AnswerDto createAnswer(CreateAnswerRequest request) {
        Answer answer = Answer.builder()
                .questionId(request.getQuestionId())
                .contentAnswer(request.getContentAnswer())
                .isCorrect(request.getIsCorrect())
                .build();

        Answer savedAnswer = answerRepository.save(answer);
        return convertToDto(savedAnswer);
    }

    public List<AnswerDto> getAllAnswers() {
        return answerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Page<AnswerDto> getAllAnswersPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return answerRepository.findAll(pageable)
                .map(this::convertToDto);
    }

    public Optional<AnswerDto> getAnswerById(Integer id) {
        return answerRepository.findById(id)
                .map(this::convertToDto);
    }

    @Transactional
    public Optional<AnswerDto> updateAnswer(Integer id, UpdateAnswerRequest request) {
        return answerRepository.findById(id)
                .map(answer -> {
                    if (request.getQuestionId() != null) {
                        answer.setQuestionId(request.getQuestionId());
                    }
                    if (request.getContentAnswer() != null) {
                        answer.setContentAnswer(request.getContentAnswer());
                    }
                    if (request.getIsCorrect() != null) {
                        answer.setIsCorrect(request.getIsCorrect());
                    }
                    Answer updatedAnswer = answerRepository.save(answer);
                    return convertToDto(updatedAnswer);
                });
    }

    @Transactional
    public boolean deleteAnswer(Integer id) {
        if (answerRepository.existsById(id)) {
            answerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<AnswerDto> getAnswersByQuestionId(Integer questionId) {
        return answerRepository.findByQuestionId(questionId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AnswerDto> getCorrectAnswersByQuestionId(Integer questionId) {
        return answerRepository.findByQuestionIdAndIsCorrect(questionId, true).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AnswerDto> getIncorrectAnswersByQuestionId(Integer questionId) {
        return answerRepository.findByQuestionIdAndIsCorrect(questionId, false).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AnswerDto> getAllCorrectAnswers() {
        return answerRepository.findByIsCorrect(true).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AnswerDto> getAllIncorrectAnswers() {
        return answerRepository.findByIsCorrect(false).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<AnswerDto> searchAnswersByContent(String content) {
        return answerRepository.findByContentAnswerContaining(content).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public QuestionAnswersDto getQuestionAnswersSummary(Integer questionId) {
        List<Answer> answers = answerRepository.findByQuestionId(questionId);

        return QuestionAnswersDto.builder()
                .questionId(questionId)
                .totalAnswers((long) answers.size())
                .correctAnswers(answers.stream().mapToLong(answer -> answer.getIsCorrect() ? 1 : 0).sum())
                .answers(answers.stream()
                        .map(this::convertToSummaryDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Long countAnswersByQuestionId(Integer questionId) {
        return answerRepository.countByQuestionId(questionId);
    }

    public List<AnswerDto> getAnswersByQuestionIds(List<Integer> questionIds) {
        return answerRepository.findByQuestionIdIn(questionIds).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AnswerDto convertToDto(Answer answer) {
        return AnswerDto.builder()
                .answerId(answer.getAnswerId())
                .questionId(answer.getQuestionId())
                .contentAnswer(answer.getContentAnswer())
                .isCorrect(answer.getIsCorrect())
                .build();
    }

    private AnswerSummaryDto convertToSummaryDto(Answer answer) {
        return AnswerSummaryDto.builder()
                .answerId(answer.getAnswerId())
                .contentAnswer(answer.getContentAnswer())
                .isCorrect(answer.getIsCorrect())
                .build();
    }
}
