package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.CheckboxDisplayCondition;
import uk.ac.herc.bcra.domain.CheckboxQuestionItem;
import uk.ac.herc.bcra.repository.CheckboxDisplayConditionRepository;
import uk.ac.herc.bcra.service.CheckboxDisplayConditionService;
import uk.ac.herc.bcra.service.dto.CheckboxDisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxDisplayConditionMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.CheckboxDisplayConditionCriteria;
import uk.ac.herc.bcra.service.CheckboxDisplayConditionQueryService;

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
 * Integration tests for the {@link CheckboxDisplayConditionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class CheckboxDisplayConditionResourceIT {

    @Autowired
    private CheckboxDisplayConditionRepository checkboxDisplayConditionRepository;

    @Autowired
    private CheckboxDisplayConditionMapper checkboxDisplayConditionMapper;

    @Autowired
    private CheckboxDisplayConditionService checkboxDisplayConditionService;

    @Autowired
    private CheckboxDisplayConditionQueryService checkboxDisplayConditionQueryService;

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

    private MockMvc restCheckboxDisplayConditionMockMvc;

    private CheckboxDisplayCondition checkboxDisplayCondition;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CheckboxDisplayConditionResource checkboxDisplayConditionResource = new CheckboxDisplayConditionResource(checkboxDisplayConditionService, checkboxDisplayConditionQueryService);
        this.restCheckboxDisplayConditionMockMvc = MockMvcBuilders.standaloneSetup(checkboxDisplayConditionResource)
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
    public static CheckboxDisplayCondition createEntity(EntityManager em) {
        CheckboxDisplayCondition checkboxDisplayCondition = new CheckboxDisplayCondition();
        return checkboxDisplayCondition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckboxDisplayCondition createUpdatedEntity(EntityManager em) {
        CheckboxDisplayCondition checkboxDisplayCondition = new CheckboxDisplayCondition();
        return checkboxDisplayCondition;
    }

    @BeforeEach
    public void initTest() {
        checkboxDisplayCondition = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckboxDisplayCondition() throws Exception {
        int databaseSizeBeforeCreate = checkboxDisplayConditionRepository.findAll().size();

        // Create the CheckboxDisplayCondition
        CheckboxDisplayConditionDTO checkboxDisplayConditionDTO = checkboxDisplayConditionMapper.toDto(checkboxDisplayCondition);
        restCheckboxDisplayConditionMockMvc.perform(post("/api/checkbox-display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxDisplayConditionDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckboxDisplayCondition in the database
        List<CheckboxDisplayCondition> checkboxDisplayConditionList = checkboxDisplayConditionRepository.findAll();
        assertThat(checkboxDisplayConditionList).hasSize(databaseSizeBeforeCreate + 1);
        CheckboxDisplayCondition testCheckboxDisplayCondition = checkboxDisplayConditionList.get(checkboxDisplayConditionList.size() - 1);
    }

    @Test
    @Transactional
    public void createCheckboxDisplayConditionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkboxDisplayConditionRepository.findAll().size();

        // Create the CheckboxDisplayCondition with an existing ID
        checkboxDisplayCondition.setId(1L);
        CheckboxDisplayConditionDTO checkboxDisplayConditionDTO = checkboxDisplayConditionMapper.toDto(checkboxDisplayCondition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckboxDisplayConditionMockMvc.perform(post("/api/checkbox-display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxDisplayConditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxDisplayCondition in the database
        List<CheckboxDisplayCondition> checkboxDisplayConditionList = checkboxDisplayConditionRepository.findAll();
        assertThat(checkboxDisplayConditionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCheckboxDisplayConditions() throws Exception {
        // Initialize the database
        checkboxDisplayConditionRepository.saveAndFlush(checkboxDisplayCondition);

        // Get all the checkboxDisplayConditionList
        restCheckboxDisplayConditionMockMvc.perform(get("/api/checkbox-display-conditions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxDisplayCondition.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCheckboxDisplayCondition() throws Exception {
        // Initialize the database
        checkboxDisplayConditionRepository.saveAndFlush(checkboxDisplayCondition);

        // Get the checkboxDisplayCondition
        restCheckboxDisplayConditionMockMvc.perform(get("/api/checkbox-display-conditions/{id}", checkboxDisplayCondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkboxDisplayCondition.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllCheckboxDisplayConditionsByCheckboxQuestionItemIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxDisplayConditionRepository.saveAndFlush(checkboxDisplayCondition);
        CheckboxQuestionItem checkboxQuestionItem = CheckboxQuestionItemResourceIT.createEntity(em);
        em.persist(checkboxQuestionItem);
        em.flush();
        checkboxDisplayCondition.setCheckboxQuestionItem(checkboxQuestionItem);
        checkboxDisplayConditionRepository.saveAndFlush(checkboxDisplayCondition);
        Long checkboxQuestionItemId = checkboxQuestionItem.getId();

        // Get all the checkboxDisplayConditionList where checkboxQuestionItem equals to checkboxQuestionItemId
        defaultCheckboxDisplayConditionShouldBeFound("checkboxQuestionItemId.equals=" + checkboxQuestionItemId);

        // Get all the checkboxDisplayConditionList where checkboxQuestionItem equals to checkboxQuestionItemId + 1
        defaultCheckboxDisplayConditionShouldNotBeFound("checkboxQuestionItemId.equals=" + (checkboxQuestionItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckboxDisplayConditionShouldBeFound(String filter) throws Exception {
        restCheckboxDisplayConditionMockMvc.perform(get("/api/checkbox-display-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxDisplayCondition.getId().intValue())));

        // Check, that the count call also returns 1
        restCheckboxDisplayConditionMockMvc.perform(get("/api/checkbox-display-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckboxDisplayConditionShouldNotBeFound(String filter) throws Exception {
        restCheckboxDisplayConditionMockMvc.perform(get("/api/checkbox-display-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckboxDisplayConditionMockMvc.perform(get("/api/checkbox-display-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCheckboxDisplayCondition() throws Exception {
        // Get the checkboxDisplayCondition
        restCheckboxDisplayConditionMockMvc.perform(get("/api/checkbox-display-conditions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckboxDisplayCondition() throws Exception {
        // Initialize the database
        checkboxDisplayConditionRepository.saveAndFlush(checkboxDisplayCondition);

        int databaseSizeBeforeUpdate = checkboxDisplayConditionRepository.findAll().size();

        // Update the checkboxDisplayCondition
        CheckboxDisplayCondition updatedCheckboxDisplayCondition = checkboxDisplayConditionRepository.findById(checkboxDisplayCondition.getId()).get();
        // Disconnect from session so that the updates on updatedCheckboxDisplayCondition are not directly saved in db
        em.detach(updatedCheckboxDisplayCondition);
        CheckboxDisplayConditionDTO checkboxDisplayConditionDTO = checkboxDisplayConditionMapper.toDto(updatedCheckboxDisplayCondition);

        restCheckboxDisplayConditionMockMvc.perform(put("/api/checkbox-display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxDisplayConditionDTO)))
            .andExpect(status().isOk());

        // Validate the CheckboxDisplayCondition in the database
        List<CheckboxDisplayCondition> checkboxDisplayConditionList = checkboxDisplayConditionRepository.findAll();
        assertThat(checkboxDisplayConditionList).hasSize(databaseSizeBeforeUpdate);
        CheckboxDisplayCondition testCheckboxDisplayCondition = checkboxDisplayConditionList.get(checkboxDisplayConditionList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckboxDisplayCondition() throws Exception {
        int databaseSizeBeforeUpdate = checkboxDisplayConditionRepository.findAll().size();

        // Create the CheckboxDisplayCondition
        CheckboxDisplayConditionDTO checkboxDisplayConditionDTO = checkboxDisplayConditionMapper.toDto(checkboxDisplayCondition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckboxDisplayConditionMockMvc.perform(put("/api/checkbox-display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxDisplayConditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxDisplayCondition in the database
        List<CheckboxDisplayCondition> checkboxDisplayConditionList = checkboxDisplayConditionRepository.findAll();
        assertThat(checkboxDisplayConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckboxDisplayCondition() throws Exception {
        // Initialize the database
        checkboxDisplayConditionRepository.saveAndFlush(checkboxDisplayCondition);

        int databaseSizeBeforeDelete = checkboxDisplayConditionRepository.findAll().size();

        // Delete the checkboxDisplayCondition
        restCheckboxDisplayConditionMockMvc.perform(delete("/api/checkbox-display-conditions/{id}", checkboxDisplayCondition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckboxDisplayCondition> checkboxDisplayConditionList = checkboxDisplayConditionRepository.findAll();
        assertThat(checkboxDisplayConditionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxDisplayCondition.class);
        CheckboxDisplayCondition checkboxDisplayCondition1 = new CheckboxDisplayCondition();
        checkboxDisplayCondition1.setId(1L);
        CheckboxDisplayCondition checkboxDisplayCondition2 = new CheckboxDisplayCondition();
        checkboxDisplayCondition2.setId(checkboxDisplayCondition1.getId());
        assertThat(checkboxDisplayCondition1).isEqualTo(checkboxDisplayCondition2);
        checkboxDisplayCondition2.setId(2L);
        assertThat(checkboxDisplayCondition1).isNotEqualTo(checkboxDisplayCondition2);
        checkboxDisplayCondition1.setId(null);
        assertThat(checkboxDisplayCondition1).isNotEqualTo(checkboxDisplayCondition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxDisplayConditionDTO.class);
        CheckboxDisplayConditionDTO checkboxDisplayConditionDTO1 = new CheckboxDisplayConditionDTO();
        checkboxDisplayConditionDTO1.setId(1L);
        CheckboxDisplayConditionDTO checkboxDisplayConditionDTO2 = new CheckboxDisplayConditionDTO();
        assertThat(checkboxDisplayConditionDTO1).isNotEqualTo(checkboxDisplayConditionDTO2);
        checkboxDisplayConditionDTO2.setId(checkboxDisplayConditionDTO1.getId());
        assertThat(checkboxDisplayConditionDTO1).isEqualTo(checkboxDisplayConditionDTO2);
        checkboxDisplayConditionDTO2.setId(2L);
        assertThat(checkboxDisplayConditionDTO1).isNotEqualTo(checkboxDisplayConditionDTO2);
        checkboxDisplayConditionDTO1.setId(null);
        assertThat(checkboxDisplayConditionDTO1).isNotEqualTo(checkboxDisplayConditionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(checkboxDisplayConditionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(checkboxDisplayConditionMapper.fromId(null)).isNull();
    }
}
