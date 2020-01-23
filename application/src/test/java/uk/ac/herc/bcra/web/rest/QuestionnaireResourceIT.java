package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.repository.QuestionnaireRepository;
import uk.ac.herc.bcra.service.QuestionnaireService;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;
import uk.ac.herc.bcra.service.mapper.QuestionnaireMapper;
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

import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
/**
 * Integration tests for the {@link QuestionnaireResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionnaireResourceIT {

    private static final QuestionnaireType DEFAULT_TYPE = QuestionnaireType.CONSENT_FORM;
    private static final QuestionnaireType UPDATED_TYPE = QuestionnaireType.RISK_ASSESMENT;

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private QuestionnaireService questionnaireService;

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

    private MockMvc restQuestionnaireMockMvc;

    private Questionnaire questionnaire;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionnaireResource questionnaireResource = new QuestionnaireResource(questionnaireService);
        this.restQuestionnaireMockMvc = MockMvcBuilders.standaloneSetup(questionnaireResource)
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
    public static Questionnaire createEntity(EntityManager em) {
        Questionnaire questionnaire = new Questionnaire()
            .type(DEFAULT_TYPE)
            .version(DEFAULT_VERSION);
        return questionnaire;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questionnaire createUpdatedEntity(EntityManager em) {
        Questionnaire questionnaire = new Questionnaire()
            .type(UPDATED_TYPE)
            .version(UPDATED_VERSION);
        return questionnaire;
    }

    @BeforeEach
    public void initTest() {
        questionnaire = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllQuestionnaires() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        // Get all the questionnaireList
        restQuestionnaireMockMvc.perform(get("/api/questionnaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionnaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)));
    }
    
    @Test
    @Transactional
    public void getConsentQuestionnaire() throws Exception {
        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        // Get the questionnaire
        restQuestionnaireMockMvc.perform(
            get("/api/questionnaires/consent", questionnaire.getId())
            .principal(new Principal() {
                @Override
                public String getName() {
                    return participant.getUser().getLogin();
                }
            })
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(participant.getProcedure().getConsentResponse().getQuestionnaire().getId().intValue()))
            .andExpect(jsonPath("$.type").value(QuestionnaireType.CONSENT_FORM.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    public void getRiskAssesmentQuestionnaire() throws Exception {
        // Initialize the database
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }

        // Get the questionnaire
        restQuestionnaireMockMvc.perform(
            get("/api/questionnaires/risk-assesment", questionnaire.getId())
            .principal(new Principal() {
                @Override
                public String getName() {
                    return participant.getUser().getLogin();
                }
            })
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(participant.getProcedure().getRiskAssesmentResponse().getQuestionnaire().getId().intValue()))
            .andExpect(jsonPath("$.type").value(QuestionnaireType.RISK_ASSESMENT.toString()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionnaire() throws Exception {
        // Get the questionnaire
        restQuestionnaireMockMvc.perform(get("/api/questionnaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Questionnaire.class);
        Questionnaire questionnaire1 = new Questionnaire();
        questionnaire1.setId(1L);
        Questionnaire questionnaire2 = new Questionnaire();
        questionnaire2.setId(questionnaire1.getId());
        assertThat(questionnaire1).isEqualTo(questionnaire2);
        questionnaire2.setId(2L);
        assertThat(questionnaire1).isNotEqualTo(questionnaire2);
        questionnaire1.setId(null);
        assertThat(questionnaire1).isNotEqualTo(questionnaire2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionnaireDTO.class);
        QuestionnaireDTO questionnaireDTO1 = new QuestionnaireDTO();
        questionnaireDTO1.setId(1L);
        QuestionnaireDTO questionnaireDTO2 = new QuestionnaireDTO();
        assertThat(questionnaireDTO1).isNotEqualTo(questionnaireDTO2);
        questionnaireDTO2.setId(questionnaireDTO1.getId());
        assertThat(questionnaireDTO1).isEqualTo(questionnaireDTO2);
        questionnaireDTO2.setId(2L);
        assertThat(questionnaireDTO1).isNotEqualTo(questionnaireDTO2);
        questionnaireDTO1.setId(null);
        assertThat(questionnaireDTO1).isNotEqualTo(questionnaireDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionnaireMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionnaireMapper.fromId(null)).isNull();
    }
}
