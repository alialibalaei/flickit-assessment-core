package org.flickit.assessment.data.jpa.core.evidence;

import org.flickit.assessment.data.jpa.core.answer.AnswerJpaEntity;
import org.flickit.assessment.data.jpa.kit.answeroption.AnswerOptionJpaEntity;
import org.flickit.assessment.data.jpa.kit.question.QuestionJpaEntity;
import org.flickit.assessment.data.jpa.kit.questionnaire.QuestionnaireJpaEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public interface EvidenceWithDetailsViewJpaEntity {

    UUID getId();

    UUID getAssessmentId();

    String getDescription();

    QuestionnaireJpaEntity getQuestionnaire();

    QuestionJpaEntity getQuestion();

    AnswerJpaEntity getAnswer();

    AnswerOptionJpaEntity getAnswerOption();

    UUID getCreatedBy();

    LocalDateTime getCreationTime();

    LocalDateTime getLastModificationTime();
}
