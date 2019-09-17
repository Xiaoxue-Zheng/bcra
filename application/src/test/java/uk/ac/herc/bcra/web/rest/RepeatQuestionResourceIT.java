package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.RepeatQuestion;
import uk.ac.herc.bcra.domain.RepeatDisplayCondition;
import uk.ac.herc.bcra.repository.RepeatQuestionRepository;
import uk.ac.herc.bcra.service.RepeatQuestionService;
import uk.ac.herc.bcra.service.dto.RepeatQuestionDTO;
import uk.ac.herc.bcra.service.mapper.RepeatQuestionMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.RepeatQuestionCriteria;
import uk.ac.herc.bcra.service.RepeatQuestionQueryService;

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
 * Integration tests for the {@link RepeatQuestionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class RepeatQuestionResourceIT {

    private static final Integer DEFAULT_MAXIMUM = 1;
    private static final Integer UPDATED_MAXIMUM = 2;
    private static final Integer SMALLER_MAXIMUM = 1 - 1;

    @Autowired
    private RepeatQuestionRepository repeatQuestionRepository;

    @Autowired
    private RepeatQuestionMapper repeatQuestionMapper;

    @Autowired
    private RepeatQuestionService repeatQuestionService;

    @Autowired
    private RepeatQuestionQueryService repeatQuestionQueryService;

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

    private MockMvc restRepeatQuestionMockMvc;

    private RepeatQuestion repeatQuestion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RepeatQuestionResource repeatQuestionResource = new RepeatQuestionResource(repeatQuestionService, repeatQuestionQueryService);
        this.restRepeatQuestionMockMvc = MockMvcBuilders.standaloneSetup(repeatQuestionResource)
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
    public static RepeatQuestion createEntity(EntityManager em) {
        RepeatQuestion repeatQuestion = new RepeatQuestion()
            .maximum(DEFAULT_MAXIMUM);

        repeatQuestion.setUuid("abc123");
        repeatQuestion.setText("How?");
        return repeatQuestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepeatQuestion createUpdatedEntity(EntityManager em) {
        RepeatQuestion repeatQuestion = new RepeatQuestion()
            .maximum(UPDATED_MAXIMUM);
        return repeatQuestion;
    }

    @BeforeEach
    public void initTest() {
        repeatQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createRepeatQuestion() throws Exception {
        int databaseSizeBeforeCreate = repeatQuestionRepository.findAll().size();

        // Create the RepeatQuestion
        RepeatQuestionDTO repeatQuestionDTO = repeatQuestionMapper.toDto(repeatQuestion);
        restRepeatQuestionMockMvc.perform(post("/api/repeat-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatQuestionDTO)))
            .andExpect(status().isCreated());

        // Validate the RepeatQuestion in the database
        List<RepeatQuestion> repeatQuestionList = repeatQuestionRepository.findAll();
        assertThat(repeatQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        RepeatQuestion testRepeatQuestion = repeatQuestionList.get(repeatQuestionList.size() - 1);
        assertThat(testRepeatQuestion.getMaximum()).isEqualTo(DEFAULT_MAXIMUM);
    }

    @Test
    @Transactional
    public void createRepeatQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = repeatQuestionRepository.findAll().size();

        // Create the RepeatQuestion with an existing ID
        repeatQuestion.setId(1L);
        RepeatQuestionDTO repeatQuestionDTO = repeatQuestionMapper.toDto(repeatQuestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepeatQuestionMockMvc.perform(post("/api/repeat-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RepeatQuestion in the database
        List<RepeatQuestion> repeatQuestionList = repeatQuestionRepository.findAll();
        assertThat(repeatQuestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMaximumIsRequired() throws Exception {
        int databaseSizeBeforeTest = repeatQuestionRepository.findAll().size();
        // set the field null
        repeatQuestion.setMaximum(null);

        // Create the RepeatQuestion, which fails.
        RepeatQuestionDTO repeatQuestionDTO = repeatQuestionMapper.toDto(repeatQuestion);

        restRepeatQuestionMockMvc.perform(post("/api/repeat-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatQuestionDTO)))
            .andExpect(status().isBadRequest());

        List<RepeatQuestion> repeatQuestionList = repeatQuestionRepository.findAll();
        assertThat(repeatQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRepeatQuestions() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        // Get all the repeatQuestionList
        restRepeatQuestionMockMvc.perform(get("/api/repeat-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repeatQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].maximum").value(hasItem(DEFAULT_MAXIMUM)));
    }
    
    @Test
    @Transactional
    public void getRepeatQuestion() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        // Get the repeatQuestion
        restRepeatQuestionMockMvc.perform(get("/api/repeat-questions/{id}", repeatQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(repeatQuestion.getId().intValue()))
            .andExpect(jsonPath("$.maximum").value(DEFAULT_MAXIMUM));
    }

    @Test
    @Transactional
    public void getAllRepeatQuestionsByMaximumIsEqualToSomething() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        // Get all the repeatQuestionList where maximum equals to DEFAULT_MAXIMUM
        defaultRepeatQuestionShouldBeFound("maximum.equals=" + DEFAULT_MAXIMUM);

        // Get all the repeatQuestionList where maximum equals to UPDATED_MAXIMUM
        defaultRepeatQuestionShouldNotBeFound("maximum.equals=" + UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllRepeatQuestionsByMaximumIsInShouldWork() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        // Get all the repeatQuestionList where maximum in DEFAULT_MAXIMUM or UPDATED_MAXIMUM
        defaultRepeatQuestionShouldBeFound("maximum.in=" + DEFAULT_MAXIMUM + "," + UPDATED_MAXIMUM);

        // Get all the repeatQuestionList where maximum equals to UPDATED_MAXIMUM
        defaultRepeatQuestionShouldNotBeFound("maximum.in=" + UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllRepeatQuestionsByMaximumIsNullOrNotNull() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        // Get all the repeatQuestionList where maximum is not null
        defaultRepeatQuestionShouldBeFound("maximum.specified=true");

        // Get all the repeatQuestionList where maximum is null
        defaultRepeatQuestionShouldNotBeFound("maximum.specified=false");
    }

    @Test
    @Transactional
    public void getAllRepeatQuestionsByMaximumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        // Get all the repeatQuestionList where maximum is greater than or equal to DEFAULT_MAXIMUM
        defaultRepeatQuestionShouldBeFound("maximum.greaterThanOrEqual=" + DEFAULT_MAXIMUM);

        // Get all the repeatQuestionList where maximum is greater than or equal to UPDATED_MAXIMUM
        defaultRepeatQuestionShouldNotBeFound("maximum.greaterThanOrEqual=" + UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllRepeatQuestionsByMaximumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        // Get all the repeatQuestionList where maximum is less than or equal to DEFAULT_MAXIMUM
        defaultRepeatQuestionShouldBeFound("maximum.lessThanOrEqual=" + DEFAULT_MAXIMUM);

        // Get all the repeatQuestionList where maximum is less than or equal to SMALLER_MAXIMUM
        defaultRepeatQuestionShouldNotBeFound("maximum.lessThanOrEqual=" + SMALLER_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllRepeatQuestionsByMaximumIsLessThanSomething() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        // Get all the repeatQuestionList where maximum is less than DEFAULT_MAXIMUM
        defaultRepeatQuestionShouldNotBeFound("maximum.lessThan=" + DEFAULT_MAXIMUM);

        // Get all the repeatQuestionList where maximum is less than UPDATED_MAXIMUM
        defaultRepeatQuestionShouldBeFound("maximum.lessThan=" + UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllRepeatQuestionsByMaximumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        // Get all the repeatQuestionList where maximum is greater than DEFAULT_MAXIMUM
        defaultRepeatQuestionShouldNotBeFound("maximum.greaterThan=" + DEFAULT_MAXIMUM);

        // Get all the repeatQuestionList where maximum is greater than SMALLER_MAXIMUM
        defaultRepeatQuestionShouldBeFound("maximum.greaterThan=" + SMALLER_MAXIMUM);
    }


    @Test
    @Transactional
    public void getAllRepeatQuestionsByRepeatDisplayConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);
        RepeatDisplayCondition repeatDisplayCondition = RepeatDisplayConditionResourceIT.createEntity(em);
        em.persist(repeatDisplayCondition);
        em.flush();
        repeatQuestion.addRepeatDisplayCondition(repeatDisplayCondition);
        repeatQuestionRepository.saveAndFlush(repeatQuestion);
        Long repeatDisplayConditionId = repeatDisplayCondition.getId();

        // Get all the repeatQuestionList where repeatDisplayCondition equals to repeatDisplayConditionId
        defaultRepeatQuestionShouldBeFound("repeatDisplayConditionId.equals=" + repeatDisplayConditionId);

        // Get all the repeatQuestionList where repeatDisplayCondition equals to repeatDisplayConditionId + 1
        defaultRepeatQuestionShouldNotBeFound("repeatDisplayConditionId.equals=" + (repeatDisplayConditionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRepeatQuestionShouldBeFound(String filter) throws Exception {
        restRepeatQuestionMockMvc.perform(get("/api/repeat-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repeatQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].maximum").value(hasItem(DEFAULT_MAXIMUM)));

        // Check, that the count call also returns 1
        restRepeatQuestionMockMvc.perform(get("/api/repeat-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRepeatQuestionShouldNotBeFound(String filter) throws Exception {
        restRepeatQuestionMockMvc.perform(get("/api/repeat-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRepeatQuestionMockMvc.perform(get("/api/repeat-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRepeatQuestion() throws Exception {
        // Get the repeatQuestion
        restRepeatQuestionMockMvc.perform(get("/api/repeat-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepeatQuestion() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        int databaseSizeBeforeUpdate = repeatQuestionRepository.findAll().size();

        // Update the repeatQuestion
        RepeatQuestion updatedRepeatQuestion = repeatQuestionRepository.findById(repeatQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedRepeatQuestion are not directly saved in db
        em.detach(updatedRepeatQuestion);
        updatedRepeatQuestion
            .maximum(UPDATED_MAXIMUM);
        RepeatQuestionDTO repeatQuestionDTO = repeatQuestionMapper.toDto(updatedRepeatQuestion);
        repeatQuestionDTO.setUuid("def321");
        repeatQuestionDTO.setText("Why?");
        restRepeatQuestionMockMvc.perform(put("/api/repeat-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatQuestionDTO)))
            .andExpect(status().isOk());

        // Validate the RepeatQuestion in the database
        List<RepeatQuestion> repeatQuestionList = repeatQuestionRepository.findAll();
        assertThat(repeatQuestionList).hasSize(databaseSizeBeforeUpdate);
        RepeatQuestion testRepeatQuestion = repeatQuestionList.get(repeatQuestionList.size() - 1);
        assertThat(testRepeatQuestion.getMaximum()).isEqualTo(UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void updateNonExistingRepeatQuestion() throws Exception {
        int databaseSizeBeforeUpdate = repeatQuestionRepository.findAll().size();

        // Create the RepeatQuestion
        RepeatQuestionDTO repeatQuestionDTO = repeatQuestionMapper.toDto(repeatQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepeatQuestionMockMvc.perform(put("/api/repeat-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RepeatQuestion in the database
        List<RepeatQuestion> repeatQuestionList = repeatQuestionRepository.findAll();
        assertThat(repeatQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRepeatQuestion() throws Exception {
        // Initialize the database
        repeatQuestionRepository.saveAndFlush(repeatQuestion);

        int databaseSizeBeforeDelete = repeatQuestionRepository.findAll().size();

        // Delete the repeatQuestion
        restRepeatQuestionMockMvc.perform(delete("/api/repeat-questions/{id}", repeatQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RepeatQuestion> repeatQuestionList = repeatQuestionRepository.findAll();
        assertThat(repeatQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepeatQuestion.class);
        RepeatQuestion repeatQuestion1 = new RepeatQuestion();
        repeatQuestion1.setId(1L);
        RepeatQuestion repeatQuestion2 = new RepeatQuestion();
        repeatQuestion2.setId(repeatQuestion1.getId());
        assertThat(repeatQuestion1).isEqualTo(repeatQuestion2);
        repeatQuestion2.setId(2L);
        assertThat(repeatQuestion1).isNotEqualTo(repeatQuestion2);
        repeatQuestion1.setId(null);
        assertThat(repeatQuestion1).isNotEqualTo(repeatQuestion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepeatQuestionDTO.class);
        RepeatQuestionDTO repeatQuestionDTO1 = new RepeatQuestionDTO();
        repeatQuestionDTO1.setId(1L);
        RepeatQuestionDTO repeatQuestionDTO2 = new RepeatQuestionDTO();
        assertThat(repeatQuestionDTO1).isNotEqualTo(repeatQuestionDTO2);
        repeatQuestionDTO2.setId(repeatQuestionDTO1.getId());
        assertThat(repeatQuestionDTO1).isEqualTo(repeatQuestionDTO2);
        repeatQuestionDTO2.setId(2L);
        assertThat(repeatQuestionDTO1).isNotEqualTo(repeatQuestionDTO2);
        repeatQuestionDTO1.setId(null);
        assertThat(repeatQuestionDTO1).isNotEqualTo(repeatQuestionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(repeatQuestionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(repeatQuestionMapper.fromId(null)).isNull();
    }
}
