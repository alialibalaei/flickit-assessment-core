package org.flickit.assessment.kit.application.service.assessmentkit;

import lombok.RequiredArgsConstructor;
import org.flickit.assessment.common.exception.AccessDeniedException;
import org.flickit.assessment.common.exception.ResourceNotFoundException;
import org.flickit.assessment.kit.application.domain.AssessmentKit;
import org.flickit.assessment.kit.application.port.in.assessmentkit.GetPublishedKitUseCase;
import org.flickit.assessment.kit.application.port.out.assessmentkit.CountKitStatsPort;
import org.flickit.assessment.kit.application.port.out.assessmentkit.LoadAssessmentKitPort;
import org.flickit.assessment.kit.application.port.out.expertgroup.LoadKitExpertGroupPort;
import org.flickit.assessment.kit.application.port.out.kittag.LoadKitTagListPort;
import org.flickit.assessment.kit.application.port.out.kituseraccess.CheckKitUserAccessPort;
import org.flickit.assessment.kit.application.port.out.maturitylevel.LoadMaturityLevelsPort;
import org.flickit.assessment.kit.application.port.out.minio.CreateFileDownloadLinkPort;
import org.flickit.assessment.kit.application.port.out.questionnaire.LoadQuestionnairesPort;
import org.flickit.assessment.kit.application.port.out.subject.LoadSubjectsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

import static org.flickit.assessment.common.error.ErrorMessageKey.COMMON_CURRENT_USER_NOT_ALLOWED;
import static org.flickit.assessment.kit.common.ErrorMessageKey.KIT_ID_NOT_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GetPublishedKitService implements GetPublishedKitUseCase {

    private static final Duration EXPIRY_DURATION = Duration.ofHours(1);

    private final LoadAssessmentKitPort loadAssessmentKitPort;
    private final CheckKitUserAccessPort checkKitUserAccessPort;
    private final CountKitStatsPort countKitStatsPort;
    private final LoadSubjectsPort loadSubjectsPort;
    private final LoadQuestionnairesPort loadQuestionnairesPort;
    private final LoadMaturityLevelsPort loadMaturityLevelsPort;
    private final LoadKitTagListPort loadKitTagListPort;
    private final LoadKitExpertGroupPort loadKitExpertGroupPort;
    private final CreateFileDownloadLinkPort createFileDownloadLinkPort;

    @Override
    public Result getPublishedKit(Param param) {
        AssessmentKit kit = loadAssessmentKitPort.load(param.getKitId());
        if (!kit.isPublished()) {
            throw new ResourceNotFoundException(KIT_ID_NOT_FOUND);
        }
        if (kit.isPrivate() && !checkKitUserAccessPort.hasAccess(param.getKitId(), param.getCurrentUserId())) {
            throw new AccessDeniedException(COMMON_CURRENT_USER_NOT_ALLOWED);
        }

        var stats = countKitStatsPort.countKitStats(param.getKitId());

        var subjects = loadSubjectsPort.loadByKitId(param.getKitId()).stream()
            .map(this::toSubject)
            .toList();

        var questionnaires = loadQuestionnairesPort.loadByKitId(param.getKitId()).stream()
            .map(this::toQuestionnaire)
            .toList();

        var maturityLevels = loadMaturityLevelsPort.loadByKitId(param.getKitId()).stream()
            .map(this::toMaturityLevel)
            .toList();

        var kitTags = loadKitTagListPort.loadByKitId(param.getKitId()).stream()
            .map(this::toKitTag)
            .toList();

        var expertGroup = toExpertGroup(loadKitExpertGroupPort.loadKitExpertGroup(param.getKitId()));

        return new Result(
            kit.getId(),
            kit.getTitle(),
            kit.getSummary(),
            kit.getAbout(),
            kit.isPublished(),
            kit.isPrivate(),
            kit.getCreationTime(),
            kit.getLastModificationTime(),
            stats.likes(),
            stats.assessmentCounts(),
            subjects.size(),
            stats.questionnairesCount(),
            subjects,
            questionnaires,
            maturityLevels,
            kitTags,
            expertGroup);
    }

    private Subject toSubject(org.flickit.assessment.kit.application.domain.Subject s) {
        return new Subject(
            s.getId(),
            s.getTitle(),
            s.getDescription(),
            s.getAttributes().stream()
                .map(this::toAttribute)
                .toList()
        );
    }

    private Attribute toAttribute(org.flickit.assessment.kit.application.domain.Attribute attribute) {
        return new Attribute(attribute.getId(), attribute.getTitle(), attribute.getDescription());
    }

    private Questionnaire toQuestionnaire(org.flickit.assessment.kit.application.domain.Questionnaire questionnaire) {
        return new Questionnaire(questionnaire.getId(), questionnaire.getTitle(), questionnaire.getDescription());
    }

    private MaturityLevel toMaturityLevel(org.flickit.assessment.kit.application.domain.MaturityLevel level) {
        return new MaturityLevel(level.getId(), level.getTitle(), level.getValue(), level.getIndex());
    }

    private KitTag toKitTag(org.flickit.assessment.kit.application.domain.KitTag tag) {
        return new KitTag(tag.getId(), tag.getTitle());
    }

    private ExpertGroup toExpertGroup(org.flickit.assessment.kit.application.domain.ExpertGroup expertGroup) {
        return new ExpertGroup(expertGroup.getId(),
            expertGroup.getTitle(),
            expertGroup.getBio(),
            expertGroup.getAbout(),
            createFileDownloadLinkPort.createDownloadLink(expertGroup.getPicture(), EXPIRY_DURATION));
    }
}
