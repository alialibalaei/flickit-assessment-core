package org.flickit.assessment.users.application.port.in.space;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.flickit.assessment.common.application.SelfValidating;

import java.util.UUID;

import static org.flickit.assessment.users.common.ErrorMessageKey.ADD_SPACE_MEMBER_SPACE_ID_NOT_NULL;
import static org.flickit.assessment.users.common.ErrorMessageKey.ADD_SPACE_MEMBER_EMAIL_NOT_NULL;
import static org.flickit.assessment.common.error.ErrorMessageKey.COMMON_CURRENT_USER_ID_NOT_NULL;

public interface AddSpaceMemberUseCase {

    void addMember(long spaceId, String email, UUID currentUserId);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class Param extends SelfValidating<Param>{

        @NotNull(message = ADD_SPACE_MEMBER_SPACE_ID_NOT_NULL)
        Long spaceId;

        @NotNull(message = ADD_SPACE_MEMBER_EMAIL_NOT_NULL)
        String email;

        @NotNull(message = COMMON_CURRENT_USER_ID_NOT_NULL)
        UUID currentUserId;

        public Param(Long spaceId, String email, UUID currentUserId) {
            this.spaceId = spaceId;
            this.email = email;
            this.currentUserId = currentUserId;
            this.validateSelf();
        }
    }
}
