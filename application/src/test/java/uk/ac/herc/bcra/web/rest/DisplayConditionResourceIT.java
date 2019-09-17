package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.DisplayCondition;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.repository.DisplayConditionRepository;
import uk.ac.herc.bcra.service.DisplayConditionService;
import uk.ac.herc.bcra.service.dto.DisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.DisplayConditionMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.DisplayConditionCriteria;
import uk.ac.herc.bcra.service.DisplayConditionQueryService;

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
 * Integration tests for the {@link DisplayConditionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class DisplayConditionResourceIT {

    @Autowired
    private DisplayConditionRepository displayConditionRepository;

    @Autowired
    private DisplayConditionMapper displayConditionMapper;

    @Autowired
    private DisplayConditionService displayConditionService;

    @Autowired
    private DisplayConditionQueryService displayConditionQueryService;

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

    private MockMvc restDisplayConditionMockMvc;

    private DisplayCondition displayCondition;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisplayConditionResource displayConditionResource = new DisplayConditionResource(displayConditionService, displayConditionQueryService);
        this.restDisplayConditionMockMvc = MockMvcBuilders.standaloneSetup(displayConditionResource)
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
    public static DisplayCondition createEntity(EntityManager em) {
        DisplayCondition displayCondition = new DisplayCondition();
        return displayCondition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DisplayCondition createUpdatedEntity(EntityManager em) {
        DisplayCondition displayCondition = new DisplayCondition();
        return displayCondition;
    }

    @BeforeEach
    public void initTest() {
        displayCondition = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisplayCondition() throws Exception {
        int databaseSizeBeforeCreate = displayConditionRepository.findAll().size();

        // Create the DisplayCondition
        DisplayConditionDTO displayConditionDTO = displayConditionMapper.toDto(displayCondition);
        restDisplayConditionMockMvc.perform(post("/api/display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(displayConditionDTO)))
            .andExpect(status().isCreated());

        // Validate the DisplayCondition in the database
        List<DisplayCondition> displayConditionList = displayConditionRepository.findAll();
        assertThat(displayConditionList).hasSize(databaseSizeBeforeCreate + 1);
        DisplayCondition testDisplayCondition = displayConditionList.get(displayConditionList.size() - 1);
    }

    @Test
    @Transactional
    public void createDisplayConditionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = displayConditionRepository.findAll().size();

        // Create the DisplayCondition with an existing ID
        displayCondition.setId(1L);
        DisplayConditionDTO displayConditionDTO = displayConditionMapper.toDto(displayCondition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisplayConditionMockMvc.perform(post("/api/display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(displayConditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DisplayCondition in the database
        List<DisplayCondition> displayConditionList = displayConditionRepository.findAll();
        assertThat(displayConditionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDisplayConditions() throws Exception {
        // Initialize the database
        displayConditionRepository.saveAndFlush(displayCondition);

        // Get all the displayConditionList
        restDisplayConditionMockMvc.perform(get("/api/display-conditions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(displayCondition.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDisplayCondition() throws Exception {
        // Initialize the database
        displayConditionRepository.saveAndFlush(displayCondition);

        // Get the displayCondition
        restDisplayConditionMockMvc.perform(get("/api/display-conditions/{id}", displayCondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(displayCondition.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllDisplayConditionsByQuestionGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        displayConditionRepository.saveAndFlush(displayCondition);
        QuestionGroup questionGroup = QuestionGroupResourceIT.createEntity(em);
        em.persist(questionGroup);
        em.flush();
        displayCondition.setQuestionGroup(questionGroup);
        displayConditionRepository.saveAndFlush(displayCondition);
        Long questionGroupId = questionGroup.getId();

        // Get all the displayConditionList where questionGroup equals to questionGroupId
        defaultDisplayConditionShouldBeFound("questionGroupId.equals=" + questionGroupId);

        // Get all the displayConditionList where questionGroup equals to questionGroupId + 1
        defaultDisplayConditionShouldNotBeFound("questionGroupId.equals=" + (questionGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDisplayConditionShouldBeFound(String filter) throws Exception {
        restDisplayConditionMockMvc.perform(get("/api/display-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(displayCondition.getId().intValue())));

        // Check, that the count call also returns 1
        restDisplayConditionMockMvc.perform(get("/api/display-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDisplayConditionShouldNotBeFound(String filter) throws Exception {
        restDisplayConditionMockMvc.perform(get("/api/display-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisplayConditionMockMvc.perform(get("/api/display-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDisplayCondition() throws Exception {
        // Get the displayCondition
        restDisplayConditionMockMvc.perform(get("/api/display-conditions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisplayCondition() throws Exception {
        // Initialize the database
        displayConditionRepository.saveAndFlush(displayCondition);

        int databaseSizeBeforeUpdate = displayConditionRepository.findAll().size();

        // Update the displayCondition
        DisplayCondition updatedDisplayCondition = displayConditionRepository.findById(displayCondition.getId()).get();
        // Disconnect from session so that the updates on updatedDisplayCondition are not directly saved in db
        em.detach(updatedDisplayCondition);
        DisplayConditionDTO displayConditionDTO = displayConditionMapper.toDto(updatedDisplayCondition);

        restDisplayConditionMockMvc.perform(put("/api/display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(displayConditionDTO)))
            .andExpect(status().isOk());

        // Validate the DisplayCondition in the database
        List<DisplayCondition> displayConditionList = displayConditionRepository.findAll();
        assertThat(displayConditionList).hasSize(databaseSizeBeforeUpdate);
        DisplayCondition testDisplayCondition = displayConditionList.get(displayConditionList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDisplayCondition() throws Exception {
        int databaseSizeBeforeUpdate = displayConditionRepository.findAll().size();

        // Create the DisplayCondition
        DisplayConditionDTO displayConditionDTO = displayConditionMapper.toDto(displayCondition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisplayConditionMockMvc.perform(put("/api/display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(displayConditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DisplayCondition in the database
        List<DisplayCondition> displayConditionList = displayConditionRepository.findAll();
        assertThat(displayConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisplayCondition() throws Exception {
        // Initialize the database
        displayConditionRepository.saveAndFlush(displayCondition);

        int databaseSizeBeforeDelete = displayConditionRepository.findAll().size();

        // Delete the displayCondition
        restDisplayConditionMockMvc.perform(delete("/api/display-conditions/{id}", displayCondition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DisplayCondition> displayConditionList = displayConditionRepository.findAll();
        assertThat(displayConditionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisplayCondition.class);
        DisplayCondition displayCondition1 = new DisplayCondition();
        displayCondition1.setId(1L);
        DisplayCondition displayCondition2 = new DisplayCondition();
        displayCondition2.setId(displayCondition1.getId());
        assertThat(displayCondition1).isEqualTo(displayCondition2);
        displayCondition2.setId(2L);
        assertThat(displayCondition1).isNotEqualTo(displayCondition2);
        displayCondition1.setId(null);
        assertThat(displayCondition1).isNotEqualTo(displayCondition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DisplayConditionDTO.class);
        DisplayConditionDTO displayConditionDTO1 = new DisplayConditionDTO();
        displayConditionDTO1.setId(1L);
        DisplayConditionDTO displayConditionDTO2 = new DisplayConditionDTO();
        assertThat(displayConditionDTO1).isNotEqualTo(displayConditionDTO2);
        displayConditionDTO2.setId(displayConditionDTO1.getId());
        assertThat(displayConditionDTO1).isEqualTo(displayConditionDTO2);
        displayConditionDTO2.setId(2L);
        assertThat(displayConditionDTO1).isNotEqualTo(displayConditionDTO2);
        displayConditionDTO1.setId(null);
        assertThat(displayConditionDTO1).isNotEqualTo(displayConditionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(displayConditionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(displayConditionMapper.fromId(null)).isNull();
    }
}
