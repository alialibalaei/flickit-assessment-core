package org.flickit.flickitassessmentcore.application.service.answer;

import org.flickit.flickitassessmentcore.application.port.in.answer.SubmitAnswerUseCase;
import org.flickit.flickitassessmentcore.application.port.out.answer.CreateAnswerPort;
import org.flickit.flickitassessmentcore.application.port.out.answer.LoadAnswerIdAndOptionIdByAssessmentAndQuestionPort;
import org.flickit.flickitassessmentcore.application.port.out.answer.LoadAnswerIdAndOptionIdByAssessmentAndQuestionPort.Result;
import org.flickit.flickitassessmentcore.application.port.out.answer.UpdateAnswerOptionPort;
import org.flickit.flickitassessmentcore.application.port.out.assessmentresult.InvalidateAssessmentResultPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitAnswerServiceTest {

    @InjectMocks
    private SubmitAnswerService service;

    @Mock
    private CreateAnswerPort createAnswerPort;

    @Mock
    private UpdateAnswerOptionPort updateAnswerPort;

    @Mock
    private LoadAnswerIdAndOptionIdByAssessmentAndQuestionPort loadAnswerIdAndOptionIdPort;

    @Mock
    private InvalidateAssessmentResultPort invalidateAssessmentResultPort;

    @Test
    void submitAnswer_AnswerNotExist_SavesAnswerAndInvalidatesAssessmentResult() {
        UUID assessmentId = UUID.randomUUID();
        Long questionnaireId = 25L;
        Long questionId = 1L;
        Long answerOptionId = 2L;
        SubmitAnswerUseCase.Param param = new SubmitAnswerUseCase.Param(
            assessmentId,
            questionnaireId,
            questionId,
            answerOptionId
        );
        when(loadAnswerIdAndOptionIdPort.loadAnswerIdAndOptionId(assessmentId, questionId))
            .thenReturn(Optional.empty());

        UUID savedAnswerId = UUID.randomUUID();
        when(createAnswerPort.persist(any(CreateAnswerPort.Param.class))).thenReturn(savedAnswerId);

        service.submitAnswer(param);

        ArgumentCaptor<CreateAnswerPort.Param> saveAnswerParam = ArgumentCaptor.forClass(CreateAnswerPort.Param.class);
        verify(createAnswerPort).persist(saveAnswerParam.capture());
        assertEquals(assessmentId, saveAnswerParam.getValue().assessmentId());
        assertEquals(questionnaireId, saveAnswerParam.getValue().questionnaireId());
        assertEquals(questionId, saveAnswerParam.getValue().questionId());
        assertEquals(answerOptionId, saveAnswerParam.getValue().answerOptionId());

        verify(createAnswerPort, times(1)).persist(any(CreateAnswerPort.Param.class));
        verify(invalidateAssessmentResultPort, times(1)).invalidateByAssessmentId(assessmentId);
        verifyNoInteractions(
            updateAnswerPort
        );
    }

    @Test
    void submitAnswer_AnswerWithDifferentAnswerOptionExist_UpdatesAnswerAndInvalidatesAssessmentResult() {
        UUID assessmentId = UUID.randomUUID();
        Long questionnaireId = 1L;
        Long questionId = 1L;
        Long newAnswerOptionId = 2L;
        Long oldAnswerOptionId = 3L;
        SubmitAnswerUseCase.Param param = new SubmitAnswerUseCase.Param(
            assessmentId,
            questionnaireId,
            questionId,
            newAnswerOptionId
        );
        UUID existAnswerId = UUID.randomUUID();
        Optional<Result> existAnswer = Optional.of(new Result(
            existAnswerId,
            oldAnswerOptionId
        ));
        assertNotEquals(oldAnswerOptionId, newAnswerOptionId);

        when(loadAnswerIdAndOptionIdPort.loadAnswerIdAndOptionId(assessmentId, questionId)).thenReturn(existAnswer);

        service.submitAnswer(param);

        ArgumentCaptor<UpdateAnswerOptionPort.Param> updateParam = ArgumentCaptor.forClass(UpdateAnswerOptionPort.Param.class);
        verify(updateAnswerPort).updateAnswerOptionById(updateParam.capture());
        assertEquals(existAnswerId, updateParam.getValue().id());
        assertEquals(newAnswerOptionId, updateParam.getValue().answerOptionId());

        verify(loadAnswerIdAndOptionIdPort, times(1)).loadAnswerIdAndOptionId(assessmentId, questionId);
        verify(updateAnswerPort, times(1)).updateAnswerOptionById(any(UpdateAnswerOptionPort.Param.class));
        verify(invalidateAssessmentResultPort, times(1)).invalidateByAssessmentId(assessmentId);
        verifyNoInteractions(
            createAnswerPort
        );
    }

    @Test
    void submitAnswer_AnswerWithSameAnswerOptionExist_DoesntInvalidateAssessmentResult() {
        UUID assessmentId = UUID.randomUUID();
        Long questionnaireId = 1L;
        Long questionId = 1L;
        Long sameAnswerOptionId = 2L;
        SubmitAnswerUseCase.Param param = new SubmitAnswerUseCase.Param(
            assessmentId,
            questionnaireId,
            questionId,
            sameAnswerOptionId
        );
        UUID existAnswerId = UUID.randomUUID();
        Optional<Result> existAnswer = Optional.of(new Result(
            existAnswerId,
            sameAnswerOptionId
        ));
        when(loadAnswerIdAndOptionIdPort.loadAnswerIdAndOptionId(assessmentId, questionId)).thenReturn(existAnswer);

        service.submitAnswer(param);

        verify(loadAnswerIdAndOptionIdPort, times(1)).loadAnswerIdAndOptionId(assessmentId, questionId);
        verifyNoInteractions(
            createAnswerPort,
            updateAnswerPort,
            invalidateAssessmentResultPort
        );
    }
}
