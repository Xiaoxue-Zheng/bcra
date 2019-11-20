package uk.ac.herc.bcra.questionnaire;

import java.util.HashSet;
import java.util.Set;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.domain.enumeration.*;
import uk.ac.herc.bcra.questionnaire.AnswerChecker.AnswerCheck;

public class AnswerResponseChecker {

    public static void checkAnswerResponsesValid(AnswerResponse answerResponse) {
        
        AnswerCheck answerCheck = checkAnswerResponse(answerResponse);

        if (answerCheck.valid) {
            answerResponse.setState(ResponseState.VALIDATED);
        }
        else {
            answerResponse.setState(ResponseState.INVALID);
            answerResponse.setStatus(answerCheck.reason);
        }
    }

    private static AnswerCheck checkAnswerResponse(AnswerResponse answerResponse) {

        Set<AnswerSection> answerSections = answerResponse.getAnswerSections();

        Questionnaire questionnaire = answerResponse.getQuestionnaire();
        Set<QuestionSection> questionSections = questionnaire.getQuestionSections();

        // Check all AnswerSections Exist in Questionnaire
        for (AnswerSection answerSection: answerSections) {
            QuestionSection questionSection = answerSection.getQuestionSection();
            if (!questionSections.contains(questionSection)) {
                return new AnswerCheck(
                    "Answer section does not have valid question section: " + answerSection
                );
            }
        }

        // Check All QuestionSections Exist in AnswerResponse
        Set<QuestionSection> answerQuestionSections = new HashSet<QuestionSection>();
        for (AnswerSection answerSection: answerSections) {
            answerQuestionSections.add(answerSection.getQuestionSection());
        }
        for (QuestionSection questionSection: questionSections) {
            if (!answerQuestionSections.contains(questionSection)) {
                return new AnswerCheck(
                    "Question section "
                    + questionSection.getIdentifier()
                    + " missing from answer response: " + answerResponse
                );
            }
        }

        // Check each AnswerSection is valid
        for (AnswerSection answerSection: answerSections) {
            AnswerCheck answerCheck = checkAnswerSection(answerSection);
            if (!answerCheck.valid) {
                return answerCheck;
            }
        }

        return new AnswerCheck(true);
    }

    private static AnswerCheck checkAnswerSection(AnswerSection answerSection) {

        Set<AnswerGroup> answerGroups = answerSection.getAnswerGroups();

        if (answerGroups.isEmpty()) {
            return new AnswerCheck(
                "Answer section does not have any AnswerGroups " + answerSection
            );
        }

        // Check each AnswerGroup is valid
        for (AnswerGroup answerGroup: answerGroups) {
            AnswerCheck answerCheck = checkAnswerGroup(answerGroup);
            if (!answerCheck.valid) {
                return answerCheck;
            }
        }

        return new AnswerCheck(true);
    }

    private static AnswerCheck checkAnswerGroup(AnswerGroup answerGroup) {
        Set<Answer> answers = answerGroup.getAnswers();

        QuestionGroup questionGroup = 
            answerGroup
            .getAnswerSection()
            .getQuestionSection()
            .getQuestionGroup();

        Set<Question> questions = questionGroup.getQuestions();

        // Check all Answers have matching Questions
        for (Answer answer: answers) {
            if (!questions.contains(answer.getQuestion())) {
                return new AnswerCheck(
                    "Answer does not have valid Question: " + answer
                );
            }
        }

        // Check all Questions have Answers
        Set<Question> answerQuestions = new HashSet<Question>();
        for (Answer answer: answers) {
            answerQuestions.add(answer.getQuestion());
        }
        for (Question question: questions) {
            if (!answerQuestions.contains(question)) {
                return new AnswerCheck(
                    "Question  "
                    + question.getIdentifier()
                    + " does not have matching answer: " + answerGroup
                );
            }
        }

        // Check all Answers are valid
        for (Answer answer: answers) {
            AnswerCheck answerCheck = checkAnswer(answer);
            if (!answerCheck.valid) {
                return answerCheck;
            }
        }

        return new AnswerCheck(true);
    } 

    private static AnswerCheck checkAnswer(Answer answer) {
        Set<AnswerItem> answerItems = answer.getAnswerItems();
        
        Question question = answer.getQuestion();
        Set<QuestionItem> questionItems = question.getQuestionItems();

        // Check all AnswerItems have matching QuestionItems
        for (AnswerItem answerItem: answerItems) {
            if (!questionItems.contains(answerItem.getQuestionItem())) {
                return new AnswerCheck(
                    "AnswerItem does not have valid QuestionItem: " + answerItem
                );
            }
        }

        // Check all QuestionItems have AnswerItems
        Set<QuestionItem> answerQuestionItems = new HashSet<QuestionItem>();
        for (AnswerItem answerItem: answerItems) {
            answerQuestionItems.add(answerItem.getQuestionItem());
        }
        for (QuestionItem questionItem: questionItems) {
            if (!answerQuestionItems.contains(questionItem)) {
                return new AnswerCheck(
                    "QuestionItem  "
                    + questionItem.getIdentifier()
                    + " does not have matching AnswerItem: " + answer
                );
            }
        }

        return new AnswerCheck(true);
    }
}