package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.NumberCheckboxQuestion;
import uk.ac.herc.bcra.repository.NumberCheckboxQuestionRepository;
import uk.ac.herc.bcra.service.NumberCheckboxQuestionService;
import uk.ac.herc.bcra.service.dto.NumberCheckboxQuestionDTO;
import uk.ac.herc.bcra.service.mapper.NumberCheckboxQuestionMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.NumberCheckboxQuestionCriteria;
import uk.ac.herc.bcra.service.NumberCheckboxQuestionQueryService;

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
 * Integration tests for the {@link NumberCheckboxQuestionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class NumberCheckboxQuestionResourceIT {

    private static final Integer DEFAULT_MINIMUM = 1;
    private static final Integer UPDATED_MINIMUM = 2;
    private static final Integer SMALLER_MINIMUM = 1 - 1;

    private static final Integer DEFAULT_MAXIMUM = 1;
    private static final Integer UPDATED_MAXIMUM = 2;
    private static final Integer SMALLER_MAXIMUM = 1 - 1;

    @Autowired
    private NumberCheckboxQuestionRepository numberCheckboxQuestionRepository;

    @Autowired
    private NumberCheckboxQuestionMapper numberCheckboxQuestionMapper;

    @Autowired
    private NumberCheckboxQuestionService numberCheckboxQuestionService;

    @Autowired
    private NumberCheckboxQuestionQueryService numberCheckboxQuestionQueryService;

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

    private MockMvc restNumberCheckboxQuestionMockMvc;

    private NumberCheckboxQuestion numberCheckboxQuestion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NumberCheckboxQuestionResource numberCheckboxQuestionResource = new NumberCheckboxQuestionResource(numberCheckboxQuestionService, numberCheckboxQuestionQueryService);
        this.restNumberCheckboxQuestionMockMvc = MockMvcBuilders.standaloneSetup(numberCheckboxQuestionResource)
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
    public static NumberCheckboxQuestion createEntity(EntityManager em) {
        NumberCheckboxQuestion numberCheckboxQuestion = new NumberCheckboxQuestion()
            .minimum(DEFAULT_MINIMUM)
            .maximum(DEFAULT_MAXIMUM);
        numberCheckboxQuestion.setUuid("abc123");
        numberCheckboxQuestion.setText("How?");
        return numberCheckboxQuestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumberCheckboxQuestion createUpdatedEntity(EntityManager em) {
        NumberCheckboxQuestion numberCheckboxQuestion = new NumberCheckboxQuestion()
            .minimum(UPDATED_MINIMUM)
            .maximum(UPDATED_MAXIMUM);
        return numberCheckboxQuestion;
    }

    @BeforeEach
    public void initTest() {
        numberCheckboxQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createNumberCheckboxQuestion() throws Exception {
        int databaseSizeBeforeCreate = numberCheckboxQuestionRepository.findAll().size();

        // Create the NumberCheckboxQuestion
        NumberCheckboxQuestionDTO numberCheckboxQuestionDTO = numberCheckboxQuestionMapper.toDto(numberCheckboxQuestion);
        restNumberCheckboxQuestionMockMvc.perform(post("/api/number-checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxQuestionDTO)))
            .andExpect(status().isCreated());

        // Validate the NumberCheckboxQuestion in the database
        List<NumberCheckboxQuestion> numberCheckboxQuestionList = numberCheckboxQuestionRepository.findAll();
        assertThat(numberCheckboxQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        NumberCheckboxQuestion testNumberCheckboxQuestion = numberCheckboxQuestionList.get(numberCheckboxQuestionList.size() - 1);
        assertThat(testNumberCheckboxQuestion.getMinimum()).isEqualTo(DEFAULT_MINIMUM);
        assertThat(testNumberCheckboxQuestion.getMaximum()).isEqualTo(DEFAULT_MAXIMUM);
    }

    @Test
    @Transactional
    public void createNumberCheckboxQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = numberCheckboxQuestionRepository.findAll().size();

        // Create the NumberCheckboxQuestion with an existing ID
        numberCheckboxQuestion.setId(1L);
        NumberCheckboxQuestionDTO numberCheckboxQuestionDTO = numberCheckboxQuestionMapper.toDto(numberCheckboxQuestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumberCheckboxQuestionMockMvc.perform(post("/api/number-checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NumberCheckboxQuestion in the database
        List<NumberCheckboxQuestion> numberCheckboxQuestionList = numberCheckboxQuestionRepository.findAll();
        assertThat(numberCheckboxQuestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMinimumIsRequired() throws Exception {
        int databaseSizeBeforeTest = numberCheckboxQuestionRepository.findAll().size();
        // set the field null
        numberCheckboxQuestion.setMinimum(null);

        // Create the NumberCheckboxQuestion, which fails.
        NumberCheckboxQuestionDTO numberCheckboxQuestionDTO = numberCheckboxQuestionMapper.toDto(numberCheckboxQuestion);

        restNumberCheckboxQuestionMockMvc.perform(post("/api/number-checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxQuestionDTO)))
            .andExpect(status().isBadRequest());

        List<NumberCheckboxQuestion> numberCheckboxQuestionList = numberCheckboxQuestionRepository.findAll();
        assertThat(numberCheckboxQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaximumIsRequired() throws Exception {
        int databaseSizeBeforeTest = numberCheckboxQuestionRepository.findAll().size();
        // set the field null
        numberCheckboxQuestion.setMaximum(null);

        // Create the NumberCheckboxQuestion, which fails.
        NumberCheckboxQuestionDTO numberCheckboxQuestionDTO = numberCheckboxQuestionMapper.toDto(numberCheckboxQuestion);

        restNumberCheckboxQuestionMockMvc.perform(post("/api/number-checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxQuestionDTO)))
            .andExpect(status().isBadRequest());

        List<NumberCheckboxQuestion> numberCheckboxQuestionList = numberCheckboxQuestionRepository.findAll();
        assertThat(numberCheckboxQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestions() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList
        restNumberCheckboxQuestionMockMvc.perform(get("/api/number-checkbox-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numberCheckboxQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].minimum").value(hasItem(DEFAULT_MINIMUM)))
            .andExpect(jsonPath("$.[*].maximum").value(hasItem(DEFAULT_MAXIMUM)));
    }
    
    @Test
    @Transactional
    public void getNumberCheckboxQuestion() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get the numberCheckboxQuestion
        restNumberCheckboxQuestionMockMvc.perform(get("/api/number-checkbox-questions/{id}", numberCheckboxQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(numberCheckboxQuestion.getId().intValue()))
            .andExpect(jsonPath("$.minimum").value(DEFAULT_MINIMUM))
            .andExpect(jsonPath("$.maximum").value(DEFAULT_MAXIMUM));
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMinimumIsEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where minimum equals to DEFAULT_MINIMUM
        defaultNumberCheckboxQuestionShouldBeFound("minimum.equals=" + DEFAULT_MINIMUM);

        // Get all the numberCheckboxQuestionList where minimum equals to UPDATED_MINIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("minimum.equals=" + UPDATED_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMinimumIsInShouldWork() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where minimum in DEFAULT_MINIMUM or UPDATED_MINIMUM
        defaultNumberCheckboxQuestionShouldBeFound("minimum.in=" + DEFAULT_MINIMUM + "," + UPDATED_MINIMUM);

        // Get all the numberCheckboxQuestionList where minimum equals to UPDATED_MINIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("minimum.in=" + UPDATED_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMinimumIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where minimum is not null
        defaultNumberCheckboxQuestionShouldBeFound("minimum.specified=true");

        // Get all the numberCheckboxQuestionList where minimum is null
        defaultNumberCheckboxQuestionShouldNotBeFound("minimum.specified=false");
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMinimumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where minimum is greater than or equal to DEFAULT_MINIMUM
        defaultNumberCheckboxQuestionShouldBeFound("minimum.greaterThanOrEqual=" + DEFAULT_MINIMUM);

        // Get all the numberCheckboxQuestionList where minimum is greater than or equal to UPDATED_MINIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("minimum.greaterThanOrEqual=" + UPDATED_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMinimumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where minimum is less than or equal to DEFAULT_MINIMUM
        defaultNumberCheckboxQuestionShouldBeFound("minimum.lessThanOrEqual=" + DEFAULT_MINIMUM);

        // Get all the numberCheckboxQuestionList where minimum is less than or equal to SMALLER_MINIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("minimum.lessThanOrEqual=" + SMALLER_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMinimumIsLessThanSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where minimum is less than DEFAULT_MINIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("minimum.lessThan=" + DEFAULT_MINIMUM);

        // Get all the numberCheckboxQuestionList where minimum is less than UPDATED_MINIMUM
        defaultNumberCheckboxQuestionShouldBeFound("minimum.lessThan=" + UPDATED_MINIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMinimumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where minimum is greater than DEFAULT_MINIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("minimum.greaterThan=" + DEFAULT_MINIMUM);

        // Get all the numberCheckboxQuestionList where minimum is greater than SMALLER_MINIMUM
        defaultNumberCheckboxQuestionShouldBeFound("minimum.greaterThan=" + SMALLER_MINIMUM);
    }


    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMaximumIsEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where maximum equals to DEFAULT_MAXIMUM
        defaultNumberCheckboxQuestionShouldBeFound("maximum.equals=" + DEFAULT_MAXIMUM);

        // Get all the numberCheckboxQuestionList where maximum equals to UPDATED_MAXIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("maximum.equals=" + UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMaximumIsInShouldWork() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where maximum in DEFAULT_MAXIMUM or UPDATED_MAXIMUM
        defaultNumberCheckboxQuestionShouldBeFound("maximum.in=" + DEFAULT_MAXIMUM + "," + UPDATED_MAXIMUM);

        // Get all the numberCheckboxQuestionList where maximum equals to UPDATED_MAXIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("maximum.in=" + UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMaximumIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where maximum is not null
        defaultNumberCheckboxQuestionShouldBeFound("maximum.specified=true");

        // Get all the numberCheckboxQuestionList where maximum is null
        defaultNumberCheckboxQuestionShouldNotBeFound("maximum.specified=false");
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMaximumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where maximum is greater than or equal to DEFAULT_MAXIMUM
        defaultNumberCheckboxQuestionShouldBeFound("maximum.greaterThanOrEqual=" + DEFAULT_MAXIMUM);

        // Get all the numberCheckboxQuestionList where maximum is greater than or equal to UPDATED_MAXIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("maximum.greaterThanOrEqual=" + UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMaximumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where maximum is less than or equal to DEFAULT_MAXIMUM
        defaultNumberCheckboxQuestionShouldBeFound("maximum.lessThanOrEqual=" + DEFAULT_MAXIMUM);

        // Get all the numberCheckboxQuestionList where maximum is less than or equal to SMALLER_MAXIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("maximum.lessThanOrEqual=" + SMALLER_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMaximumIsLessThanSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where maximum is less than DEFAULT_MAXIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("maximum.lessThan=" + DEFAULT_MAXIMUM);

        // Get all the numberCheckboxQuestionList where maximum is less than UPDATED_MAXIMUM
        defaultNumberCheckboxQuestionShouldBeFound("maximum.lessThan=" + UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxQuestionsByMaximumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        // Get all the numberCheckboxQuestionList where maximum is greater than DEFAULT_MAXIMUM
        defaultNumberCheckboxQuestionShouldNotBeFound("maximum.greaterThan=" + DEFAULT_MAXIMUM);

        // Get all the numberCheckboxQuestionList where maximum is greater than SMALLER_MAXIMUM
        defaultNumberCheckboxQuestionShouldBeFound("maximum.greaterThan=" + SMALLER_MAXIMUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNumberCheckboxQuestionShouldBeFound(String filter) throws Exception {
        restNumberCheckboxQuestionMockMvc.perform(get("/api/number-checkbox-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numberCheckboxQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].minimum").value(hasItem(DEFAULT_MINIMUM)))
            .andExpect(jsonPath("$.[*].maximum").value(hasItem(DEFAULT_MAXIMUM)));

        // Check, that the count call also returns 1
        restNumberCheckboxQuestionMockMvc.perform(get("/api/number-checkbox-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNumberCheckboxQuestionShouldNotBeFound(String filter) throws Exception {
        restNumberCheckboxQuestionMockMvc.perform(get("/api/number-checkbox-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNumberCheckboxQuestionMockMvc.perform(get("/api/number-checkbox-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNumberCheckboxQuestion() throws Exception {
        // Get the numberCheckboxQuestion
        restNumberCheckboxQuestionMockMvc.perform(get("/api/number-checkbox-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNumberCheckboxQuestion() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        int databaseSizeBeforeUpdate = numberCheckboxQuestionRepository.findAll().size();

        // Update the numberCheckboxQuestion
        NumberCheckboxQuestion updatedNumberCheckboxQuestion = numberCheckboxQuestionRepository.findById(numberCheckboxQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedNumberCheckboxQuestion are not directly saved in db
        em.detach(updatedNumberCheckboxQuestion);
        updatedNumberCheckboxQuestion
            .minimum(UPDATED_MINIMUM)
            .maximum(UPDATED_MAXIMUM);
        NumberCheckboxQuestionDTO numberCheckboxQuestionDTO = numberCheckboxQuestionMapper.toDto(updatedNumberCheckboxQuestion);
        numberCheckboxQuestionDTO.setUuid("def321");
        numberCheckboxQuestionDTO.setText("Why?");
        restNumberCheckboxQuestionMockMvc.perform(put("/api/number-checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxQuestionDTO)))
            .andExpect(status().isOk());

        // Validate the NumberCheckboxQuestion in the database
        List<NumberCheckboxQuestion> numberCheckboxQuestionList = numberCheckboxQuestionRepository.findAll();
        assertThat(numberCheckboxQuestionList).hasSize(databaseSizeBeforeUpdate);
        NumberCheckboxQuestion testNumberCheckboxQuestion = numberCheckboxQuestionList.get(numberCheckboxQuestionList.size() - 1);
        assertThat(testNumberCheckboxQuestion.getMinimum()).isEqualTo(UPDATED_MINIMUM);
        assertThat(testNumberCheckboxQuestion.getMaximum()).isEqualTo(UPDATED_MAXIMUM);
    }

    @Test
    @Transactional
    public void updateNonExistingNumberCheckboxQuestion() throws Exception {
        int databaseSizeBeforeUpdate = numberCheckboxQuestionRepository.findAll().size();

        // Create the NumberCheckboxQuestion
        NumberCheckboxQuestionDTO numberCheckboxQuestionDTO = numberCheckboxQuestionMapper.toDto(numberCheckboxQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumberCheckboxQuestionMockMvc.perform(put("/api/number-checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NumberCheckboxQuestion in the database
        List<NumberCheckboxQuestion> numberCheckboxQuestionList = numberCheckboxQuestionRepository.findAll();
        assertThat(numberCheckboxQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNumberCheckboxQuestion() throws Exception {
        // Initialize the database
        numberCheckboxQuestionRepository.saveAndFlush(numberCheckboxQuestion);

        int databaseSizeBeforeDelete = numberCheckboxQuestionRepository.findAll().size();

        // Delete the numberCheckboxQuestion
        restNumberCheckboxQuestionMockMvc.perform(delete("/api/number-checkbox-questions/{id}", numberCheckboxQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NumberCheckboxQuestion> numberCheckboxQuestionList = numberCheckboxQuestionRepository.findAll();
        assertThat(numberCheckboxQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumberCheckboxQuestion.class);
        NumberCheckboxQuestion numberCheckboxQuestion1 = new NumberCheckboxQuestion();
        numberCheckboxQuestion1.setId(1L);
        NumberCheckboxQuestion numberCheckboxQuestion2 = new NumberCheckboxQuestion();
        numberCheckboxQuestion2.setId(numberCheckboxQuestion1.getId());
        assertThat(numberCheckboxQuestion1).isEqualTo(numberCheckboxQuestion2);
        numberCheckboxQuestion2.setId(2L);
        assertThat(numberCheckboxQuestion1).isNotEqualTo(numberCheckboxQuestion2);
        numberCheckboxQuestion1.setId(null);
        assertThat(numberCheckboxQuestion1).isNotEqualTo(numberCheckboxQuestion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumberCheckboxQuestionDTO.class);
        NumberCheckboxQuestionDTO numberCheckboxQuestionDTO1 = new NumberCheckboxQuestionDTO();
        numberCheckboxQuestionDTO1.setId(1L);
        NumberCheckboxQuestionDTO numberCheckboxQuestionDTO2 = new NumberCheckboxQuestionDTO();
        assertThat(numberCheckboxQuestionDTO1).isNotEqualTo(numberCheckboxQuestionDTO2);
        numberCheckboxQuestionDTO2.setId(numberCheckboxQuestionDTO1.getId());
        assertThat(numberCheckboxQuestionDTO1).isEqualTo(numberCheckboxQuestionDTO2);
        numberCheckboxQuestionDTO2.setId(2L);
        assertThat(numberCheckboxQuestionDTO1).isNotEqualTo(numberCheckboxQuestionDTO2);
        numberCheckboxQuestionDTO1.setId(null);
        assertThat(numberCheckboxQuestionDTO1).isNotEqualTo(numberCheckboxQuestionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(numberCheckboxQuestionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(numberCheckboxQuestionMapper.fromId(null)).isNull();
    }
}
