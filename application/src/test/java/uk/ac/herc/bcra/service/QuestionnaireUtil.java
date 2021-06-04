package uk.ac.herc.bcra.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.AnswerItem;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;

@Service
@Transactional
public class QuestionnaireUtil {

    public static String UNKNOWN = "Don't Know";
    public static String BROTHER = "Brother";
    public static String MOTHER = "Mother";
    public static String GRANDMOTHER = "Grandmother";
    public static String SISTER = "Sister";
    public static String HALF_SISTER = "Half-Sister";
    public static String NIECE = "Niece";
    public static String FATHER = "Father";
    public static String AUNT = "Aunt";

    public static String PATERNAL = "Father's Side";
    public static String MATERNAL = "Mother's Side";
    public static String PATERNAL_SISTER = "Father's Sister";
    public static String MATERNAL_SISTER = "Mother's Sister";
    public static String FRATERNAL_DAUGHTER = "Brother's Daughter";
    public static String SORORAL_DAUGHTER = "Sister's Daughter";

    private static String ONE = "One";
    private static String MANY = "More than one";

    public static String YES = "Yes";
    public static String NO = "No";
    public static String DONT_KNOW = "Don't Know";

    public static String DIAG_ALH = "ALH";
    public static String DIAG_LCIS = "LCIS";
    public static String DIAG_ADH = "ADH";
    public static String DIAG_OTHER = "Other";
    
    public void selectFamilyBreastAffected(EntityManager em, AnswerResponse answerResponse, String familyMember, boolean onlyOne, int ageAffected) {
        Answer breastAffected = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_BREAST_AFFECTED);
        for (AnswerItem ai : breastAffected.getAnswerItems()) {
            if (ai.getQuestionItem().getLabel().equals(familyMember)) {
                ai.setSelected(true);
                em.persist(ai);
                break;
            }
        }

