package org.flickit.flickitassessmentcore.adapter.out.persistence.answer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnswerJpaRepository extends JpaRepository<AnswerJpaEntity, UUID> {

    boolean existsByAssessmentResult_IdAndQuestionId(UUID assessmentResultId, Long questionId);

    AnswerJpaEntity findByAssessmentResult_IdAndQuestionId(UUID assessmentResultId, Long questionId);
}
