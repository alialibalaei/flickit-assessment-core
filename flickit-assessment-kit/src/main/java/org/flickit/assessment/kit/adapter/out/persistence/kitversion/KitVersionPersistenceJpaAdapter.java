package org.flickit.assessment.kit.adapter.out.persistence.kitversion;

import lombok.RequiredArgsConstructor;
import org.flickit.assessment.common.exception.ResourceNotFoundException;
import org.flickit.assessment.data.jpa.kit.kitversion.KitVersionJpaEntity;
import org.flickit.assessment.data.jpa.kit.kitversion.KitVersionJpaRepository;
import org.flickit.assessment.kit.application.port.out.kitversion.LoadKitVersionExpertGroupPort;
import org.springframework.stereotype.Component;

import static org.flickit.assessment.kit.common.ErrorMessageKey.KIT_ID_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class KitVersionPersistenceJpaAdapter implements LoadKitVersionExpertGroupPort {

    private final KitVersionJpaRepository repository;

    @Override
    public Long loadKitVersionExpertGroupId(Long kitVersionId) {
        KitVersionJpaEntity entity = repository.findById(kitVersionId)
            .orElseThrow(() -> new ResourceNotFoundException(KIT_ID_NOT_FOUND));
        return entity.getKit().getExpertGroupId();
    }
}
