package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Parent;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Relative;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

import uk.ac.herc.bcra.algorithm.answers.version1.ResponseAccess;

public class SingleRelativeMapper {

    public static enum Cancer {
        BREAST_CANCER,
        OVARIAN_CANCER
    };

    public static void mapMother(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            algorithm.mother.breastCancer = true;
            algorithm.mother.breastAge = breastAge(response);
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            algorithm.mother.ovarianCancer = true;
            algorithm.mother.ovarianAge = ovarianAge(response);
        }
    }

    public static void mapMaternalGrandmother(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            algorithm.maternalGrandmother.breastCancer = true;
            algorithm.maternalGrandmother.breastAge = breastAge(response);
            algorithm.mother.breastCancer = false;
            algorithm.mother.breastAge = grandmotherMotherAge(response);
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            algorithm.maternalGrandmother.ovarianCancer = true;
            algorithm.maternalGrandmother.ovarianAge = ovarianAge(response);
            algorithm.mother.breastCancer = false;
            algorithm.mother.breastAge = grandmotherMotherAge(response);
        }
    }

    public static void mapPaternalGrandmother(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            algorithm.paternalGrandmother.breastCancer = true;
            algorithm.paternalGrandmother.breastAge = breastAge(response);
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            algorithm.paternalGrandmother.ovarianCancer = true;
            algorithm.paternalGrandmother.ovarianAge = ovarianAge(response);
        }
    }    

    public static void mapSister(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            Parent sister = new Parent();
            sister.breastCancer = true;
            sister.breastAge = breastAge(response);
            algorithm.sisters.add(sister);
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            Parent sister = new Parent();
            sister.ovarianCancer = true;
            sister.ovarianAge = ovarianAge(response);
            algorithm.sisters.add(sister);
        }
    }    

    public static void mapMaternalHalfsister(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            Relative maternalHalfSister = new Relative();
            maternalHalfSister.breastCancer = true;
            maternalHalfSister.breastAge = breastAge(response);
            algorithm.maternalHalfSisters.add(maternalHalfSister);
            algorithm.mother.breastCancer = false;
            algorithm.mother.breastAge = halfsisterMotherAge(response);
            
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            Relative maternalHalfSister = new Relative();
            maternalHalfSister.ovarianCancer = true;
            maternalHalfSister.ovarianAge = ovarianAge(response);
            algorithm.maternalHalfSisters.add(maternalHalfSister);
            algorithm.mother.breastCancer = false;
            algorithm.mother.breastAge = halfsisterMotherAge(response);            
        }
    }

    public static void mapPaternalHalfsister(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            Relative paternalHalfSister = new Relative();
            paternalHalfSister.breastCancer = true;
            paternalHalfSister.breastAge = breastAge(response);
            algorithm.paternalHalfSisters.add(paternalHalfSister);
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            Relative paternalHalfSister = new Relative();
            paternalHalfSister.ovarianCancer = true;
            paternalHalfSister.ovarianAge = ovarianAge(response);
            algorithm.paternalHalfSisters.add(paternalHalfSister);
        }
    }
    
    public static void mapMaternalAunt(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            Parent maternalAunt = new Parent();
            maternalAunt.breastCancer = true;
            maternalAunt.breastAge  = breastAge(response);
            algorithm.maternalAunts.add(maternalAunt);
            algorithm.mother.breastCancer = false;
            algorithm.mother.breastAge = auntMotherAge(response);            
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            Parent maternalAunt = new Parent();
            maternalAunt.ovarianCancer = true;
            maternalAunt.ovarianAge  = ovarianAge(response);
            algorithm.maternalAunts.add(maternalAunt);
            algorithm.mother.breastCancer = false;
            algorithm.mother.breastAge = auntMotherAge(response);  
        }
    }
    
    public static void mapPaternalAunt(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            Parent paternalAunt = new Parent();
            paternalAunt.breastCancer = true;
            paternalAunt.breastAge  = breastAge(response);
            algorithm.paternalAunts.add(paternalAunt);
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            Parent paternalAunt = new Parent();
            paternalAunt.ovarianCancer = true;
            paternalAunt.ovarianAge  = ovarianAge(response);
            algorithm.paternalAunts.add(paternalAunt);
        }
    }
    
    public static void nieceSister(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            Relative niece = new Relative();
            niece.breastCancer = true;
            niece.breastAge = breastAge(response);
            Parent sister = new Parent();
            sister.breastCancer = false;
            sister.breastAge = nieceSisterAge(response);
            sister.daughters.add(niece);
            algorithm.sisters.add(sister);
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            Relative niece = new Relative();
            niece.ovarianCancer = true;
            niece.ovarianAge = ovarianAge(response);
            Parent sister = new Parent();
            sister.breastCancer = false;
            sister.breastAge = nieceSisterAge(response);
            sister.daughters.add(niece);
            algorithm.sisters.add(sister);
        }
    }
    
    public static void nieceBrother(ResponseAccess response, AlgorithmModel algorithm, Cancer cancer) {
        if (cancer == Cancer.BREAST_CANCER) {
            Relative niece = new Relative();
            niece.breastCancer = true;
            niece.breastAge = breastAge(response);
            algorithm.unaffectedBrother.daughters.add(niece);
        }
        else if (cancer == Cancer.OVARIAN_CANCER) {
            Relative niece = new Relative();
            niece.ovarianCancer = true;
            niece.ovarianAge = ovarianAge(response);
            algorithm.unaffectedBrother.daughters.add(niece);
        }
    }   

    private static Integer breastAge(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_BREAST)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_BREAST_AGE)
            .getNumber();
    }

    private static Integer ovarianAge(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_OVARIAN)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_OVARIAN_AGE)
            .getNumber();
    }    

    private static Integer grandmotherMotherAge(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_AFFECTED_GRANDMOTHER)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE)
            .getNumber();
    }

    private static Integer auntMotherAge(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_AFFECTED_AUNT)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_AFFECTED_AUNT_MOTHERS_AGE)
            .getNumber();
    }    

    private static Integer nieceSisterAge(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_AFFECTED_NIECE)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_AFFECTED_NIECE_SISTERS_AGE)
            .getNumber();
    }
    
    private static Integer halfsisterMotherAge(ResponseAccess response) {
        return 
            response
            .section(QuestionSectionIdentifier.FAMILY_AFFECTED_HALF_SISTER)
            .getOnlyGroup()
            .answer(QuestionIdentifier.FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE)
            .getNumber();
    }     
}
