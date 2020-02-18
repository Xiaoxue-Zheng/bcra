package uk.ac.herc.bcra.questionnaire;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.AnswerItem;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;

public class AnswerResponseCheckerTest {

    /* This file contains output straight from the API, obtained from: */
    /* JHipster Admin Interface > Administration > API > questionnaire-resource */
    private final String QUESTIONNAIRE_JSON_FILE = "json/test-questionnaire.json";

    private AnswerResponse answerResponse;

    @BeforeEach
    public void setup() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Questionnaire questionnaire = 
            mapper.readValue(
                new ClassPathResource(QUESTIONNAIRE_JSON_FILE).getFile(), 
                Questionnaire.class
            );

        answerResponse = AnswerResponseGenerator.generateAnswerResponse(questionnaire);
    }
    
    @Test
    public void generatedResponseIsValid() {
        AnswerResponseChecker.checkAnswerResponsesValid(answerResponse);
        assertThat(answerResponse.getStatus()).isEqualTo(null);
        assertThat(answerResponse.getState()).isEqualTo(ResponseState.VALIDATED);
    }

    @Test
    public void missingSectionIsInvalid() {

        answerResponse.removeAnswerSection(
            answerResponse.getAnswerSections().iterator().next()
        );
        
        AnswerResponseChecker.checkAnswerResponsesValid(answerResponse);
        assertThat(answerResponse.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void extraSectionIsInvalid() {

        answerResponse.addAnswerSection(
            new AnswerSection()
        );
        
        AnswerResponseChecker.checkAnswerResponsesValid(answerResponse);
        assertThat(answerResponse.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void missingGroupIsInvalid() {
        AnswerSection section = answerResponse.getAnswerSections().iterator().next();

        section.removeAnswerGroup(
            section.getAnswerGroups().iterator().next()
        );
        
        AnswerResponseChecker.checkAnswerResponsesValid(answerResponse);
        assertThat(answerResponse.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void extraGroupIsInvalid() {
        AnswerSection section = answerResponse.getAnswerSections().iterator().next();

        section.addAnswerGroup(
            new AnswerGroup()
        );
        
        AnswerResponseChecker.checkAnswerResponsesValid(answerResponse);
        assertThat(answerResponse.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void missingAnswerIsInvalid() {
        AnswerGroup group = 
            answerResponse
            .getAnswerSections()
            .iterator()
            .next()
            .getAnswerGroups()
            .iterator()
            .next();

        group.removeAnswer(
          group.getAnswers().iterator().next()  
        );
        
        AnswerResponseChecker.checkAnswerResponsesValid(answerResponse);
        assertThat(answerResponse.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void extraAnswerIsInvalid() {
        AnswerGroup group = 
            answerResponse
            .getAnswerSections()
            .iterator()
            .next()
            .getAnswerGroups()
            .iterator()
            .next();

        group.addAnswer(
          new Answer()
        );
        
        AnswerResponseChecker.checkAnswerResponsesValid(answerResponse);
        assertThat(answerResponse.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void missingItemIsInvalid() {

        AnswerSection sectionWithGuaranteedItems = null;

        // Section FAMILY_AFFECTED has 2 Questions, both with Items
        for (AnswerSection eachSection: answerResponse.getAnswerSections()) {
            if (eachSection.getQuestionSection().getIdentifier() 
                == QuestionSectionIdentifier.FAMILY_AFFECTED) {
                sectionWithGuaranteedItems = eachSection;
                break;
            }
        }

        Answer answer = 
            sectionWithGuaranteedItems
            .getAnswerGroups()
            .iterator()
            .next()
            .getAnswers()
            .iterator()
            .next();

        answer.removeAnswerItem(
            answer.getAnswerItems().iterator().next()  
        );
        
        AnswerResponseChecker.checkAnswerResponsesValid(answerResponse);
        assertThat(answerResponse.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void extraItemIsInvalid() {
        Answer answer = 
            answerResponse
            .getAnswerSections()
            .iterator()
            .next()
            .getAnswerGroups()
            .iterator()
            .next()
            .getAnswers()
            .iterator()
            .next();

        answer.addAnswerItem(
            new AnswerItem()
        );
        
        AnswerResponseChecker.checkAnswerResponsesValid(answerResponse);
        assertThat(answerResponse.getState()).isEqualTo(ResponseState.INVALID);
    }
}
