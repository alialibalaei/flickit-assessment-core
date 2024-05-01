package org.flickit.assessment.core.application.domain;

import org.flickit.assessment.core.test.fixture.application.MaturityLevelMother;
import org.flickit.assessment.core.test.fixture.application.QuestionMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QuestionTest {

    @Test
    void testFindImpactByMaturityLevel() {
        Question question = QuestionMother.withImpactsOnLevel24();

        QuestionImpact impact = question.findImpactByMaturityLevel(MaturityLevelMother.levelTwo());

        assertNotNull(impact);
        assertEquals(MaturityLevelMother.levelTwo().getId(), impact.getMaturityLevelId());
    }
}
