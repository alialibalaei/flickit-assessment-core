package org.flickit.assessment.core.application.service.assessment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flickit.assessment.common.application.port.out.ValidateAssessmentResultPort;
import org.flickit.assessment.common.exception.AccessDeniedException;
import org.flickit.assessment.common.exception.ResourceNotFoundException;
import org.flickit.assessment.core.application.domain.report.AttributeReportItem;
import org.flickit.assessment.core.application.domain.report.TopAttributeResolver;
import org.flickit.assessment.core.application.port.in.assessment.ReportAssessmentUseCase;
import org.flickit.assessment.core.application.port.out.assessment.CheckAssessmentExistencePort;
import org.flickit.assessment.core.application.port.out.assessment.CheckUserAssessmentAccessPort;
import org.flickit.assessment.core.application.port.out.assessmentresult.LoadAssessmentReportInfoPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.flickit.assessment.common.error.ErrorMessageKey.COMMON_CURRENT_USER_NOT_ALLOWED;
import static org.flickit.assessment.core.application.domain.MaturityLevel.middleLevel;
import static org.flickit.assessment.core.common.ErrorMessageKey.REPORT_ASSESSMENT_ASSESSMENT_ID_NOT_FOUND;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportAssessmentService implements ReportAssessmentUseCase {

    private final ValidateAssessmentResultPort validateAssessmentResultPort;
    private final LoadAssessmentReportInfoPort loadReportInfoPort;
    private final CheckAssessmentExistencePort checkAssessmentExistencePort;
    private final CheckUserAssessmentAccessPort checkUserAssessmentAccessPort;

    @Override
    public Result reportAssessment(Param param) {
        if (!checkAssessmentExistencePort.existsById(param.getAssessmentId()))
            throw new ResourceNotFoundException(REPORT_ASSESSMENT_ASSESSMENT_ID_NOT_FOUND);

        if (!checkUserAssessmentAccessPort.hasAccess(param.getAssessmentId(), param.getCurrentUserId()))
            throw new AccessDeniedException(COMMON_CURRENT_USER_NOT_ALLOWED);

        validateAssessmentResultPort.validate(param.getAssessmentId());

        var assessmentReport = loadReportInfoPort.load(param.getAssessmentId());
        List<AttributeReportItem> attributes = assessmentReport.attributes();
        var midLevelMaturity = middleLevel(assessmentReport.maturityLevels());
        TopAttributeResolver topAttributeResolver = new TopAttributeResolver(attributes, midLevelMaturity);
        var topStrengths = topAttributeResolver.getTopStrengths();
        var topWeaknesses = topAttributeResolver.getTopWeaknesses();

        log.debug("AssessmentReport returned for assessmentId=[{}].", param.getAssessmentId());

        return new Result(assessmentReport.assessment(),
            topStrengths,
            topWeaknesses,
            assessmentReport.subjects());
    }
}
