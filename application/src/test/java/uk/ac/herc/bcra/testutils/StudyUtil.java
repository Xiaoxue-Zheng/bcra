package uk.ac.herc.bcra.testutils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.AnswerItem;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Risk;
import uk.ac.herc.bcra.domain.RiskAssessmentResult;
import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.domain.YearlyRisk;
import uk.ac.herc.bcra.domain.enumeration.ParticipantContactWay;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
import uk.ac.herc.bcra.questionnaire.AnswerResponseGenerator;

@Service
@Transactional
public class StudyUtil {
    @Autowired
    private AnswerResponseGenerator answerResponseGenerator;

    public Participant createParticipant(EntityManager em, String identifier, LocalDate dateOfBirth) {
        User u = new User();
        u.setLogin(identifier);
        u.setPassword("PASSWORDPASSWORDPASSWORDPASSWORDPASSWORDPASSWORDPASSWORDPASS");
        u.setEmail(identifier + "@" + RandomStringUtils.randomAlphabetic(5));
        u.setActivated(true);

        em.persist(u);

        IdentifiableData id = new IdentifiableData();
        id.setDateOfBirth(dateOfBirth);
        id.setFirstname("FIRSTNAME");
        id.setSurname("SURNAME");
        id.setAddress1("ADDRESS_1");
        id.setPostcode("POSTCODE");
        id.setPreferContactWay(ParticipantContactWay.calculateCodes(Arrays.asList(ParticipantContactWay.SMS)));
        em.persist(id);

        AnswerResponse cr = answerResponseGenerator.generateAnswerResponseToQuestionnaire(QuestionnaireType.CONSENT_FORM);
        AnswerResponse rar = answerResponseGenerator.generateAnswerResponseToQuestionnaire(QuestionnaireType.RISK_ASSESSMENT);
        rar.setState(ResponseState.VALIDATED);

        em.persist(cr);
        em.persist(rar);

        StudyId studyId = new StudyId();
        studyId.setCode(identifier);
        studyId.setConsentResponse(cr);
        studyId.setRiskAssessmentResponse(rar);
        em.persist(studyId);

        Participant p = new Participant();
        p.setUser(u);
        p.setIdentifiableData(id);
        p.dateOfBirth(LocalDate.of(1990, 9, 15));
        p.setStatus(ResponseState.IN_PROGRESS.name());
        p.setStudyId(studyId);
        studyId.setParticipant(p);
        em.persist(studyId);
        em.persist(p);

        em.flush();

        return p;
    }

    public void answerQuestionnaireWithAnything(EntityManager em, AnswerResponse res) {
        for (AnswerSection as : res.getAnswerSections()) {
            for (AnswerGroup ag : as.getAnswerGroups()) {
                for (Answer a : ag.getAnswers()) {
                    a.setTicked(true);
                    a.setNumber(3);
                    for (AnswerItem ai : a.getAnswerItems()) {
                        ai.setSelected(true);
                        break;
                    }
                }
            }
        }
    }

    public RiskAssessmentResult createRiskAssessmentResultForParticipant(EntityManager em, Participant participant) {
        RiskAssessmentResult rar = new RiskAssessmentResult();
        String userId = participant.getUser().getLogin();
        rar.setFilename("tcResult" + userId + ".txt");
        rar.setIndividualRisk(createRisk(em));
        rar.setPopulationRisk(createRisk(em));
        rar.setParticipant(participant);

        em.persist(rar);
        em.flush();
        return rar;
    }

    private Risk createRisk(EntityManager em) {
        Random random = new Random();

        Risk risk = new Risk();
        risk.setLifetimeRisk(random.nextDouble());
        risk.setProbBcra1(random.nextDouble());
        risk.setProbBcra2(random.nextDouble());
        risk.setProbNotBcra(random.nextDouble());

        Set<YearlyRisk> yearlyRisks = new HashSet<YearlyRisk>();
        for (int i = 0; i < 20; i++) {
            YearlyRisk rf = createRiskFactorForRisk(em, risk, i+1);
            yearlyRisks.add(rf);
        }

        risk.setYearlyRisks(yearlyRisks);

        em.persist(risk);
        return risk;
    }

    private YearlyRisk createRiskFactorForRisk(EntityManager em, Risk risk, int year) {
        Random random = new Random();

        YearlyRisk yearlyRisk = new YearlyRisk();
        yearlyRisk.setRisk(risk);
        yearlyRisk.setRiskFactor(random.nextDouble());
        yearlyRisk.setYear(year);

        em.persist(yearlyRisk);
        return yearlyRisk;
    }
}
