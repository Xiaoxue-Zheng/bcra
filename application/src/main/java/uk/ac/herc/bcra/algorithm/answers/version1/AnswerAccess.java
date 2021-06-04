package uk.ac.herc.bcra.algorithm.answers.version1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.herc.bcra.algorithm.AlgorithmException;
import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.domain.AnswerItem;
import uk.ac.herc.bcra.domain.Question;
import uk.ac.herc.bcra.domain.enumeration.AnswerUnits;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;


public class AnswerAccess {

    private final static Float CENTIMETERS_PER_METER = 100f;
    private final static Float INCHES_PER_METER = 39.3701f;
    private final static Float POUNDS_PER_KILO = 2.20462f;

    private Answer answer;
    private Question question;

    private Map<QuestionItemIdentifier, AnswerItem> itemMap 
        = new HashMap<QuestionItemIdentifier, AnswerItem>();

    private List<AnswerItem> selectedItems = new ArrayList<AnswerItem>();

    public AnswerAccess(Answer answer) {
        super();

        this.answer = answer;
        this.question = answer.getQuestion();

        for (AnswerItem item: answer.getAnswerItems()) {
            itemMap.put(
                item.getQuestionItem().getIdentifier(),
                item
            );
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
    }

    public Boolean selected(QuestionItemIdentifier identifier) {
        if (!itemMap.containsKey(identifier)) {
            throw new AlgorithmException(
                "Answer does not contain item "
                + identifier
                + ": "
                + answer
            );
        }
        return itemMap.get(identifier).isSelected();
    }

    public int selectedCount() {
        return selectedItems.size();
    }

    public QuestionItemIdentifier getOnlySelectedItem() {
        if (selectedItems.size() != 1) {
            throw new AlgorithmException("Answer does not have one selected item: " + answer);
        }
        return selectedItems.get(0).getQuestionItem().getIdentifier();
    }

    public Integer getNumber() {

        if (
            (question.getType() == QuestionType.NUMBER) ||
            (question.getType() == QuestionType.NUMBER_UNKNOWN) ||
            (question.getType() == QuestionType.DROPDOWN_NUMBER)
         ) {
            return answer.getNumber();   
         }
         throw new AlgorithmException("Answer is not a number: " + answer);
    }

    public Integer getWeight() {
        if (question.getType() != QuestionType.NUMBER_WEIGHT) {
            throw new AlgorithmException("Answer is not a weight: " + answer + question);
        }

        Integer weight = answer.getNumber();
        if (answer.getUnits() == AnswerUnits.KILOS) {
            return weight;
        }
        else if (answer.getUnits() == AnswerUnits.POUNDS) {
            return Math.round(weight / POUNDS_PER_KILO);
        }
        throw new AlgorithmException("Weight answer has invalid units: " + answer);
    }

    public Float getHeight() {
        if (question.getType() != QuestionType.NUMBER_HEIGHT) {
            throw new AlgorithmException("Answer is not a height: " + answer);
        }

        if (answer.getNumber() != null) {
            Float height = answer.getNumber().floatValue();
            if (answer.getUnits() == AnswerUnits.CENTIMETERS) {
                return height / CENTIMETERS_PER_METER;
            }
            else if (answer.getUnits() == AnswerUnits.INCHES) {
                return height / INCHES_PER_METER;
            }
        }
        throw new AlgorithmException("Height answer has invalid units: " + answer);
    }
}
