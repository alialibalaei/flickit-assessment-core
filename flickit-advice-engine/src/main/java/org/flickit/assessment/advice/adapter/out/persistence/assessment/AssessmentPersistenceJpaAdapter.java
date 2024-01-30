package org.flickit.assessment.advice.adapter.out.persistence.assessment;

import lombok.RequiredArgsConstructor;
import org.flickit.assessment.advice.application.port.out.assessment.CheckUserAssessmentAccessPort;
import org.flickit.assessment.data.jpa.core.assessment.AssessmentJpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AssessmentPersistenceJpaAdapter implements
    CheckUserAssessmentAccessPort {

    private final AssessmentJpaRepository repository;

    @Override
    public boolean hasAccess(UUID assessmentId, UUID userId) {
        return repository.checkUserAccess(assessmentId, userId).isPresent();
    }
}
