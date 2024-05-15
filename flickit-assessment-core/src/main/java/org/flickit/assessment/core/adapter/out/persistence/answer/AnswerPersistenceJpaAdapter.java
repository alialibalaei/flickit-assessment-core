package org.flickit.assessment.core.adapter.out.persistence.answer;

import lombok.RequiredArgsConstructor;
import org.flickit.assessment.common.application.domain.crud.PaginatedResponse;
import org.flickit.assessment.common.exception.ResourceNotFoundException;
import org.flickit.assessment.core.application.domain.Answer;
import org.flickit.assessment.core.application.port.in.answer.GetAnswerListUseCase.AnswerListItem;
import org.flickit.assessment.core.application.port.in.questionnaire.GetQuestionnairesProgressUseCase.QuestionnaireProgress;
import org.flickit.assessment.core.application.port.out.answer.*;
import org.flickit.assessment.core.application.port.out.questionnaire.GetQuestionnairesProgressPort;
import org.flickit.assessment.data.jpa.core.answer.AnswerJpaEntity;
import org.flickit.assessment.data.jpa.core.answer.AnswerJpaRepository;
import org.flickit.assessment.data.jpa.core.assessmentresult.AssessmentResultJpaRepository;
import org.flickit.assessment.data.jpa.kit.answeroption.AnswerOptionJpaRepository;
import org.flickit.assessment.data.jpa.kit.question.FirstUnansweredQuestionView;
import org.flickit.assessment.data.jpa.kit.question.QuestionJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static org.flickit.assessment.core.common.ErrorMessageKey.*;

@Component
@RequiredArgsConstructor
public class AnswerPersistenceJpaAdapter implements
    CreateAnswerPort,
    LoadAnswersByQuestionnaireIdPort,
    GetQuestionnairesProgressPort,
    CountAnswersByQuestionIdsPort,
    LoadAnswerPort,
    UpdateAnswerPort {

    private final AnswerJpaRepository repository;
    private final AssessmentResultJpaRepository assessmentResultRepo;
    private final QuestionJpaRepository questionRepository;
    private final AnswerOptionJpaRepository answerOptionRepository;

    @Override
    public UUID persist(CreateAnswerPort.Param param) {
        var assessmentResult = assessmentResultRepo.findById(param.assessmentResultId())
            .orElseThrow(() -> new ResourceNotFoundException(SUBMIT_ANSWER_ASSESSMENT_RESULT_NOT_FOUND));
        var question = questionRepository.findById(param.questionId())
            .orElseThrow(() -> new ResourceNotFoundException(SUBMIT_ANSWER_QUESTION_ID_NOT_FOUND)); // TODO: This query must be changed after question id deletion
        var answerOption = answerOptionRepository.findById(param.answerOptionId())
            .orElseThrow(() -> new ResourceNotFoundException(SUBMIT_ANSWER_ANSWER_OPTION_ID_NOT_FOUND));

        if (!Objects.equals(question.getKitVersionId(), assessmentResult.getKitVersionId()) ||
            !Objects.equals(question.getId(), answerOption.getQuestionId()) ||
            !Objects.equals(question.getQuestionnaireId(), param.questionnaireId()))
            throw new ResourceNotFoundException(SUBMIT_ANSWER_QUESTION_ID_NOT_FOUND);

        AnswerJpaEntity unsavedEntity = AnswerMapper.mapCreateParamToJpaEntity(param, question.getRefNum());
        unsavedEntity.setAssessmentResult(assessmentResult);
        AnswerJpaEntity entity = repository.save(unsavedEntity);
        return entity.getId();
    }

    @Override
    public PaginatedResponse<AnswerListItem> loadAnswersByQuestionnaireId(LoadAnswersByQuestionnaireIdPort.Param param) {
        var assessmentResult = assessmentResultRepo.findFirstByAssessment_IdOrderByLastModificationTimeDesc(param.assessmentId())
            .orElseThrow(() -> new ResourceNotFoundException(GET_ANSWER_LIST_ASSESSMENT_RESULT_ID_NOT_FOUND));

        var pageResult = repository.findByAssessmentResultIdAndQuestionnaireIdOrderByQuestionIdAsc(assessmentResult.getId(),
            param.questionnaireId(),
            PageRequest.of(param.page(), param.size()));

        var items = pageResult.getContent().stream()
            .map(AnswerMapper::mapJpaEntityToAnswerItem)
            .toList();
        return new PaginatedResponse<>(
            items,
            pageResult.getNumber(),
            pageResult.getSize(),
            AnswerJpaEntity.Fields.QUESTION_ID,
            Sort.Direction.ASC.name().toLowerCase(),
            (int) pageResult.getTotalElements()
        );
    }

    @Override
    public int countByQuestionIds(UUID assessmentResultId, List<Long> questionIds) {
        return repository.getCountByQuestionIds(assessmentResultId, questionIds);
    }

    @Override
    public List<QuestionnaireProgress> getQuestionnairesProgressByAssessmentId(UUID assessmentId) {
        var assessmentResult = assessmentResultRepo.findFirstByAssessment_IdOrderByLastModificationTimeDesc(assessmentId)
            .orElseThrow(() -> new ResourceNotFoundException(GET_QUESTIONNAIRES_PROGRESS_ASSESSMENT_RESULT_NOT_FOUND));

        var progresses = repository.getQuestionnairesProgressByAssessmentResultId(assessmentResult.getId());
        var unansweredQuestions = questionRepository.findQuestionnairesFirstUnansweredQuestion(assessmentResult.getId());

        Map<Long, Integer> questionnaireIdToQuestionIndex = unansweredQuestions.stream()
            .collect(Collectors.toMap(FirstUnansweredQuestionView::getQuestionnaireId, FirstUnansweredQuestionView::getIndex));
        return progresses.stream()
            .map(p -> new QuestionnaireProgress(p.getQuestionnaireId(),
                p.getAnswerCount(),
                questionnaireIdToQuestionIndex.get(p.getQuestionnaireId())))
            .toList();
    }

    @Override
    public Optional<Answer> load(UUID assessmentResultId, Long questionId) {
        return repository.findByAssessmentResultIdAndQuestionId(assessmentResultId, questionId)
            .map(AnswerMapper::mapToDomainModel);
    }

    @Override
    public void update(UpdateAnswerPort.Param param) {
        var answer = repository.findById(param.answerId())
            .orElseThrow(() -> new ResourceNotFoundException(SUBMIT_ANSWER_ANSWER_ID_NOT_FOUND));
        var answerOption = answerOptionRepository.findById(param.answerOptionId())
            .orElseThrow(() -> new ResourceNotFoundException(SUBMIT_ANSWER_ANSWER_OPTION_ID_NOT_FOUND));
        if (!Objects.equals(answer.getQuestionId(), answerOption.getQuestionId()))
            throw new ResourceNotFoundException(SUBMIT_ANSWER_ANSWER_OPTION_ID_NOT_FOUND);

        repository.update(param.answerId(), param.answerOptionId(), param.confidenceLevelId(), param.isNotApplicable(), param.currentUserId());
    }
}
