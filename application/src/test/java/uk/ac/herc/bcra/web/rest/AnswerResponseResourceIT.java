package uk.ac.herc.bcra.web.rest;

import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.repository.AnswerResponseRepository;
import uk.ac.herc.bcra.security.RoleManager;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uk.ac.herc.bcra.domain.enumeration.ResponseState;
/**
 * Integration tests for the {@link AnswerResponseResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerResponseResourceIT {

    @Autowired
    private WebApplicationContext context;

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

    private MockMvc securityRestMvc;

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
        this.securityRestMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }


    @BeforeEach
    public void initTest() {
        DataFactory.createAnswerResponse(em);
    }

    @Test
    @Transactional
    public void getConsentAnswerResponseEmptyResult() throws Exception {

        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em);
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
            participant = DataFactory.createUpdatedParticipant(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        StudyId studyId = DataFactory.createStudyId(em, participant);

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
            participant = DataFactory.createUpdatedParticipant(em);
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
            participant = DataFactory.createUpdatedParticipant(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        StudyId studyId = DataFactory.createStudyId(em, participant);

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
    @WithMockUser(value = "getUnauthorizedGetRiskAssessmentAnswer", authorities = {RoleManager.USER, RoleManager.MANAGER, RoleManager.ADMIN})
    public void getUnauthorizedGetRiskAssessmentAnswer() throws Exception {
        String login = "getUnauthorizedGetRiskAssessmentAnswer";
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em, login);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        StudyId studyId = DataFactory.createStudyId(em, participant);

        MvcResult result = securityRestMvc.perform(
            get("/api/answer-responses/risk-assessment/" + studyId.getCode())
            .with(csrf()))
            .andExpect(status().is(403))
            .andReturn();
    }

    @Test
    @Transactional
    public void saveConsentAnswerResponse() throws Exception {

        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse =
            answerResponseRepository.findById(
                participant.getProcedure().getConsentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(DataFactory.UPDATED_STATE)
            .status(DataFactory.UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(DataFactory.UPDATED_ANSWER_VALUE);

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

        assertThat(testAnswerResponse.getState()).isNotEqualTo(DataFactory.UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(DataFactory.UPDATED_STATUS);

        // Check State and Updated Answer
        assertThat(testAnswerResponse.getState()).isEqualTo(ResponseState.IN_PROGRESS);
        assertThat(
            testAnswerResponse
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .getNumber()
        ).isEqualTo(DataFactory.UPDATED_ANSWER_VALUE);
    }

    @Test
    @Transactional
    public void submitConsentAnswerResponse() throws Exception {

        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse =
            answerResponseRepository.findById(
                participant.getProcedure().getConsentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(DataFactory.UPDATED_STATE)
            .status(DataFactory.UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(DataFactory.UPDATED_ANSWER_VALUE);

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

        assertThat(testAnswerResponse.getState()).isNotEqualTo(DataFactory.UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(DataFactory.UPDATED_STATUS);

        // Check State and Updated Answer
        assertThat(testAnswerResponse.getState()).isEqualTo(ResponseState.SUBMITTED);
        assertThat(
            testAnswerResponse
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .getNumber()
        ).isEqualTo(DataFactory.UPDATED_ANSWER_VALUE);
    }

    @Test
    @Transactional
    public void saveRiskAssessmentAnswerResponse() throws Exception {

        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse =
            answerResponseRepository.findById(
                participant.getProcedure().getRiskAssessmentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(DataFactory.UPDATED_STATE)
            .status(DataFactory.UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(DataFactory.UPDATED_ANSWER_VALUE);

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

        assertThat(testAnswerResponse.getState()).isNotEqualTo(DataFactory.UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(DataFactory.UPDATED_STATUS);

        // Check State and Updated Answer
        assertThat(testAnswerResponse.getState()).isEqualTo(ResponseState.IN_PROGRESS);
        assertThat(
            testAnswerResponse
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .getNumber()
        ).isEqualTo(DataFactory.UPDATED_ANSWER_VALUE);
    }

    @Test
    @Transactional
    @WithMockUser(value = "unauthorizedSaveRiskAssessmentAnswerResponse", authorities = {RoleManager.USER, RoleManager.MANAGER, RoleManager.ADMIN})
    public void unauthorizedSaveRiskAssessmentAnswerResponse() throws Exception {
        // Initialize the database
        Participant participant;
        String login = "unauthorizedSaveRiskAssessmentAnswerResponse";
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em, login);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse =
            answerResponseRepository.findById(
                participant.getProcedure().getRiskAssessmentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(DataFactory.UPDATED_STATE)
            .status(DataFactory.UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(DataFactory.UPDATED_ANSWER_VALUE);

        // Save
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);
        securityRestMvc.perform(
            put("/api/answer-responses/risk-assessment/save")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO))
                .with(csrf()))
            .andExpect(status().is(403));

    }

    @Test
    @Transactional
    public void submitRiskAssessmentAnswerResponse() throws Exception {

        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse =
            answerResponseRepository.findById(
                participant.getProcedure().getRiskAssessmentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(DataFactory.UPDATED_STATE)
            .status(DataFactory.UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(DataFactory.UPDATED_ANSWER_VALUE);

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

        assertThat(testAnswerResponse.getState()).isNotEqualTo(DataFactory.UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(DataFactory.UPDATED_STATUS);

        // Check State and Updated Answer
        assertThat(testAnswerResponse.getState()).isEqualTo(ResponseState.VALIDATED);
        assertThat(
            testAnswerResponse
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .getNumber()
        ).isEqualTo(DataFactory.UPDATED_ANSWER_VALUE);
    }

    @Test
    @Transactional
    @WithMockUser(value = "unauthorizedSubmitRiskAssessmentAnswerResponse", authorities = {RoleManager.USER, RoleManager.MANAGER, RoleManager.ADMIN})
    public void unauthorizedSubmitRiskAssessmentAnswerResponse() throws Exception {
        String login = "unauthorizedSubmitRiskAssessmentAnswerResponse";
        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em, login);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse =
            answerResponseRepository.findById(
                participant.getProcedure().getRiskAssessmentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(DataFactory.UPDATED_STATE)
            .status(DataFactory.UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(DataFactory.UPDATED_ANSWER_VALUE);

        // Save
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);
        securityRestMvc.perform(
            put("/api/answer-responses/risk-assessment/submit")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO))
                .with(csrf()))
            .andExpect(status().is(403));
    }

    @Test
    @Transactional
    public void referralRiskAssessmentAnswerResponse() throws Exception {

        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse =
            answerResponseRepository.findById(
                participant.getProcedure().getRiskAssessmentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(DataFactory.UPDATED_STATE)
            .status(DataFactory.UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(DataFactory.UPDATED_ANSWER_VALUE);

        // Save
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);
        restAnswerResponseMockMvc.perform(
            put("/api/answer-responses/risk-assessment/referral")
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

        assertThat(testAnswerResponse.getState()).isNotEqualTo(DataFactory.UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(DataFactory.UPDATED_STATUS);

        // Check State and Updated Answer
        assertThat(testAnswerResponse.getState()).isEqualTo(ResponseState.REFERRED);
        assertThat(
            testAnswerResponse
                .getAnswerSections().iterator().next()
                .getAnswerGroups().iterator().next()
                .getAnswers().iterator().next()
                .getNumber()
        ).isEqualTo(DataFactory.UPDATED_ANSWER_VALUE);
    }

    @Test
    @Transactional
    @WithMockUser(value = "unauthorizedReferralRiskAssessmentAnswerResponse", authorities = {RoleManager.USER, RoleManager.MANAGER, RoleManager.ADMIN})
    public void unauthorizedReferralRiskAssessmentAnswerResponse() throws Exception {
        String login = "unauthorizedReferralRiskAssessmentAnswerResponse";
        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = DataFactory.createUpdatedParticipant(em, login);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse =
            answerResponseRepository.findById(
                participant.getProcedure().getRiskAssessmentResponse().getId()
            ).get();

        em.detach(updatedAnswerResponse);

        updatedAnswerResponse
            .state(DataFactory.UPDATED_STATE)
            .status(DataFactory.UPDATED_STATUS)
            .getAnswerSections().iterator().next()
            .getAnswerGroups().iterator().next()
            .getAnswers().iterator().next()
            .setNumber(DataFactory.UPDATED_ANSWER_VALUE);

        // Save
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);
        securityRestMvc.perform(
            put("/api/answer-responses/risk-assessment/referral")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO))
                .with(csrf()))
            .andExpect(status().is(403));

    }
}
