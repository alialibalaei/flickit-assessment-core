package org.flickit.assessment.core.test.fixture.application;

import org.flickit.assessment.core.application.domain.AssessmentResult;
import org.flickit.assessment.core.application.domain.MaturityLevel;
import org.flickit.assessment.core.application.domain.SubjectValue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AssessmentResultMother {

    public static AssessmentResult invalidResultWithSubjectValues(List<SubjectValue> subjectValues) {
        return new AssessmentResult(UUID.randomUUID(), AssessmentMother.assessment(), subjectValues, LocalDateTime.now(), LocalDateTime.now());
    }

    public static AssessmentResult validResultWithSubjectValuesAndMaturityLevel(List<SubjectValue> subjectValues, MaturityLevel maturityLevel) {
        AssessmentResult assessmentResult = new AssessmentResult(UUID.randomUUID(), AssessmentMother.assessment(), subjectValues, LocalDateTime.now(), LocalDateTime.now());
        assessmentResult.setIsCalculateValid(true);
        assessmentResult.setMaturityLevel(maturityLevel);
        assessmentResult.setLastCalculationTime(LocalDateTime.now());
        assessmentResult.setLastConfidenceCalculationTime(LocalDateTime.now());
        return assessmentResult;
    }

    public static AssessmentResult validResultWithSubjectValuesAndMaturityLevelAndKitId(List<SubjectValue> subjectValues, MaturityLevel maturityLevel, long kitId) {
        AssessmentResult assessmentResult = new AssessmentResult(UUID.randomUUID(), AssessmentMother.assessmentWithKitId(kitId), subjectValues, LocalDateTime.now(), LocalDateTime.now());
        assessmentResult.setIsCalculateValid(true);
        assessmentResult.setMaturityLevel(maturityLevel);
        assessmentResult.setLastCalculationTime(LocalDateTime.now());
        assessmentResult.setLastConfidenceCalculationTime(LocalDateTime.now());
        return assessmentResult;
    }

    public static AssessmentResult validResultWithJustAnId() {
        AssessmentResult assessmentResult = new AssessmentResult(UUID.randomUUID(), null, new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
        assessmentResult.setIsCalculateValid(true);
        return assessmentResult;
    }

    public static AssessmentResult resultWithValidations(Boolean isCalculateValid, Boolean isConfCalculationValid,
                                                         LocalDateTime lastCalculationTime, LocalDateTime lastConfCalculationTime) {
        AssessmentResult assessmentResult = new AssessmentResult(UUID.randomUUID(), AssessmentMother.assessment(), List.of(), LocalDateTime.now(), LocalDateTime.now());
        assessmentResult.setIsCalculateValid(isCalculateValid);
        assessmentResult.setIsConfidenceValid(isConfCalculationValid);
        assessmentResult.setLastCalculationTime(lastCalculationTime);
        assessmentResult.setLastConfidenceCalculationTime(lastConfCalculationTime);
        return assessmentResult;
    }
}
