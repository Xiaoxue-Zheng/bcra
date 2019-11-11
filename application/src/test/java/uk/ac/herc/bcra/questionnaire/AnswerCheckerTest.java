package uk.ac.herc.bcra.questionnaire;

import org.junit.jupiter.api.Test;

import uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8.MapperTestHelper;
import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.domain.enumeration.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerCheckerTest {
    
    @Test
    public void numberWithinRangeIsValid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER,
            null,
            456,
            123,
            789
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED);
    }

    @Test
    public void unknownNumberIsValid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER,
            null,
            null,
            123,
            789
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED);
    }
        
    @Test
    public void numberOverMaximumIsInvalid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER,
            null,
            987,
            123,
            789
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID);
    }
    
    @Test
    public void numberUnderMinimumIsInvalid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER,
            null,
            222,
            333,
            444
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void weightWithinRangeIsValid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER_WEIGHT,
            AnswerUnits.POUNDS,
            123,    // LB = 56kg
            50,     // kilos
            60      // kilos
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED);
    }

    @Test
    public void unknownWeightValid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER_WEIGHT,
            AnswerUnits.POUNDS,
            null,    // LB = 56kg
            50,     // kilos
            60      // kilos
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED);
    }
            
    @Test
    public void weightOverMaximumIsInvalid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER_WEIGHT,
            AnswerUnits.POUNDS,
            123,    // LB = 56kg
            40,     // kilos
            50      // kilos
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void weightUnderMinimumIsInvalid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER_WEIGHT,
            AnswerUnits.POUNDS,
            123,    // pounds = 56kg
            60,     // kilos
            150     // kilos
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void heightWithinRangeIsValid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER_HEIGHT,
            AnswerUnits.INCHES,
            67,     // inches = 170cm
            160,    // centimetres
            180     // centimetres
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED);
    }

    @Test
    public void unknownHeightIsValid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER_HEIGHT,
            AnswerUnits.INCHES,
            null,     // inches = 170cm
            160,    // centimetres
            180     // centimetres
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED);
    }    
            
    @Test
    public void heightOverMaximumIsInvalid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER_HEIGHT,
            AnswerUnits.INCHES,
            67,    // inches = 170cm
            50,    // centimetres
            80     // centimetres
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID);
    }

    @Test
    public void heightUnderMinimumIsInvalid() {
        AnswerResponse response = createResponseWithNumberAnswer(
            QuestionType.NUMBER_HEIGHT,
            AnswerUnits.INCHES,
            67,      // inches = 170cm
            180,     // centimetres
            270      // centimetres
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID);
    }    
    
    @Test
    public void checkboxEnoughItemsIsValid() {
        AnswerResponse response = createResponseWithAnswerAndItems(
            QuestionType.CHECKBOX,
            3,  // selected
            2,  // minimum
            0,  // exclusive
            0   // necessary
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED);
    }
    
    @Test
    public void checkboxNotEnoughItemsIsInvalid() {
        AnswerResponse response = createResponseWithAnswerAndItems(
            QuestionType.CHECKBOX,
            2,  // selected
            3,  // minimum
            0,  // exclusive
            0   // necessary
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID);
    }
    
    @Test
    public void checkboxOneExclusiveItemIsValid() {
        AnswerResponse response = createResponseWithAnswerAndItems(
            QuestionType.CHECKBOX,
            1,  // selected
            0,  // minimum
            1,  // exclusive
            0   // necessary
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED);
    }
    
    @Test
    public void checkboxManyExclusiveItemsIsInvalid() {
        AnswerResponse response = createResponseWithAnswerAndItems(
            QuestionType.CHECKBOX,
            3,  // selected
            0,  // minimum
            1,  // exclusive
            0   // necessary
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID); 
    }
    
    @Test
    public void checkboxNecessaryItemIsValid() {
        AnswerResponse response = createResponseWithAnswerAndItems(
            QuestionType.CHECKBOX,
            3,  // selected
            0,  // minimum
            0,  // exclusive
            3   // necessary
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED); 
    }
    
    @Test
    public void checkboxNotNecessaryItemIsInvalid() {
        AnswerResponse response = createResponseWithAnswerAndItems(
            QuestionType.CHECKBOX,
            2,  // selected
            0,  // minimum
            0,  // exclusive
            3   // necessary
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID); 
    }

    @Test
    public void radioUnansweredIsValid() {
        AnswerResponse response = createResponseWithAnswerAndItems(
            QuestionType.RADIO,
            0,  // selected
            0,  // minimum
            0,  // exclusive
            0   // necessary
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED); 
    }

    @Test
    public void radioOneItemIsValid() {
        AnswerResponse response = createResponseWithAnswerAndItems(
            QuestionType.RADIO,
            1,  // selected
            0,  // minimum
            0,  // exclusive
            0   // necessary
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.VALIDATED); 
    }
    
    @Test
    public void radioMultipleItemsIsInvalid() {
        AnswerResponse response = createResponseWithAnswerAndItems(
            QuestionType.RADIO,
            2,  // selected
            0,  // minimum
            0,  // exclusive
            0   // necessary
        );
            
        AnswerChecker.checkAnswersValid(response);
        assertThat(response.getState()).isEqualTo(ResponseState.INVALID);  
    }

    private AnswerResponse createResponseWithNumberAnswer(
        QuestionType questionType,
        AnswerUnits units,
        Integer number,
        Integer maximum,
        Integer minimum
    ) {
        AnswerGroup group = new AnswerGroup();
        MapperTestHelper.addAnswerNumberUnitsMinMax(
            group,
            QuestionIdentifier.FAMILY_BREAST_AGE,
            questionType,
            units,
            number,
            maximum,
            minimum
        );

        AnswerSection section =
            MapperTestHelper.createAnswerSection(
                QuestionSectionIdentifier.FAMILY_AFFECTED_GRANDMOTHER
                , group
            );
        section.addAnswerGroup(group);

        AnswerResponse response = new AnswerResponse();
        response.addAnswerSection(section);

        return response;
    }

    private AnswerResponse createResponseWithAnswerAndItems(
        QuestionType questionType,
        int selected,
        int minimum,
        int exclusive,
        int neccessary
    ) {
        AnswerGroup group = new AnswerGroup();
        MapperTestHelper.addAnswerAndItemsWithProperties(
            group,
            QuestionIdentifier.FAMILY_BREAST_AFFECTED,
            questionType,
            new QuestionItemIdentifier[] {
                QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_MOTHER,
                QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_GRANDMOTHER,
                QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_AUNT,
                QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NIECE,
                QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_SISTER,
                QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_HALFSISTER
            },
            selected,
            minimum,
            exclusive,
            neccessary
        );

        AnswerSection section =
            MapperTestHelper.createAnswerSection(
                QuestionSectionIdentifier.FAMILY_AFFECTED_GRANDMOTHER
                , group
            );
        section.addAnswerGroup(group);

        AnswerResponse response = new AnswerResponse();
        response.addAnswerSection(section);

        return response;
    }
}
