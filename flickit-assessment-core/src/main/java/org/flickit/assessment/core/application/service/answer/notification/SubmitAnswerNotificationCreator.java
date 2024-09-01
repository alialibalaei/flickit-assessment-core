package org.flickit.assessment.core.application.service.answer.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flickit.assessment.common.application.domain.notification.NotificationCreator;
import org.flickit.assessment.common.application.domain.notification.NotificationEnvelope;
import org.flickit.assessment.core.application.port.out.assessment.GetAssessmentPort;
import org.flickit.assessment.core.application.port.out.assessment.GetAssessmentProgressPort;
import org.flickit.assessment.core.application.port.out.user.LoadUserPort;
import org.flickit.assessment.core.application.service.answer.notification.SubmitAnswerNotificationPayload.AssessmentModel;
import org.flickit.assessment.core.application.service.answer.notification.SubmitAnswerNotificationPayload.UserModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubmitAnswerNotificationCreator implements
    NotificationCreator<SubmitAnswerNotificationCmd> {

    private final GetAssessmentProgressPort getAssessmentProgressPort;
    private final GetAssessmentPort getAssessmentPort;
    private final LoadUserPort loadUserPort;

    @Override
    public List<NotificationEnvelope> create(SubmitAnswerNotificationCmd cmd) {
        var progress = getAssessmentProgressPort.getProgress(cmd.assessmentId());
        var assessment = getAssessmentPort.getAssessmentById(cmd.assessmentId());
        var user = loadUserPort.loadById(cmd.assessorId());

        if (assessment.isEmpty() || user.isEmpty()) {
            log.warn("assessment or user not found");
            return List.of();
        }

        return (progress.answersCount() == progress.questionsCount()) && (!cmd.assessorId().equals(assessment.get().getCreatedBy()))
            ? List.of(new NotificationEnvelope(assessment.get().getCreatedBy(), new SubmitAnswerNotificationPayload(
            new AssessmentModel(assessment.get().getId(), assessment.get().getTitle()),
            new UserModel(user.get().getId(), user.get().getDisplayName()))))
            : Collections.emptyList();
    }

    @Override
    public Class<SubmitAnswerNotificationCmd> cmdClass() {
        return SubmitAnswerNotificationCmd.class;
    }
}
