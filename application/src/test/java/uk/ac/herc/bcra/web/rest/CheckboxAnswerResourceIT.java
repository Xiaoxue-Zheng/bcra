package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.CheckboxAnswer;
import uk.ac.herc.bcra.domain.CheckboxAnswerItem;
import uk.ac.herc.bcra.repository.CheckboxAnswerRepository;
import uk.ac.herc.bcra.service.CheckboxAnswerService;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxAnswerMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerCriteria;
import uk.ac.herc.bcra.service.CheckboxAnswerQueryService;

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
 * Integration tests for the {@link CheckboxAnswerResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class CheckboxAnswerResourceIT {

    @Autowired
    private CheckboxAnswerRepository checkboxAnswerRepository;

    @Autowired
    private CheckboxAnswerMapper checkboxAnswerMapper;

    @Autowired
    private CheckboxAnswerService checkboxAnswerService;

    @Autowired
    private CheckboxAnswerQueryService checkboxAnswerQueryService;

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

    private MockMvc restCheckboxAnswerMockMvc;

    private CheckboxAnswer checkboxAnswer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CheckboxAnswerResource checkboxAnswerResource = new CheckboxAnswerResource(checkboxAnswerService, checkboxAnswerQueryService);
        this.restCheckboxAnswerMockMvc = MockMvcBuilders.standaloneSetup(checkboxAnswerResource)
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
    public static CheckboxAnswer createEntity(EntityManager em) {
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer();
        return checkboxAnswer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckboxAnswer createUpdatedEntity(EntityManager em) {
        CheckboxAnswer checkboxAnswer = new CheckboxAnswer();
        return checkboxAnswer;
    }

    @BeforeEach
    public void initTest() {
        checkboxAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckboxAnswer() throws Exception {
        int databaseSizeBeforeCreate = checkboxAnswerRepository.findAll().size();

        // Create the CheckboxAnswer
        CheckboxAnswerDTO checkboxAnswerDTO = checkboxAnswerMapper.toDto(checkboxAnswer);
        restCheckboxAnswerMockMvc.perform(post("/api/checkbox-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckboxAnswer in the database
        List<CheckboxAnswer> checkboxAnswerList = checkboxAnswerRepository.findAll();
        assertThat(checkboxAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        CheckboxAnswer testCheckboxAnswer = checkboxAnswerList.get(checkboxAnswerList.size() - 1);
    }

    @Test
    @Transactional
    public void createCheckboxAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkboxAnswerRepository.findAll().size();

        // Create the CheckboxAnswer with an existing ID
        checkboxAnswer.setId(1L);
        CheckboxAnswerDTO checkboxAnswerDTO = checkboxAnswerMapper.toDto(checkboxAnswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckboxAnswerMockMvc.perform(post("/api/checkbox-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxAnswer in the database
        List<CheckboxAnswer> checkboxAnswerList = checkboxAnswerRepository.findAll();
        assertThat(checkboxAnswerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCheckboxAnswers() throws Exception {
        // Initialize the database
        checkboxAnswerRepository.saveAndFlush(checkboxAnswer);

        // Get all the checkboxAnswerList
        restCheckboxAnswerMockMvc.perform(get("/api/checkbox-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxAnswer.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCheckboxAnswer() throws Exception {
        // Initialize the database
        checkboxAnswerRepository.saveAndFlush(checkboxAnswer);

        // Get the checkboxAnswer
        restCheckboxAnswerMockMvc.perform(get("/api/checkbox-answers/{id}", checkboxAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkboxAnswer.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllCheckboxAnswersByCheckboxAnswerItemIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxAnswerRepository.saveAndFlush(checkboxAnswer);
        CheckboxAnswerItem checkboxAnswerItem = CheckboxAnswerItemResourceIT.createEntity(em);
        em.persist(checkboxAnswerItem);
        em.flush();
        checkboxAnswer.addCheckboxAnswerItem(checkboxAnswerItem);
        checkboxAnswerRepository.saveAndFlush(checkboxAnswer);
        Long checkboxAnswerItemId = checkboxAnswerItem.getId();

        // Get all the checkboxAnswerList where checkboxAnswerItem equals to checkboxAnswerItemId
        defaultCheckboxAnswerShouldBeFound("checkboxAnswerItemId.equals=" + checkboxAnswerItemId);

        // Get all the checkboxAnswerList where checkboxAnswerItem equals to checkboxAnswerItemId + 1
        defaultCheckboxAnswerShouldNotBeFound("checkboxAnswerItemId.equals=" + (checkboxAnswerItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckboxAnswerShouldBeFound(String filter) throws Exception {
        restCheckboxAnswerMockMvc.perform(get("/api/checkbox-answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxAnswer.getId().intValue())));

        // Check, that the count call also returns 1
        restCheckboxAnswerMockMvc.perform(get("/api/checkbox-answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckboxAnswerShouldNotBeFound(String filter) throws Exception {
        restCheckboxAnswerMockMvc.perform(get("/api/checkbox-answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckboxAnswerMockMvc.perform(get("/api/checkbox-answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCheckboxAnswer() throws Exception {
        // Get the checkboxAnswer
        restCheckboxAnswerMockMvc.perform(get("/api/checkbox-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckboxAnswer() throws Exception {
        // Initialize the database
        checkboxAnswerRepository.saveAndFlush(checkboxAnswer);

        int databaseSizeBeforeUpdate = checkboxAnswerRepository.findAll().size();

        // Update the checkboxAnswer
        CheckboxAnswer updatedCheckboxAnswer = checkboxAnswerRepository.findById(checkboxAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedCheckboxAnswer are not directly saved in db
        em.detach(updatedCheckboxAnswer);
        CheckboxAnswerDTO checkboxAnswerDTO = checkboxAnswerMapper.toDto(updatedCheckboxAnswer);

        restCheckboxAnswerMockMvc.perform(put("/api/checkbox-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxAnswerDTO)))
            .andExpect(status().isOk());

        // Validate the CheckboxAnswer in the database
        List<CheckboxAnswer> checkboxAnswerList = checkboxAnswerRepository.findAll();
        assertThat(checkboxAnswerList).hasSize(databaseSizeBeforeUpdate);
        CheckboxAnswer testCheckboxAnswer = checkboxAnswerList.get(checkboxAnswerList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckboxAnswer() throws Exception {
        int databaseSizeBeforeUpdate = checkboxAnswerRepository.findAll().size();

        // Create the CheckboxAnswer
        CheckboxAnswerDTO checkboxAnswerDTO = checkboxAnswerMapper.toDto(checkboxAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckboxAnswerMockMvc.perform(put("/api/checkbox-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxAnswer in the database
        List<CheckboxAnswer> checkboxAnswerList = checkboxAnswerRepository.findAll();
        assertThat(checkboxAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckboxAnswer() throws Exception {
        // Initialize the database
        checkboxAnswerRepository.saveAndFlush(checkboxAnswer);

        int databaseSizeBeforeDelete = checkboxAnswerRepository.findAll().size();

        // Delete the checkboxAnswer
        restCheckboxAnswerMockMvc.perform(delete("/api/checkbox-answers/{id}", checkboxAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckboxAnswer> checkboxAnswerList = checkboxAnswerRepository.findAll();
        assertThat(checkboxAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxAnswer.class);
        CheckboxAnswer checkboxAnswer1 = new CheckboxAnswer();
        checkboxAnswer1.setId(1L);
        CheckboxAnswer checkboxAnswer2 = new CheckboxAnswer();
        checkboxAnswer2.setId(checkboxAnswer1.getId());
        assertThat(checkboxAnswer1).isEqualTo(checkboxAnswer2);
        checkboxAnswer2.setId(2L);
        assertThat(checkboxAnswer1).isNotEqualTo(checkboxAnswer2);
        checkboxAnswer1.setId(null);
        assertThat(checkboxAnswer1).isNotEqualTo(checkboxAnswer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxAnswerDTO.class);
        CheckboxAnswerDTO checkboxAnswerDTO1 = new CheckboxAnswerDTO();
        checkboxAnswerDTO1.setId(1L);
        CheckboxAnswerDTO checkboxAnswerDTO2 = new CheckboxAnswerDTO();
        assertThat(checkboxAnswerDTO1).isNotEqualTo(checkboxAnswerDTO2);
        checkboxAnswerDTO2.setId(checkboxAnswerDTO1.getId());
        assertThat(checkboxAnswerDTO1).isEqualTo(checkboxAnswerDTO2);
        checkboxAnswerDTO2.setId(2L);
        assertThat(checkboxAnswerDTO1).isNotEqualTo(checkboxAnswerDTO2);
        checkboxAnswerDTO1.setId(null);
        assertThat(checkboxAnswerDTO1).isNotEqualTo(checkboxAnswerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(checkboxAnswerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(checkboxAnswerMapper.fromId(null)).isNull();
    }
}
