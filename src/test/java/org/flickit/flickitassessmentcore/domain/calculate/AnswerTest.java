package org.flickit.flickitassessmentcore.domain.calculate;

import org.flickit.flickitassessmentcore.domain.Answer;
import org.flickit.flickitassessmentcore.domain.AnswerOption;
import org.flickit.flickitassessmentcore.domain.AnswerOptionImpact;
import org.flickit.flickitassessmentcore.domain.calculate.mother.AnswerMother;
import org.flickit.flickitassessmentcore.domain.calculate.mother.AnswerOptionImpactMother;
import org.flickit.flickitassessmentcore.domain.calculate.mother.AnswerOptionMother;
import org.junit.jupiter.api.Test;

import static org.flickit.flickitassessmentcore.domain.calculate.mother.MaturityLevelMother.*;
import static org.junit.jupiter.api.Assertions.*;

class AnswerTest {

    @Test
    void findImpactByMaturityLevel_optionOne() {
        AnswerOption optionOne = AnswerOptionMother.optionOne();
        Answer answer = AnswerMother.answer(optionOne);

        AnswerOptionImpact onLevelOne = answer.findImpactByMaturityLevel(levelOne());
        AnswerOptionImpact onLevelTwo = answer.findImpactByMaturityLevel(levelTwo());
        AnswerOptionImpact onLevelThree = answer.findImpactByMaturityLevel(levelThree());
        AnswerOptionImpact onLevelFour = answer.findImpactByMaturityLevel(levelFour());
        AnswerOptionImpact onLevelFive = answer.findImpactByMaturityLevel(levelFive());

        assertNull(onLevelOne);
        assertNull(onLevelTwo);

        assertNotNull(onLevelThree);
        assertEquals(AnswerOptionImpactMother.onLevelThree(0.0).getValue(), onLevelThree.getValue());

        assertNotNull(onLevelFour);
        assertEquals(AnswerOptionImpactMother.onLevelFour(0.0).getValue(), onLevelFour.getValue());

        assertNull(onLevelFive);
    }

    @Test
    void findImpactByMaturityLevel_optionTwo() {
        AnswerOption optionTwo = AnswerOptionMother.optionTwo();
        Answer answer = AnswerMother.answer(optionTwo);

        AnswerOptionImpact onLevelOne = answer.findImpactByMaturityLevel(levelOne());
        AnswerOptionImpact onLevelTwo = answer.findImpactByMaturityLevel(levelTwo());
        AnswerOptionImpact onLevelThree = answer.findImpactByMaturityLevel(levelThree());
        AnswerOptionImpact onLevelFour = answer.findImpactByMaturityLevel(levelFour());
        AnswerOptionImpact onLevelFive = answer.findImpactByMaturityLevel(levelFive());

        assertNull(onLevelOne);
        assertNull(onLevelTwo);

        assertNotNull(onLevelThree);
        assertEquals(AnswerOptionImpactMother.onLevelThree(0.5).getValue(), onLevelThree.getValue());

        assertNotNull(onLevelFour);
        assertEquals(AnswerOptionImpactMother.onLevelFour(0.0).getValue(), onLevelFour.getValue());

        assertNull(onLevelFive);
    }

    @Test
    void findImpactByMaturityLevel_optionThree() {
        AnswerOption optionThree = AnswerOptionMother.optionThree();
        Answer answer = AnswerMother.answer(optionThree);

        AnswerOptionImpact onLevelOne = answer.findImpactByMaturityLevel(levelOne());
        AnswerOptionImpact onLevelTwo = answer.findImpactByMaturityLevel(levelTwo());
        AnswerOptionImpact onLevelThree = answer.findImpactByMaturityLevel(levelThree());
        AnswerOptionImpact onLevelFour = answer.findImpactByMaturityLevel(levelFour());
        AnswerOptionImpact onLevelFive = answer.findImpactByMaturityLevel(levelFive());

        assertNull(onLevelOne);
        assertNull(onLevelTwo);

        assertNotNull(onLevelThree);
        assertEquals(AnswerOptionImpactMother.onLevelThree(1.0).getValue(), onLevelThree.getValue());

        assertNotNull(onLevelFour);
        assertEquals(AnswerOptionImpactMother.onLevelFour(0.0).getValue(), onLevelFour.getValue());

        assertNull(onLevelFive);
    }

    @Test
    void findImpactByMaturityLevel_optionFour() {
        AnswerOption optionFour = AnswerOptionMother.optionFour();
        Answer answer = AnswerMother.answer(optionFour);

        AnswerOptionImpact onLevelOne = answer.findImpactByMaturityLevel(levelOne());
        AnswerOptionImpact onLevelTwo = answer.findImpactByMaturityLevel(levelTwo());
        AnswerOptionImpact onLevelThree = answer.findImpactByMaturityLevel(levelThree());
        AnswerOptionImpact onLevelFour = answer.findImpactByMaturityLevel(levelFour());
        AnswerOptionImpact onLevelFive = answer.findImpactByMaturityLevel(levelFive());

        assertNull(onLevelOne);
        assertNull(onLevelTwo);

        assertNotNull(onLevelThree);
        assertEquals(AnswerOptionImpactMother.onLevelThree(1.0).getValue(), onLevelThree.getValue());

        assertNotNull(onLevelFour);
        assertEquals(AnswerOptionImpactMother.onLevelFour(1.0).getValue(), onLevelFour.getValue());

        assertNull(onLevelFive);
    }
}