        Answer breastAffectedHowMany = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_BREAST_HOW_MANY);
        for (AnswerItem ai : breastAffectedHowMany.getAnswerItems()) {
            if (onlyOne) {
                if (ai.getQuestionItem().getLabel().equals(ONE)) {
                    ai.setSelected(true);
                    em.persist(ai);
                    break;
                }
            } else {
                if (ai.getQuestionItem().getLabel().equals(MANY)) {
                    ai.setSelected(true);
                    em.persist(ai);
                    break;
                }
            }
        }

        if (ageAffected > 0) {
            Answer breastAge = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_BREAST_AGE);
            breastAge.setNumber(ageAffected);
            em.persist(breastAge);
        }

        em.flush();
    }

    public void selectFamilyOvarianAffected(EntityManager em, AnswerResponse answerResponse, String familyMember, boolean onlyOne, int ageAffected) {
        Answer ovarianAffected = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_OVARIAN_AFFECTED);
        for (AnswerItem ai : ovarianAffected.getAnswerItems()) {
            if (ai.getQuestionItem().getLabel().equals(familyMember)) {
                ai.setSelected(true);
                em.persist(ai);
                break;
            }
        }

        Answer ovarianAffectedHowMany = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_OVARIAN_HOW_MANY);
        for (AnswerItem ai : ovarianAffectedHowMany.getAnswerItems()) {
            if (onlyOne) {
                if (ai.getQuestionItem().getLabel().equals(ONE)) {
                    ai.setSelected(true);
                    em.persist(ai);
                    break;
                }
            } else {
                if (ai.getQuestionItem().getLabel().equals(MANY)) {
                    ai.setSelected(true);
                    em.persist(ai);
                    break;
                }
            }
        }

        if (ageAffected > 0) {
            Answer ovarianAge = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_OVARIAN_AGE);
            ovarianAge.setNumber(ageAffected);
            em.persist(ovarianAge);
        }

        em.flush();
    }

    public void selectFamilyMembersAffected(EntityManager em, AnswerResponse answerResponse, String familyMember, boolean maternalSororal) {
        if (familyMember.equals(AUNT)) {
            Answer familySide = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_AFFECTED_AUNT_SIDE);
            for (AnswerItem ai : familySide.getAnswerItems()) {
                if (maternalSororal) {
                    if (ai.getQuestionItem().getLabel().equals(MATERNAL_SISTER)) {
                        ai.setSelected(true);
                        em.persist(ai);
                        break;
                    }
                } else {
                    if (ai.getQuestionItem().getLabel().equals(PATERNAL_SISTER)) {
                        ai.setSelected(true);
                        em.persist(ai);
                        break;
                    }
                }
            }
        } else if (familyMember.equals(GRANDMOTHER)) {
            Answer familySide = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_AFFECTED_GRANDMOTHER_SIDE);
            for (AnswerItem ai : familySide.getAnswerItems()) {
                if (maternalSororal) {
                    if (ai.getQuestionItem().getLabel().equals(MATERNAL)) {
                        ai.setSelected(true);
                        em.persist(ai);
                        break;
                    }
                } else {
                    if (ai.getQuestionItem().getLabel().equals(PATERNAL)) {
                        ai.setSelected(true);
                        em.persist(ai);
                        break;
                    }
                }
            }
        } else if (familyMember.equals(HALF_SISTER)) {
            Answer familySide = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_AFFECTED_HALFSISTER_SIDE);
            for (AnswerItem ai : familySide.getAnswerItems()) {
                if (maternalSororal) {
                    if (ai.getQuestionItem().getLabel().equals(MATERNAL)) {
                        ai.setSelected(true);
                        em.persist(ai);
                        break;
                    }
                } else {
                    if (ai.getQuestionItem().getLabel().equals(PATERNAL)) {
                        ai.setSelected(true);
                        em.persist(ai);
                        break;
                    }
                }
            }
        } else if (familyMember.equals(NIECE)) {
            Answer familySide = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.FAMILY_AFFECTED_NIECE_SIDE);
            for (AnswerItem ai : familySide.getAnswerItems()) {
                if (maternalSororal) {
                    if (ai.getQuestionItem().getLabel().equals(SORORAL_DAUGHTER)) {
                        ai.setSelected(true);
                        em.persist(ai);
                        break;
                    }
                } else {
                    if (ai.getQuestionItem().getLabel().equals(FRATERNAL_DAUGHTER)) {
                        ai.setSelected(true);
                        em.persist(ai);
                        break;
                    }
                }
            }
        }

        em.flush();
    }

    public void setHeightAndWeight(EntityManager em, AnswerResponse answerResponse, int heightCm, int weightKg) {
        Answer height = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_HEIGHT);
        height.setNumber(heightCm);

        Answer weight = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_WEIGHT);
        weight.setNumber(weightKg);

        em.persist(height);
        em.persist(weight);
        em.flush();
    }

    public void setAgeOfFirstPeriod(EntityManager em, AnswerResponse answerResponse, int age) {
        Answer menarch = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_FIRST_PERIOD);
        menarch.setNumber(age);

        em.persist(menarch);
        em.flush();
    }

    public void setThirdTrimesterPregnancies(EntityManager em, AnswerResponse answerResponse, int numPregnancies, int ageOfFirstPregnancy) {
        Answer pregnanices = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_PREGNANCIES);
        pregnanices.setNumber(numPregnancies);
        em.persist(pregnanices);

        if (numPregnancies > 0) {
            Answer age = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_PREGNANCY_FIRST_AGE);
            age.setNumber(ageOfFirstPregnancy);
            em.persist(age);
        }

        em.flush();
    }

    public void setPremenopauseStatus(EntityManager em, AnswerResponse answerResponse, String premenopausal, int menopauseAge) {
        Answer premenopauseAnswer = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_PREMENOPAUSAL);
        for (AnswerItem ai : premenopauseAnswer.getAnswerItems()) {
            if (ai.getQuestionItem().getLabel().equals(premenopausal)) {
                ai.setSelected(true);
                em.persist(ai);
                break;
            }
        }

        if (premenopausal.equals(NO)) {
            Answer menopauseAgeAnswer = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_MENOPAUSAL_AGE);
            menopauseAgeAnswer.setNumber(menopauseAge);
            em.persist(menopauseAgeAnswer);
        }

        em.flush();
    }

    public void setIsAshkenaziJewish(EntityManager em, AnswerResponse answerResponse, String isAshkenaziJewish) {
        Answer ashkenaziJewishAnswer = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_ASHKENAZI);
        for (AnswerItem ai : ashkenaziJewishAnswer.getAnswerItems()) {
            if (ai.getQuestionItem().getLabel().equals(isAshkenaziJewish)) {
                ai.setSelected(true);
                em.persist(ai);
                break;
            }
        }

        em.flush();
    }

    public void setBreastBiopsyStatus(EntityManager em, AnswerResponse answerResponse, String hadBreastBiopsy, String atRisk, String diagnosis) {
        Answer previousBreastBiopsy = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_BREAST_BIOPSY);
        for (AnswerItem ai : previousBreastBiopsy.getAnswerItems()) {
            if (ai.getQuestionItem().getLabel().equals(hadBreastBiopsy)) {
                ai.setSelected(true);
                em.persist(ai);
                break;
            }
        }

        if (hadBreastBiopsy.equals(YES)) {
            Answer breastCancerRisk = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_RISK);
            for (AnswerItem ai : breastCancerRisk.getAnswerItems()) {
                if (ai.getQuestionItem().getLabel().equals(atRisk)) {
                    ai.setSelected(true);
                    em.persist(ai);
                    break;
                }
            }
        }

        if (atRisk.equals(YES)) {
            Answer biopsyDiagnosis = getAnswerByQuestionIdentifier(answerResponse, QuestionIdentifier.SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES);
            for (AnswerItem ai : biopsyDiagnosis.getAnswerItems()) {
                if (ai.getQuestionItem().getLabel().equals(diagnosis)) {
                    ai.setSelected(true);
                    em.persist(ai);
                    break;
                }
            }
        }

        em.flush();
    }

    private Answer getAnswerByQuestionIdentifier(AnswerResponse answerResponse, QuestionIdentifier identifier) {
        for (AnswerSection as : answerResponse.getAnswerSections()) {
            for (AnswerGroup ag : as.getAnswerGroups()) {
                for (Answer a : ag.getAnswers()) {
                    if (a.getQuestion().getIdentifier().equals(identifier)) {
                        return a;
                    }
                }
            }
        }

        return null;
    }
}
