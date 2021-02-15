package uk.ac.herc.bcra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.questionnaire.AnswerResponseGenerator;

@Service
public class DataGenerationService {

    private final Logger log = LoggerFactory.getLogger(DataGenerationService.class);

    @Autowired
    private StudyIdService studyIdService;

    @Autowired
    private AnswerResponseGenerator answerResponseGenerator;
	
	public void generateTestData() {
        // TODO: Tidy up as part of later tickets (CLIN-1140 and CLIN-1005)
		log.info("Adding test data...");

		try {
            AnswerResponse consentForm = answerResponseGenerator.generateAnswerResponseToQuestionnaire(
                QuestionnaireType.CONSENT_FORM
            );

            AnswerResponse riskAssessment = answerResponseGenerator.generateAnswerResponseToQuestionnaire(
                QuestionnaireType.RISK_ASSESSMENT
            );

            StudyId studyId = new StudyId();
            studyId.setCode("TST_1");
            studyId.setConsentResponse(consentForm);
            studyId.setRiskAssessmentResponse(riskAssessment);
            studyIdService.save(studyId);

		} catch (Exception e) {
			log.error("Problem adding test data.", e);
		}

		log.info("Finished adding test data.");
	}

}

