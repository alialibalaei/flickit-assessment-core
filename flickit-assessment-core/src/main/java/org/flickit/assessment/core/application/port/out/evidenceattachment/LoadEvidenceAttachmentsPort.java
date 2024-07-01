package org.flickit.assessment.core.application.port.out.evidenceattachment;

import java.util.List;
import java.util.UUID;

public interface LoadEvidenceAttachmentsPort {

    List<Result> loadEvidenceAttachments(UUID evidenceId);

    record Result(UUID id, String file, String description){
    }
}
