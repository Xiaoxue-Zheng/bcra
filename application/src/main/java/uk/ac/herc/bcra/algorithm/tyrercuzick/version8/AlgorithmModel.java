package uk.ac.herc.bcra.algorithm.tyrercuzick.version8;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class AlgorithmModel {

    public enum MenopausalStatus {
        PRE_MENOPAUSAL,
        PERI_MENOPAUSAL,
        POST_MENOPAUSAL
    }

    public enum UseHRT {
        NEVER,
        OVER_FIVE_YEARS_AGO,
        UNDER_FIVE_YEARS_AGO,
        CURRENT_USER
    }

    public enum TypeOfHRT {
        OESTROGEN,
        COMBINED
    }  
    
    public enum GeneticTest {
        NEVER,
        NEGATIVE,
        BRCA1,
        BRCA2,
    }

    public enum BreastDensityMeasure {
        VOLPARA,
        VAS,
        BIRADS_ATLAS
    }

    public enum HyperplasiaHistory {
        HAS_NO_HISTORY,
        HAS_HISTORY,
        HISTORY_UNKNOWN
    }

    public AlgorithmModel(String participantIdentifier) {
        self.participantIdentifier = participantIdentifier;
    }

    public static class Self {
        public String participantIdentifier = "";
        public Integer ageInYears = null;
        public Integer firstPeriodAge = null;
        public Integer liveBirths = null;
        public Integer firstBirthAge = null;
        public MenopausalStatus menopausalStatus = null;
        public Integer menopauseAge = null;
        public Float heightMeters = null;
        public Integer weightKilos = null;
        public HyperplasiaHistory hyperplasiaHistory = null;
        public Boolean atypicalHyperplasia = null;
        public Boolean lcisHistory = null;
        public Boolean ovarianCancer = null;
        public Integer ovarianAge = null;
        public Boolean ashkanaziJewish = null;
        public UseHRT useHRT = null;
        public TypeOfHRT typeOfHRT = null;
        public Float yearsHRT = null;
        public Float futureHRT = null;
        public Float sinceHRT = null;
        public GeneticTest geneticTest = null;
        public GeneticTest geneticTestFather = null;
    }

    public static class Relative {
        public Boolean breastCancer = null;
        public Boolean bilateral = null;
        public Boolean ovarianCancer = null;
        public Integer breastAge = null;
        public Integer bilateralAge = null;
        public Integer ovarianAge = null;
        public GeneticTest geneticTest = null;
    }

    public static class Parent extends Relative {
        public List<Relative> daughters = new ArrayList<Relative>();
    }    

    public static class MaleParent {
        public List<Relative> daughters = new ArrayList<Relative>();
    }

    public static class MaleRelative {
        public Boolean breastCancer = null;
    }

    public static class BreastDensity {
        public BreastDensityMeasure measure = null;
        public Float value = null;
    }

    public class PolygenicSNP {
        public Float score;
    }

    public Self self = new Self();

    public Relative mother = new Relative();
    public Relative paternalGrandmother = new Relative();
    public Relative maternalGrandmother = new Relative();

    public List<Parent> sisters = new ArrayList<Parent>();
    public List<Parent> paternalAunts = new ArrayList<Parent>();
    public List<Parent> maternalAunts = new ArrayList<Parent>();
    public List<Relative> daughters = new ArrayList<Relative>();

    public MaleParent unaffectedBrother = new MaleParent();

    public List<Relative> paternalHalfSisters = new ArrayList<Relative>();
    public List<Relative> maternalHalfSisters = new ArrayList<Relative>();

    public MaleParent unaffectedPaternalUncle = new MaleParent();
    public MaleParent unaffectedMaternalUncle = new MaleParent();    

    public MaleRelative father = new MaleRelative();
    public MaleRelative brother = new MaleRelative();
    public BreastDensity breastDensity = new BreastDensity();
    public PolygenicSNP polygenicSNP = new PolygenicSNP();

    public void setDateOfBirth(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        self.ageInYears = Period.between(dateOfBirth, now).getYears();
    }
}