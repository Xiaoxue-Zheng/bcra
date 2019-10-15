package uk.ac.herc.bcra.algorithm.answers.version1;

import java.util.HashMap;
import java.util.Map;

import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;

public class GroupAccess implements Comparable<GroupAccess>{

    private Map<QuestionIdentifier, AnswerAccess> answerMap
        = new HashMap<QuestionIdentifier, AnswerAccess>();

    private Integer order;

    public GroupAccess(AnswerGroup group) {
        super();

        for (Answer answer: group.getAnswers()) {
            answerMap.put(
                answer.getQuestion().getIdentifier(),
                new AnswerAccess(answer)
            );
        }

        order = group.getOrder();
    }

    public AnswerAccess answer(QuestionIdentifier identifier) {
        return answerMap.get(identifier);
    }

    public Integer getOrder() {
        return 1;
    }

    @Override
    public int compareTo(GroupAccess groupAccess) {
        return order.compareTo(groupAccess.order);
    }
}