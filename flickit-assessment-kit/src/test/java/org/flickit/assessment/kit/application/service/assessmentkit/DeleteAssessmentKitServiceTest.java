package org.flickit.assessment.kit.application.service.assessmentkit;

import org.flickit.assessment.common.exception.AccessDeniedException;
import org.flickit.assessment.common.exception.ValidationException;
import org.flickit.assessment.kit.application.domain.ExpertGroup;
import org.flickit.assessment.kit.application.port.in.assessmentkit.DeleteAssessmentKitUseCase;
import org.flickit.assessment.kit.application.port.out.assessmentkit.CountKitAssessmentsPort;
import org.flickit.assessment.kit.application.port.out.assessmentkit.DeleteAssessmentKitPort;
import org.flickit.assessment.kit.application.port.out.expertgroup.LoadKitExpertGroupPort;
import org.flickit.assessment.kit.test.fixture.application.ExpertGroupMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteAssessmentKitServiceTest {

    @InjectMocks
    private DeleteAssessmentKitService deleteAssessmentKitService;

    @Mock
    public DeleteAssessmentKitPort deleteAssessmentKitPort;

    @Mock
    public LoadKitExpertGroupPort loadKitExpertGroupPort;

    @Mock
    public CountKitAssessmentsPort countKitAssessmentsPort;

    @Test
    void testDeleteAssessmentKit_WhenCurrentUserIsNotKitOwner_ThenThrowException() {
        UUID currentUserId = UUID.randomUUID();
        DeleteAssessmentKitUseCase.Param param = new DeleteAssessmentKitUseCase.Param(1L, currentUserId);
        ExpertGroup expertGroup = ExpertGroupMother.createExpertGroup();

        when(loadKitExpertGroupPort.loadKitExpertGroup(param.getKitId())).thenReturn(expertGroup);

        assertThrows(AccessDeniedException.class, () -> deleteAssessmentKitService.delete(param));
    }

    @Test
    void testDeleteAssessmentKit_WhenKitHasAssessments_ThenThrowException() {
        ExpertGroup expertGroup = ExpertGroupMother.createExpertGroup();
        UUID currentUserId = expertGroup.getOwnerId();
        DeleteAssessmentKitUseCase.Param param = new DeleteAssessmentKitUseCase.Param(1L, currentUserId);

        when(loadKitExpertGroupPort.loadKitExpertGroup(param.getKitId())).thenReturn(expertGroup);
        when(countKitAssessmentsPort.count(param.getKitId())).thenReturn(5L);

        assertThrows(ValidationException.class, () -> deleteAssessmentKitService.delete(param));
    }

    @Test
    void testDeleteAssessmentKit_WhenValidInput_ThenSucceedDeletion() {
        ExpertGroup expertGroup = ExpertGroupMother.createExpertGroup();
        UUID currentUserId = expertGroup.getOwnerId();
        DeleteAssessmentKitUseCase.Param param = new DeleteAssessmentKitUseCase.Param(1L, currentUserId);

        when(loadKitExpertGroupPort.loadKitExpertGroup(param.getKitId())).thenReturn(expertGroup);
        when(countKitAssessmentsPort.count(param.getKitId())).thenReturn(0L);
        doNothing().when(deleteAssessmentKitPort).delete(param.getKitId());

        deleteAssessmentKitService.delete(param);
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(deleteAssessmentKitPort).delete(longArgumentCaptor.capture());
    }
}
