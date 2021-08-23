package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import uk.ac.herc.bcra.domain.*;
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

    public static AnswerSection createAnswerSectionWithDisplayCondition(QuestionSectionIdentifier identifier,
                                                                      AnswerGroup answerGroup, DisplayCondition... displayConditions) {
        QuestionSection questionSection = new QuestionSection();
        questionSection.setIdentifier(identifier);
        questionSection.setDisplayConditions(new HashSet<>(Arrays.asList(displayConditions)));

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

    public static void addAnswerAndItemsWithProperties(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        QuestionItemIdentifier[] itemIdentifiers,
        int selected,
        int minimum,
        int exclusive,
        int neccessary
    ) {
        Question question = new Question();
        question.setIdentifier(questionIdentifier);
        question.setType(questionType);
        question.setMinimum(minimum);
        question.setNecessary(true);
        question.setOrder(0);

        Answer answer = new Answer();
        answer.setQuestion(question);

        for (QuestionItemIdentifier itemIdentifier: itemIdentifiers) {
            QuestionItem questionItem = new QuestionItem();
            questionItem.identifier(itemIdentifier);

            AnswerItem answerItem = new AnswerItem();
            answerItem.questionItem(questionItem);

            if (selected > 0) {
                answerItem.setSelected(true);
                selected--;
            }
            else {
                answerItem.setSelected(false);
            }

            if (exclusive > 0) {
                questionItem.setExclusive(true);
                exclusive--;
            }
            else {
                questionItem.setExclusive(false);
            }

            if (neccessary > 0) {
                questionItem.setNecessary(true);
                neccessary--;
            }
            else {
                questionItem.setNecessary(false);
            }

            answer.addAnswerItem(answerItem);
        }

        answerGroup.addAnswer(answer);
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
        question.setNecessary(true);
        question.setOrder(0);

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

    public static void addAnswerNumberMinMax(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        Integer number,
        Integer minimum,
        Integer maximum
    ) {
        addAnswerNumberUnitsMinMax(
            answerGroup,
            questionIdentifier,
            questionType,
            null,
            number,
            minimum,
            maximum
        );
    }

    public static void addAnswerNumberUnits(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        AnswerUnits units,
        Integer number
    ) {
        addAnswerNumberUnitsMinMax(
            answerGroup,
            questionIdentifier,
            questionType,
            units,
            number,
            null,
            null
        );
    }

    public static void addAnswerNumberUnitsMinMax(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        AnswerUnits units,
        Integer number,
        Integer minimum,
        Integer maximum
    ) {
        Question question = new Question();
        question.setIdentifier(questionIdentifier);
        question.setType(questionType);
        question.setMinimum(minimum);
        question.setMaximum(maximum);
        question.setNecessary(true);
        question.setOrder(0);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setNumber(number);
        answer.setUnits(units);

        answerGroup.addAnswer(answer);
    }

    public static void addDontKnowAnswerNumberUnitsMinMax(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        AnswerUnits units,
        Integer number,
        Integer minimum,
        Integer maximum
    ) {
        Question question = new Question();
        question.setIdentifier(questionIdentifier);
        question.setType(questionType);
        question.setMinimum(minimum);
        question.setMaximum(maximum);
        question.setNecessary(true);
        question.setOrder(0);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setNumber(number);
        answer.setUnits(units);
        answer.setDontKnow(true);
        answerGroup.addAnswer(answer);
    }

    public static void addAnswerAndItemsWithProperties(AnswerGroup answerGroup, QuestionIdentifier questionIdentifier, QuestionType questionType, Boolean ticked) {
        Question question = new Question();
        question.setIdentifier(questionIdentifier);
        question.setType(questionType);
        question.setNecessary(true);
        question.setOrder(0);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setTicked(ticked);
        answerGroup.addAnswer(answer);
    }

    public static void addAnswerWithDisplayCondition(
        AnswerGroup answerGroup,
        QuestionIdentifier questionIdentifier,
        QuestionType questionType,
        AnswerUnits units,
        Integer number,
        Integer minimum,
        Integer maximum,
        DisplayCondition... displayCondition
    ) {
        Question question = new Question();
        question.setIdentifier(questionIdentifier);
        question.setDisplayConditions(new HashSet<>(Arrays.asList(displayCondition)));
        question.setType(questionType);
        question.setMinimum(minimum);
        question.setMaximum(maximum);
        question.setNecessary(true);
        question.setOrder(0);

        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setNumber(number);
        answer.setUnits(units);

        answerGroup.addAnswer(answer);
    }
}
