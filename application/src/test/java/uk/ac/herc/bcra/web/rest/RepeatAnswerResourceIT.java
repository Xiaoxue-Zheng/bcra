package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.RepeatAnswer;
import uk.ac.herc.bcra.repository.RepeatAnswerRepository;
import uk.ac.herc.bcra.service.RepeatAnswerService;
import uk.ac.herc.bcra.service.dto.RepeatAnswerDTO;
import uk.ac.herc.bcra.service.mapper.RepeatAnswerMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.RepeatAnswerCriteria;
import uk.ac.herc.bcra.service.RepeatAnswerQueryService;

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
 * Integration tests for the {@link RepeatAnswerResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class RepeatAnswerResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;
    private static final Integer SMALLER_QUANTITY = 1 - 1;

    @Autowired
    private RepeatAnswerRepository repeatAnswerRepository;

    @Autowired
    private RepeatAnswerMapper repeatAnswerMapper;

    @Autowired
    private RepeatAnswerService repeatAnswerService;

    @Autowired
    private RepeatAnswerQueryService repeatAnswerQueryService;

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

    private MockMvc restRepeatAnswerMockMvc;

    private RepeatAnswer repeatAnswer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RepeatAnswerResource repeatAnswerResource = new RepeatAnswerResource(repeatAnswerService, repeatAnswerQueryService);
        this.restRepeatAnswerMockMvc = MockMvcBuilders.standaloneSetup(repeatAnswerResource)
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
    public static RepeatAnswer createEntity(EntityManager em) {
        RepeatAnswer repeatAnswer = new RepeatAnswer()
            .quantity(DEFAULT_QUANTITY);
        return repeatAnswer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepeatAnswer createUpdatedEntity(EntityManager em) {
        RepeatAnswer repeatAnswer = new RepeatAnswer()
            .quantity(UPDATED_QUANTITY);
        return repeatAnswer;
    }

    @BeforeEach
    public void initTest() {
        repeatAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createRepeatAnswer() throws Exception {
        int databaseSizeBeforeCreate = repeatAnswerRepository.findAll().size();

        // Create the RepeatAnswer
        RepeatAnswerDTO repeatAnswerDTO = repeatAnswerMapper.toDto(repeatAnswer);
        restRepeatAnswerMockMvc.perform(post("/api/repeat-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the RepeatAnswer in the database
        List<RepeatAnswer> repeatAnswerList = repeatAnswerRepository.findAll();
        assertThat(repeatAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        RepeatAnswer testRepeatAnswer = repeatAnswerList.get(repeatAnswerList.size() - 1);
        assertThat(testRepeatAnswer.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createRepeatAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = repeatAnswerRepository.findAll().size();

        // Create the RepeatAnswer with an existing ID
        repeatAnswer.setId(1L);
        RepeatAnswerDTO repeatAnswerDTO = repeatAnswerMapper.toDto(repeatAnswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepeatAnswerMockMvc.perform(post("/api/repeat-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RepeatAnswer in the database
        List<RepeatAnswer> repeatAnswerList = repeatAnswerRepository.findAll();
        assertThat(repeatAnswerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = repeatAnswerRepository.findAll().size();
        // set the field null
        repeatAnswer.setQuantity(null);

        // Create the RepeatAnswer, which fails.
        RepeatAnswerDTO repeatAnswerDTO = repeatAnswerMapper.toDto(repeatAnswer);

        restRepeatAnswerMockMvc.perform(post("/api/repeat-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatAnswerDTO)))
            .andExpect(status().isBadRequest());

        List<RepeatAnswer> repeatAnswerList = repeatAnswerRepository.findAll();
        assertThat(repeatAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRepeatAnswers() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        // Get all the repeatAnswerList
        restRepeatAnswerMockMvc.perform(get("/api/repeat-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repeatAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getRepeatAnswer() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        // Get the repeatAnswer
        restRepeatAnswerMockMvc.perform(get("/api/repeat-answers/{id}", repeatAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(repeatAnswer.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getAllRepeatAnswersByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        // Get all the repeatAnswerList where quantity equals to DEFAULT_QUANTITY
        defaultRepeatAnswerShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the repeatAnswerList where quantity equals to UPDATED_QUANTITY
        defaultRepeatAnswerShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllRepeatAnswersByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        // Get all the repeatAnswerList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultRepeatAnswerShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the repeatAnswerList where quantity equals to UPDATED_QUANTITY
        defaultRepeatAnswerShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllRepeatAnswersByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        // Get all the repeatAnswerList where quantity is not null
        defaultRepeatAnswerShouldBeFound("quantity.specified=true");

        // Get all the repeatAnswerList where quantity is null
        defaultRepeatAnswerShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllRepeatAnswersByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        // Get all the repeatAnswerList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultRepeatAnswerShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the repeatAnswerList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultRepeatAnswerShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllRepeatAnswersByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        // Get all the repeatAnswerList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultRepeatAnswerShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the repeatAnswerList where quantity is less than or equal to SMALLER_QUANTITY
        defaultRepeatAnswerShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllRepeatAnswersByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        // Get all the repeatAnswerList where quantity is less than DEFAULT_QUANTITY
        defaultRepeatAnswerShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the repeatAnswerList where quantity is less than UPDATED_QUANTITY
        defaultRepeatAnswerShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllRepeatAnswersByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        // Get all the repeatAnswerList where quantity is greater than DEFAULT_QUANTITY
        defaultRepeatAnswerShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the repeatAnswerList where quantity is greater than SMALLER_QUANTITY
        defaultRepeatAnswerShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRepeatAnswerShouldBeFound(String filter) throws Exception {
        restRepeatAnswerMockMvc.perform(get("/api/repeat-answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repeatAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));

        // Check, that the count call also returns 1
        restRepeatAnswerMockMvc.perform(get("/api/repeat-answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRepeatAnswerShouldNotBeFound(String filter) throws Exception {
        restRepeatAnswerMockMvc.perform(get("/api/repeat-answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRepeatAnswerMockMvc.perform(get("/api/repeat-answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRepeatAnswer() throws Exception {
        // Get the repeatAnswer
        restRepeatAnswerMockMvc.perform(get("/api/repeat-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepeatAnswer() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        int databaseSizeBeforeUpdate = repeatAnswerRepository.findAll().size();

        // Update the repeatAnswer
        RepeatAnswer updatedRepeatAnswer = repeatAnswerRepository.findById(repeatAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedRepeatAnswer are not directly saved in db
        em.detach(updatedRepeatAnswer);
        updatedRepeatAnswer
            .quantity(UPDATED_QUANTITY);
        RepeatAnswerDTO repeatAnswerDTO = repeatAnswerMapper.toDto(updatedRepeatAnswer);

        restRepeatAnswerMockMvc.perform(put("/api/repeat-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatAnswerDTO)))
            .andExpect(status().isOk());

        // Validate the RepeatAnswer in the database
        List<RepeatAnswer> repeatAnswerList = repeatAnswerRepository.findAll();
        assertThat(repeatAnswerList).hasSize(databaseSizeBeforeUpdate);
        RepeatAnswer testRepeatAnswer = repeatAnswerList.get(repeatAnswerList.size() - 1);
        assertThat(testRepeatAnswer.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingRepeatAnswer() throws Exception {
        int databaseSizeBeforeUpdate = repeatAnswerRepository.findAll().size();

        // Create the RepeatAnswer
        RepeatAnswerDTO repeatAnswerDTO = repeatAnswerMapper.toDto(repeatAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepeatAnswerMockMvc.perform(put("/api/repeat-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RepeatAnswer in the database
        List<RepeatAnswer> repeatAnswerList = repeatAnswerRepository.findAll();
        assertThat(repeatAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRepeatAnswer() throws Exception {
        // Initialize the database
        repeatAnswerRepository.saveAndFlush(repeatAnswer);

        int databaseSizeBeforeDelete = repeatAnswerRepository.findAll().size();

        // Delete the repeatAnswer
        restRepeatAnswerMockMvc.perform(delete("/api/repeat-answers/{id}", repeatAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RepeatAnswer> repeatAnswerList = repeatAnswerRepository.findAll();
        assertThat(repeatAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepeatAnswer.class);
        RepeatAnswer repeatAnswer1 = new RepeatAnswer();
        repeatAnswer1.setId(1L);
        RepeatAnswer repeatAnswer2 = new RepeatAnswer();
        repeatAnswer2.setId(repeatAnswer1.getId());
        assertThat(repeatAnswer1).isEqualTo(repeatAnswer2);
        repeatAnswer2.setId(2L);
        assertThat(repeatAnswer1).isNotEqualTo(repeatAnswer2);
        repeatAnswer1.setId(null);
        assertThat(repeatAnswer1).isNotEqualTo(repeatAnswer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepeatAnswerDTO.class);
        RepeatAnswerDTO repeatAnswerDTO1 = new RepeatAnswerDTO();
        repeatAnswerDTO1.setId(1L);
        RepeatAnswerDTO repeatAnswerDTO2 = new RepeatAnswerDTO();
        assertThat(repeatAnswerDTO1).isNotEqualTo(repeatAnswerDTO2);
        repeatAnswerDTO2.setId(repeatAnswerDTO1.getId());
        assertThat(repeatAnswerDTO1).isEqualTo(repeatAnswerDTO2);
        repeatAnswerDTO2.setId(2L);
        assertThat(repeatAnswerDTO1).isNotEqualTo(repeatAnswerDTO2);
        repeatAnswerDTO1.setId(null);
        assertThat(repeatAnswerDTO1).isNotEqualTo(repeatAnswerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(repeatAnswerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(repeatAnswerMapper.fromId(null)).isNull();
    }
}
