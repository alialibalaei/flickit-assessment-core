package org.flickit.flickitassessmentcore.application.port.out.subject;

import org.flickit.flickitassessmentcore.domain.Subject;

import java.util.List;

public interface LoadSubjectByAssessmentKitIdPort {

    List<Subject> loadByAssessmentKitId(Long assessmentKitId);
}
