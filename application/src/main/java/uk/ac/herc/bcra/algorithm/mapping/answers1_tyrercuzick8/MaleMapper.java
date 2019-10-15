package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.MaleRelative;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
import uk.ac.herc.bcra.algorithm.answers.version1.GroupAccess;

public class MaleMapper {

    public static void map(GroupAccess group, MaleRelative father, MaleRelative brother) {
        // FATHER_BREAST_CANCER
        father.breastCancer =
            group
            .answer(QuestionIdentifier.FATHER_BREAST_CANCER)
            .selected(QuestionItemIdentifier.FATHER_BREAST_CANCER_YES);

        // BROTHER_BREAST_CANCER
        brother.breastCancer = 
            group
            .answer(QuestionIdentifier.BROTHER_BREAST_CANCER)
            .selected(QuestionItemIdentifier.BROTHER_BREAST_CANCER_YES);
    }
}
