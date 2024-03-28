package org.flickit.assessment.users.adapter.out.persistence.expertgroupaccess;

import org.flickit.assessment.common.exception.AccessDeniedException;
import org.flickit.assessment.users.application.port.out.expertgroupaccess.*;
import lombok.RequiredArgsConstructor;
import org.flickit.assessment.common.application.domain.crud.PaginatedResponse;
import org.flickit.assessment.data.jpa.users.expertgroup.MembersView;
import org.flickit.assessment.data.jpa.users.expertgroupaccess.ExpertGroupAccessJpaEntity;
import org.flickit.assessment.data.jpa.users.expertgroupaccess.ExpertGroupAccessJpaRepository;
import org.flickit.assessment.data.jpa.users.user.UserJpaEntity;
import org.flickit.assessment.users.application.port.out.expertgroupaccess.CreateExpertGroupAccessPort;
import org.flickit.assessment.users.application.port.out.expertgroupaccess.LoadExpertGroupMembersPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static org.flickit.assessment.users.common.ErrorMessageKey.CONFIRM_EXPERT_GROUP_INVITATION_INVITE_TOKEN_IS_NOT_VALID;

@Component
@RequiredArgsConstructor
public class ExpertGroupAccessPersistenceJpaAdapter implements
    CreateExpertGroupAccessPort,
    LoadExpertGroupMembersPort,
    InviteExpertGroupMemberPort,
    LoadExpertGroupMemberStatusPort,
    CheckInviteTokenValidationPort,
    ConfirmExpertGroupInvitationPort {

    private final ExpertGroupAccessJpaRepository repository;

    @Override
    public PaginatedResponse<Member> loadExpertGroupMembers(long expertGroupId, int page, int size) {
        var pageResult = repository.findExpertGroupMembers(expertGroupId,
            PageRequest.of(page, size, Sort.Direction.ASC, UserJpaEntity.Fields.NAME));

        var items = pageResult
            .stream()
            .map(ExpertGroupAccessPersistenceJpaAdapter::mapToResult)
            .toList();

        return new PaginatedResponse<>(
            items,
            pageResult.getNumber(),
            pageResult.getSize(),
            UserJpaEntity.Fields.NAME,
            Sort.Direction.ASC.name().toLowerCase(),
            (int) pageResult.getTotalElements()
        );
    }

    @Override
    public void persist(CreateExpertGroupAccessPort.Param param) {
        ExpertGroupAccessJpaEntity unsavedEntity = ExpertGroupAccessMapper.mapCreateParamToJpaEntity(param);
        repository.save(unsavedEntity);
    }

    @Override
    public void invite(InviteExpertGroupMemberPort.Param param) {
        ExpertGroupAccessJpaEntity unsavedEntity = (ExpertGroupAccessMapper.mapInviteParamToJpaEntity(param));
        repository.save(unsavedEntity);
    }

    private static LoadExpertGroupMembersPort.Member mapToResult(MembersView view) {
        return new LoadExpertGroupMembersPort.Member(
            view.getId(),
            view.getEmail(),
            view.getDisplayName(),
            view.getBio(),
            view.getPicture(),
            view.getLinkedin());
    }

    @Override
    public Optional<Integer> getMemberStatus(long expertGroupId, UUID userId) {
        return repository.findExpertGroupMemberStatus(expertGroupId, userId);
    }

    @Override
    public void checkToken(long expertGroupId, UUID userId, UUID inviteToken) {
        boolean exist = repository.existsByExpertGroupIdAndUserIdAndInviteToken(expertGroupId, userId, inviteToken);
        if (!exist)
            throw new AccessDeniedException(CONFIRM_EXPERT_GROUP_INVITATION_INVITE_TOKEN_IS_NOT_VALID);
    }

    @Override
    public void confirmInvitation(UUID inviteToken) {
        repository.confirmInvitation(inviteToken);
    }
}
