package org.flickit.flickitassessmentcore.domain.calculate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LevelCompetence {

    private final long id;
    private final int value;
    private final long maturityLevelId;
}
