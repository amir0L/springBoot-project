package com.amir.RESTfulApi.answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    private Integer answerId;
    private Integer questionId;
    private String contentAnswer;
    private Boolean isCorrect;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class CreateAnswerRequest {
    private Integer questionId;
    private String contentAnswer;
    private Boolean isCorrect;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class UpdateAnswerRequest {
    private Integer questionId;
    private String contentAnswer;
    private Boolean isCorrect;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class AnswerSummaryDto {
    private Integer answerId;
    private String contentAnswer;
    private Boolean isCorrect;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class QuestionAnswersDto {
    private Integer questionId;
    private Long totalAnswers;
    private Long correctAnswers;
    private List<AnswerSummaryDto> answers;
}
