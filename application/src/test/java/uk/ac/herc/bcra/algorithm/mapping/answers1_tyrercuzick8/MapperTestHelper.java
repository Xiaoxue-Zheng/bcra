package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import java.util.HashMap;
import java.util.Map;

import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.AnswerItem;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.domain.Question;
import uk.ac.herc.bcra.domain.QuestionItem;
import uk.ac.herc.bcra.domain.QuestionSection;
import uk.ac.herc.bcra.domain.enumeration.AnswerUnits;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;

public class MapperTestHelper {

    public static AnswerSection createAnswerSection(QuestionSectionIdentifier identifier, AnswerGroup answerGroup) {
        QuestionSection questionSection = new QuestionSection();
        questionSection.setIdentifier(identifier);

        AnswerSection answerSection = new AnswerSection();
        answerSection.setQuestionSection(questionSection);
        answerSection.addAnswerGroup(answerGroup);

        return answerSection;
    }

    public static void addAnswerAndItem(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        QuestionItemIdentifier itemIdentifier,
        Boolean value
    ) {
        Map<QuestionItemIdentifier, Boolean> items =
            new HashMap<QuestionItemIdentifier, Boolean>();

        items.put(itemIdentifier, value);

        addAnswerAndItems(
            answerGroup,
            questionIdentifier,
            questionType,
            items
        );
    }

    public static void addAnswerAndItems(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        Map<QuestionItemIdentifier, Boolean> items
    ) {
        Question question = new Question();
        question.setIdentifier(questionIdentifier);
        question.setType(questionType);
        
        Answer answer = new Answer();
        answer.setQuestion(question);

        for (QuestionItemIdentifier itemIdentifier: items.keySet()) {
            QuestionItem questionItem = new QuestionItem();
            questionItem.identifier(itemIdentifier);

            AnswerItem answerItem = new AnswerItem();
            answerItem.questionItem(questionItem);
            answerItem.setSelected(items.get(itemIdentifier));

            answer.addAnswerItem(answerItem);
        }

        answerGroup.addAnswer(answer);
    }

    public static void addAnswerNumber(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        Integer number
    ) {
        addAnswerNumberUnits(
            answerGroup,
            questionIdentifier,
            questionType,
            null,
            number
        );
    }

    public static void addAnswerNumberUnits(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        AnswerUnits units,
        Integer number
    ) {
        Question question = new Question();
        question.setIdentifier(questionIdentifier);
        question.setType(questionType);
        
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setNumber(number);
        answer.setUnits(units);

        answerGroup.addAnswer(answer);
    }
}
