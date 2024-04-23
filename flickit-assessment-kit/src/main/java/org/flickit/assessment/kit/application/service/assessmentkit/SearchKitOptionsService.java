package org.flickit.assessment.kit.application.service.assessmentkit;

import lombok.RequiredArgsConstructor;
import org.flickit.assessment.common.application.domain.crud.PaginatedResponse;
import org.flickit.assessment.kit.application.domain.AssessmentKit;
import org.flickit.assessment.kit.application.port.in.assessmentkit.SearchKitOptionsUseCase;
import org.flickit.assessment.kit.application.port.out.assessmentkit.SearchKitOptionsPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchKitOptionsService implements SearchKitOptionsUseCase {

    private final SearchKitOptionsPort port;

    @Override
    public PaginatedResponse<KitListItem> searchKitOptions(Param param) {
        PaginatedResponse<AssessmentKit> paginatedResponse = port.searchKitOptions(toParam(param.getQueryTerm(),
            param.getCurrentUserId(),
            param.getPage(),
            param.getSize()));

        List<KitListItem> items = paginatedResponse.getItems().stream()
            .map(e -> new KitListItem(e.getId(), e.getTitle()))
            .toList();

        return new PaginatedResponse<>(
            items,
            paginatedResponse.getPage(),
            paginatedResponse.getSize(),
            paginatedResponse.getSort(),
            paginatedResponse.getOrder(),
            paginatedResponse.getTotal());
    }

    private SearchKitOptionsPort.Param toParam(String queryTerm, UUID currentUserId, int page, int size) {
        return new SearchKitOptionsPort.Param(queryTerm, currentUserId, page, size);
    }
}
