package uk.ac.herc.bcra.questionnaire;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.domain.enumeration.AnswerUnits;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
import uk.ac.herc.bcra.repository.AnswerResponseRepository;
import uk.ac.herc.bcra.repository.QuestionnaireRepository;

@Service
@Transactional
public class AnswerResponseGenerator {

    private final static ResponseState INITIAL_RESPONSE_STATE = ResponseState.NOT_STARTED;

    private final QuestionnaireRepository questionnaireRepository;
    private final AnswerResponseRepository answerResponseRepository;

    public AnswerResponseGenerator(
        QuestionnaireRepository questionnaireRepository,
        AnswerResponseRepository answerResponseRepository
    ) {
        this.questionnaireRepository = questionnaireRepository;
        this.answerResponseRepository = answerResponseRepository;
    }

    public AnswerResponse generateAnswerResponseToQuestionnaire(
        QuestionnaireType questionnaireType
    ) {
        AnswerResponse response = 
            generateAnswerResponse(
                questionnaireRepository.getLatestQuestionnaire(
                    questionnaireType.toString()
                )
            );

        return answerResponseRepository.saveAndFlush(response);
    }    

    public static AnswerResponse generateAnswerResponse(Questionnaire questionnaire) {
        
        AnswerResponse response = new AnswerResponse();
        response.setQuestionnaire(questionnaire);
        response.setState(INITIAL_RESPONSE_STATE);
        response.setStatus(null);

        for (QuestionSection questionSection: questionnaire.getQuestionSections()) {
            AnswerSection section = generateSection(questionSection);
            section.setAnswerResponse(response);
            response.addAnswerSection(section);
        }
        return response;
    }

    private static AnswerSection generateSection(QuestionSection questionSection) {

        AnswerSection section = new AnswerSection();
        section.setQuestionSection(questionSection);

        QuestionGroup questionGroup = questionSection.getQuestionGroup();
        AnswerGroup group = generateGroup(questionGroup);
        section.addAnswerGroup(group);

        return section;
    }

    private static AnswerGroup generateGroup(QuestionGroup questionGroup) {
        AnswerGroup group = new AnswerGroup();
        group.setOrder(0);
        
        for (Question question: questionGroup.getQuestions()) {
            Answer answer = generateAnswer(question);
            answer.setAnswerGroup(group);
            group.addAnswer(answer);
        }
        return group;
    }

    private static Answer generateAnswer(Question question) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setNumber(null);
        
        QuestionType questionType = question.getType();
        if (questionType == QuestionType.NUMBER_HEIGHT) {
            answer.setUnits(AnswerUnits.CENTIMETERS);
        }
        else if (questionType == QuestionType.NUMBER_WEIGHT) {
            answer.setUnits(AnswerUnits.KILOS);
        }
        else {
            answer.setUnits(null);
        }

        for (QuestionItem questionItem: question.getQuestionItems()) {
            AnswerItem item = generateItem(questionItem);
            item.setAnswer(answer);
            answer.addAnswerItem(item);
        }
        return answer;
    }

    private static AnswerItem generateItem(QuestionItem questionItem) {
        AnswerItem item = new AnswerItem();
        item.setQuestionItem(questionItem);
        item.setSelected(false);
        return item;
    }
}
