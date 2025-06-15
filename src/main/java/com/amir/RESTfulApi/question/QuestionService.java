package com.amir.RESTfulApi.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public QuestionDto.QuestionResponse createQuestion(QuestionDto.CreateQuestionRequest request) {
        // Validate matiere name
        if (request.getMatiereName() == null || request.getMatiereName().trim().isEmpty()) {
            throw new RuntimeException("Matiere name cannot be null or empty");
        }

        // Check if question already exists for this matiere
        if (questionRepository.existsByContentQuestionAndMatiereName(
                request.getContentQuestion(), request.getMatiereName())) {
            throw new RuntimeException("Question already exists for this matiere");
        }

        Question question = Question.builder()
                .contentQuestion(request.getContentQuestion())
                .type(request.getType())
                .anneeScolaire(request.getAnneeScolaire())
                .trimestre(request.getTrimestre())
                .unitNumber(request.getUnitNumber())
                .matiereName(request.getMatiereName())
                .build();

        Question savedQuestion = questionRepository.save(question);
        return mapToResponse(savedQuestion);
    }

    // Get all questions
    public List<QuestionDto.QuestionResponse> getAllQuestions() {
        List<Question> questions = questionRepository.findAllOrderedByYearTrimesterAndUnit();
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get question by ID
    public Optional<QuestionDto.QuestionResponse> getQuestionById(Integer questionId) {
        return questionRepository.findById(questionId)
                .map(this::mapToResponse);
    }

    @Transactional
    public Optional<QuestionDto.QuestionResponse> updateQuestion(Integer questionId, QuestionDto.UpdateQuestionRequest request) {
        return questionRepository.findById(questionId)
                .map(question -> {
                    // Update fields if they are not null
                    if (request.getContentQuestion() != null) {
                        question.setContentQuestion(request.getContentQuestion());
                    }
                    if (request.getType() != null) {
                        question.setType(request.getType());
                    }
                    if (request.getAnneeScolaire() != null) {
                        question.setAnneeScolaire(request.getAnneeScolaire());
                    }
                    if (request.getTrimestre() != null) {
                        question.setTrimestre(request.getTrimestre());
                    }
                    if (request.getUnitNumber() != null) {
                        question.setUnitNumber(request.getUnitNumber());
                    }
                    if (request.getMatiereName() != null) {
                        question.setMatiereName(request.getMatiereName());
                    }

                    Question updatedQuestion = questionRepository.save(question);
                    return mapToResponse(updatedQuestion);
                });
    }

    @Transactional
    public boolean deleteQuestion(Integer questionId) {
        if (questionRepository.existsById(questionId)) {
            questionRepository.deleteById(questionId);
            return true;
        }
        return false;
    }

    // Get questions by matiere name
    public List<QuestionDto.QuestionResponse> getQuestionsByMatiereName(String matiereName) {
        List<Question> questions = questionRepository.findByMatiereName(matiereName);
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get questions by school year
    public List<QuestionDto.QuestionResponse> getQuestionsByYear(Integer anneeScolaire) {
        List<Question> questions = questionRepository.findByAnneeScolaire(anneeScolaire);
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get questions by school year and trimester
    public List<QuestionDto.QuestionResponse> getQuestionsByYearAndTrimester(Integer anneeScolaire, Integer trimestre) {
        List<Question> questions = questionRepository.findByAnneeScolaireAndTrimestre(anneeScolaire, trimestre);
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get questions by school year, trimester and unit
    public List<QuestionDto.QuestionResponse> getQuestionsByYearTrimesterAndUnit(
            Integer anneeScolaire, Integer trimestre, Integer unitNumber) {
        List<Question> questions = questionRepository.findByAnneeScolaireAndTrimestreAndUnitNumber(
                anneeScolaire, trimestre, unitNumber);
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get questions by type
    public List<QuestionDto.QuestionResponse> getQuestionsByType(Integer type) {
        List<Question> questions = questionRepository.findByType(type);
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Search questions by content
    public List<QuestionDto.QuestionResponse> searchQuestionsByContent(String content) {
        List<Question> questions = questionRepository.findByContentQuestionContainingIgnoreCase(content);
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get questions count by matiere name
    public Long getQuestionsCountByMatiereName(String matiereName) {
        return questionRepository.countByMatiereName(matiereName);
    }

    // Get questions by matiere name, year and trimester - NEW METHOD
    public List<QuestionDto.QuestionResponse> getQuestionsByMatiereNameYearAndTrimester(
            String matiereName, Integer anneeScolaire, Integer trimestre) {
        List<Question> questions = questionRepository.findByMatiereNameAndAnneeScolaireAndTrimestre(
                matiereName, anneeScolaire, trimestre);
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get questions by matiere name and year - NEW METHOD
    public List<QuestionDto.QuestionResponse> getQuestionsByMatiereNameAndYear(
            String matiereName, Integer anneeScolaire) {
        List<Question> questions = questionRepository.findByMatiereNameAndAnneeScolaire(matiereName, anneeScolaire);
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get questions by matiere name and trimester - NEW METHOD
    public List<QuestionDto.QuestionResponse> getQuestionsByMatiereNameAndTrimester(
            String matiereName, Integer trimestre) {
        List<Question> questions = questionRepository.findByMatiereNameAndTrimestre(matiereName, trimestre);
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Helper method to map entity to response DTO
    private QuestionDto.QuestionResponse mapToResponse(Question question) {
        return QuestionDto.QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .contentQuestion(question.getContentQuestion())
                .type(question.getType())
                .anneeScolaire(question.getAnneeScolaire())
                .trimestre(question.getTrimestre())
                .unitNumber(question.getUnitNumber())
                .matiereName(question.getMatiereName())
                .build();
    }
}