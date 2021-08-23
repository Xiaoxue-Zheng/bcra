package uk.ac.herc.bcra.questionnaire;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import uk.ac.herc.bcra.algorithm.answers.version1.AnswerAccess;
import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.domain.enumeration.*;

public class AnswerChecker {
    private static final Set<QuestionType> NUMBER_QUESTION_TYPES = new HashSet<>(
        Arrays.asList(QuestionType.NUMBER,
        QuestionType.NUMBER_UNKNOWN, QuestionType.DROPDOWN_NUMBER,
        QuestionType.NUMBER_WEIGHT, QuestionType.NUMBER_HEIGHT));

    public static void checkAnswersValid(AnswerResponse answerResponse) {
        AnswerCheck answerCheck = checkEachAnswerSection(answerResponse.getAnswerSections(), answerResponse);

        if (answerCheck.valid) {
            answerResponse.setState(ResponseState.VALIDATED);
        }
        else {
            answerResponse.setState(ResponseState.INVALID);
            answerResponse.setStatus(answerCheck.reason);
        }
    }

    public static AnswerCheck checkConsentAnswersValid(AnswerResponse answerResponse){
        Set<AnswerSection> answerSections = answerResponse.getAnswerSections();
        Set<AnswerSection> consentAnswerSections = answerSections.stream().filter(it ->
            Objects.equals(QuestionSectionIdentifier.CONSENT_FORM, it.getQuestionSection().getIdentifier()))
            .collect(Collectors.toSet());
        return checkEachAnswerSection(consentAnswerSections, answerResponse);
    }

    public static AnswerCheck checkAnswerSection(AnswerSection answerSection, AnswerResponse answerResponse) {
        if(!isDisplayed(answerSection, answerResponse)){
            return new AnswerCheck(true);
        }
        Set<AnswerGroup> answerGroups = answerSection.getAnswerGroups();
        for (AnswerGroup answerGroup: answerGroups) {
            Set<Answer> answers = answerGroup.getAnswers();
            List<Answer> sortedAnswer = answers.stream().sorted(Comparator.comparing(it->it.getQuestion().getOrder())).collect(Collectors.toList());
            for (Answer answer: sortedAnswer) {
                if(!isDisplayed(answer, answerResponse)){
                    continue;
                }
                AnswerCheck result = checkAnswer(answer, answerResponse);
                if (!result.valid) {
                    return result;
                }
            }
        }
        return new AnswerCheck(true);
    }

    private static AnswerCheck checkEachAnswerSection( Set<AnswerSection> answerSections, AnswerResponse answerResponse ) {
        for (AnswerSection answerSection: answerSections) {
            AnswerCheck result = checkAnswerSection(answerSection, answerResponse);
            if (!result.isValid()) return result;
        }
        return new AnswerCheck(true);
    }

    private static AnswerCheck checkAnswer(Answer answer, AnswerResponse answerResponse) {

        AnswerCheck result;
        Question question = answer.getQuestion();
        result = checkNumberAnswer(answer, question);

        if (!result.valid) {
            return formatReason(result, answerResponse);
        }

        result = checkCheckboxAnswer(answer, question);

        if (!result.valid) {
            return formatReason(result, answerResponse);
        }

        result = checkTickBoxAnswer(answer, question);

        if (!result.valid) {
            return formatReason(result, answerResponse);
        }

        result = checkRadioAnswer(answer, question);

        if (!result.valid) {
            return formatReason(result, answerResponse);
        }

        return new AnswerCheck(true);
    }

    private static Map<String, String> buildQuestionVariables(AnswerResponse answerResponse) {
        Map<String, String> result = new HashMap<>();
        for(AnswerSection answerSection: answerResponse.getAnswerSections()) {
            for (AnswerGroup answerGroup : answerSection.getAnswerGroups()) {
                for (Answer answer : answerGroup.getAnswers()) {
                    if(StringUtils.isNotEmpty(answer.getQuestion().getVariableName())){
                        Optional<AnswerItem> answerItem = answer.getAnswerItems().stream().filter(AnswerItem::isSelected).findAny();
                        if(answerItem.isPresent()){
                            String variableValue = answerItem.get().getQuestionItem().getLabel().toLowerCase(Locale.ROOT);
                            result.put(answer.getQuestion().getVariableName(), variableValue);
                        }
                    }
                }
            }
        }
        return result;
    }

    private static AnswerCheck formatReason(AnswerCheck result, AnswerResponse answerResponse) {
        Map<String, String> questionVariables = buildQuestionVariables(answerResponse);
        String reason = result.getReason();
        if(result.getReason().contains("{{")){
            for(Map.Entry<String, String> variable: questionVariables.entrySet()){
                reason = reason.replaceAll("\\{\\{"+variable.getKey()+"}}", variable.getValue());
            }
        }
        return new AnswerCheck(reason);
    }

    private static AnswerCheck checkNumberAnswer(Answer answer, Question question) {
        QuestionType type = question.getType();
        Integer number = answer.getNumber();
        if(!NUMBER_QUESTION_TYPES.contains(type)){
            return new AnswerCheck(true);
        }
        if(null != question.getNecessary() && question.getNecessary() && null == number && (null == answer.getDontKnow() || !answer.getDontKnow())){
            return new AnswerCheck(String.format("Please fill question: %s", question.getText()));
        }
        if (number != null)
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
                float centimeters = 100 * answerAccess.getHeight();
                number = Math.round(centimeters);
            }

