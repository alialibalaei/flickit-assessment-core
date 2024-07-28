package org.flickit.assessment.core.test.fixture.application;

import org.flickit.assessment.core.application.domain.Attribute;
import org.flickit.assessment.core.application.domain.Question;

import java.util.List;

public class AttributeMother {

    private static long id = 134L;

    public static Attribute simpleAttribute() {
        return new Attribute(id++, null, null, 1, null);
    }

    public static Attribute withIdAndQuestions(long id, List<Question> questions) {
        return new Attribute(id, null, null, 1, questions);
    }

    public static Attribute withIdQuestionsAndWeight(long id, List<Question> questions, int weight) {
        return new Attribute(id, null, null, weight, questions);
    }

    public static Attribute completeAttribute(long id, List<Question> questions, int weight) {
        return new Attribute(id, "title" + id, "description" + id, weight, questions);
    }
}
