package org.flickit.assessment.users.application.port.in.expertgroup;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.flickit.assessment.common.error.ErrorMessageKey.COMMON_CURRENT_USER_ID_NOT_NULL;
import static org.flickit.assessment.users.common.ErrorMessageKey.*;
import static org.junit.jupiter.api.Assertions.*;

class RemoveExpertGroupPictureUseCaseParamTest {

    @Test
    void testRemoveExpertGroupPictureParam_idIsNull_ErrorMessage() {

        UUID currentUserId = UUID.randomUUID();
        var throwable = assertThrows(ConstraintViolationException.class,
            () -> new RemoveExpertGroupPictureUseCase.Param(null, currentUserId));
        assertThat(throwable).hasMessage("expertGroupId: " + REMOVE_EXPERT_GROUP_PICTURE_EXPERT_GROUP_ID_NOT_NULL);
    }

    @Test
    void testRemoveExpertGroupPictureParam_currentUserIdIsNull_ErrorMessage() {
        long expertGroupId = 0L;
        var throwable = assertThrows(ConstraintViolationException.class,
            () -> new RemoveExpertGroupPictureUseCase.Param(expertGroupId, null));
        assertThat(throwable).hasMessage("currentUserId: " + COMMON_CURRENT_USER_ID_NOT_NULL);
    }

}