            if (number < question.getMinimum()) {
                return new AnswerCheck(
                    "Answer number is less than question minimum: "
                    + question.getMinimum()
                );
            }

            if (number > question.getMaximum()) {
                return new AnswerCheck(
                    "Answer number is more than question maximum: "
                    + question.getMaximum()
                );
            }
        }
        return new AnswerCheck(true);
    }

    private static AnswerCheck checkCheckboxAnswer(Answer answer, Question question) {
        if (question.getType() == QuestionType.CHECKBOX) {
            int selectedItemCount = countSelectedItems(answer);
            if(question.getNecessary() && selectedItemCount == 0){
                return new AnswerCheck(String.format("Please select at least one option for question: %s", question.getText()));
            }
            if (null != question.getMinimum() && selectedItemCount < question.getMinimum()) {
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

    private static AnswerCheck checkTickBoxAnswer(Answer answer, Question question) {
        if (question.getType() == QuestionType.TICKBOX_CONSENT) {
            if((null != question.getNecessary() && question.getNecessary()) && !answer.isTicked()){
                return new AnswerCheck("Please confirm all items");
            }
        }
        return new AnswerCheck(true);
    }

    private static AnswerCheck checkRadioAnswer(Answer answer, Question question) {
        if (question.getType() == QuestionType.RADIO) {
            int selectedItemCount = countSelectedItems(answer);
            if (selectedItemCount > 1) {
                return new AnswerCheck(
                    "Radio answer should only have one selected item, not "
                    + selectedItemCount
                    + ": "
                    + getAnswerLocation(answer)
                );
            }
            if (necessaryItemIsNotSelected(answer)) {
                return new AnswerCheck(
                    "A necessary item is not selected: "
                        + getAnswerLocation(answer)
                );
            }
            if (question.getNecessary() && selectedItemCount == 0) {
                return new AnswerCheck(String.format("Please select one option for question: %s", question.getText()));
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

    private static boolean isDisplayed(AnswerSection answerSection, AnswerResponse answerResponse) {
        Set<DisplayCondition> displayConditions = answerSection.getQuestionSection().getDisplayConditions();
        if(CollectionUtils.isEmpty(displayConditions)){
            return true;
        }
        for(DisplayCondition displayCondition: displayConditions){
            if(displayConditionMet(displayCondition, answerResponse)){
                return true;
            }
        }
        return false;
    }

    private static boolean isDisplayed(Answer answer, AnswerResponse answerResponse) {
        Set<DisplayCondition> displayConditions = answer.getQuestion().getDisplayConditions();
        if(CollectionUtils.isEmpty(displayConditions)){
            return true;
        }
        for(DisplayCondition displayCondition: displayConditions){
            if(displayConditionMet(displayCondition, answerResponse)){
                return true;
            }
        }
        return false;
    }

    private static boolean displayConditionMet(DisplayCondition displayCondition, AnswerResponse answerResponse) {
        if(null != displayCondition.getItemIdentifier()){
            return answerItemIsSelected(displayCondition.getItemIdentifier(), answerResponse);
        }else if(null != displayCondition.getQuestionIdentifier()){
            return answerIsNotNullOrZero(displayCondition.getQuestionIdentifier(), answerResponse);
        }
        return true;
    }

    private static boolean answerIsNotNullOrZero(QuestionIdentifier questionIdentifier, AnswerResponse answerResponse) {
        for(AnswerSection answerSection: answerResponse.getAnswerSections()){
            if (answerIsNotNullOrZero(questionIdentifier, answerSection)) return true;
        }
        return false;
    }

    private static boolean answerIsNotNullOrZero(QuestionIdentifier questionIdentifier, AnswerSection answerSection) {
        for(AnswerGroup answerGroup: answerSection.getAnswerGroups()){
            for(Answer answer: answerGroup.getAnswers()){
               if(Objects.equals(answer.getQuestion().getIdentifier(), questionIdentifier)){
                   if(null != answer.getNumber() && answer.getNumber()>0){
                       return true;
                   }
               }
            }
        }
        return false;
    }

    private static boolean answerItemIsSelected(QuestionItemIdentifier itemIdentifier, AnswerResponse answerResponse) {
        for(AnswerSection answerSection: answerResponse.getAnswerSections()){
            if (answerItemIsSelected(itemIdentifier, answerSection)) return true;
        }
        return false;
    }

    private static boolean answerItemIsSelected(QuestionItemIdentifier itemIdentifier, AnswerSection answerSection) {
        for(AnswerGroup answerGroup: answerSection.getAnswerGroups()){
            for(Answer answer: answerGroup.getAnswers()){
                Optional<AnswerItem> conditionItem = answer.getAnswerItems().stream()
                    .filter(it -> Objects.equals(it.getQuestionItem().getIdentifier(), itemIdentifier)
                        && it.isSelected()).findAny();
                if(conditionItem.isPresent()){
                    return true;
                }
            }
        }
        return false;
    }

}
