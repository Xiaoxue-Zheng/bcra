package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.repository.StudyIdRepository;

@SpringBootTest(classes = BcraApp.class)
public class StudyIdResourceIT {

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    
    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private StudyIdRepository studyIdRepository;

    @Autowired
    private StudyIdService studyIdService;

    private static int numberOfStudies = 0;
    public static final String DEFAULT_STUDY_CODE_PREFIX = "TST_";

    private StudyId studyId;

    private Participant participant;

    private MockMvc restStudyIdMockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudyIdResource studyIdResource = new StudyIdResource(
            studyIdService
        );
        this.restStudyIdMockMvc = MockMvcBuilders
            .standaloneSetup(studyIdResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator)
            .build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyId createEntity(EntityManager em) {
        StudyId studyId = new StudyId()
            .code(DEFAULT_STUDY_CODE_PREFIX + numberOfStudies);

        numberOfStudies += 1;

        AnswerResponse consentResponse = AnswerResponseResourceIT.createEntity(em, QuestionnaireType.CONSENT_FORM);
        AnswerResponse riskAssessmentResponse = AnswerResponseResourceIT.createEntity(em, QuestionnaireType.RISK_ASSESSMENT);

        studyId.setConsentResponse(consentResponse);
        studyId.setRiskAssessmentResponse(riskAssessmentResponse);

        em.persist(studyId);

        return studyId;
    }

    public static StudyId createEntity(EntityManager em, Participant participant) {
        StudyId studyId = new StudyId()
            .code(DEFAULT_STUDY_CODE_PREFIX + numberOfStudies);
        numberOfStudies += 1;

        AnswerResponse consentResponse = AnswerResponseResourceIT.createEntity(em);
        AnswerResponse riskAssessmentResponse = AnswerResponseResourceIT.createEntity(em);
        studyId.setConsentResponse(consentResponse);
        studyId.setRiskAssessmentResponse(riskAssessmentResponse);
        studyId.setParticipant(participant);

        em.persist(studyId);

        return studyId;
    }

    @BeforeEach
    public void initTest() {
        studyId = createEntity(em);
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }
    }

    @Test
    @Transactional
    public void isStudyIdInUse() throws Exception {
        // Call to see if study id that doesn't exist is available - should be false
        MvcResult result1 = restStudyIdMockMvc.perform(get("/api/study-ids/TST_DOES_NOT_EXIST"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();

        String content1 = result1.getResponse().getContentAsString();
        assertThat(content1).isEqualTo("false");

        // Add the default study id to the database.
        studyIdRepository.saveAndFlush(studyId);

        // Try call again, this time with a study that exists - should be true.
        MvcResult result2 = restStudyIdMockMvc.perform(get("/api/study-ids/" + studyId.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();

        String content2 = result2.getResponse().getContentAsString();
        assertThat(content2).isEqualTo("true");

        // Add an assigned study id to the database.
        studyId.setParticipant(participant);

        studyIdRepository.saveAndFlush(studyId);

        // Try to call a final time, this time study is assigned - should be false.
        MvcResult result3 = restStudyIdMockMvc.perform(get("/api/study-ids/" + studyId.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();

        String content3 = result3.getResponse().getContentAsString();
        assertThat(content3).isEqualTo("false");
    }

    @Test
    @Transactional
    public void getStudyCode() throws Exception {
        // Add an assigned study id to the database.
        StudyId studyIdAssigned = createEntity(em);
        studyIdAssigned.setParticipant(participant);

        studyIdRepository.saveAndFlush(studyIdAssigned);

        MvcResult result = restStudyIdMockMvc.perform(
            get("/api/study-ids/current")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andReturn();

        String returnedStudyCode = result.getResponse().getContentAsString();
        assertThat(returnedStudyCode).isEqualTo("\"" + studyIdAssigned.getCode() + "\"");
    }
}
