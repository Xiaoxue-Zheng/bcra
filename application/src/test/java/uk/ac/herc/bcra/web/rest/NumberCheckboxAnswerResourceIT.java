package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.NumberCheckboxAnswer;
import uk.ac.herc.bcra.repository.NumberCheckboxAnswerRepository;
import uk.ac.herc.bcra.service.NumberCheckboxAnswerService;
import uk.ac.herc.bcra.service.dto.NumberCheckboxAnswerDTO;
import uk.ac.herc.bcra.service.mapper.NumberCheckboxAnswerMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.NumberCheckboxAnswerCriteria;
import uk.ac.herc.bcra.service.NumberCheckboxAnswerQueryService;

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
 * Integration tests for the {@link NumberCheckboxAnswerResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class NumberCheckboxAnswerResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;
    private static final Integer SMALLER_NUMBER = 1 - 1;

    private static final Boolean DEFAULT_CHECK = false;
    private static final Boolean UPDATED_CHECK = true;

    @Autowired
    private NumberCheckboxAnswerRepository numberCheckboxAnswerRepository;

    @Autowired
    private NumberCheckboxAnswerMapper numberCheckboxAnswerMapper;

    @Autowired
    private NumberCheckboxAnswerService numberCheckboxAnswerService;

    @Autowired
    private NumberCheckboxAnswerQueryService numberCheckboxAnswerQueryService;

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

    private MockMvc restNumberCheckboxAnswerMockMvc;

    private NumberCheckboxAnswer numberCheckboxAnswer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NumberCheckboxAnswerResource numberCheckboxAnswerResource = new NumberCheckboxAnswerResource(numberCheckboxAnswerService, numberCheckboxAnswerQueryService);
        this.restNumberCheckboxAnswerMockMvc = MockMvcBuilders.standaloneSetup(numberCheckboxAnswerResource)
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
    public static NumberCheckboxAnswer createEntity(EntityManager em) {
        NumberCheckboxAnswer numberCheckboxAnswer = new NumberCheckboxAnswer()
            .number(DEFAULT_NUMBER)
            .check(DEFAULT_CHECK);
        return numberCheckboxAnswer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumberCheckboxAnswer createUpdatedEntity(EntityManager em) {
        NumberCheckboxAnswer numberCheckboxAnswer = new NumberCheckboxAnswer()
            .number(UPDATED_NUMBER)
            .check(UPDATED_CHECK);
        return numberCheckboxAnswer;
    }

    @BeforeEach
    public void initTest() {
        numberCheckboxAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createNumberCheckboxAnswer() throws Exception {
        int databaseSizeBeforeCreate = numberCheckboxAnswerRepository.findAll().size();

        // Create the NumberCheckboxAnswer
        NumberCheckboxAnswerDTO numberCheckboxAnswerDTO = numberCheckboxAnswerMapper.toDto(numberCheckboxAnswer);
        restNumberCheckboxAnswerMockMvc.perform(post("/api/number-checkbox-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the NumberCheckboxAnswer in the database
        List<NumberCheckboxAnswer> numberCheckboxAnswerList = numberCheckboxAnswerRepository.findAll();
        assertThat(numberCheckboxAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        NumberCheckboxAnswer testNumberCheckboxAnswer = numberCheckboxAnswerList.get(numberCheckboxAnswerList.size() - 1);
        assertThat(testNumberCheckboxAnswer.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testNumberCheckboxAnswer.isCheck()).isEqualTo(DEFAULT_CHECK);
    }

    @Test
    @Transactional
    public void createNumberCheckboxAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = numberCheckboxAnswerRepository.findAll().size();

        // Create the NumberCheckboxAnswer with an existing ID
        numberCheckboxAnswer.setId(1L);
        NumberCheckboxAnswerDTO numberCheckboxAnswerDTO = numberCheckboxAnswerMapper.toDto(numberCheckboxAnswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumberCheckboxAnswerMockMvc.perform(post("/api/number-checkbox-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NumberCheckboxAnswer in the database
        List<NumberCheckboxAnswer> numberCheckboxAnswerList = numberCheckboxAnswerRepository.findAll();
        assertThat(numberCheckboxAnswerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNumberCheckboxAnswers() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList
        restNumberCheckboxAnswerMockMvc.perform(get("/api/number-checkbox-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numberCheckboxAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].check").value(hasItem(DEFAULT_CHECK.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getNumberCheckboxAnswer() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get the numberCheckboxAnswer
        restNumberCheckboxAnswerMockMvc.perform(get("/api/number-checkbox-answers/{id}", numberCheckboxAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(numberCheckboxAnswer.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.check").value(DEFAULT_CHECK.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where number equals to DEFAULT_NUMBER
        defaultNumberCheckboxAnswerShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the numberCheckboxAnswerList where number equals to UPDATED_NUMBER
        defaultNumberCheckboxAnswerShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultNumberCheckboxAnswerShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the numberCheckboxAnswerList where number equals to UPDATED_NUMBER
        defaultNumberCheckboxAnswerShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where number is not null
        defaultNumberCheckboxAnswerShouldBeFound("number.specified=true");

        // Get all the numberCheckboxAnswerList where number is null
        defaultNumberCheckboxAnswerShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where number is greater than or equal to DEFAULT_NUMBER
        defaultNumberCheckboxAnswerShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the numberCheckboxAnswerList where number is greater than or equal to UPDATED_NUMBER
        defaultNumberCheckboxAnswerShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where number is less than or equal to DEFAULT_NUMBER
        defaultNumberCheckboxAnswerShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the numberCheckboxAnswerList where number is less than or equal to SMALLER_NUMBER
        defaultNumberCheckboxAnswerShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where number is less than DEFAULT_NUMBER
        defaultNumberCheckboxAnswerShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the numberCheckboxAnswerList where number is less than UPDATED_NUMBER
        defaultNumberCheckboxAnswerShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where number is greater than DEFAULT_NUMBER
        defaultNumberCheckboxAnswerShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the numberCheckboxAnswerList where number is greater than SMALLER_NUMBER
        defaultNumberCheckboxAnswerShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByCheckIsEqualToSomething() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where check equals to DEFAULT_CHECK
        defaultNumberCheckboxAnswerShouldBeFound("check.equals=" + DEFAULT_CHECK);

        // Get all the numberCheckboxAnswerList where check equals to UPDATED_CHECK
        defaultNumberCheckboxAnswerShouldNotBeFound("check.equals=" + UPDATED_CHECK);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByCheckIsInShouldWork() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where check in DEFAULT_CHECK or UPDATED_CHECK
        defaultNumberCheckboxAnswerShouldBeFound("check.in=" + DEFAULT_CHECK + "," + UPDATED_CHECK);

        // Get all the numberCheckboxAnswerList where check equals to UPDATED_CHECK
        defaultNumberCheckboxAnswerShouldNotBeFound("check.in=" + UPDATED_CHECK);
    }

    @Test
    @Transactional
    public void getAllNumberCheckboxAnswersByCheckIsNullOrNotNull() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        // Get all the numberCheckboxAnswerList where check is not null
        defaultNumberCheckboxAnswerShouldBeFound("check.specified=true");

        // Get all the numberCheckboxAnswerList where check is null
        defaultNumberCheckboxAnswerShouldNotBeFound("check.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNumberCheckboxAnswerShouldBeFound(String filter) throws Exception {
        restNumberCheckboxAnswerMockMvc.perform(get("/api/number-checkbox-answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numberCheckboxAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].check").value(hasItem(DEFAULT_CHECK.booleanValue())));

        // Check, that the count call also returns 1
        restNumberCheckboxAnswerMockMvc.perform(get("/api/number-checkbox-answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNumberCheckboxAnswerShouldNotBeFound(String filter) throws Exception {
        restNumberCheckboxAnswerMockMvc.perform(get("/api/number-checkbox-answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNumberCheckboxAnswerMockMvc.perform(get("/api/number-checkbox-answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNumberCheckboxAnswer() throws Exception {
        // Get the numberCheckboxAnswer
        restNumberCheckboxAnswerMockMvc.perform(get("/api/number-checkbox-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNumberCheckboxAnswer() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        int databaseSizeBeforeUpdate = numberCheckboxAnswerRepository.findAll().size();

        // Update the numberCheckboxAnswer
        NumberCheckboxAnswer updatedNumberCheckboxAnswer = numberCheckboxAnswerRepository.findById(numberCheckboxAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedNumberCheckboxAnswer are not directly saved in db
        em.detach(updatedNumberCheckboxAnswer);
        updatedNumberCheckboxAnswer
            .number(UPDATED_NUMBER)
            .check(UPDATED_CHECK);
        NumberCheckboxAnswerDTO numberCheckboxAnswerDTO = numberCheckboxAnswerMapper.toDto(updatedNumberCheckboxAnswer);

        restNumberCheckboxAnswerMockMvc.perform(put("/api/number-checkbox-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxAnswerDTO)))
            .andExpect(status().isOk());

        // Validate the NumberCheckboxAnswer in the database
        List<NumberCheckboxAnswer> numberCheckboxAnswerList = numberCheckboxAnswerRepository.findAll();
        assertThat(numberCheckboxAnswerList).hasSize(databaseSizeBeforeUpdate);
        NumberCheckboxAnswer testNumberCheckboxAnswer = numberCheckboxAnswerList.get(numberCheckboxAnswerList.size() - 1);
        assertThat(testNumberCheckboxAnswer.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testNumberCheckboxAnswer.isCheck()).isEqualTo(UPDATED_CHECK);
    }

    @Test
    @Transactional
    public void updateNonExistingNumberCheckboxAnswer() throws Exception {
        int databaseSizeBeforeUpdate = numberCheckboxAnswerRepository.findAll().size();

        // Create the NumberCheckboxAnswer
        NumberCheckboxAnswerDTO numberCheckboxAnswerDTO = numberCheckboxAnswerMapper.toDto(numberCheckboxAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumberCheckboxAnswerMockMvc.perform(put("/api/number-checkbox-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(numberCheckboxAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NumberCheckboxAnswer in the database
        List<NumberCheckboxAnswer> numberCheckboxAnswerList = numberCheckboxAnswerRepository.findAll();
        assertThat(numberCheckboxAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNumberCheckboxAnswer() throws Exception {
        // Initialize the database
        numberCheckboxAnswerRepository.saveAndFlush(numberCheckboxAnswer);

        int databaseSizeBeforeDelete = numberCheckboxAnswerRepository.findAll().size();

        // Delete the numberCheckboxAnswer
        restNumberCheckboxAnswerMockMvc.perform(delete("/api/number-checkbox-answers/{id}", numberCheckboxAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NumberCheckboxAnswer> numberCheckboxAnswerList = numberCheckboxAnswerRepository.findAll();
        assertThat(numberCheckboxAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumberCheckboxAnswer.class);
        NumberCheckboxAnswer numberCheckboxAnswer1 = new NumberCheckboxAnswer();
        numberCheckboxAnswer1.setId(1L);
        NumberCheckboxAnswer numberCheckboxAnswer2 = new NumberCheckboxAnswer();
        numberCheckboxAnswer2.setId(numberCheckboxAnswer1.getId());
        assertThat(numberCheckboxAnswer1).isEqualTo(numberCheckboxAnswer2);
        numberCheckboxAnswer2.setId(2L);
        assertThat(numberCheckboxAnswer1).isNotEqualTo(numberCheckboxAnswer2);
        numberCheckboxAnswer1.setId(null);
        assertThat(numberCheckboxAnswer1).isNotEqualTo(numberCheckboxAnswer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumberCheckboxAnswerDTO.class);
        NumberCheckboxAnswerDTO numberCheckboxAnswerDTO1 = new NumberCheckboxAnswerDTO();
        numberCheckboxAnswerDTO1.setId(1L);
        NumberCheckboxAnswerDTO numberCheckboxAnswerDTO2 = new NumberCheckboxAnswerDTO();
        assertThat(numberCheckboxAnswerDTO1).isNotEqualTo(numberCheckboxAnswerDTO2);
        numberCheckboxAnswerDTO2.setId(numberCheckboxAnswerDTO1.getId());
        assertThat(numberCheckboxAnswerDTO1).isEqualTo(numberCheckboxAnswerDTO2);
        numberCheckboxAnswerDTO2.setId(2L);
        assertThat(numberCheckboxAnswerDTO1).isNotEqualTo(numberCheckboxAnswerDTO2);
        numberCheckboxAnswerDTO1.setId(null);
        assertThat(numberCheckboxAnswerDTO1).isNotEqualTo(numberCheckboxAnswerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(numberCheckboxAnswerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(numberCheckboxAnswerMapper.fromId(null)).isNull();
    }
}
