package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import uk.ac.herc.bcra.algorithm.answers.version1.GroupAccess;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.MenopausalStatus;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Self;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.enumeration.AnswerUnits;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;

public class SelfMapperTest {

    /* Conversions by Google */
    private final float FIFTY_FIVE_INCHES_IN_METERS = 1.397f;

    /* Rounded up to nearest kilogram */
    private final int SIXTY_SIX_POUNDS_IN_KILOS = 30;

    @Test
    public void testSelfMapper() throws Exception {

        AnswerGroup answerGroup = generateTestAnswerGroup();
        GroupAccess group = new GroupAccess(answerGroup);

        Self self = new Self();
        SelfMapper.map(group, self);

        assertThat(self.firstPeriodAge).isEqualTo(123);

        assertThat(self.liveBirths).isEqualTo(456);
        assertThat(self.firstBirthAge).isEqualTo(654);

        assertThat(self.menopausalStatus).isEqualTo(MenopausalStatus.PRE_MENOPAUSAL);
        assertThat(self.menopauseAge).isEqualTo(321);

        assertThat(self.heightMeters).isCloseTo(FIFTY_FIVE_INCHES_IN_METERS, within(0.001f));
        assertThat(self.weightKilos).isEqualTo(SIXTY_SIX_POUNDS_IN_KILOS);

        assertThat(self.hyperplasiaHistory).isTrue();
        assertThat(self.atypicalHyperplasia).isFalse();
        assertThat(self.lcisHistory).isTrue();

        assertThat(self.ovarianCancer).isNull();
        assertThat(self.ovarianAge).isNull();

        assertThat(self.ashkanaziJewish).isFalse();

        assertThat(self.useHRT).isNull();
        assertThat(self.typeOfHRT).isNull();
        assertThat(self.yearsHRT).isNull();
        assertThat(self.futureHRT).isNull();
        assertThat(self.sinceHRT).isNull();

        assertThat(self.geneticTest).isNull();
        assertThat(self.geneticTestFather).isNull();
    }

    private AnswerGroup generateTestAnswerGroup() {

        AnswerGroup answerGroup = new AnswerGroup();

        // SELF_FIRST_PERIOD
        MapperTestHelper.addAnswerNumber(
            answerGroup,
            QuestionIdentifier.SELF_FIRST_PERIOD,
            QuestionType.NUMBER,
            123
        );

        // SELF_PREMENOPAUSAL
        MapperTestHelper.addAnswerAndItem(
            answerGroup,
            QuestionIdentifier.SELF_PREMENOPAUSAL,
            QuestionType.RADIO,
            QuestionItemIdentifier.SELF_PREMENOPAUSAL_YES,
            true
        );

        // SELF_MENOPAUSAL_AGE
        MapperTestHelper.addAnswerNumber(
            answerGroup,
            QuestionIdentifier.SELF_MENOPAUSAL_AGE,
            QuestionType.NUMBER,
            321
        );        

        // SELF_PREGNANCIES
        MapperTestHelper.addAnswerNumber(
            answerGroup,
            QuestionIdentifier.SELF_PREGNANCIES,
            QuestionType.NUMBER,
            456
        );   
        
        // SELF_PREGNANCY_FIRST_AGE
        MapperTestHelper.addAnswerNumber(
            answerGroup,
            QuestionIdentifier.SELF_PREGNANCY_FIRST_AGE,
            QuestionType.NUMBER,
            654
        );  

        // SELF_HEIGHT
        MapperTestHelper.addAnswerNumberUnits(
            answerGroup,
            QuestionIdentifier.SELF_HEIGHT,
            QuestionType.NUMBER_HEIGHT,
            AnswerUnits.INCHES,
            55
        );  

        // SELF_WEIGHT
        MapperTestHelper.addAnswerNumberUnits(
            answerGroup,
            QuestionIdentifier.SELF_WEIGHT,
            QuestionType.NUMBER_WEIGHT,
            AnswerUnits.POUNDS,
            66
        );  

        Map<QuestionItemIdentifier, Boolean> biopsyItems =
            ImmutableMap.of(
                QuestionItemIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ADH, true,
                QuestionItemIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ALH, false,
                QuestionItemIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_LCIS, true
            );

        MapperTestHelper.addAnswerAndItems(
            answerGroup,
            QuestionIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES,
            QuestionType.CHECKBOX,
            biopsyItems
        );

        // SELF_ASHKENAZI
        MapperTestHelper.addAnswerAndItem(
            answerGroup,
            QuestionIdentifier.SELF_ASHKENAZI,
            QuestionType.RADIO,
            QuestionItemIdentifier.SELF_ASHKENAZI_NO,
            true
        );
        
        return answerGroup;
    }
}
