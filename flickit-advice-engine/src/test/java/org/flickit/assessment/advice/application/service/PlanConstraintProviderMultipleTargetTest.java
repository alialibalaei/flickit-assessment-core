package org.flickit.assessment.advice.application.service;

import ai.timefold.solver.test.api.score.stream.ConstraintVerifier;
import org.flickit.assessment.advice.application.domain.Plan;
import org.flickit.assessment.advice.application.domain.Question;
import org.flickit.assessment.advice.application.domain.Target;
import org.junit.jupiter.api.Test;

class PlanConstraintProviderMultipleTargetTest {

    ConstraintVerifier<PlanConstraintProvider, Plan> constraintVerifier = ConstraintVerifier.build(
        new PlanConstraintProvider(), Plan.class, Question.class);

    @Test
    void gainLeastTest_PenalizesWhenQuestionsGainIsLessThanTarget() {
        Target target = new Target(0, 10);
        Question question1 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target, 0, 1);
        Question question2 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target, 0, 0);

        Target target2 = new Target(0, 10);
        Question question3 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target2, 0, 3);
        Question question4 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target2, 0, 1);

        constraintVerifier.verifyThat(PlanConstraintProvider::minGain)
            .given(question1, question2, target, question3, question4, target2)
            .penalizesBy(8);
    }

    @Test
    void gainLeastTest_PenalizesWhenNoQuestionChosen() {
        Target target = new Target(0, 12);
        Question question1 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target, 0, 0);
        Question question2 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target, 0, 0);

        Target target2 = new Target(0, 10);
        Question question3 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target2, 0, 0);
        Question question4 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target2, 0, 0);

        constraintVerifier.verifyThat(PlanConstraintProvider::minGain)
            .given(
                target,
                question1,
                question2,
                target2,
                question3,
                question4
            )
            .penalizesBy(22);
    }

    @Test
    void totalBenefit() {
        Target target = new Target(2, 12);
        Question question = QuestionMother.createQuestionWithTargetAndOptionIndexes(target, 0, 3);

        Target target2 = new Target(0, 10);
        Question question2 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target2, 0, 3);

        constraintVerifier.verifyThat(PlanConstraintProvider::totalBenefit)
            .given(question, target, question2, target2)
            .rewardsWith(80);
    }

    @Test
    void leastCount() {
        Target target = new Target(0, 10);
        Question question1 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target, 0, 1);
        Question question2 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target, 0, 2);
        Question question3 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target, 1, 1);

        Target target2 = new Target(0, 10);
        Question question4 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target2, 0, 0);
        Question question5 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target2, 0, 2);
        Question question6 = QuestionMother.createQuestionWithTargetAndOptionIndexes(target2, 0, 0);

        constraintVerifier.verifyThat(PlanConstraintProvider::leastCount)
            .given(question1, question2, question3, target,
                question4, question5, question6, target2)
            .penalizesBy(3);
    }
}
