package org.flickit.assessment.core.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessageKey {
    public static final String ASSESSMENT_KIT_ID_NOT_FOUND = "assessment-kit-id.notFound";
    public static final String EVIDENCE_ID_NOT_FOUND = "evidence-id.notFound";
    public static final String ASSESSMENT_ID_NOT_FOUND = "assessment-id.notFound";

    public static final String CREATE_ASSESSMENT_TITLE_NOT_BLANK = "create-assessment.title.notBlank";
    public static final String CREATE_ASSESSMENT_TITLE_SIZE_MIN = "create-assessment.title.size.min";
    public static final String CREATE_ASSESSMENT_TITLE_SIZE_MAX = "create-assessment.title.size.max";
    public static final String CREATE_ASSESSMENT_ASSESSMENT_KIT_ID_NOT_NULL = "create-assessment.assessmentKitId.notNull";
    public static final String CREATE_ASSESSMENT_SPACE_ID_NOT_NULL = "create-assessment.spaceId.notNull";
    public static final String CREATE_ASSESSMENT_COLOR_ID_NOT_NULL = "create-assessment.colorId.notNull";
    public static final String CREATE_ASSESSMENT_DUPLICATE_TITLE_AND_SPACE_ID = "create-assessment.duplicate.titleAndSpaceId";

    public static final String CREATE_ASSESSMENT_RESULT_ASSESSMENT_ID_NOT_FOUND = "create-assessmentresult.assessmentId.notFound";

    public static final String CREATE_QUALITY_ATTRIBUTE_VALUE_ASSESSMENT_RESULT_ID_NOT_FOUND = "create-qualityattributevalue.assessmentResultId.notFound";

    public static final String CREATE_SUBJECT_VALUE_ASSESSMENT_RESULT_ID_NOT_FOUND = "crate-subjectvalue.assessmentResultId.notFound";

    public static final String SUBMIT_ANSWER_ASSESSMENT_RESULT_NOT_FOUND = "submit-answer.assessmentResult.notFound";
    public static final String SUBMIT_ANSWER_ASSESSMENT_ID_NOT_NULL = "submit-answer.assessmentId.notNull";
    public static final String SUBMIT_ANSWER_QUESTIONNAIRE_ID_NOT_NULL = "submit-answer.questionnaireId.notNull";
    public static final String SUBMIT_ANSWER_QUESTION_ID_NOT_NULL = "submit-answer.questionId.notNull";
    public static final String SUBMIT_ANSWER_QUESTION_ID_NOT_FOUND = "submit-answer.questionId.notFound";
    public static final String SUBMIT_ANSWER_ANSWER_OPTION_ID_NOT_FOUND = "submit-answer.answerOptionId.notFound";
    public static final String SUBMIT_ANSWER_ANSWER_ID_NOT_FOUND = "submit-answer.answerId.notFound";

    public static final String GET_ASSESSMENT_LIST_SPACE_IDS_NOT_NULL = "get-assessment-list.spaceId.notNullOrEmpty";
    public static final String GET_ASSESSMENT_LIST_SIZE_MIN = "get-assessment-list.size.min";
    public static final String GET_ASSESSMENT_LIST_SIZE_MAX = "get-assessment-list.size.max";
    public static final String GET_ASSESSMENT_LIST_PAGE_MIN = "get-assessment-list.page.min";

    public static final String ADD_EVIDENCE_ASSESSMENT_ID_NOT_FOUND = "add-evidence.assessmentId.notFound";
    public static final String ADD_EVIDENCE_DESC_NOT_BLANK = "add-evidence.desc.notBlank";
    public static final String ADD_EVIDENCE_DESC_SIZE_MIN = "add-evidence.desc.size.min";
    public static final String ADD_EVIDENCE_DESC_SIZE_MAX = "add-evidence.desc.size.max";
    public static final String ADD_EVIDENCE_ASSESSMENT_ID_NOT_NULL = "add-evidence.assessmentId.notNull";
    public static final String ADD_EVIDENCE_QUESTION_ID_NOT_NULL = "add-evidence.questionId.notNull";
    public static final String ADD_EVIDENCE_QUESTION_ID_NOT_FOUND = "add-evidence.questionId.notFound";
    public static final String ADD_EVIDENCE_TYPE_INVALID = "add-evidence.type.invalid";

    public static final String UPDATE_ASSESSMENT_ID_NOT_NULL = "update-assessment.id.notNull";
    public static final String UPDATE_ASSESSMENT_ID_NOT_FOUND = "update-assessment.id.notFound";
    public static final String UPDATE_ASSESSMENT_TITLE_NOT_BLANK = "update-assessment.title.notBlank";
    public static final String UPDATE_ASSESSMENT_TITLE_SIZE_MIN = "update-assessment.title.min";
    public static final String UPDATE_ASSESSMENT_TITLE_SIZE_MAX = "update-assessment.title.max";
    public static final String UPDATE_ASSESSMENT_COLOR_ID_NOT_NULL = "update-assessment.color-id.notNull";

    public static final String CALCULATE_ASSESSMENT_ID_NOT_NULL = "calculate-assessment.assessment.id.notNull";
    public static final String CALCULATE_ASSESSMENT_ASSESSMENT_RESULT_NOT_FOUND = "calculate-assessment.assessmentResult.notFount";

    public static final String GET_ANSWER_LIST_ASSESSMENT_ID_NOTNULL = "get-answer-list.assessmentId.notNull";
    public static final String GET_ANSWER_LIST_QUESTIONNAIRE_ID_NOTNULL = "get-answer-list.questionnaireId.notNull";
    public static final String GET_ANSWER_LIST_SIZE_MIN = "get-answer-list.size.min";
    public static final String GET_ANSWER_LIST_SIZE_MAX = "get-answer-list.size.max";
    public static final String GET_ANSWER_LIST_PAGE_MIN = "get-answer-list.page.min";

    public static final String GET_ATTRIBUTE_EVIDENCE_LIST_ASSESSMENT_ID_NOT_NULL = "get-attribute-evidence-list.assessmentId.notNull";
    public static final String GET_ATTRIBUTE_EVIDENCE_LIST_ATTRIBUTE_ID_NOT_NULL = "get-attribute-evidence-list.attributeId.notNull";
    public static final String GET_ATTRIBUTE_EVIDENCE_LIST_ASSESSMENT_ID_NOT_FOUND = "get-attribute-evidence-list.assessmentId.notFound";
    public static final String GET_ATTRIBUTE_EVIDENCE_LIST_TYPE_INVALID = "get-attribute-evidence-list.type.invalid";
    public static final String GET_ATTRIBUTE_EVIDENCE_LIST_TYPE_NOT_NULL = "get-attribute-evidence-list.type.notNull";
    public static final String GET_ATTRIBUTE_EVIDENCE_LIST_SIZE_MIN = "get-attribute-evidence-list.size.min";
    public static final String GET_ATTRIBUTE_EVIDENCE_LIST_SIZE_MAX = "get-attribute-evidence-list.size.max";
    public static final String GET_ATTRIBUTE_EVIDENCE_LIST_PAGE_MIN = "get-attribute-evidence-list.page.min";

    public static final String GET_EVIDENCE_LIST_QUESTION_ID_NOT_NULL = "get-evidence-list.questionId.notNull";
    public static final String GET_EVIDENCE_LIST_ASSESSMENT_ID_NOT_NULL = "get-evidence-list.assessmentId.notNull";
    public static final String GET_EVIDENCE_LIST_SIZE_MIN = "get-evidence-list.size.min";
    public static final String GET_EVIDENCE_LIST_SIZE_MAX = "get-evidence-list.size.max";
    public static final String GET_EVIDENCE_LIST_PAGE_MIN = "get-evidence-list.page.min";

    public static final String REPORT_ASSESSMENT_ASSESSMENT_ID_NOT_FOUND = "report-assessment-assessment.id.notFound";
    public static final String REPORT_ASSESSMENT_ID_NOT_NULL = "report-assessment.assessment.id.notNull";
    public static final String REPORT_ASSESSMENT_ASSESSMENT_RESULT_NOT_FOUND = "report-assessment.assessmentResult.notFound";
    public static final String REPORT_ASSESSMENT_ASSESSMENT_KIT_NOT_FOUND = "report-assessment.assessmentKit.notFound";
    public static final String REPORT_ASSESSMENT_EXPERT_GROUP_NOT_FOUND = "report-assessment.expertGroup.notFound";

    public static final String GET_ASSESSMENT_PROGRESS_ASSESSMENT_ID_NOT_NULL = "get-assessment-progress.assessment.id.notNull";
    public static final String GET_ASSESSMENT_PROGRESS_ASSESSMENT_NOT_FOUND = "get-assessment-progress.assessment.notFound";

    public static final String GET_QUESTIONNAIRES_PROGRESS_ASSESSMENT_ID_NOT_NULL = "get-questionnaires-progress.assessmentId.notNull";
    public static final String GET_QUESTIONNAIRES_PROGRESS_ASSESSMENT_RESULT_NOT_FOUND = "get-questionnaires-progress.assessmentResult.notFound";

    public static final String REPORT_SUBJECT_ASSESSMENT_ID_NOT_NULL = "report-subject.assessment.id.notNull";
    public static final String REPORT_SUBJECT_ID_NOT_NULL = "report-subject.subject.id.notNull";
    public static final String REPORT_SUBJECT_ID_NOT_FOUND = "report-subject.subject.id.notFound";
    public static final String REPORT_SUBJECT_ASSESSMENT_RESULT_NOT_FOUND = "report-subject.assessmentResult.notFound";
    public static final String REPORT_SUBJECT_ASSESSMENT_SUBJECT_VALUE_NOT_FOUND = "report-subject.subjectValue.notFound";

    public static final String UPDATE_EVIDENCE_ID_NOT_NULL = "update-evidence.id.notNull";
    public static final String UPDATE_EVIDENCE_DESC_NOT_BLANK = "update-evidence.description.notBlank";
    public static final String UPDATE_EVIDENCE_DESC_MIN_SIZE = "update-evidence.description.size.min";
    public static final String UPDATE_EVIDENCE_DESC_MAX_SIZE = "update-evidence.description.size.max";
    public static final String UPDATE_EVIDENCE_TYPE_INVALID = "update-evidence.type.invalid";

    public static final String GET_SUBJECT_PROGRESS_ASSESSMENT_ID_NOT_NULL = "get-subject-progress.assessment.id.notNull";
    public static final String GET_SUBJECT_PROGRESS_ASSESSMENT_RESULT_NOT_FOUND = "get-subject-progress.assessmentResultId.notFound";
    public static final String GET_SUBJECT_PROGRESS_SUBJECT_ID_NOT_NULL = "get-subject-progress.subject.id.notNull";
    public static final String GET_SUBJECT_PROGRESS_SUBJECT_ID_NOT_FOUND = "get-subject-progress.subject.id.notFound";

    public static final String GET_ASSESSMENT_ASSESSMENT_ID_NOT_NULL = "get-assessment.assessmentId.notNull";
    public static final String GET_ASSESSMENT_ASSESSMENT_ID_NOT_FOUND = "get-assessment.assessmentId.notFound";

    public static final String DELETE_ASSESSMENT_ID_NOT_NULL = "delete-assessment.id.notNull";
    public static final String DELETE_ASSESSMENT_ID_NOT_FOUND = "delete-assessment.id.notFound";

    public static final String COUNT_ASSESSMENTS_KIT_ID_AND_SPACE_ID_NOT_NULL = "count-assessments.assessment.kit.id.and.space.id.notNull";

    public static final String DELETE_EVIDENCE_EVIDENCE_ID_NOT_NULL = "delete-evidence.id.notNull";

    public static final String CALCULATE_CONFIDENCE_ASSESSMENT_ID_NOT_NULL = "calculate-confidence.assessment.id.notNull";
    public static final String CALCULATE_CONFIDENCE_ASSESSMENT_RESULT_NOT_FOUND = "calculate-confidence.assessmentResult.notFount";

    public static final String GET_ATTRIBUTE_SCORE_DETAIL_ASSESSMENT_RESULT_NOT_FOUND = "get-attribute-score-detail.assessmentResult.notFound";
    public static final String GET_ATTRIBUTE_SCORE_DETAIL_ASSESSMENT_ID_NOT_NULL = "get-attribute-score-detail.assessment.id.notNull";
    public static final String GET_ATTRIBUTE_SCORE_DETAIL_ATTRIBUTE_ID_NOT_NULL = "get-attribute-score-detail.attribute.id.notNull";
    public static final String GET_ATTRIBUTE_SCORE_DETAIL_MATURITY_LEVEL_ID_NOT_NULL = "get-attribute-score-detail.maturityLevel.id.notNull";

    public static final String GET_ASSESSMENT_QUESTIONNAIRE_LIST_ASSESSMENT_ID_NOT_NULL = "get-assessment-questionnaire-list.assessmentId.notNull";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_LIST_CURRENT_USER_ID_NOT_NULL = "get-assessment-questionnaire-list.currentUserId.notNull";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_LIST_SIZE_MIN = "get-assessment-questionnaire-list.size.min";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_LIST_SIZE_MAX = "get-assessment-questionnaire-list.size.max";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_LIST_PAGE_MIN = "get-assessment-questionnaire-list.page.min";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_LIST_ASSESSMENT_RESULT_ID_NOT_FOUND = "get-assessment-questionnaire-list.assessmentResultId.notFound";

    public static final String GET_ASSESSMENT_QUESTIONNAIRE_QUESTION_LIST_ASSESSMENT_ID_NOT_NULL = "get-assessment-questionnaire-question-list.assessmentId.notNull";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_QUESTION_LIST_QUESTIONNAIRE_ID_NOT_NULL = "get-assessment-questionnaire-question-list.questionnaireId.notNull";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_QUESTION_LIST_SIZE_MIN = "get-assessment-questionnaire-question-list.size.min";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_QUESTION_LIST_SIZE_MAX = "get-assessment-questionnaire-question-list.size.max";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_QUESTION_LIST_PAGE_MIN = "get-assessment-questionnaire-question-list.page.min";
    public static final String GET_ASSESSMENT_QUESTIONNAIRE_QUESTION_LIST_ASSESSMENT_ID_NOT_FOUND = "get-assessment-questionnaire-question-list.assessmentResultId.notFound";

    public static final String GRANT_ASSESSMENT_USER_ROLE_ASSESSMENT_ID_NOT_NULL = "grant-assessment-user-role.assessmentId.notNull";
    public static final String GRANT_ASSESSMENT_USER_ROLE_USER_ID_NOT_NULL = "grant-assessment-user-role.userId.notNull";
    public static final String GRANT_ASSESSMENT_USER_ROLE_USER_ROLE_DUPLICATE = "grant-assessment-user-role.user.role.duplicate";
    public static final String GRANT_ASSESSMENT_USER_ROLE_ROLE_ID_NOT_NULL = "grant-assessment-user-role.roleId.notNull";
    public static final String GRANT_ASSESSMENT_USER_ROLE_ROLE_ID_NOT_FOUND = "grant-assessment-user-role.roleId.notFound";
}
