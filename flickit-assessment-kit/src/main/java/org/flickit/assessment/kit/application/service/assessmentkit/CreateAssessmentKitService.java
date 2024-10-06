package org.flickit.assessment.kit.application.service.assessmentkit;

import lombok.RequiredArgsConstructor;
import org.flickit.assessment.common.exception.AccessDeniedException;
import org.flickit.assessment.kit.application.domain.AssessmentKit;
import org.flickit.assessment.kit.application.domain.KitVersionStatus;
import org.flickit.assessment.kit.application.port.in.assessmentkit.CreateAssessmentKitUseCase;
import org.flickit.assessment.kit.application.port.out.assessmentkit.CreateAssessmentKitPort;
import org.flickit.assessment.kit.application.port.out.expertgroup.LoadExpertGroupOwnerPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static org.flickit.assessment.common.error.ErrorMessageKey.COMMON_CURRENT_USER_NOT_ALLOWED;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateAssessmentKitService implements CreateAssessmentKitUseCase {

    private final LoadExpertGroupOwnerPort loadExpertGroupOwnerPort;
    private final CreateAssessmentKitPort createAssessmentKitPort;

    @Override
    public Result createAssessmentKit(Param param) {
        UUID expertGroupOwnerId = loadExpertGroupOwnerPort.loadOwnerId(param.getExpertGroupId());
        if (!Objects.equals(expertGroupOwnerId, param.getCurrentUserId()))
            throw new AccessDeniedException(COMMON_CURRENT_USER_NOT_ALLOWED);

        var portResult = createAssessmentKitPort.persist(toPortParam(param));
        return new Result(portResult.kitId());
    }

    private CreateAssessmentKitPort.Param toPortParam(Param param) {
        return new CreateAssessmentKitPort.Param(
            AssessmentKit.generateSlugCode(param.getTitle()),
            param.getTitle(),
            param.getSummary(),
            param.getAbout(),
            Boolean.FALSE,
            param.getIsPrivate(),
            param.getExpertGroupId(),
            KitVersionStatus.UPDATING,
            param.getCurrentUserId()
        );
    }
}
