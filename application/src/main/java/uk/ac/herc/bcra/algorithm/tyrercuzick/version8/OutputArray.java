package uk.ac.herc.bcra.algorithm.tyrercuzick.version8;

import java.util.ArrayList;
import java.util.List;

import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.BreastDensity;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.BreastDensityMeasure;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.GeneticTest;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.HyperplasiaHistory;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.MaleRelative;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.MenopausalStatus;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Parent;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.PolygenicSNP;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Relative;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Self;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.TypeOfHRT;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.MaleParent;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.UseHRT;

public class OutputArray {

    public static List<Object> getOutputArray(AlgorithmModel algorithm) {

        List<Object> array = new ArrayList<Object>();

        mapSelfOutput(algorithm.self, array);

        mapSingleRelative(algorithm.mother, array);

        mapMultipleParents(algorithm.sisters, array);

        mapSingleRelative(algorithm.paternalGrandmother, array);

        mapSingleRelative(algorithm.maternalGrandmother, array);

        mapMultipleParents(algorithm.paternalAunts, array);

        mapMultipleParents(algorithm.maternalAunts, array);

        mapMultipleRelatives(algorithm.daughters, array);

        mapWomensDaughters(algorithm.sisters, algorithm.unaffectedBrother, array);

        mapMansDaughters(algorithm.unaffectedBrother, true, array);

        mapMultipleRelatives(algorithm.paternalHalfSisters, array);

        mapMultipleRelatives(algorithm.maternalHalfSisters, array);

        mapWomensDaughters(algorithm.paternalAunts, null, array);

        mapWomensDaughters(algorithm.maternalAunts, null, array);

        mapMansDaughters(algorithm.unaffectedPaternalUncle, false, array);

        mapMansDaughters(algorithm.unaffectedMaternalUncle, false, array);

        mapFinalFields(
            algorithm.father, 
            algorithm.brother,
            algorithm.breastDensity,
            algorithm.polygenicSNP,
            array
        );

        return array;
    }

    private static final Integer MINUS_NINETY_NINE = -99;
    private static final Integer ZERO = 0;
    private static final Integer TWO = 2;

    private static void mapSelfOutput(Self self, List<Object>array) {

        // Participant identifier
        array.add(self.participantIdentifier);

        // Current age (of participant)
        array.add(swapIfNull(self.ageInYears, MINUS_NINETY_NINE));

        // Age at menarche
        array.add(swapIfNull(self.firstPeriodAge, MINUS_NINETY_NINE));

        // Number of live births
        array.add(swapIfNull(self.liveBirths, TWO));

        // Age at first birth
        array.add(mapAgeAtFirstBirth(self.liveBirths, self.firstBirthAge));

        // Menopausal status
        array.add(mapMenopausalStatus(self.menopausalStatus));

        // Age at menopause
        array.add(mapMenopausalAge(self.menopauseAge, self.menopausalStatus));

        // Height
        array.add(swapIfNull(self.heightMeters, MINUS_NINETY_NINE));

        // Weight
        array.add(swapIfNull(self.weightKilos, MINUS_NINETY_NINE));

        // History of hyperplasia
        array.add(mapHyperplasiaHistory(self.hyperplasiaHistory));

        // History of atypical hyperplasia
        array.add(swapIfNull(self.atypicalHyperplasia, ZERO));

        // History of LCIS
        array.add(swapIfNull(self.lcisHistory, ZERO));

        // NOT IN USE
        array.add(ZERO);

        // History of ovarian cancer
        array.add(swapIfNull(self.ovarianCancer, ZERO));

        // Age at diagnosis of ovarian cancer
        array.add(swapIfNull(self.ovarianAge, MINUS_NINETY_NINE));

        // Ashkanazi Jewish heritage
        array.add(swapIfNull(self.ashkanaziJewish, ZERO));

        // HRT use
        array.add(mapHRTValue(self.useHRT));

        // Type of hrt taken
        array.add(mapTypeOfHRTValue(self.typeOfHRT, self.useHRT));

        // Length of time taking HRT in the past
        array.add(swapIfNull(self.yearsHRT, ZERO));

        // Length of time woman intends to use HRT in the future (if current user)
        array.add(swapIfNull(self.futureHRT, ZERO));

        // Time since HRT last used (if previous HRT user & time since last use < 5 years)
        array.add(swapIfNull(self.sinceHRT, ZERO));

        // Genetic testing of the woman
        array.add(mapGeneticTest(self.geneticTest));

        // Genetic testing of the woman’s father
        array.add(mapGeneticTest(self.geneticTestFather));
    }

    private static void mapSingleRelative(Relative relative, List<Object>array) {

        // Has the <relative> had breast cancer?
        array.add(swapIfNull(relative.breastCancer, ZERO));

        // Was the <relative> breast cancer bilateral?
        array.add(swapIfNull(relative.bilateral, ZERO));

        // Has the <relative> had ovarian cancer?
        array.add(swapIfNull(relative.ovarianCancer, ZERO));

        // Age at which <relative> developed breast cancer, if not then their current age or the age at which they died 
        array.add(swapIfNull(relative.breastAge, MINUS_NINETY_NINE));

        // Age at which <relative> developed bilateral breast cancer
        array.add(swapIfNull(relative.bilateralAge, MINUS_NINETY_NINE));

        // Age at which <relative> developed ovarian cancer
        array.add(swapIfNull(relative.ovarianAge, MINUS_NINETY_NINE));

        // Genetic testing of the woman’s <relative>
        array.add(mapGeneticTest(relative.geneticTest));
    }

