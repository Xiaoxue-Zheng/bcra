package uk.ac.herc.bcra.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;

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
import uk.ac.herc.bcra.domain.Procedure;
import uk.ac.herc.bcra.domain.Risk;
import uk.ac.herc.bcra.domain.RiskAssessmentResult;
import uk.ac.herc.bcra.domain.RiskFactor;
import uk.ac.herc.bcra.domain.User;
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

        Procedure pr = new Procedure();
        pr.setConsentResponse(cr);
        pr.setRiskAssessmentResponse(rar);

        em.persist(pr);

        Participant p = new Participant();
        p.setUser(u);
        p.setIdentifiableData(id);
        p.setProcedure(pr);
        p.dateOfBirth(LocalDate.of(1990, 9, 15));
        pr.setParticipant(p);

        em.persist(p);
        em.persist(pr);

        em.flush();

        return p;
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

        Set<RiskFactor> riskFactors = new HashSet<RiskFactor>();
        for (int i = 0; i < 20; i++) {
            RiskFactor rf = createRiskFactorForRisk(em, risk);
            riskFactors.add(rf);
        }

        risk.setRiskFactors(riskFactors);

        em.persist(risk);
        return risk;
    }

    private RiskFactor createRiskFactorForRisk(EntityManager em, Risk risk) {
        Random random = new Random();

        RiskFactor riskFactor = new RiskFactor();
        riskFactor.setRisk(risk);
        riskFactor.setFactor(random.nextDouble());

        em.persist(riskFactor);
        return riskFactor;
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
}
