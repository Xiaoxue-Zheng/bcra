package uk.ac.herc.bcra.questionnaire;

import java.util.Set;

import uk.ac.herc.bcra.algorithm.answers.version1.AnswerAccess;
import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.domain.enumeration.AnswerUnits;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;

public class AnswerChecker {

    public static class AnswerCheck {
        boolean valid;
        String reason;

        public AnswerCheck(Boolean valid) {
            this.valid = valid;
        }

        public AnswerCheck(String reason) {
            this.valid = false;
            this.reason = reason;
        }        
    }

    public static void checkAnswersValid(AnswerResponse answerResponse) {
        AnswerCheck answerCheck = checkEachAnswer(answerResponse);

        if (answerCheck.valid) {
            answerResponse.setState(ResponseState.VALIDATED);
        }
        else {
            answerResponse.setState(ResponseState.INVALID);
            answerResponse.setStatus(answerCheck.reason);
        }
    }

    private static AnswerCheck checkEachAnswer(AnswerResponse answerResponse) {
        Set<AnswerSection> answerSections = answerResponse.getAnswerSections();
        for (AnswerSection answerSection: answerSections) {

            Set<AnswerGroup> answerGroups = answerSection.getAnswerGroups();
            for (AnswerGroup answerGroup: answerGroups) {

                Set<Answer> answers = answerGroup.getAnswers();
                for (Answer answer: answers) {
                    
                    AnswerCheck result = checkAnswer(answer);
                    if (result.valid == false) {
                        return result;
                    }
                }
            }
        }

        return new AnswerCheck(true);
    }

    private static AnswerCheck checkAnswer(Answer answer) {
        AnswerCheck result;
        Question question = answer.getQuestion();
      
        result = checkNumberAnswer(answer, question);
        
        if (!result.valid) {
            return result;
        }

        result = checkCheckboxAnswer(answer, question);
        
        if (!result.valid) {
            return result;
        }

        result = checkRadioAnswer(answer, question);
        
        if (!result.valid) {
            return result;
        }

        return new AnswerCheck(true);
    }

    private static AnswerCheck checkNumberAnswer(Answer answer, Question question) {
        QuestionType type = question.getType();
        Integer number = answer.getNumber();

        if (
            (
                (type == QuestionType.NUMBER) ||
                (type == QuestionType.NUMBER_UNKNOWN) ||
                (type == QuestionType.DROPDOWN_NUMBER) ||
                (type == QuestionType.DROPDOWN_NUMBER) ||
                (type == QuestionType.NUMBER_WEIGHT) ||
                (type == QuestionType.NUMBER_HEIGHT)
            ) &&
            (number != null)
        )
        {
            AnswerUnits units = answer.getUnits();
            
            if (
                (type == QuestionType.NUMBER_WEIGHT) &&
                (units == AnswerUnits.POUNDS)
            ) {
                AnswerAccess answerAccess = new AnswerAccess(answer);
                number = answerAccess.getWeight();
            }
            else if (
                (type == QuestionType.NUMBER_HEIGHT) &&
                (units == AnswerUnits.INCHES) 
            ) {
                AnswerAccess answerAccess = new AnswerAccess(answer);
                Float centimeters = 100 * answerAccess.getHeight();
                number = Math.round(centimeters);
            }

            if (number < question.getMinimum()) {
                return new AnswerCheck(
                    "Answer number is less than question minimum: "
                    + getAnswerLocation(answer)
                );
            }

            if (number > question.getMaximum()) {
                return new AnswerCheck(
                    "Answer number is more than question maximum: "
                    + getAnswerLocation(answer)
                );
            }
        }
        return new AnswerCheck(true);
    }

    private static AnswerCheck checkCheckboxAnswer(Answer answer, Question question) {
        if (question.getType() == QuestionType.CHECKBOX) {

            Integer selectedItemCount = countSelectedItems(answer);
            if (selectedItemCount < question.getMinimum()) {
                return new AnswerCheck(
                    "Not enough checkboxes (only "
                    + selectedItemCount
                    + ") are selected: "
                    + getAnswerLocation(answer)
                );
            }

            if (
                exclusiveItemIsSelected(answer) &&
                (selectedItemCount != 1)
            ) {
                return new AnswerCheck(
                    "Only one exclusive item should be selected, not "
                    + selectedItemCount
                    + ": "
                    + getAnswerLocation(answer)
                );
            }

            if (
                necessaryItemIsNotSelected(answer)
            ) {
                return new AnswerCheck(
                    "A necessary item is not selected: "
                    + getAnswerLocation(answer)
                );
            }
        }
        return new AnswerCheck(true);
    }

    private static AnswerCheck checkRadioAnswer(Answer answer, Question question) {
        if (question.getType() == QuestionType.RADIO) {

            Integer selectedItemCount = countSelectedItems(answer);
            if (selectedItemCount > 1) {
                return new AnswerCheck(
                    "Radio answer should only have one selected item, not "
                    + selectedItemCount 
                    + ": "
                    + getAnswerLocation(answer)
                );
            }
        }
        return new AnswerCheck(true);
    }

    private static int countSelectedItems(Answer answer) {
        int count = 0;
        Set<AnswerItem> items = answer.getAnswerItems();
        for (AnswerItem item: items) {
            if (item.isSelected()) {
                count++;
            }
        }
        return count;
    }

    private static boolean exclusiveItemIsSelected(Answer answer) {
        Set<AnswerItem> items = answer.getAnswerItems();
        for (AnswerItem item: items) {
            if (
                item.getQuestionItem().isExclusive() &&
                item.isSelected()
            ) {
                return true;
            }
        }
        return false;
    }

    private static boolean necessaryItemIsNotSelected(Answer answer) {
        Set<AnswerItem> items = answer.getAnswerItems();
        for (AnswerItem item: items) {
            if (
                item.getQuestionItem().isNecessary() &&
                item.isSelected().equals(false)
            ) {
                return true;
            }
        }
        return false;
    }    

    private static String getAnswerLocation(Answer answer) {
        String sectionString =
            answer
                .getAnswerGroup()
                .getAnswerSection()
                .getQuestionSection()
                .getIdentifier()
                .toString();

        String answerString =
            answer
                .getQuestion()
                .getIdentifier()
                .toString();

        return sectionString + " > " + answerString;            
    }
}
