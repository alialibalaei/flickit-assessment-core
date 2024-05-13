package org.flickit.assessment.users.application.service.space;

import org.flickit.assessment.common.application.domain.crud.PaginatedResponse;
import org.flickit.assessment.data.jpa.users.spaceuseraccess.SpaceUserAccessJpaEntity;
import org.flickit.assessment.data.jpa.users.user.UserJpaEntity;
import org.flickit.assessment.users.application.port.in.space.GetSpaceListUseCase;
import org.flickit.assessment.users.application.port.out.space.LoadSpaceListPort;
import org.flickit.assessment.users.application.port.out.space.LoadSpaceListPort.Param;
import org.flickit.assessment.users.application.port.out.space.LoadSpaceListPort.Result;
import org.flickit.assessment.users.test.fixture.application.SpaceMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetSpaceListServiceTest {

    @InjectMocks
    private GetSpaceListService service;

    @Mock
    private LoadSpaceListPort loadSpaceListPort;

    @Test
    void testGetSpaceListParam_validInputs_validResults() {
        int size = 10;
        int page = 0;
        UUID currentUserId = UUID.randomUUID();
        var space1 = SpaceMother.createSpace(currentUserId);
        var space2 = SpaceMother.createSpace(UUID.randomUUID());
        var spacePortList = List.of(
            new LoadSpaceListPort.Result(space1, 2, 5),
            new Result(space2, 4, 3));

        PaginatedResponse<Result> paginatedResponse = new PaginatedResponse<>(
            spacePortList,
            page,
            size,
            SpaceUserAccessJpaEntity.Fields.LAST_SEEN,
            Sort.Direction.DESC.name().toLowerCase(),
            spacePortList.size());

        when(loadSpaceListPort.loadSpaceList(any(Param.class))).thenReturn(paginatedResponse);

        GetSpaceListUseCase.Param param = new GetSpaceListUseCase.Param(size, page, currentUserId);
        var result = service.getSpaceList(param);

        ArgumentCaptor<Param> loadPortParam = ArgumentCaptor.forClass(Param.class);
        verify(loadSpaceListPort).loadSpaceList(loadPortParam.capture());

        assertEquals(page, loadPortParam.getValue().page());
        assertEquals(size, loadPortParam.getValue().size());
        assertNotNull(paginatedResponse);
        assertNotNull(result.getItems());
        assertEquals(2, result.getItems().size());
        assertEquals(spacePortList.get(0).space().getId(), result.getItems().get(0).id());
        assertEquals(spacePortList.get(0).space().getTitle(), result.getItems().get(0).title());
        assertTrue(result.getItems().get(0).isOwner());
        assertEquals(spacePortList.get(0).space().getLastModificationTime(), result.getItems().get(0).lastModificationTime());
        assertEquals(spacePortList.get(0).assessmentsCount(), result.getItems().get(0).assessmentsCount());
        assertEquals(spacePortList.get(0).membersCount(), result.getItems().get(0).membersCount());

        assertEquals(spacePortList.get(1).space().getId(), result.getItems().get(1).id());
        assertEquals(spacePortList.get(1).space().getTitle(), result.getItems().get(1).title());
        assertFalse(result.getItems().get(1).isOwner());
        assertEquals(spacePortList.get(1).space().getLastModificationTime(), result.getItems().get(1).lastModificationTime());
        assertEquals(spacePortList.get(1).assessmentsCount(), result.getItems().get(1).assessmentsCount());
        assertEquals(spacePortList.get(1).membersCount(), result.getItems().get(1).membersCount());
    }

    @Test
    void testGetSpaceListParam_ValidInputs_emptyResults() {
        int page = 0;
        int size = 10;
        UUID currentUserId = UUID.randomUUID();

        PaginatedResponse<Result> paginatedResponse = new PaginatedResponse<>(
            Collections.emptyList(),
            page,
            size,
            UserJpaEntity.Fields.NAME,
            Sort.Direction.ASC.name().toLowerCase(),
            0);
        when(loadSpaceListPort.loadSpaceList(any(Param.class)))
            .thenReturn(paginatedResponse);

        var param = new GetSpaceListUseCase.Param(size, page, currentUserId);
        var result = service.getSpaceList(param);

        ArgumentCaptor<LoadSpaceListPort.Param> loadPortParam = ArgumentCaptor.forClass(LoadSpaceListPort.Param.class);
        verify(loadSpaceListPort).loadSpaceList(loadPortParam.capture());

        assertEquals(page, loadPortParam.getValue().page());
        assertEquals(size, loadPortParam.getValue().size());
        assertNotNull(paginatedResponse);
        assertNotNull(result.getItems());
        assertEquals(0, result.getItems().size());
    }
}
