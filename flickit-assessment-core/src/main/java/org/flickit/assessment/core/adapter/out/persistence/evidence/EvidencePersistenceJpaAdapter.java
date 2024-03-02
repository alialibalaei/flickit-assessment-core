package org.flickit.assessment.core.adapter.out.persistence.evidence;

import lombok.RequiredArgsConstructor;
import org.flickit.assessment.common.application.domain.crud.PaginatedResponse;
import org.flickit.assessment.core.application.port.in.evidence.GetAttributeEvidenceListUseCase.AttributeEvidenceListItem;
import org.flickit.assessment.core.application.port.in.evidence.GetEvidenceListUseCase.EvidenceListItem;
import org.flickit.assessment.core.application.port.out.evidence.*;
import org.flickit.assessment.data.jpa.core.evidence.EvidenceJpaEntity;
import org.flickit.assessment.data.jpa.core.evidence.EvidenceJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EvidencePersistenceJpaAdapter implements
    CreateEvidencePort,
    LoadEvidencesPort,
    UpdateEvidencePort,
    DeleteEvidencePort,
    CheckEvidenceExistencePort,
    LoadAttributeEvidencesPort {

    private final EvidenceJpaRepository repository;

    @Override
    public UUID persist(CreateEvidencePort.Param param) {
        var unsavedEntity = EvidenceMapper.mapCreateParamToJpaEntity(param);
        EvidenceJpaEntity entity = repository.save(unsavedEntity);
        return entity.getId();
    }

    @Override
    public PaginatedResponse<EvidenceListItem> loadNotDeletedEvidences(Long questionId, UUID assessmentId, int page, int size) {
        var pageResult = repository.findByQuestionIdAndAssessmentIdAndDeletedFalseOrderByLastModificationTimeDesc(
            questionId, assessmentId, PageRequest.of(page, size)
        );
        var items = pageResult.getContent().stream()
            .map(EvidenceMapper::toEvidenceListItem)
            .toList();
        return new PaginatedResponse<>(
            items,
            pageResult.getNumber(),
            pageResult.getSize(),
            EvidenceJpaEntity.Fields.LAST_MODIFICATION_TIME,
            Sort.Direction.DESC.name().toLowerCase(),
            (int) pageResult.getTotalElements()
        );
    }

    @Override
    public UpdateEvidencePort.Result update(UpdateEvidencePort.Param param) {
        repository.update(
            param.id(),
            param.description(),
            param.type(),
            param.lastModificationTime(),
            param.lastModifiedById()
        );
        return new UpdateEvidencePort.Result(param.id());
    }

    @Override
    public void deleteById(UUID id) {
        repository.delete(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return repository.existsByIdAndDeletedFalse(id);
    }

    @Override
    public PaginatedResponse<AttributeEvidenceListItem> loadAttributeEvidences(UUID assessmentId, Long attributeId,
                                                                               Integer type, int page, int size) {
        var pageResult = repository.findAssessmentAttributeEvidencesByTypeOrderByLastModificationTimeDesc(
            assessmentId, attributeId, type, PageRequest.of(page, size));

        var items = pageResult.getContent().stream()
            .map(AttributeEvidenceListItem::new)
            .toList();
        return new PaginatedResponse<>(
            items,
            pageResult.getNumber(),
            pageResult.getSize(),
            EvidenceJpaEntity.Fields.LAST_MODIFICATION_TIME,
            Sort.Direction.DESC.name().toLowerCase(),
            (int) pageResult.getTotalElements()
        );
    }
}
