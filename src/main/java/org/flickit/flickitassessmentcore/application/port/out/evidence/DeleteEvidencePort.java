package org.flickit.flickitassessmentcore.application.port.out.evidence;

import java.util.UUID;

public interface DeleteEvidencePort {

    void setDeletionTimeById(UUID id, Long deletionTime);

}
