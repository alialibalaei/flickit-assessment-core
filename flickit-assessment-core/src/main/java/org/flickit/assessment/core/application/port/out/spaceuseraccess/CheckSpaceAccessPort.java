package org.flickit.assessment.core.application.port.out.spaceuseraccess;

import java.util.UUID;

public interface CheckSpaceAccessPort {

    boolean checkIsMember(long spaceId, UUID userId);
}
