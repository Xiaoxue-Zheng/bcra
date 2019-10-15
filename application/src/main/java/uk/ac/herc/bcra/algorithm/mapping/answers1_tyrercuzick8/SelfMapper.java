package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import java.util.Map;
import com.google.common.collect.ImmutableMap;

import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.MenopausalStatus;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Self;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
import uk.ac.herc.bcra.algorithm.answers.version1.GroupAccess;

public class SelfMapper {

    private final static Map<QuestionItemIdentifier, Boolean> ashkenaziMap
        = ImmutableMap.of(
            QuestionItemIdentifier.SELF_ASHKENAZI_YES, true,
            QuestionItemIdentifier.SELF_ASHKENAZI_NO, false,

            /* This ought to be null, but null is not supported by ImmutableMap. */
            /* As it happens, TCv8 uses the same value for false and unknown. */
            QuestionItemIdentifier.SELF_ASHKENAZI_UNKNOWN, false
        );

    private final static Map<QuestionItemIdentifier, MenopausalStatus> menopausalMap
        = ImmutableMap.of(
            QuestionItemIdentifier.SELF_PREMENOPAUSAL_YES, MenopausalStatus.PRE_MENOPAUSAL,
            QuestionItemIdentifier.SELF_PREMENOPAUSAL_NO, MenopausalStatus.POST_MENOPAUSAL,
            QuestionItemIdentifier.SELF_PREMENOPAUSAL_UNKNOWN, MenopausalStatus.PRE_MENOPAUSAL
        );

    public static void map(GroupAccess group, Self self) {
        
        // SELF_FIRST_PERIOD
        self.firstPeriodAge = group.answer(QuestionIdentifier.SELF_FIRST_PERIOD).getNumber();

        // SELF_PREMENOPAUSAL
        self.menopausalStatus = menopausalMap.get(
            group.answer(QuestionIdentifier.SELF_PREMENOPAUSAL).getOnlySelectedItem()
        );

        // SELF_MENOPAUSAL_AGE
        self.menopauseAge = group.answer(QuestionIdentifier.SELF_MENOPAUSAL_AGE).getNumber();

        // SELF_PREGNANCIES
        self.liveBirths = group.answer(QuestionIdentifier.SELF_PREGNANCIES).getNumber();

        // SELF_PREGNANCY_FIRST_AGE
        self.firstBirthAge = group.answer(QuestionIdentifier.SELF_PREGNANCY_FIRST_AGE).getNumber();

        // SELF_HEIGHT
        self.heightMeters = group.answer(QuestionIdentifier.SELF_HEIGHT).getHeight();

        // SELF_WEIGHT
        self.weightKilos = group.answer(QuestionIdentifier.SELF_WEIGHT).getWeight();

        // SELF_BREAST_BIOPSY
        // Ignore - only diagnosis matters (below)

        // SELF_BREAST_BIOPSY_DIAGNOSIS_RISK
        // Ignore - only diagnosis matters (below)

        // SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES
        self.hyperplasiaHistory = 
            group
            .answer(QuestionIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES)
            .selected(QuestionItemIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ADH);

        self.atypicalHyperplasia = 
            group
            .answer(QuestionIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES)
            .selected(QuestionItemIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ALH);

        self.lcisHistory = 
            group
            .answer(QuestionIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES)
            .selected(QuestionItemIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_LCIS);
    
        // SELF_ASHKENAZI
        self.ashkanaziJewish = ashkenaziMap.get(
            group.answer(QuestionIdentifier.SELF_ASHKENAZI).getOnlySelectedItem()
        );
    }
}
