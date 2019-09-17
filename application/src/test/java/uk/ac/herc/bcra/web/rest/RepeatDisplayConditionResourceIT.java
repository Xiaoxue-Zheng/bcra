package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.RepeatDisplayCondition;
import uk.ac.herc.bcra.domain.RepeatQuestion;
import uk.ac.herc.bcra.repository.RepeatDisplayConditionRepository;
import uk.ac.herc.bcra.service.RepeatDisplayConditionService;
import uk.ac.herc.bcra.service.dto.RepeatDisplayConditionDTO;
import uk.ac.herc.bcra.service.mapper.RepeatDisplayConditionMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.RepeatDisplayConditionCriteria;
import uk.ac.herc.bcra.service.RepeatDisplayConditionQueryService;

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
 * Integration tests for the {@link RepeatDisplayConditionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class RepeatDisplayConditionResourceIT {

    @Autowired
    private RepeatDisplayConditionRepository repeatDisplayConditionRepository;

    @Autowired
    private RepeatDisplayConditionMapper repeatDisplayConditionMapper;

    @Autowired
    private RepeatDisplayConditionService repeatDisplayConditionService;

    @Autowired
    private RepeatDisplayConditionQueryService repeatDisplayConditionQueryService;

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

    private MockMvc restRepeatDisplayConditionMockMvc;

    private RepeatDisplayCondition repeatDisplayCondition;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RepeatDisplayConditionResource repeatDisplayConditionResource = new RepeatDisplayConditionResource(repeatDisplayConditionService, repeatDisplayConditionQueryService);
        this.restRepeatDisplayConditionMockMvc = MockMvcBuilders.standaloneSetup(repeatDisplayConditionResource)
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
    public static RepeatDisplayCondition createEntity(EntityManager em) {
        RepeatDisplayCondition repeatDisplayCondition = new RepeatDisplayCondition();
        return repeatDisplayCondition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepeatDisplayCondition createUpdatedEntity(EntityManager em) {
        RepeatDisplayCondition repeatDisplayCondition = new RepeatDisplayCondition();
        return repeatDisplayCondition;
    }

    @BeforeEach
    public void initTest() {
        repeatDisplayCondition = createEntity(em);
    }

    @Test
    @Transactional
    public void createRepeatDisplayCondition() throws Exception {
        int databaseSizeBeforeCreate = repeatDisplayConditionRepository.findAll().size();

        // Create the RepeatDisplayCondition
        RepeatDisplayConditionDTO repeatDisplayConditionDTO = repeatDisplayConditionMapper.toDto(repeatDisplayCondition);
        restRepeatDisplayConditionMockMvc.perform(post("/api/repeat-display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatDisplayConditionDTO)))
            .andExpect(status().isCreated());

        // Validate the RepeatDisplayCondition in the database
        List<RepeatDisplayCondition> repeatDisplayConditionList = repeatDisplayConditionRepository.findAll();
        assertThat(repeatDisplayConditionList).hasSize(databaseSizeBeforeCreate + 1);
        RepeatDisplayCondition testRepeatDisplayCondition = repeatDisplayConditionList.get(repeatDisplayConditionList.size() - 1);
    }

    @Test
    @Transactional
    public void createRepeatDisplayConditionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = repeatDisplayConditionRepository.findAll().size();

        // Create the RepeatDisplayCondition with an existing ID
        repeatDisplayCondition.setId(1L);
        RepeatDisplayConditionDTO repeatDisplayConditionDTO = repeatDisplayConditionMapper.toDto(repeatDisplayCondition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepeatDisplayConditionMockMvc.perform(post("/api/repeat-display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatDisplayConditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RepeatDisplayCondition in the database
        List<RepeatDisplayCondition> repeatDisplayConditionList = repeatDisplayConditionRepository.findAll();
        assertThat(repeatDisplayConditionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRepeatDisplayConditions() throws Exception {
        // Initialize the database
        repeatDisplayConditionRepository.saveAndFlush(repeatDisplayCondition);

        // Get all the repeatDisplayConditionList
        restRepeatDisplayConditionMockMvc.perform(get("/api/repeat-display-conditions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repeatDisplayCondition.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getRepeatDisplayCondition() throws Exception {
        // Initialize the database
        repeatDisplayConditionRepository.saveAndFlush(repeatDisplayCondition);

        // Get the repeatDisplayCondition
        restRepeatDisplayConditionMockMvc.perform(get("/api/repeat-display-conditions/{id}", repeatDisplayCondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(repeatDisplayCondition.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllRepeatDisplayConditionsByRepeatQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        repeatDisplayConditionRepository.saveAndFlush(repeatDisplayCondition);
        RepeatQuestion repeatQuestion = RepeatQuestionResourceIT.createEntity(em);
        em.persist(repeatQuestion);
        em.flush();
        repeatDisplayCondition.setRepeatQuestion(repeatQuestion);
        repeatDisplayConditionRepository.saveAndFlush(repeatDisplayCondition);
        Long repeatQuestionId = repeatQuestion.getId();

        // Get all the repeatDisplayConditionList where repeatQuestion equals to repeatQuestionId
        defaultRepeatDisplayConditionShouldBeFound("repeatQuestionId.equals=" + repeatQuestionId);

        // Get all the repeatDisplayConditionList where repeatQuestion equals to repeatQuestionId + 1
        defaultRepeatDisplayConditionShouldNotBeFound("repeatQuestionId.equals=" + (repeatQuestionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRepeatDisplayConditionShouldBeFound(String filter) throws Exception {
        restRepeatDisplayConditionMockMvc.perform(get("/api/repeat-display-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(repeatDisplayCondition.getId().intValue())));

        // Check, that the count call also returns 1
        restRepeatDisplayConditionMockMvc.perform(get("/api/repeat-display-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRepeatDisplayConditionShouldNotBeFound(String filter) throws Exception {
        restRepeatDisplayConditionMockMvc.perform(get("/api/repeat-display-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRepeatDisplayConditionMockMvc.perform(get("/api/repeat-display-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRepeatDisplayCondition() throws Exception {
        // Get the repeatDisplayCondition
        restRepeatDisplayConditionMockMvc.perform(get("/api/repeat-display-conditions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepeatDisplayCondition() throws Exception {
        // Initialize the database
        repeatDisplayConditionRepository.saveAndFlush(repeatDisplayCondition);

        int databaseSizeBeforeUpdate = repeatDisplayConditionRepository.findAll().size();

        // Update the repeatDisplayCondition
        RepeatDisplayCondition updatedRepeatDisplayCondition = repeatDisplayConditionRepository.findById(repeatDisplayCondition.getId()).get();
        // Disconnect from session so that the updates on updatedRepeatDisplayCondition are not directly saved in db
        em.detach(updatedRepeatDisplayCondition);
        RepeatDisplayConditionDTO repeatDisplayConditionDTO = repeatDisplayConditionMapper.toDto(updatedRepeatDisplayCondition);

        restRepeatDisplayConditionMockMvc.perform(put("/api/repeat-display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatDisplayConditionDTO)))
            .andExpect(status().isOk());

        // Validate the RepeatDisplayCondition in the database
        List<RepeatDisplayCondition> repeatDisplayConditionList = repeatDisplayConditionRepository.findAll();
        assertThat(repeatDisplayConditionList).hasSize(databaseSizeBeforeUpdate);
        RepeatDisplayCondition testRepeatDisplayCondition = repeatDisplayConditionList.get(repeatDisplayConditionList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingRepeatDisplayCondition() throws Exception {
        int databaseSizeBeforeUpdate = repeatDisplayConditionRepository.findAll().size();

        // Create the RepeatDisplayCondition
        RepeatDisplayConditionDTO repeatDisplayConditionDTO = repeatDisplayConditionMapper.toDto(repeatDisplayCondition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepeatDisplayConditionMockMvc.perform(put("/api/repeat-display-conditions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(repeatDisplayConditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RepeatDisplayCondition in the database
        List<RepeatDisplayCondition> repeatDisplayConditionList = repeatDisplayConditionRepository.findAll();
        assertThat(repeatDisplayConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRepeatDisplayCondition() throws Exception {
        // Initialize the database
        repeatDisplayConditionRepository.saveAndFlush(repeatDisplayCondition);

        int databaseSizeBeforeDelete = repeatDisplayConditionRepository.findAll().size();

        // Delete the repeatDisplayCondition
        restRepeatDisplayConditionMockMvc.perform(delete("/api/repeat-display-conditions/{id}", repeatDisplayCondition.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RepeatDisplayCondition> repeatDisplayConditionList = repeatDisplayConditionRepository.findAll();
        assertThat(repeatDisplayConditionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepeatDisplayCondition.class);
        RepeatDisplayCondition repeatDisplayCondition1 = new RepeatDisplayCondition();
        repeatDisplayCondition1.setId(1L);
        RepeatDisplayCondition repeatDisplayCondition2 = new RepeatDisplayCondition();
        repeatDisplayCondition2.setId(repeatDisplayCondition1.getId());
        assertThat(repeatDisplayCondition1).isEqualTo(repeatDisplayCondition2);
        repeatDisplayCondition2.setId(2L);
        assertThat(repeatDisplayCondition1).isNotEqualTo(repeatDisplayCondition2);
        repeatDisplayCondition1.setId(null);
        assertThat(repeatDisplayCondition1).isNotEqualTo(repeatDisplayCondition2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RepeatDisplayConditionDTO.class);
        RepeatDisplayConditionDTO repeatDisplayConditionDTO1 = new RepeatDisplayConditionDTO();
        repeatDisplayConditionDTO1.setId(1L);
        RepeatDisplayConditionDTO repeatDisplayConditionDTO2 = new RepeatDisplayConditionDTO();
        assertThat(repeatDisplayConditionDTO1).isNotEqualTo(repeatDisplayConditionDTO2);
        repeatDisplayConditionDTO2.setId(repeatDisplayConditionDTO1.getId());
        assertThat(repeatDisplayConditionDTO1).isEqualTo(repeatDisplayConditionDTO2);
        repeatDisplayConditionDTO2.setId(2L);
        assertThat(repeatDisplayConditionDTO1).isNotEqualTo(repeatDisplayConditionDTO2);
        repeatDisplayConditionDTO1.setId(null);
        assertThat(repeatDisplayConditionDTO1).isNotEqualTo(repeatDisplayConditionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(repeatDisplayConditionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(repeatDisplayConditionMapper.fromId(null)).isNull();
    }
}
