package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.RadioQuestion;
import uk.ac.herc.bcra.domain.RadioQuestionItem;
import uk.ac.herc.bcra.repository.RadioQuestionRepository;
import uk.ac.herc.bcra.service.RadioQuestionService;
import uk.ac.herc.bcra.service.dto.RadioQuestionDTO;
import uk.ac.herc.bcra.service.mapper.RadioQuestionMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.RadioQuestionCriteria;
import uk.ac.herc.bcra.service.RadioQuestionQueryService;

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
 * Integration tests for the {@link RadioQuestionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class RadioQuestionResourceIT {

    @Autowired
    private RadioQuestionRepository radioQuestionRepository;

    @Autowired
    private RadioQuestionMapper radioQuestionMapper;

    @Autowired
    private RadioQuestionService radioQuestionService;

    @Autowired
    private RadioQuestionQueryService radioQuestionQueryService;

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

    private MockMvc restRadioQuestionMockMvc;

    private RadioQuestion radioQuestion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RadioQuestionResource radioQuestionResource = new RadioQuestionResource(radioQuestionService, radioQuestionQueryService);
        this.restRadioQuestionMockMvc = MockMvcBuilders.standaloneSetup(radioQuestionResource)
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
    public static RadioQuestion createEntity(EntityManager em) {
        RadioQuestion radioQuestion = new RadioQuestion();
        radioQuestion.setUuid("abc123");
        radioQuestion.setText("How?");
        return radioQuestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RadioQuestion createUpdatedEntity(EntityManager em) {
        RadioQuestion radioQuestion = new RadioQuestion();
        return radioQuestion;
    }

    @BeforeEach
    public void initTest() {
        radioQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createRadioQuestion() throws Exception {
        int databaseSizeBeforeCreate = radioQuestionRepository.findAll().size();

        // Create the RadioQuestion
        RadioQuestionDTO radioQuestionDTO = radioQuestionMapper.toDto(radioQuestion);
        restRadioQuestionMockMvc.perform(post("/api/radio-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionDTO)))
            .andExpect(status().isCreated());

        // Validate the RadioQuestion in the database
        List<RadioQuestion> radioQuestionList = radioQuestionRepository.findAll();
        assertThat(radioQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        RadioQuestion testRadioQuestion = radioQuestionList.get(radioQuestionList.size() - 1);
    }

    @Test
    @Transactional
    public void createRadioQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = radioQuestionRepository.findAll().size();

        // Create the RadioQuestion with an existing ID
        radioQuestion.setId(1L);
        RadioQuestionDTO radioQuestionDTO = radioQuestionMapper.toDto(radioQuestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRadioQuestionMockMvc.perform(post("/api/radio-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RadioQuestion in the database
        List<RadioQuestion> radioQuestionList = radioQuestionRepository.findAll();
        assertThat(radioQuestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRadioQuestions() throws Exception {
        // Initialize the database
        radioQuestionRepository.saveAndFlush(radioQuestion);

        // Get all the radioQuestionList
        restRadioQuestionMockMvc.perform(get("/api/radio-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radioQuestion.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getRadioQuestion() throws Exception {
        // Initialize the database
        radioQuestionRepository.saveAndFlush(radioQuestion);

        // Get the radioQuestion
        restRadioQuestionMockMvc.perform(get("/api/radio-questions/{id}", radioQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(radioQuestion.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllRadioQuestionsByRadioQuestionItemIsEqualToSomething() throws Exception {
        // Initialize the database
        radioQuestionRepository.saveAndFlush(radioQuestion);
        RadioQuestionItem radioQuestionItem = RadioQuestionItemResourceIT.createEntity(em);
        em.persist(radioQuestionItem);
        em.flush();
        radioQuestion.addRadioQuestionItem(radioQuestionItem);
        radioQuestionRepository.saveAndFlush(radioQuestion);
        Long radioQuestionItemId = radioQuestionItem.getId();

        // Get all the radioQuestionList where radioQuestionItem equals to radioQuestionItemId
        defaultRadioQuestionShouldBeFound("radioQuestionItemId.equals=" + radioQuestionItemId);

        // Get all the radioQuestionList where radioQuestionItem equals to radioQuestionItemId + 1
        defaultRadioQuestionShouldNotBeFound("radioQuestionItemId.equals=" + (radioQuestionItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRadioQuestionShouldBeFound(String filter) throws Exception {
        restRadioQuestionMockMvc.perform(get("/api/radio-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radioQuestion.getId().intValue())));

        // Check, that the count call also returns 1
        restRadioQuestionMockMvc.perform(get("/api/radio-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRadioQuestionShouldNotBeFound(String filter) throws Exception {
        restRadioQuestionMockMvc.perform(get("/api/radio-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRadioQuestionMockMvc.perform(get("/api/radio-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRadioQuestion() throws Exception {
        // Get the radioQuestion
        restRadioQuestionMockMvc.perform(get("/api/radio-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRadioQuestion() throws Exception {
        // Initialize the database
        radioQuestionRepository.saveAndFlush(radioQuestion);

        int databaseSizeBeforeUpdate = radioQuestionRepository.findAll().size();

        // Update the radioQuestion
        RadioQuestion updatedRadioQuestion = radioQuestionRepository.findById(radioQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedRadioQuestion are not directly saved in db
        em.detach(updatedRadioQuestion);
        RadioQuestionDTO radioQuestionDTO = radioQuestionMapper.toDto(updatedRadioQuestion);
        radioQuestionDTO.setUuid("def321");
        radioQuestionDTO.setText("Why?");
        restRadioQuestionMockMvc.perform(put("/api/radio-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionDTO)))
            .andExpect(status().isOk());

        // Validate the RadioQuestion in the database
         List<RadioQuestion> radioQuestionList = radioQuestionRepository.findAll();
        // assertThat(radioQuestionList).hasSize(databaseSizeBeforeUpdate);
        // RadioQuestion testRadioQuestion = radioQuestionList.get(radioQuestionList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingRadioQuestion() throws Exception {
        int databaseSizeBeforeUpdate = radioQuestionRepository.findAll().size();

        // Create the RadioQuestion
        RadioQuestionDTO radioQuestionDTO = radioQuestionMapper.toDto(radioQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRadioQuestionMockMvc.perform(put("/api/radio-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RadioQuestion in the database
        List<RadioQuestion> radioQuestionList = radioQuestionRepository.findAll();
        assertThat(radioQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRadioQuestion() throws Exception {
        // Initialize the database
        radioQuestionRepository.saveAndFlush(radioQuestion);

        int databaseSizeBeforeDelete = radioQuestionRepository.findAll().size();

        // Delete the radioQuestion
        restRadioQuestionMockMvc.perform(delete("/api/radio-questions/{id}", radioQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RadioQuestion> radioQuestionList = radioQuestionRepository.findAll();
        assertThat(radioQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RadioQuestion.class);
        RadioQuestion radioQuestion1 = new RadioQuestion();
        radioQuestion1.setId(1L);
        RadioQuestion radioQuestion2 = new RadioQuestion();
        radioQuestion2.setId(radioQuestion1.getId());
        assertThat(radioQuestion1).isEqualTo(radioQuestion2);
        radioQuestion2.setId(2L);
        assertThat(radioQuestion1).isNotEqualTo(radioQuestion2);
        radioQuestion1.setId(null);
        assertThat(radioQuestion1).isNotEqualTo(radioQuestion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RadioQuestionDTO.class);
        RadioQuestionDTO radioQuestionDTO1 = new RadioQuestionDTO();
        radioQuestionDTO1.setId(1L);
        RadioQuestionDTO radioQuestionDTO2 = new RadioQuestionDTO();
        assertThat(radioQuestionDTO1).isNotEqualTo(radioQuestionDTO2);
        radioQuestionDTO2.setId(radioQuestionDTO1.getId());
        assertThat(radioQuestionDTO1).isEqualTo(radioQuestionDTO2);
        radioQuestionDTO2.setId(2L);
        assertThat(radioQuestionDTO1).isNotEqualTo(radioQuestionDTO2);
        radioQuestionDTO1.setId(null);
        assertThat(radioQuestionDTO1).isNotEqualTo(radioQuestionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(radioQuestionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(radioQuestionMapper.fromId(null)).isNull();
    }
}
