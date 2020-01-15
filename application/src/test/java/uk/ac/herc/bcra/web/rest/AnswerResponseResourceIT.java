package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.repository.AnswerResponseRepository;
import uk.ac.herc.bcra.service.AnswerResponseService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

import java.security.Principal;
import java.util.List;

import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uk.ac.herc.bcra.domain.enumeration.ResponseState;
import uk.ac.herc.bcra.questionnaire.AnswerResponseGenerator;
/**
 * Integration tests for the {@link AnswerResponseResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerResponseResourceIT {

    private static final ResponseState DEFAULT_STATE = ResponseState.PROCESSED;
    private static final ResponseState UPDATED_STATE = ResponseState.INVALID;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private AnswerResponseRepository answerResponseRepository;

    @Autowired
    private AnswerResponseMapper answerResponseMapper;

    @Autowired
    private AnswerResponseService answerResponseService;

    @Autowired
    private AnswerResponseGenerator answerResponseGenerator;
    
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

    private AnswerResponse answerResponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnswerResponseResource answerResponseResource = new AnswerResponseResource(
            answerResponseService,
            answerResponseGenerator,
            answerResponseMapper
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
        answerResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void getConsentAnswerResponse() throws Exception {
        
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/consent")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.questionnaireId").value(
                participant
                    .getProcedure()
                    .getConsentResponse()
                    .getQuestionnaire()
                    .getId()
                    .intValue()
            ));
    }

    @Test
    @Transactional
    public void getRiskAssesmentAnswerResponse() throws Exception {
        
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        restAnswerResponseMockMvc.perform(
                get("/api/answer-responses/questionnaire")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.questionnaireId").value(
                participant
                    .getProcedure()
                    .getRiskAssesmentResponse()
                    .getQuestionnaire()
                    .getId()
                    .intValue()
            ));
    }

    @Test
    @Transactional
    public void updateAnswerResponse() throws Exception {
        // Initialize the database
        answerResponseRepository.saveAndFlush(answerResponse);

        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Update the answerResponse
        AnswerResponse updatedAnswerResponse = answerResponseRepository.findById(answerResponse.getId()).get();
        // Disconnect from session so that the updates on updatedAnswerResponse are not directly saved in db
        em.detach(updatedAnswerResponse);
        updatedAnswerResponse
            .state(UPDATED_STATE)
            .status(UPDATED_STATUS);
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);

        restAnswerResponseMockMvc.perform(put("/api/answer-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO)))
            .andExpect(status().isOk());

        // Validate the AnswerResponse in the database
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeUpdate);
        AnswerResponse testAnswerResponse = answerResponseList.get(answerResponseList.size() - 1);

        // Check that State and Status cannot be modified via API
        assertThat(testAnswerResponse.getState()).isNotEqualTo(UPDATED_STATE);
        assertThat(testAnswerResponse.getStatus()).isNotEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingAnswerResponse() throws Exception {
        int databaseSizeBeforeUpdate = answerResponseRepository.findAll().size();

        // Create the AnswerResponse
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(answerResponse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerResponseMockMvc.perform(put("/api/answer-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerResponse in the database
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerResponse.class);
        AnswerResponse answerResponse1 = new AnswerResponse();
        answerResponse1.setId(1L);
        AnswerResponse answerResponse2 = new AnswerResponse();
        answerResponse2.setId(answerResponse1.getId());
        assertThat(answerResponse1).isEqualTo(answerResponse2);
        answerResponse2.setId(2L);
        assertThat(answerResponse1).isNotEqualTo(answerResponse2);
        answerResponse1.setId(null);
        assertThat(answerResponse1).isNotEqualTo(answerResponse2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerResponseDTO.class);
        AnswerResponseDTO answerResponseDTO1 = new AnswerResponseDTO();
        answerResponseDTO1.setId(1L);
        AnswerResponseDTO answerResponseDTO2 = new AnswerResponseDTO();
        assertThat(answerResponseDTO1).isNotEqualTo(answerResponseDTO2);
        answerResponseDTO2.setId(answerResponseDTO1.getId());
        assertThat(answerResponseDTO1).isEqualTo(answerResponseDTO2);
        answerResponseDTO2.setId(2L);
        assertThat(answerResponseDTO1).isNotEqualTo(answerResponseDTO2);
        answerResponseDTO1.setId(null);
        assertThat(answerResponseDTO1).isNotEqualTo(answerResponseDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(answerResponseMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(answerResponseMapper.fromId(null)).isNull();
    }
}
