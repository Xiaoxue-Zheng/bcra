package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerResponse;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AnswerResponseResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerResponseResourceIT {

    @Autowired
    private AnswerResponseRepository answerResponseRepository;

    @Autowired
    private AnswerResponseMapper answerResponseMapper;

    @Autowired
    private AnswerResponseService answerResponseService;

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
        final AnswerResponseResource answerResponseResource = new AnswerResponseResource(answerResponseService);
        this.restAnswerResponseMockMvc = MockMvcBuilders.standaloneSetup(answerResponseResource)
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
        AnswerResponse answerResponse = new AnswerResponse();
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
        AnswerResponse answerResponse = new AnswerResponse();
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
    public void createAnswerResponse() throws Exception {
        int databaseSizeBeforeCreate = answerResponseRepository.findAll().size();

        // Create the AnswerResponse
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(answerResponse);
        restAnswerResponseMockMvc.perform(post("/api/answer-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO)))
            .andExpect(status().isCreated());

        // Validate the AnswerResponse in the database
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    @Transactional
    public void createAnswerResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = answerResponseRepository.findAll().size();

        // Create the AnswerResponse with an existing ID
        answerResponse.setId(1L);
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(answerResponse);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerResponseMockMvc.perform(post("/api/answer-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerResponse in the database
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnswerResponses() throws Exception {
        // Initialize the database
        answerResponseRepository.saveAndFlush(answerResponse);

        // Get all the answerResponseList
        restAnswerResponseMockMvc.perform(get("/api/answer-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerResponse.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getAnswerResponse() throws Exception {
        // Initialize the database
        answerResponseRepository.saveAndFlush(answerResponse);

        // Get the answerResponse
        restAnswerResponseMockMvc.perform(get("/api/answer-responses/{id}", answerResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(answerResponse.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAnswerResponse() throws Exception {
        // Get the answerResponse
        restAnswerResponseMockMvc.perform(get("/api/answer-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
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
        AnswerResponseDTO answerResponseDTO = answerResponseMapper.toDto(updatedAnswerResponse);

        restAnswerResponseMockMvc.perform(put("/api/answer-responses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerResponseDTO)))
            .andExpect(status().isOk());

        // Validate the AnswerResponse in the database
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeUpdate);
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
    public void deleteAnswerResponse() throws Exception {
        // Initialize the database
        answerResponseRepository.saveAndFlush(answerResponse);

        int databaseSizeBeforeDelete = answerResponseRepository.findAll().size();

        // Delete the answerResponse
        restAnswerResponseMockMvc.perform(delete("/api/answer-responses/{id}", answerResponse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnswerResponse> answerResponseList = answerResponseRepository.findAll();
        assertThat(answerResponseList).hasSize(databaseSizeBeforeDelete - 1);
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
