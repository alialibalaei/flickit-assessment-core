package org.flickit.flickitassessmentcore.application.service.answer;

import org.flickit.flickitassessmentcore.application.port.in.answer.SubmitAnswerCommand;
import org.flickit.flickitassessmentcore.application.port.out.answer.CheckAnswerExistenceByAssessmentResultIdAndQuestionIdPort;
import org.flickit.flickitassessmentcore.application.port.out.answer.LoadAnswerIdAndOptionIdByAssessmentResultAndQuestionPort;
import org.flickit.flickitassessmentcore.application.port.out.answer.SaveAnswerPort;
import org.flickit.flickitassessmentcore.application.port.out.answer.UpdateAnswerOptionPort;
import org.flickit.flickitassessmentcore.application.port.out.assessmentresult.InvalidateAssessmentResultPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitAnswerServiceTest {

    @Spy
    @InjectMocks
    private SubmitAnswerService service;

    @Mock
    private SaveAnswerPort saveAnswerPort;

    @Mock
    private UpdateAnswerOptionPort updateAnswerPort;

    @Mock
    private LoadAnswerIdAndOptionIdByAssessmentResultAndQuestionPort loadAnswerIdAndOptionIdPort;

    @Mock
    private InvalidateAssessmentResultPort invalidateAssessmentResultPort;

    @Mock
    private CheckAnswerExistenceByAssessmentResultIdAndQuestionIdPort checkAnswerExistencePort;

    @Test
    void submitAnswer_SavesAnswerAndInvalidatesAssessmentResult_WhenAnswerNotExist() {
        SubmitAnswerCommand command = new SubmitAnswerCommand(
            UUID.randomUUID(),
            1L,
            1L
        );
        doReturn(false).when(checkAnswerExistencePort).existsByAssessmentResultIdAndQuestionId(any(UUID.class), anyLong());

        UUID savedAnswerId = UUID.randomUUID();
        doReturn(savedAnswerId).when(saveAnswerPort).persist(any(SaveAnswerPort.Param.class));

        service.submitAnswer(command);

        verify(saveAnswerPort, times(1)).persist(any(SaveAnswerPort.Param.class));
        verify(invalidateAssessmentResultPort, times(1)).invalidateById(command.getAssessmentResultId());
        verifyNoInteractions(
            updateAnswerPort,
            loadAnswerIdAndOptionIdPort
        );
    }

    @Test
    void submitAnswer_UpdatesAnswerAndInvalidatesAssessmentResult_WhenAnswerWithDifferentAnswerOptionExist() {
        SubmitAnswerCommand command = new SubmitAnswerCommand(
            UUID.randomUUID(),
            1L,
            1L
        );
        UUID existAnswerId = UUID.randomUUID();
        LoadAnswerIdAndOptionIdByAssessmentResultAndQuestionPort.Result existAnswer = new LoadAnswerIdAndOptionIdByAssessmentResultAndQuestionPort.Result(
            existAnswerId,
            2L
        );

        doReturn(true).when(checkAnswerExistencePort).existsByAssessmentResultIdAndQuestionId(any(UUID.class), anyLong());
        doReturn(existAnswer).when(loadAnswerIdAndOptionIdPort).loadByAssessmentResultIdAndQuestionId(any(UUID.class), anyLong());

        service.submitAnswer(command);

        verify(loadAnswerIdAndOptionIdPort, times(1)).loadByAssessmentResultIdAndQuestionId(any(UUID.class), anyLong());
        verify(updateAnswerPort, times(1)).updateAnswerOptionById(any(UpdateAnswerOptionPort.Param.class));
        verify(invalidateAssessmentResultPort, times(1)).invalidateById(command.getAssessmentResultId());
        verifyNoInteractions(
            saveAnswerPort
        );
    }

    @Test
    void submitAnswer_DoesntInvalidateAssessmentResult_WhenAnswerWithSameAnswerOptionExist() {
        SubmitAnswerCommand command = new SubmitAnswerCommand(
            UUID.randomUUID(),
            1L,
            1L
        );
        UUID existAnswerId = UUID.randomUUID();
        LoadAnswerIdAndOptionIdByAssessmentResultAndQuestionPort.Result existAnswer = new LoadAnswerIdAndOptionIdByAssessmentResultAndQuestionPort.Result(
            existAnswerId,
            1L
        );
        doReturn(true).when(checkAnswerExistencePort).existsByAssessmentResultIdAndQuestionId(any(UUID.class), anyLong());
        doReturn(existAnswer).when(loadAnswerIdAndOptionIdPort).loadByAssessmentResultIdAndQuestionId(any(UUID.class), anyLong());

        service.submitAnswer(command);

        verify(loadAnswerIdAndOptionIdPort, times(1)).loadByAssessmentResultIdAndQuestionId(any(UUID.class), anyLong());
        verifyNoInteractions(
            saveAnswerPort,
            updateAnswerPort,
            invalidateAssessmentResultPort
        );
    }
}
