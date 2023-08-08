package org.flickit.flickitassessmentcore.application.port.out.assessment;

import org.flickit.flickitassessmentcore.domain.Assessment;

import java.util.List;

public interface LoadAssessmentBySpacePort {

    List<Assessment> loadAssessmentBySpaceId(Long spaceId, int page, int size);
}
