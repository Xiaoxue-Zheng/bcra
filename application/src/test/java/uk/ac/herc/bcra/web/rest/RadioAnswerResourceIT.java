package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.RadioAnswer;
import uk.ac.herc.bcra.domain.RadioAnswerItem;
import uk.ac.herc.bcra.repository.RadioAnswerRepository;
import uk.ac.herc.bcra.service.RadioAnswerService;
import uk.ac.herc.bcra.service.dto.RadioAnswerDTO;
import uk.ac.herc.bcra.service.mapper.RadioAnswerMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.RadioAnswerCriteria;
import uk.ac.herc.bcra.service.RadioAnswerQueryService;

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
 * Integration tests for the {@link RadioAnswerResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class RadioAnswerResourceIT {

    @Autowired
    private RadioAnswerRepository radioAnswerRepository;

    @Autowired
    private RadioAnswerMapper radioAnswerMapper;

    @Autowired
    private RadioAnswerService radioAnswerService;

    @Autowired
    private RadioAnswerQueryService radioAnswerQueryService;

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

    private MockMvc restRadioAnswerMockMvc;

    private RadioAnswer radioAnswer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RadioAnswerResource radioAnswerResource = new RadioAnswerResource(radioAnswerService, radioAnswerQueryService);
        this.restRadioAnswerMockMvc = MockMvcBuilders.standaloneSetup(radioAnswerResource)
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
    public static RadioAnswer createEntity(EntityManager em) {
        RadioAnswer radioAnswer = new RadioAnswer();
        return radioAnswer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RadioAnswer createUpdatedEntity(EntityManager em) {
        RadioAnswer radioAnswer = new RadioAnswer();
        return radioAnswer;
    }

    @BeforeEach
    public void initTest() {
        radioAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createRadioAnswer() throws Exception {
        int databaseSizeBeforeCreate = radioAnswerRepository.findAll().size();

        // Create the RadioAnswer
        RadioAnswerDTO radioAnswerDTO = radioAnswerMapper.toDto(radioAnswer);
        restRadioAnswerMockMvc.perform(post("/api/radio-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the RadioAnswer in the database
        List<RadioAnswer> radioAnswerList = radioAnswerRepository.findAll();
        assertThat(radioAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        RadioAnswer testRadioAnswer = radioAnswerList.get(radioAnswerList.size() - 1);
    }

    @Test
    @Transactional
    public void createRadioAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = radioAnswerRepository.findAll().size();

        // Create the RadioAnswer with an existing ID
        radioAnswer.setId(1L);
        RadioAnswerDTO radioAnswerDTO = radioAnswerMapper.toDto(radioAnswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRadioAnswerMockMvc.perform(post("/api/radio-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RadioAnswer in the database
        List<RadioAnswer> radioAnswerList = radioAnswerRepository.findAll();
        assertThat(radioAnswerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRadioAnswers() throws Exception {
        // Initialize the database
        radioAnswerRepository.saveAndFlush(radioAnswer);

        // Get all the radioAnswerList
        restRadioAnswerMockMvc.perform(get("/api/radio-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radioAnswer.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getRadioAnswer() throws Exception {
        // Initialize the database
        radioAnswerRepository.saveAndFlush(radioAnswer);

        // Get the radioAnswer
        restRadioAnswerMockMvc.perform(get("/api/radio-answers/{id}", radioAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(radioAnswer.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllRadioAnswersByRadioAnswerItemIsEqualToSomething() throws Exception {
        // Initialize the database
        radioAnswerRepository.saveAndFlush(radioAnswer);
        RadioAnswerItem radioAnswerItem = RadioAnswerItemResourceIT.createEntity(em);
        em.persist(radioAnswerItem);
        em.flush();
        radioAnswer.setRadioAnswerItem(radioAnswerItem);
        radioAnswerItem.setRadioAnswer(radioAnswer);
        radioAnswerRepository.saveAndFlush(radioAnswer);
        Long radioAnswerItemId = radioAnswerItem.getId();

        // Get all the radioAnswerList where radioAnswerItem equals to radioAnswerItemId
        defaultRadioAnswerShouldBeFound("radioAnswerItemId.equals=" + radioAnswerItemId);

        // Get all the radioAnswerList where radioAnswerItem equals to radioAnswerItemId + 1
        defaultRadioAnswerShouldNotBeFound("radioAnswerItemId.equals=" + (radioAnswerItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRadioAnswerShouldBeFound(String filter) throws Exception {
        restRadioAnswerMockMvc.perform(get("/api/radio-answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radioAnswer.getId().intValue())));

        // Check, that the count call also returns 1
        restRadioAnswerMockMvc.perform(get("/api/radio-answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRadioAnswerShouldNotBeFound(String filter) throws Exception {
        restRadioAnswerMockMvc.perform(get("/api/radio-answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRadioAnswerMockMvc.perform(get("/api/radio-answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRadioAnswer() throws Exception {
        // Get the radioAnswer
        restRadioAnswerMockMvc.perform(get("/api/radio-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRadioAnswer() throws Exception {
        // Initialize the database
        radioAnswerRepository.saveAndFlush(radioAnswer);

        int databaseSizeBeforeUpdate = radioAnswerRepository.findAll().size();

        // Update the radioAnswer
        RadioAnswer updatedRadioAnswer = radioAnswerRepository.findById(radioAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedRadioAnswer are not directly saved in db
        em.detach(updatedRadioAnswer);
        RadioAnswerDTO radioAnswerDTO = radioAnswerMapper.toDto(updatedRadioAnswer);

        restRadioAnswerMockMvc.perform(put("/api/radio-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioAnswerDTO)))
            .andExpect(status().isOk());

        // Validate the RadioAnswer in the database
        List<RadioAnswer> radioAnswerList = radioAnswerRepository.findAll();
        assertThat(radioAnswerList).hasSize(databaseSizeBeforeUpdate);
        RadioAnswer testRadioAnswer = radioAnswerList.get(radioAnswerList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingRadioAnswer() throws Exception {
        int databaseSizeBeforeUpdate = radioAnswerRepository.findAll().size();

        // Create the RadioAnswer
        RadioAnswerDTO radioAnswerDTO = radioAnswerMapper.toDto(radioAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRadioAnswerMockMvc.perform(put("/api/radio-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RadioAnswer in the database
        List<RadioAnswer> radioAnswerList = radioAnswerRepository.findAll();
        assertThat(radioAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRadioAnswer() throws Exception {
        // Initialize the database
        radioAnswerRepository.saveAndFlush(radioAnswer);

        int databaseSizeBeforeDelete = radioAnswerRepository.findAll().size();

        // Delete the radioAnswer
        restRadioAnswerMockMvc.perform(delete("/api/radio-answers/{id}", radioAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RadioAnswer> radioAnswerList = radioAnswerRepository.findAll();
        assertThat(radioAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RadioAnswer.class);
        RadioAnswer radioAnswer1 = new RadioAnswer();
        radioAnswer1.setId(1L);
        RadioAnswer radioAnswer2 = new RadioAnswer();
        radioAnswer2.setId(radioAnswer1.getId());
        assertThat(radioAnswer1).isEqualTo(radioAnswer2);
        radioAnswer2.setId(2L);
        assertThat(radioAnswer1).isNotEqualTo(radioAnswer2);
        radioAnswer1.setId(null);
        assertThat(radioAnswer1).isNotEqualTo(radioAnswer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RadioAnswerDTO.class);
        RadioAnswerDTO radioAnswerDTO1 = new RadioAnswerDTO();
        radioAnswerDTO1.setId(1L);
        RadioAnswerDTO radioAnswerDTO2 = new RadioAnswerDTO();
        assertThat(radioAnswerDTO1).isNotEqualTo(radioAnswerDTO2);
        radioAnswerDTO2.setId(radioAnswerDTO1.getId());
        assertThat(radioAnswerDTO1).isEqualTo(radioAnswerDTO2);
        radioAnswerDTO2.setId(2L);
        assertThat(radioAnswerDTO1).isNotEqualTo(radioAnswerDTO2);
        radioAnswerDTO1.setId(null);
        assertThat(radioAnswerDTO1).isNotEqualTo(radioAnswerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(radioAnswerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(radioAnswerMapper.fromId(null)).isNull();
    }
}
