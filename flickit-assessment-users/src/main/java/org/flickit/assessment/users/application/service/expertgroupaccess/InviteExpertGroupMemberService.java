package org.flickit.assessment.users.application.service.expertgroupaccess;

import lombok.AllArgsConstructor;
import org.flickit.assessment.common.exception.AccessDeniedException;
import org.flickit.assessment.common.exception.ResourceNotFoundException;
import org.flickit.assessment.users.application.domain.ExpertGroupAccessStatus;
import org.flickit.assessment.users.application.port.out.expertgroup.CheckExpertGroupExistsPort;
import org.flickit.assessment.users.application.port.out.expertgroup.CheckExpertGroupOwnerPort;
import org.flickit.assessment.users.application.port.out.expertgroupaccess.InviteExpertGroupMemberPort;
import org.flickit.assessment.users.application.port.out.mail.SendExpertGroupInvitationMailPort;
import org.flickit.assessment.users.application.port.in.expertgroupaccess.InviteExpertGroupMemberUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import org.flickit.assessment.users.application.port.out.user.LoadUserEmailByUserIdPort;

import static org.flickit.assessment.users.common.ErrorMessageKey.EXPERT_GROUP_ID_NOT_FOUND;
import static org.flickit.assessment.users.common.ErrorMessageKey.INVITE_EXPERT_GROUP_MEMBER_OWNER_ID_ACCESS_DENIED;

@Service
@Transactional
@AllArgsConstructor
public class InviteExpertGroupMemberService implements InviteExpertGroupMemberUseCase {

    private final LoadUserEmailByUserIdPort loadUserEmailByUserIdPort;
    private final CheckExpertGroupExistsPort checkExpertGroupExistsPort;
    private final CheckExpertGroupOwnerPort checkExpertGroupOwnerPort;
    private final InviteExpertGroupMemberPort inviteExpertGroupMemberPort;
    private final SendExpertGroupInvitationMailPort sendExpertGroupInvitationMailPort;
    private static final Duration EXPIRY_DURATION = Duration.ofDays(7);

    @Override
    public void inviteMember(Param param) {
        UUID inviteToken = UUID.randomUUID();
        var inviteDate = LocalDateTime.now();
        var inviteExpirationDate = inviteDate.plusDays(EXPIRY_DURATION.toDays());
        String email = loadUserEmailByUserIdPort.loadEmail(param.getUserId());

        boolean expertGroupExists = checkExpertGroupExistsPort.existsById(param.getExpertGroupId());
        if (!expertGroupExists)
            throw new ResourceNotFoundException(EXPERT_GROUP_ID_NOT_FOUND);

        boolean isOwner = checkExpertGroupOwnerPort.checkIsOwner(param.getExpertGroupId(), param.getCurrentUserId());

        if (!isOwner)
            throw new AccessDeniedException(INVITE_EXPERT_GROUP_MEMBER_OWNER_ID_ACCESS_DENIED);

        var result = inviteExpertGroupMemberPort.persist(toParam(param, inviteDate, inviteExpirationDate, inviteToken));

        if (result != null)
            new Thread(() ->
                sendExpertGroupInvitationMailPort.sendInviteExpertGroupMemberEmail(email, inviteToken)).start();
    }

    private InviteExpertGroupMemberPort.Param toParam(Param param, LocalDateTime inviteDate,
                                                      LocalDateTime inviteExpirationDate, UUID inviteToken) {
        return new InviteExpertGroupMemberPort.Param(
            param.getExpertGroupId(),
            param.getUserId(),
            param.getCurrentUserId(),
            inviteDate,
            inviteExpirationDate,
            inviteToken,
            ExpertGroupAccessStatus.PENDING
        );
    }
}
