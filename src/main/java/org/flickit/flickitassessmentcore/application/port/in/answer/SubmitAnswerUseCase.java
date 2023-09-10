package org.flickit.flickitassessmentcore.application.port.in.answer;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.flickit.flickitassessmentcore.common.SelfValidating;

import java.util.UUID;

import static org.flickit.flickitassessmentcore.common.ErrorMessageKey.*;

public interface SubmitAnswerUseCase {

    Result submitAnswer(Param param);

    @Value
    class Param extends SelfValidating<Param> {

        @NotNull(message = SUBMIT_ANSWER_ASSESSMENT_ID_NOT_NULL)
        UUID assessmentId;

        @NotNull(message = SUBMIT_ANSWER_QUESTIONNAIRE_ID_NOT_NULL)
        Long questionnaireId;

        @NotNull(message = SUBMIT_ANSWER_QUESTION_ID_NOT_NULL)
        Long questionId;

        Long answerOptionId;

        public Param(UUID assessmentId, Long questionnaireId, Long questionId, Long answerOptionId) {
            this.assessmentId = assessmentId;
            this.questionnaireId = questionnaireId;
            this.questionId = questionId;
            this.answerOptionId = answerOptionId;
            this.validateSelf();
        }
    }

    record Result(UUID id) {
    }
}
