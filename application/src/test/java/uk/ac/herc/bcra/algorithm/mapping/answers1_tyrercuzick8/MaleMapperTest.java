package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import uk.ac.herc.bcra.algorithm.answers.version1.GroupAccess;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.MaleRelative;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;

public class MaleMapperTest {

    @Test
    public void testFatherAndBrother() throws Exception {

        AnswerGroup answerGroup = generateTestAnswerGroup();
        GroupAccess group = new GroupAccess(answerGroup);

        MaleRelative father = new MaleRelative();
        MaleRelative brother = new MaleRelative();        
        MaleMapper.map(group, father, brother);

        assertThat(father.breastCancer).isTrue();
        assertThat(brother.breastCancer).isFalse();
    }

    private AnswerGroup generateTestAnswerGroup() {

        AnswerGroup answerGroup = new AnswerGroup();

        MapperTestHelper.addAnswerAndItem(
            answerGroup,
            QuestionIdentifier.FATHER_BREAST_CANCER,
            QuestionType.RADIO,
            QuestionItemIdentifier.FATHER_BREAST_CANCER_YES,
            true
        );

        MapperTestHelper.addAnswerAndItem(
            answerGroup,
            QuestionIdentifier.BROTHER_BREAST_CANCER,
            QuestionType.RADIO,
            QuestionItemIdentifier.BROTHER_BREAST_CANCER_YES,
            false
        );

        return answerGroup;
    }
}
