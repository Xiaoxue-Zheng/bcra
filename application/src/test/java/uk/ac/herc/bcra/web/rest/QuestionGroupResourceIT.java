package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.domain.DisplayCondition;
import uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup;
import uk.ac.herc.bcra.domain.QuestionGroupQuestion;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.repository.QuestionGroupRepository;
import uk.ac.herc.bcra.service.QuestionGroupService;
import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;
import uk.ac.herc.bcra.service.mapper.QuestionGroupMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.QuestionGroupCriteria;
import uk.ac.herc.bcra.service.QuestionGroupQueryService;

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
 * Integration tests for the {@link QuestionGroupResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionGroupResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    @Autowired
    private QuestionGroupRepository questionGroupRepository;

    @Autowired
    private QuestionGroupMapper questionGroupMapper;

    @Autowired
    private QuestionGroupService questionGroupService;

    @Autowired
    private QuestionGroupQueryService questionGroupQueryService;

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

    private MockMvc restQuestionGroupMockMvc;

    private QuestionGroup questionGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionGroupResource questionGroupResource = new QuestionGroupResource(questionGroupService, questionGroupQueryService);
        this.restQuestionGroupMockMvc = MockMvcBuilders.standaloneSetup(questionGroupResource)
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
    public static QuestionGroup createEntity(EntityManager em) {
        QuestionGroup questionGroup = new QuestionGroup()
            .uuid(DEFAULT_UUID);
        return questionGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionGroup createUpdatedEntity(EntityManager em) {
        QuestionGroup questionGroup = new QuestionGroup()
            .uuid(UPDATED_UUID);
        return questionGroup;
    }

    @BeforeEach
    public void initTest() {
        questionGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionGroup() throws Exception {
        int databaseSizeBeforeCreate = questionGroupRepository.findAll().size();

        // Create the QuestionGroup
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(questionGroup);
        restQuestionGroupMockMvc.perform(post("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionGroup in the database
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionGroup testQuestionGroup = questionGroupList.get(questionGroupList.size() - 1);
        assertThat(testQuestionGroup.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createQuestionGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionGroupRepository.findAll().size();

        // Create the QuestionGroup with an existing ID
        questionGroup.setId(1L);
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(questionGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionGroupMockMvc.perform(post("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionGroup in the database
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionGroupRepository.findAll().size();
        // set the field null
        questionGroup.setUuid(null);

        // Create the QuestionGroup, which fails.
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(questionGroup);

        restQuestionGroupMockMvc.perform(post("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionGroups() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        // Get all the questionGroupList
        restQuestionGroupMockMvc.perform(get("/api/question-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }
    
    @Test
    @Transactional
    public void getQuestionGroup() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        // Get the questionGroup
        restQuestionGroupMockMvc.perform(get("/api/question-groups/{id}", questionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionGroup.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }

    @Test
    @Transactional
    public void getAllQuestionGroupsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        // Get all the questionGroupList where uuid equals to DEFAULT_UUID
        defaultQuestionGroupShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the questionGroupList where uuid equals to UPDATED_UUID
        defaultQuestionGroupShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        // Get all the questionGroupList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultQuestionGroupShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the questionGroupList where uuid equals to UPDATED_UUID
        defaultQuestionGroupShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        // Get all the questionGroupList where uuid is not null
        defaultQuestionGroupShouldBeFound("uuid.specified=true");

        // Get all the questionGroupList where uuid is null
        defaultQuestionGroupShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionGroupsByDisplayConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);
        DisplayCondition displayCondition = DisplayConditionResourceIT.createEntity(em);
        em.persist(displayCondition);
        em.flush();
        questionGroup.addDisplayCondition(displayCondition);
        questionGroupRepository.saveAndFlush(questionGroup);
        Long displayConditionId = displayCondition.getId();

        // Get all the questionGroupList where displayCondition equals to displayConditionId
        defaultQuestionGroupShouldBeFound("displayConditionId.equals=" + displayConditionId);

        // Get all the questionGroupList where displayCondition equals to displayConditionId + 1
        defaultQuestionGroupShouldNotBeFound("displayConditionId.equals=" + (displayConditionId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionGroupsByQuestionnaireQuestionGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);
        QuestionnaireQuestionGroup questionnaireQuestionGroup = QuestionnaireQuestionGroupResourceIT.createEntity(em);
        em.persist(questionnaireQuestionGroup);
        em.flush();
        questionGroup.addQuestionnaireQuestionGroup(questionnaireQuestionGroup);
        questionGroupRepository.saveAndFlush(questionGroup);
        Long questionnaireQuestionGroupId = questionnaireQuestionGroup.getId();

        // Get all the questionGroupList where questionnaireQuestionGroup equals to questionnaireQuestionGroupId
        defaultQuestionGroupShouldBeFound("questionnaireQuestionGroupId.equals=" + questionnaireQuestionGroupId);

        // Get all the questionGroupList where questionnaireQuestionGroup equals to questionnaireQuestionGroupId + 1
        defaultQuestionGroupShouldNotBeFound("questionnaireQuestionGroupId.equals=" + (questionnaireQuestionGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionGroupsByQuestionGroupQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);
        QuestionGroupQuestion questionGroupQuestion = QuestionGroupQuestionResourceIT.createEntity(em);
        em.persist(questionGroupQuestion);
        em.flush();
        questionGroup.addQuestionGroupQuestion(questionGroupQuestion);
        questionGroupRepository.saveAndFlush(questionGroup);
        Long questionGroupQuestionId = questionGroupQuestion.getId();

        // Get all the questionGroupList where questionGroupQuestion equals to questionGroupQuestionId
        defaultQuestionGroupShouldBeFound("questionGroupQuestionId.equals=" + questionGroupQuestionId);

        // Get all the questionGroupList where questionGroupQuestion equals to questionGroupQuestionId + 1
        defaultQuestionGroupShouldNotBeFound("questionGroupQuestionId.equals=" + (questionGroupQuestionId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionGroupsByAnswerGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);
        AnswerGroup answerGroup = AnswerGroupResourceIT.createEntity(em);
        em.persist(answerGroup);
        em.flush();
        questionGroup.addAnswerGroup(answerGroup);
        questionGroupRepository.saveAndFlush(questionGroup);
        Long answerGroupId = answerGroup.getId();

        // Get all the questionGroupList where answerGroup equals to answerGroupId
        defaultQuestionGroupShouldBeFound("answerGroupId.equals=" + answerGroupId);

        // Get all the questionGroupList where answerGroup equals to answerGroupId + 1
        defaultQuestionGroupShouldNotBeFound("answerGroupId.equals=" + (answerGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionGroupShouldBeFound(String filter) throws Exception {
        restQuestionGroupMockMvc.perform(get("/api/question-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)));

        // Check, that the count call also returns 1
        restQuestionGroupMockMvc.perform(get("/api/question-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionGroupShouldNotBeFound(String filter) throws Exception {
        restQuestionGroupMockMvc.perform(get("/api/question-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionGroupMockMvc.perform(get("/api/question-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQuestionGroup() throws Exception {
        // Get the questionGroup
        restQuestionGroupMockMvc.perform(get("/api/question-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionGroup() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        int databaseSizeBeforeUpdate = questionGroupRepository.findAll().size();

        // Update the questionGroup
        QuestionGroup updatedQuestionGroup = questionGroupRepository.findById(questionGroup.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionGroup are not directly saved in db
        em.detach(updatedQuestionGroup);
        updatedQuestionGroup
            .uuid(UPDATED_UUID);
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(updatedQuestionGroup);

        restQuestionGroupMockMvc.perform(put("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionGroup in the database
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeUpdate);
        QuestionGroup testQuestionGroup = questionGroupList.get(questionGroupList.size() - 1);
        assertThat(testQuestionGroup.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionGroup() throws Exception {
        int databaseSizeBeforeUpdate = questionGroupRepository.findAll().size();

        // Create the QuestionGroup
        QuestionGroupDTO questionGroupDTO = questionGroupMapper.toDto(questionGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionGroupMockMvc.perform(put("/api/question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionGroup in the database
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionGroup() throws Exception {
        // Initialize the database
        questionGroupRepository.saveAndFlush(questionGroup);

        int databaseSizeBeforeDelete = questionGroupRepository.findAll().size();

        // Delete the questionGroup
        restQuestionGroupMockMvc.perform(delete("/api/question-groups/{id}", questionGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionGroup> questionGroupList = questionGroupRepository.findAll();
        assertThat(questionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionGroup.class);
        QuestionGroup questionGroup1 = new QuestionGroup();
        questionGroup1.setId(1L);
        QuestionGroup questionGroup2 = new QuestionGroup();
        questionGroup2.setId(questionGroup1.getId());
        assertThat(questionGroup1).isEqualTo(questionGroup2);
        questionGroup2.setId(2L);
        assertThat(questionGroup1).isNotEqualTo(questionGroup2);
        questionGroup1.setId(null);
        assertThat(questionGroup1).isNotEqualTo(questionGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionGroupDTO.class);
        QuestionGroupDTO questionGroupDTO1 = new QuestionGroupDTO();
        questionGroupDTO1.setId(1L);
        QuestionGroupDTO questionGroupDTO2 = new QuestionGroupDTO();
        assertThat(questionGroupDTO1).isNotEqualTo(questionGroupDTO2);
        questionGroupDTO2.setId(questionGroupDTO1.getId());
        assertThat(questionGroupDTO1).isEqualTo(questionGroupDTO2);
        questionGroupDTO2.setId(2L);
        assertThat(questionGroupDTO1).isNotEqualTo(questionGroupDTO2);
        questionGroupDTO1.setId(null);
        assertThat(questionGroupDTO1).isNotEqualTo(questionGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionGroupMapper.fromId(null)).isNull();
    }
}
