package org.flickit.assessment.core.adapter.out.report;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.flickit.assessment.core.application.domain.*;
import org.flickit.assessment.core.test.fixture.adapter.jpa.AssessmentResultJpaEntityMother;
import org.flickit.assessment.core.test.fixture.adapter.jpa.MaturityLevelJpaEntityMother;
import org.flickit.assessment.core.test.fixture.application.*;
import org.flickit.assessment.data.jpa.core.assessmentresult.AssessmentResultJpaEntity;
import org.flickit.assessment.data.jpa.core.assessmentresult.AssessmentResultJpaRepository;
import org.flickit.assessment.data.jpa.kit.maturitylevel.MaturityLevelJpaEntity;
import org.flickit.assessment.data.jpa.kit.maturitylevel.MaturityLevelJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateAttributeValueReportFileAdapterTest {

    @InjectMocks
    private GenerateAttributeValueReportFileAdapter adapter;

    @Mock
    private AssessmentResultJpaRepository assessmentResultRepository;

    @Mock
    private MaturityLevelJpaRepository maturityLevelRepository;

    @Test
    void testGenerateAttributeValueReportFile_ValidParam_CreateFile() {
        var attributeId = 1563L;
        var assessmentId = UUID.randomUUID();

        Answer answer = AnswerMother.fullScoreOnLevels23(attributeId);
        Question question = QuestionMother.withIdAndImpactsOnLevel23(answer.getQuestionId(), attributeId);
        Attribute attribute = AttributeMother.withIdAndQuestions(attributeId, List.of(question));
        AttributeValue attributeValue = AttributeValueMother.withAttributeAndAnswerAndLevelOne(attribute, List.of(answer));
        List<MaturityLevel> maturityLevels = MaturityLevelMother.allLevels();
        AssessmentResultJpaEntity assessmentResultEntity = AssessmentResultJpaEntityMother.validSimpleAssessmentResultEntity(156L, Boolean.TRUE, Boolean.TRUE);
        List<MaturityLevelJpaEntity> maturityLevelEntities = maturityLevels.stream()
            .map(x -> MaturityLevelJpaEntityMother.mapToJpaEntity(x, assessmentResultEntity.getKitVersionId()))
            .toList();

        when(assessmentResultRepository.findFirstByAssessment_IdOrderByLastModificationTimeDesc(assessmentId)).thenReturn(Optional.of(assessmentResultEntity));
        when(maturityLevelRepository.findAllByKitVersionIdOrderByIndex(assessmentResultEntity.getKitVersionId())).thenReturn(maturityLevelEntities);

        InputStream inputStream = adapter.generateReport(assessmentId, attributeValue);

        assertNotNull(inputStream);
    }

    @SneakyThrows
    @Test
    void testGenerateAttributeValueReportFile_ValidParam_FileStructureShouldNotBeChanged() {
        var attributeId = 1563L;
        var assessmentId = UUID.randomUUID();

        Answer answer = AnswerMother.fullScoreOnLevels23(attributeId);
        Question question = QuestionMother.withIdAndImpactsOnLevel23(answer.getQuestionId(), attributeId);
        Attribute attribute = AttributeMother.withIdAndQuestions(attributeId, List.of(question));
        AttributeValue attributeValue = AttributeValueMother.withAttributeAndAnswerAndLevelOne(attribute, List.of(answer));
        List<MaturityLevel> maturityLevels = MaturityLevelMother.allLevels();
        AssessmentResultJpaEntity assessmentResultEntity = AssessmentResultJpaEntityMother.validSimpleAssessmentResultEntity(156L, Boolean.TRUE, Boolean.TRUE);
        List<MaturityLevelJpaEntity> maturityLevelEntities = maturityLevels.stream()
            .map(x -> MaturityLevelJpaEntityMother.mapToJpaEntity(x, assessmentResultEntity.getKitVersionId()))
            .toList();

        when(assessmentResultRepository.findFirstByAssessment_IdOrderByLastModificationTimeDesc(assessmentId)).thenReturn(Optional.of(assessmentResultEntity));
        when(maturityLevelRepository.findAllByKitVersionIdOrderByIndex(assessmentResultEntity.getKitVersionId())).thenReturn(maturityLevelEntities);

        InputStream inputStream = adapter.generateReport(assessmentId, attributeValue);

        assertNotNull(inputStream);
        Workbook workbook = WorkbookFactory.create(inputStream);

        assertEquals(3, workbook.getNumberOfSheets());

        Sheet questionsSheet = workbook.getSheetAt(0);
        assertEquals(1, questionsSheet.getLastRowNum());

        Row questionsHeaderRow = questionsSheet.getRow(0);
        assertEquals(4, questionsHeaderRow.getLastCellNum());
        assertEquals("Question", questionsHeaderRow.getCell(0).getStringCellValue());
        assertEquals("Hint", questionsHeaderRow.getCell(1).getStringCellValue());
        assertEquals("Weight", questionsHeaderRow.getCell(2).getStringCellValue());
        assertEquals("Score", questionsHeaderRow.getCell(3).getStringCellValue());

        Row questionsFirstRow = questionsSheet.getRow(1);
        assertEquals(question.getTitle(), questionsFirstRow.getCell(0).getStringCellValue());
        assertEquals(question.getHint(), questionsFirstRow.getCell(1).getStringCellValue());
        assertEquals(question.getImpacts().get(0).getWeight(), questionsFirstRow.getCell(2).getNumericCellValue());
        assertEquals(answer.getSelectedOption().getImpacts().get(0).getValue(), questionsFirstRow.getCell(3).getNumericCellValue());

        Sheet attributeSheet = workbook.getSheetAt(1);
        assertEquals(1, attributeSheet.getLastRowNum());

        Row attributeHeaderRow = attributeSheet.getRow(0);
        assertEquals(2, attributeHeaderRow.getLastCellNum());
        assertEquals("Attribute Title", attributeHeaderRow.getCell(0).getStringCellValue());
        assertEquals("Attribute Maturity Level", attributeHeaderRow.getCell(1).getStringCellValue());

        Row attributeValueRow = attributeSheet.getRow(1);
        assertEquals(attribute.getTitle(), attributeValueRow.getCell(0).getStringCellValue());
        assertEquals(attributeValue.getMaturityLevel().getTitle(), attributeValueRow.getCell(1).getStringCellValue());

        Sheet maturityLevelsSheet = workbook.getSheetAt(2);
        assertEquals(maturityLevels.size(), maturityLevelsSheet.getLastRowNum());

        Row maturityLevelsHeaderRow = maturityLevelsSheet.getRow(0);
        assertEquals(3, maturityLevelsHeaderRow.getLastCellNum());
        assertEquals("Title", maturityLevelsHeaderRow.getCell(0).getStringCellValue());
        assertEquals("Index", maturityLevelsHeaderRow.getCell(1).getStringCellValue());
        assertEquals("Description", maturityLevelsHeaderRow.getCell(2).getStringCellValue());

        Row maturityLevelsFirstRow = maturityLevelsSheet.getRow(1);
        assertEquals(maturityLevels.get(0).getTitle(), maturityLevelsFirstRow.getCell(0).getStringCellValue());
        assertEquals(maturityLevels.get(0).getIndex(), maturityLevelsFirstRow.getCell(1).getNumericCellValue());
    }
}
