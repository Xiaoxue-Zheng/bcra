package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.repository.QuestionnaireRepository;
import uk.ac.herc.bcra.service.QuestionnaireService;
import uk.ac.herc.bcra.service.dto.QuestionnaireDTO;
import uk.ac.herc.bcra.service.mapper.QuestionnaireMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.QuestionnaireCriteria;
import uk.ac.herc.bcra.service.QuestionnaireQueryService;

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
 * Integration tests for the {@link QuestionnaireResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionnaireResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private QuestionnaireQueryService questionnaireQueryService;

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
        final QuestionnaireResource questionnaireResource = new QuestionnaireResource(questionnaireService, questionnaireQueryService);
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
            .uuid(DEFAULT_UUID);
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
            .uuid(UPDATED_UUID);
        return questionnaire;
    }

    @BeforeEach
    public void initTest() {
        questionnaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionnaire() throws Exception {
        int databaseSizeBeforeCreate = questionnaireRepository.findAll().size();

        // Create the Questionnaire
        QuestionnaireDTO questionnaireDTO = questionnaireMapper.toDto(questionnaire);
        restQuestionnaireMockMvc.perform(post("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireDTO)))
            .andExpect(status().isCreated());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeCreate + 1);
        Questionnaire testQuestionnaire = questionnaireList.get(questionnaireList.size() - 1);
        assertThat(testQuestionnaire.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createQuestionnaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionnaireRepository.findAll().size();

        // Create the Questionnaire with an existing ID
        questionnaire.setId(1L);
        QuestionnaireDTO questionnaireDTO = questionnaireMapper.toDto(questionnaire);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionnaireMockMvc.perform(post("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionnaireRepository.findAll().size();
        // set the field null
        questionnaire.setUuid(null);

        // Create the Questionnaire, which fails.
        QuestionnaireDTO questionnaireDTO = questionnaireMapper.toDto(questionnaire);

        restQuestionnaireMockMvc.perform(post("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireDTO)))
            .andExpect(status().isBadRequest());

        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }
    
    @Test
    @Transactional
    public void getQuestionnaire() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        // Get the questionnaire
        restQuestionnaireMockMvc.perform(get("/api/questionnaires/{id}", questionnaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionnaire.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }

    @Test
    @Transactional
    public void getAllQuestionnairesByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        // Get all the questionnaireList where uuid equals to DEFAULT_UUID
        defaultQuestionnaireShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the questionnaireList where uuid equals to UPDATED_UUID
        defaultQuestionnaireShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionnairesByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        // Get all the questionnaireList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultQuestionnaireShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the questionnaireList where uuid equals to UPDATED_UUID
        defaultQuestionnaireShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionnairesByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        // Get all the questionnaireList where uuid is not null
        defaultQuestionnaireShouldBeFound("uuid.specified=true");

        // Get all the questionnaireList where uuid is null
        defaultQuestionnaireShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionnairesByQuestionnaireQuestionGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);
        QuestionnaireQuestionGroup questionnaireQuestionGroup = QuestionnaireQuestionGroupResourceIT.createEntity(em);
        em.persist(questionnaireQuestionGroup);
        em.flush();
        questionnaire.addQuestionnaireQuestionGroup(questionnaireQuestionGroup);
        questionnaireRepository.saveAndFlush(questionnaire);
        Long questionnaireQuestionGroupId = questionnaireQuestionGroup.getId();

        // Get all the questionnaireList where questionnaireQuestionGroup equals to questionnaireQuestionGroupId
        defaultQuestionnaireShouldBeFound("questionnaireQuestionGroupId.equals=" + questionnaireQuestionGroupId);

        // Get all the questionnaireList where questionnaireQuestionGroup equals to questionnaireQuestionGroupId + 1
        defaultQuestionnaireShouldNotBeFound("questionnaireQuestionGroupId.equals=" + (questionnaireQuestionGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionnairesByAnswerResponseIsEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);
        AnswerResponse answerResponse = AnswerResponseResourceIT.createEntity(em);
        em.persist(answerResponse);
        em.flush();
        questionnaire.addAnswerResponse(answerResponse);
        questionnaireRepository.saveAndFlush(questionnaire);
        Long answerResponseId = answerResponse.getId();

        // Get all the questionnaireList where answerResponse equals to answerResponseId
        defaultQuestionnaireShouldBeFound("answerResponseId.equals=" + answerResponseId);

        // Get all the questionnaireList where answerResponse equals to answerResponseId + 1
        defaultQuestionnaireShouldNotBeFound("answerResponseId.equals=" + (answerResponseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionnaireShouldBeFound(String filter) throws Exception {
        restQuestionnaireMockMvc.perform(get("/api/questionnaires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionnaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));

        // Check, that the count call also returns 1
        restQuestionnaireMockMvc.perform(get("/api/questionnaires/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionnaireShouldNotBeFound(String filter) throws Exception {
        restQuestionnaireMockMvc.perform(get("/api/questionnaires?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionnaireMockMvc.perform(get("/api/questionnaires/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
    public void updateQuestionnaire() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        int databaseSizeBeforeUpdate = questionnaireRepository.findAll().size();

        // Update the questionnaire
        Questionnaire updatedQuestionnaire = questionnaireRepository.findById(questionnaire.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionnaire are not directly saved in db
        em.detach(updatedQuestionnaire);
        updatedQuestionnaire
            .uuid(UPDATED_UUID);
        QuestionnaireDTO questionnaireDTO = questionnaireMapper.toDto(updatedQuestionnaire);

        restQuestionnaireMockMvc.perform(put("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireDTO)))
            .andExpect(status().isOk());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeUpdate);
        Questionnaire testQuestionnaire = questionnaireList.get(questionnaireList.size() - 1);
        assertThat(testQuestionnaire.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = questionnaireRepository.findAll().size();

        // Create the Questionnaire
        QuestionnaireDTO questionnaireDTO = questionnaireMapper.toDto(questionnaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionnaireMockMvc.perform(put("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionnaire() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        int databaseSizeBeforeDelete = questionnaireRepository.findAll().size();

        // Delete the questionnaire
        restQuestionnaireMockMvc.perform(delete("/api/questionnaires/{id}", questionnaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeDelete - 1);
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
