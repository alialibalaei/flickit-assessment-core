package org.flickit.assessment.kit.application.port.out.assessmentkit;

import org.flickit.assessment.kit.application.domain.dsl.AssessmentKitDslModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GetDslContentPort {

    AssessmentKitDslModel getDslContent(MultipartFile dslFile) throws IOException;
}
