package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

import java.util.HashMap;
import java.util.Map;

import uk.ac.herc.bcra.algorithm.answers.version1.AnswerAccess;
import uk.ac.herc.bcra.algorithm.answers.version1.ResponseAccess;
import uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8.SingleRelativeMapper.Cancer;

public class RelativeMapper {

    private static enum Relative {
        MOTHER,
        GRANDMOTHER,
        SISTER,
        HALFSISTER,
        AUNT,
        NIECE
    };

    public static void map(ResponseAccess response, AlgorithmModel algorithm) {

        Relative breastRelative = breastRelative(response);
        Relative ovarianRelative = ovarianRelative(response);

        Cancer cancer = null;
        Relative relative = null;

        if ((breastRelative != null) && (ovarianRelative == null)) {
            cancer = Cancer.BREAST_CANCER;
            relative = breastRelative;
        }
        else if ((ovarianRelative != null) && (breastRelative == null)) {
            cancer = Cancer.OVARIAN_CANCER;
            relative = ovarianRelative;
        }

        if (cancer != null) {
            if (relative == Relative.MOTHER) {
                SingleRelativeMapper.mapMother(response, algorithm, cancer);
            }
            else if (relative == Relative.SISTER) {
                SingleRelativeMapper.mapSister(response, algorithm, cancer);
            }
            else if (relative == Relative.GRANDMOTHER) {
                if (grandmotherIsMaternal(response)) {
                    SingleRelativeMapper.mapMaternalGrandmother(response, algorithm, cancer);
                }
                else {
                    SingleRelativeMapper.mapPaternalGrandmother(response, algorithm, cancer);
                }
            }
            else if (relative == Relative.HALFSISTER) {
                if (halfsisterIsMaternal(response)) {
                    SingleRelativeMapper.mapMaternalHalfsister(response, algorithm, cancer);
                }
                else {
                    SingleRelativeMapper.mapPaternalHalfsister(response, algorithm, cancer);
                }
            }
            else if (relative == Relative.AUNT) {
                if (auntIsMaternal(response)) {
                    SingleRelativeMapper.mapMaternalAunt(response, algorithm, cancer);
                }
                else {
                    SingleRelativeMapper.mapPaternalAunt(response, algorithm, cancer);
                }
            }
            else if (relative == Relative.NIECE) {
                if (nieceIsSistersDaughter(response)) {
                    SingleRelativeMapper.nieceSister(response, algorithm, cancer);
                }
                else {
                    SingleRelativeMapper.nieceBrother(response, algorithm, cancer);
                }
            }

        }
    }

    private static Relative breastRelative(ResponseAccess response) {

        AnswerAccess relativeAnswer = 
            response
            .section(QuestionSectionIdentifier.FAMILY_BREAST_AFFECTED)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_BREAST_AFFECTED);

        boolean relativeNone = relativeAnswer.selected(QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NONE);
        int relativeCount = relativeAnswer.selectedCount();
        QuestionItemIdentifier relativeItem = relativeAnswer.getOnlySelectedItem();

        boolean relativeOnlyOne = 
            response
            .section(QuestionSectionIdentifier.FAMILY_BREAST_HOW_MANY)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_BREAST_HOW_MANY)
            .selected(QuestionItemIdentifier.FAMILY_BREAST_HOW_MANY_ONE);
            
        Map<QuestionItemIdentifier, Relative> relativeMap =
            new HashMap<QuestionItemIdentifier, Relative>();
        relativeMap.put(QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_MOTHER, Relative.MOTHER);
        relativeMap.put(QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_GRANDMOTHER, Relative.GRANDMOTHER);
        relativeMap.put(QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_SISTER, Relative.SISTER);
        relativeMap.put(QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_HALFSISTER, Relative.HALFSISTER);
        relativeMap.put(QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_AUNT, Relative.AUNT);
        relativeMap.put(QuestionItemIdentifier.FAMILY_BREAST_AFFECTED_NIECE, Relative.NIECE);

        return getRelative(
            relativeNone,
            relativeCount,
            relativeItem,
            relativeOnlyOne,
            relativeMap
        );
    }

    private static Relative ovarianRelative(ResponseAccess response) {

        AnswerAccess relativeAnswer = 
            response
            .section(QuestionSectionIdentifier.FAMILY_OVARIAN_AFFECTED)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_OVARIAN_AFFECTED);

        boolean relativeNone = relativeAnswer .selected(QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NONE);
        int relativeCount = relativeAnswer.selectedCount();
        QuestionItemIdentifier relativeItem = relativeAnswer.getOnlySelectedItem();

        boolean relativeOnlyOne = 
            response
            .section(QuestionSectionIdentifier.FAMILY_OVARIAN_HOW_MANY)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_OVARIAN_HOW_MANY)
            .selected(QuestionItemIdentifier.FAMILY_OVARIAN_HOW_MANY_ONE);

        Map<QuestionItemIdentifier, Relative> relativeMap =
            new HashMap<QuestionItemIdentifier, Relative>();
        relativeMap.put(QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_MOTHER, Relative.MOTHER);
        relativeMap.put(QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_GRANDMOTHER, Relative.GRANDMOTHER);
        relativeMap.put(QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_SISTER, Relative.SISTER);
        relativeMap.put(QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_HALFSISTER, Relative.HALFSISTER);
        relativeMap.put(QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_AUNT, Relative.AUNT);
        relativeMap.put(QuestionItemIdentifier.FAMILY_OVARIAN_AFFECTED_NIECE, Relative.NIECE);        

        return getRelative(
            relativeNone,
            relativeCount,
            relativeItem,
            relativeOnlyOne,
            relativeMap
        );
    } 

    private static Relative getRelative(
        boolean relativeNone, 
        int relativeCount, 
        QuestionItemIdentifier relativeItem,
        boolean relativeOnlyOne,
        final Map<QuestionItemIdentifier, Relative> relativeMap
    ) {
        if (!relativeNone) {
            if (relativeCount == 1) {
                Relative relative = relativeMap.get(relativeItem);

                if ((relative == Relative.MOTHER) || (relative == Relative.SISTER)) {
                    return relative;
                }
                else if (relativeOnlyOne) {
                    return relative;
                }
            }
        }

        return null;
    }

    private static boolean grandmotherIsMaternal(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_AFFECTED_GRANDMOTHER)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE)
            .selected(QuestionItemIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER);
    }

    private static boolean halfsisterIsMaternal(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_AFFECTED_HALF_SISTER)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE)
            .selected(QuestionItemIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER);
    }
    
    private static boolean auntIsMaternal(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_AFFECTED_AUNT)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_AFFECTED_AUNT_SIDE)
            .selected(QuestionItemIdentifier.FAMILY_AFFECTED_AUNT_SIDE_MOTHER);
    }    

    private static boolean nieceIsSistersDaughter(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_AFFECTED_NIECE)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_AFFECTED_NIECE_SIDE)
            .selected(QuestionItemIdentifier.FAMILY_AFFECTED_NIECE_SIDE_SISTER);
    }
}
