package org.flickit.assessment.advice.application.port.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.flickit.assessment.advice.application.domain.advice.QuestionListItem;
import org.flickit.assessment.common.application.SelfValidating;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.flickit.assessment.advice.common.ErrorMessageKey.SUGGEST_ADVICE_ASSESSMENT_ID_NOT_NULL;
import static org.flickit.assessment.advice.common.ErrorMessageKey.SUGGEST_ADVICE_TARGETS_SIZE_MIN;
import static org.flickit.assessment.common.error.ErrorMessageKey.COMMON_CURRENT_USER_ID_NOT_NULL;

public interface SuggestAdviceUseCase {

    Result suggestAdvice(Param param);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class Param extends SelfValidating<Param> {

        @NotNull(message = SUGGEST_ADVICE_ASSESSMENT_ID_NOT_NULL)
        UUID assessmentId;

        @Size(min = 1, message = SUGGEST_ADVICE_TARGETS_SIZE_MIN)
        Map<Long, Long> targets;

        @NotNull(message = COMMON_CURRENT_USER_ID_NOT_NULL)
        UUID currentUserId;

        public Param(UUID assessmentId, Map<Long, Long> targets, UUID currentUserId) {
            this.assessmentId = assessmentId;
            this.targets = targets;
            this.currentUserId = currentUserId;
            this.validateSelf();
        }
    }

    record Result(List<QuestionListItem> questions) {
    }
}
