package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.repository.AnswerResponseRepository;
import uk.ac.herc.bcra.service.AnswerResponseService;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;
import uk.ac.herc.bcra.service.mapper.AnswerResponseMapper;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.Principal;
import java.util.List;

import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
/**
 * Integration tests for the {@link AnswerResponseResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerResponseResourceIT {

    private static final ResponseState DEFAULT_STATE = ResponseState.PROCESSED;
    private static final ResponseState UPDATED_STATE = ResponseState.INVALID;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";
    private static final Integer UPDATED_ANSWER_VALUE = 54321;

    @Autowired
    private AnswerResponseRepository answerResponseRepository;

    @Autowired
    private AnswerResponseMapper answerResponseMapper;

    @Autowired
    private AnswerResponseService answerResponseService;

    @Autowired
    private StudyIdService studyIdService;
    
    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAnswerResponseMockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnswerResponseResource answerResponseResource = new AnswerResponseResource(
            answerResponseService, studyIdService
        );
        this.restAnswerResponseMockMvc = MockMvcBuilders
            .standaloneSetup(answerResponseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerResponse createEntity(EntityManager em) {
        AnswerResponse answerResponse = new AnswerResponse()
            .state(DEFAULT_STATE)
            .status(DEFAULT_STATUS);
        // Add required entity
        Questionnaire questionnaire;
        if (TestUtil.findAll(em, Questionnaire.class).isEmpty()) {
            questionnaire = QuestionnaireResourceIT.createEntity(em);
            em.persist(questionnaire);
            em.flush();
        } else {
            questionnaire = TestUtil.findAll(em, Questionnaire.class).get(0);
        }
        answerResponse.setQuestionnaire(questionnaire);
        em.persist(answerResponse);
        em.flush();

        answerResponse.addAnswerSection(
            AnswerSectionResourceIT.createEntity(em)
        );
        return answerResponse;
    }

    public static AnswerResponse createEntity(EntityManager em, QuestionnaireType type) {
        AnswerResponse answerResponse = new AnswerResponse()
            .state(DEFAULT_STATE)
            .status(DEFAULT_STATUS);
        // Add required entity
        Questionnaire questionnaire = QuestionnaireResourceIT.createEntity(em, type);
        em.persist(questionnaire);
        em.flush();

        answerResponse.setQuestionnaire(questionnaire);
        em.persist(answerResponse);
        em.flush();

        answerResponse.addAnswerSection(
            AnswerSectionResourceIT.createEntity(em)
        );
        return answerResponse;
    }



    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerResponse createUpdatedEntity(EntityManager em) {
        AnswerResponse answerResponse = new AnswerResponse()
            .state(UPDATED_STATE)
            .status(UPDATED_STATUS);
        // Add required entity
        Questionnaire questionnaire;
        if (TestUtil.findAll(em, Questionnaire.class).isEmpty()) {
            questionnaire = QuestionnaireResourceIT.createUpdatedEntity(em);
            em.persist(questionnaire);
            em.flush();
        } else {
            questionnaire = TestUtil.findAll(em, Questionnaire.class).get(0);
        }
        answerResponse.setQuestionnaire(questionnaire);
        return answerResponse;
    }

    @BeforeEach
    public void initTest() {
        createEntity(em);
    }

    @Test
    @Transactional
    public void getConsentAnswerResponseEmptyResult() throws Exception {
        
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        MvcResult result = restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/consent/TST_1")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andReturn();
        
        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("");
    }

    @Test
    @Transactional
    public void getConsentAnswerResponseValidResult() throws Exception {
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        StudyId studyId = StudyIdResourceIT.createEntity(em, participant);

        MvcResult result = restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/consent/" + studyId.getCode())
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();
        
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        AnswerResponseDTO response = mapper.readValue(content, AnswerResponseDTO.class);
        assertThat(response.getQuestionnaireId()).isNotNull();
        assertThat(response.getAnswerSections()).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getAnswerSections().size() > 0).isEqualTo(true);
    }

    @Test
    @Transactional
    public void getRiskAssessmentAnswerResponseEmptyResult() throws Exception {
        
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        MvcResult result = restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/risk-assessment/TST_1")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andReturn();
        
        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("");
    }

    @Test
    @Transactional
    public void getRiskAssessmentAnswerResponseValidResult() throws Exception {
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        StudyId studyId = StudyIdResourceIT.createEntity(em, participant);

        MvcResult result = restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/risk-assessment/" + studyId.getCode())
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andReturn();
        
        String content = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        AnswerResponseDTO response = mapper.readValue(content, AnswerResponseDTO.class);
        assertThat(response.getQuestionnaireId()).isNotNull();
        assertThat(response.getAnswerSections()).isNotNull();
        assertThat(response.getId()).isNotNull();
        assertThat(response.getAnswerSections().size() > 0).isEqualTo(true);
    }

    @Test
    @Transactional
    public void saveConsentAnswerResponse() throws Exception {

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse = 
            answerResponseRepository.findById(
                participant.getProcedure().getConsentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(UPDATED_STATE)
            .status(UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(UPDATED_ANSWER_VALUE);

        // Save
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);
        restAnswerResponseMockMvc.perform(
            put("/api/answer-responses/consent/save")
            .principal(new Principal() {
                @Override
                public String getName() {
                    return participant.getUser().getLogin();
                }
            })
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO)))
            .andExpect(status().isOk());

        // Validate the AnswerResponse in the database
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeUpdate);

        // Check that State and Status cannot be modified via API
        AnswerResponse testAnswerResponse = 
            answerResponseRepository.getOne(participant.getProcedure().getConsentResponse().getId());

        assertThat(testAnswerResponse.getState()).isNotEqualTo(UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(UPDATED_STATUS);

        // Check State and Updated Answer
        assertThat(testAnswerResponse.getState()).isEqualTo(ResponseState.IN_PROGRESS);
        assertThat(
            testAnswerResponse
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .getNumber()
        ).isEqualTo(UPDATED_ANSWER_VALUE);
    }

    @Test
    @Transactional
    public void submitConsentAnswerResponse() throws Exception {

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse = 
            answerResponseRepository.findById(
                participant.getProcedure().getConsentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(UPDATED_STATE)
            .status(UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(UPDATED_ANSWER_VALUE);

        // Save
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);
        restAnswerResponseMockMvc.perform(
            put("/api/answer-responses/consent/submit")
            .principal(new Principal() {
                @Override
                public String getName() {
                    return participant.getUser().getLogin();
                }
            })
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO)))
            .andExpect(status().isOk());

        // Validate the AnswerResponse in the database
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeUpdate);

        // Check that State and Status cannot be modified via API
        AnswerResponse testAnswerResponse = 
            answerResponseRepository.getOne(participant.getProcedure().getConsentResponse().getId());

        assertThat(testAnswerResponse.getState()).isNotEqualTo(UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(UPDATED_STATUS);

        // Check State and Updated Answer
        assertThat(testAnswerResponse.getState()).isEqualTo(ResponseState.SUBMITTED);
        assertThat(
            testAnswerResponse
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .getNumber()
        ).isEqualTo(UPDATED_ANSWER_VALUE);
    }

    @Test
    @Transactional
    public void saveRiskAssessmentAnswerResponse() throws Exception {

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse = 
            answerResponseRepository.findById(
                participant.getProcedure().getRiskAssessmentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(UPDATED_STATE)
            .status(UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(UPDATED_ANSWER_VALUE);

        // Save
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);
        restAnswerResponseMockMvc.perform(
            put("/api/answer-responses/risk-assessment/save")
            .principal(new Principal() {
                @Override
                public String getName() {
                    return participant.getUser().getLogin();
                }
            })
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO)))
            .andExpect(status().isOk());

        // Validate the AnswerResponse in the database
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeUpdate);

        // Check that State and Status cannot be modified via API
        AnswerResponse testAnswerResponse = 
            answerResponseRepository.getOne(participant.getProcedure().getRiskAssessmentResponse().getId());

        assertThat(testAnswerResponse.getState()).isNotEqualTo(UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(UPDATED_STATUS);

        // Check State and Updated Answer
        assertThat(testAnswerResponse.getState()).isEqualTo(ResponseState.IN_PROGRESS);
        assertThat(
            testAnswerResponse
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .getNumber()
        ).isEqualTo(UPDATED_ANSWER_VALUE);
    }

    
    @Test
    @Transactional
    public void submitRiskAssessmentAnswerResponse() throws Exception {

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse = 
            answerResponseRepository.findById(
                participant.getProcedure().getRiskAssessmentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(UPDATED_STATE)
            .status(UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(UPDATED_ANSWER_VALUE);

        // Save
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);
        restAnswerResponseMockMvc.perform(
            put("/api/answer-responses/risk-assessment/submit")
            .principal(new Principal() {
                @Override
                public String getName() {
                    return participant.getUser().getLogin();
                }
            })
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO)))
            .andExpect(status().isOk());

        // Validate the AnswerResponse in the database
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeUpdate);

        // Check that State and Status cannot be modified via API
        AnswerResponse testAnswerResponse = 
            answerResponseRepository.getOne(participant.getProcedure().getRiskAssessmentResponse().getId());

        assertThat(testAnswerResponse.getState()).isNotEqualTo(UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(UPDATED_STATUS);

        // Check State and Updated Answer
        assertThat(testAnswerResponse.getState()).isEqualTo(ResponseState.VALIDATED);
        assertThat(
            testAnswerResponse
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .getNumber()
        ).isEqualTo(UPDATED_ANSWER_VALUE);
    }
}
