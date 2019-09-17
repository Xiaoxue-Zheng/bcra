package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.CheckboxQuestion;
import uk.ac.herc.bcra.domain.CheckboxQuestionItem;
import uk.ac.herc.bcra.repository.CheckboxQuestionRepository;
import uk.ac.herc.bcra.service.CheckboxQuestionService;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxQuestionMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionCriteria;
import uk.ac.herc.bcra.service.CheckboxQuestionQueryService;

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
 * Integration tests for the {@link CheckboxQuestionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class CheckboxQuestionResourceIT {

    @Autowired
    private CheckboxQuestionRepository checkboxQuestionRepository;

    @Autowired
    private CheckboxQuestionMapper checkboxQuestionMapper;

    @Autowired
    private CheckboxQuestionService checkboxQuestionService;

    @Autowired
    private CheckboxQuestionQueryService checkboxQuestionQueryService;

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

    private MockMvc restCheckboxQuestionMockMvc;

    private CheckboxQuestion checkboxQuestion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CheckboxQuestionResource checkboxQuestionResource = new CheckboxQuestionResource(checkboxQuestionService, checkboxQuestionQueryService);
        this.restCheckboxQuestionMockMvc = MockMvcBuilders.standaloneSetup(checkboxQuestionResource)
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
    public static CheckboxQuestion createEntity(EntityManager em) {
        CheckboxQuestion checkboxQuestion = new CheckboxQuestion();
        checkboxQuestion.setUuid("abc123");
        checkboxQuestion.setText("How?");
        return checkboxQuestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckboxQuestion createUpdatedEntity(EntityManager em) {
        CheckboxQuestion checkboxQuestion = new CheckboxQuestion();
        return checkboxQuestion;
    }

    @BeforeEach
    public void initTest() {
        checkboxQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckboxQuestion() throws Exception {
        int databaseSizeBeforeCreate = checkboxQuestionRepository.findAll().size();

        // Create the CheckboxQuestion
        CheckboxQuestionDTO checkboxQuestionDTO = checkboxQuestionMapper.toDto(checkboxQuestion);
        restCheckboxQuestionMockMvc.perform(post("/api/checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckboxQuestion in the database
        List<CheckboxQuestion> checkboxQuestionList = checkboxQuestionRepository.findAll();
        assertThat(checkboxQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        CheckboxQuestion testCheckboxQuestion = checkboxQuestionList.get(checkboxQuestionList.size() - 1);
    }

    @Test
    @Transactional
    public void createCheckboxQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkboxQuestionRepository.findAll().size();

        // Create the CheckboxQuestion with an existing ID
        checkboxQuestion.setId(1L);
        CheckboxQuestionDTO checkboxQuestionDTO = checkboxQuestionMapper.toDto(checkboxQuestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckboxQuestionMockMvc.perform(post("/api/checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxQuestion in the database
        List<CheckboxQuestion> checkboxQuestionList = checkboxQuestionRepository.findAll();
        assertThat(checkboxQuestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCheckboxQuestions() throws Exception {
        // Initialize the database
        checkboxQuestionRepository.saveAndFlush(checkboxQuestion);

        // Get all the checkboxQuestionList
        restCheckboxQuestionMockMvc.perform(get("/api/checkbox-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxQuestion.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCheckboxQuestion() throws Exception {
        // Initialize the database
        checkboxQuestionRepository.saveAndFlush(checkboxQuestion);

        // Get the checkboxQuestion
        restCheckboxQuestionMockMvc.perform(get("/api/checkbox-questions/{id}", checkboxQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkboxQuestion.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionsByCheckboxQuestionItemIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxQuestionRepository.saveAndFlush(checkboxQuestion);
        CheckboxQuestionItem checkboxQuestionItem = CheckboxQuestionItemResourceIT.createEntity(em);
        em.persist(checkboxQuestionItem);
        em.flush();
        checkboxQuestion.addCheckboxQuestionItem(checkboxQuestionItem);
        checkboxQuestionRepository.saveAndFlush(checkboxQuestion);
        Long checkboxQuestionItemId = checkboxQuestionItem.getId();

        // Get all the checkboxQuestionList where checkboxQuestionItem equals to checkboxQuestionItemId
        defaultCheckboxQuestionShouldBeFound("checkboxQuestionItemId.equals=" + checkboxQuestionItemId);

        // Get all the checkboxQuestionList where checkboxQuestionItem equals to checkboxQuestionItemId + 1
        defaultCheckboxQuestionShouldNotBeFound("checkboxQuestionItemId.equals=" + (checkboxQuestionItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckboxQuestionShouldBeFound(String filter) throws Exception {
        restCheckboxQuestionMockMvc.perform(get("/api/checkbox-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxQuestion.getId().intValue())));

        // Check, that the count call also returns 1
        restCheckboxQuestionMockMvc.perform(get("/api/checkbox-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckboxQuestionShouldNotBeFound(String filter) throws Exception {
        restCheckboxQuestionMockMvc.perform(get("/api/checkbox-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckboxQuestionMockMvc.perform(get("/api/checkbox-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCheckboxQuestion() throws Exception {
        // Get the checkboxQuestion
        restCheckboxQuestionMockMvc.perform(get("/api/checkbox-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckboxQuestion() throws Exception {
        // Initialize the database
        checkboxQuestionRepository.saveAndFlush(checkboxQuestion);

        int databaseSizeBeforeUpdate = checkboxQuestionRepository.findAll().size();

        // Update the checkboxQuestion
        CheckboxQuestion updatedCheckboxQuestion = checkboxQuestionRepository.findById(checkboxQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedCheckboxQuestion are not directly saved in db
        em.detach(updatedCheckboxQuestion);
        CheckboxQuestionDTO checkboxQuestionDTO = checkboxQuestionMapper.toDto(updatedCheckboxQuestion);
        checkboxQuestionDTO.setUuid("def321");
        checkboxQuestionDTO.setText("Why?");
        restCheckboxQuestionMockMvc.perform(put("/api/checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionDTO)))
            .andExpect(status().isOk());

        // Validate the CheckboxQuestion in the database
        List<CheckboxQuestion> checkboxQuestionList = checkboxQuestionRepository.findAll();
        assertThat(checkboxQuestionList).hasSize(databaseSizeBeforeUpdate);
        CheckboxQuestion testCheckboxQuestion = checkboxQuestionList.get(checkboxQuestionList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckboxQuestion() throws Exception {
        int databaseSizeBeforeUpdate = checkboxQuestionRepository.findAll().size();

        // Create the CheckboxQuestion
        CheckboxQuestionDTO checkboxQuestionDTO = checkboxQuestionMapper.toDto(checkboxQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckboxQuestionMockMvc.perform(put("/api/checkbox-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxQuestion in the database
        List<CheckboxQuestion> checkboxQuestionList = checkboxQuestionRepository.findAll();
        assertThat(checkboxQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckboxQuestion() throws Exception {
        // Initialize the database
        checkboxQuestionRepository.saveAndFlush(checkboxQuestion);

        int databaseSizeBeforeDelete = checkboxQuestionRepository.findAll().size();

        // Delete the checkboxQuestion
        restCheckboxQuestionMockMvc.perform(delete("/api/checkbox-questions/{id}", checkboxQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckboxQuestion> checkboxQuestionList = checkboxQuestionRepository.findAll();
        assertThat(checkboxQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxQuestion.class);
        CheckboxQuestion checkboxQuestion1 = new CheckboxQuestion();
        checkboxQuestion1.setId(1L);
        CheckboxQuestion checkboxQuestion2 = new CheckboxQuestion();
        checkboxQuestion2.setId(checkboxQuestion1.getId());
        assertThat(checkboxQuestion1).isEqualTo(checkboxQuestion2);
        checkboxQuestion2.setId(2L);
        assertThat(checkboxQuestion1).isNotEqualTo(checkboxQuestion2);
        checkboxQuestion1.setId(null);
        assertThat(checkboxQuestion1).isNotEqualTo(checkboxQuestion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxQuestionDTO.class);
        CheckboxQuestionDTO checkboxQuestionDTO1 = new CheckboxQuestionDTO();
        checkboxQuestionDTO1.setId(1L);
        CheckboxQuestionDTO checkboxQuestionDTO2 = new CheckboxQuestionDTO();
        assertThat(checkboxQuestionDTO1).isNotEqualTo(checkboxQuestionDTO2);
        checkboxQuestionDTO2.setId(checkboxQuestionDTO1.getId());
        assertThat(checkboxQuestionDTO1).isEqualTo(checkboxQuestionDTO2);
        checkboxQuestionDTO2.setId(2L);
        assertThat(checkboxQuestionDTO1).isNotEqualTo(checkboxQuestionDTO2);
        checkboxQuestionDTO1.setId(null);
        assertThat(checkboxQuestionDTO1).isNotEqualTo(checkboxQuestionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(checkboxQuestionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(checkboxQuestionMapper.fromId(null)).isNull();
    }
}