    private static Integer mapAgeAtFirstBirth(Integer liveBirths, Integer ageAtBirth) {
        if (liveBirths == null || liveBirths == 0) {
            return 0;
        }

        if (ageAtBirth == null) {
            return MINUS_NINETY_NINE;
        } else {
            return ageAtBirth;
        }
    }

    private static void mapMultipleParents(List<Parent> parents, List<Object>array) {
        
        Integer relativeCount = parents.size();
        array.add(relativeCount);

        for (Parent relative: parents) {
            
            mapSingleRelative(relative, array);
        }
    }
    
    private static void mapMultipleRelatives(List<Relative> relatives, List<Object>array) {
        
        Integer relativeCount = relatives.size();
        array.add(relativeCount);

        for (Relative relative: relatives) {
            
            mapSingleRelative(relative, array);
        }
    }

    private static void mapWomensDaughters(List<Parent> mothers, MaleParent extraMan, List<Object>array) {
        
        Integer motherCount = mothers.size();

        if (extraMan != null) {
            if (extraMan.daughters.size() > 0) {
                motherCount += 1;
            }
        }

        array.add(motherCount);

        for (Parent mother: mothers) {
            mapMultipleRelatives(mother.daughters, array);
        }
    }

    private static void mapMansDaughters(MaleParent father, boolean isBrother, List<Object> array) {

        boolean hasDaughters = father.daughters.size() > 0;

        if (!isBrother) {
            if (hasDaughters) {
                array.add(1);
            }
            else {
                array.add(0);
            }
        }

        if (hasDaughters) {
            mapMultipleRelatives(father.daughters, array);
        }
    }

    private static void mapFinalFields(
        MaleRelative father,
        MaleRelative brother,
        BreastDensity breastDensity,
        PolygenicSNP polygenicSNP,
        List<Object> array
    ) {
        array.add(swapIfNull(father.breastCancer, ZERO));

        array.add(swapIfNull(brother.breastCancer, ZERO));

        array.add(mapBreastDensityMeasure(breastDensity.measure));

        array.add(swapIfNull(breastDensity.value, MINUS_NINETY_NINE));

        array.add(swapIfNull(polygenicSNP.score, ZERO));

    }


    private static Integer mapMenopausalStatus(MenopausalStatus status) {
        if (status == MenopausalStatus.PRE_MENOPAUSAL) {
            return 0;
        }
        else if (status == MenopausalStatus.PERI_MENOPAUSAL) {
            return 1;
        }
        else if (status == MenopausalStatus.POST_MENOPAUSAL) {
            return 2;
        }
        else /* UNKNOWN */ {
            return 3;
        }
    }

    private static Integer mapMenopausalAge(Integer age, MenopausalStatus status) {
        if (
            (status == MenopausalStatus.PRE_MENOPAUSAL) ||
            (status == MenopausalStatus.PERI_MENOPAUSAL) ||
            (status == null) ||
            (age == null)
        ) {
            return MINUS_NINETY_NINE;
        }
        else {
            return age;
        }
    }

    private static Integer mapHyperplasiaHistory(HyperplasiaHistory hyperplasiaHistory) {
        if (hyperplasiaHistory == HyperplasiaHistory.HISTORY_UNKNOWN) {
            return 2;
        } else if (hyperplasiaHistory == HyperplasiaHistory.HAS_HISTORY) {
            return 1;
        } else /* NULL OR NO HISTORY */ {
            return 0;
        }
    }

    private static Integer mapHRTValue(UseHRT useHRT) {
        if (useHRT == UseHRT.OVER_FIVE_YEARS_AGO) {
            return 1;
        }
        else if (useHRT == UseHRT.UNDER_FIVE_YEARS_AGO) {
            return 2;
        }
        else if (useHRT == UseHRT.CURRENT_USER) {
            return 3;
        }
        else /* NEVER (or unknown - not in spec!) */ {
            return 0;
        }
    }

    private static Integer mapTypeOfHRTValue(TypeOfHRT typeOfHRT, UseHRT useHRT) {

        Integer useHRTInteger = mapHRTValue(useHRT);

        if (
            (typeOfHRT == TypeOfHRT.COMBINED) ||
            (typeOfHRT == null) ||
            (useHRTInteger == 0) ||
            (useHRTInteger == 1)
         ) {
            return 1;
        }
        else /* OESTROGEN */ {
            return 0;
        }
    }

    private static Integer mapGeneticTest(GeneticTest result) {
        if (result == GeneticTest.NEGATIVE) {
            return 1;
        }
        else if (result == GeneticTest.BRCA1) {
            return 2;
        }
        else if (result == GeneticTest.BRCA2) {
            return 3;
        }
        else /* NEVER or Unknown */ {
            return 0;
        }
    }

    private static Integer mapBreastDensityMeasure(BreastDensityMeasure measure) {
        if (measure == BreastDensityMeasure.VOLPARA) {
            return 0;
        }
        else if (measure == BreastDensityMeasure.VAS) {
            return 1;
        }
        else if (measure == BreastDensityMeasure.BIRADS_ATLAS) {
            return 2;
        }
        else /* not available */ {
            return 0;
        }
    }

    private static Object swapIfNull(Object value, Object swap) {
        if (value == null) {
            return swap;
        }
        else {
            return value;
        }
    }
}
