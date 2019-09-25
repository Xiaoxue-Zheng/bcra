package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.repository.QuestionnaireQuestionGroupRepository;
import uk.ac.herc.bcra.service.QuestionnaireQuestionGroupService;
import uk.ac.herc.bcra.service.dto.QuestionnaireQuestionGroupDTO;
import uk.ac.herc.bcra.service.mapper.QuestionnaireQuestionGroupMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.QuestionnaireQuestionGroupCriteria;
import uk.ac.herc.bcra.service.QuestionnaireQuestionGroupQueryService;

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
 * Integration tests for the {@link QuestionnaireQuestionGroupResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionnaireQuestionGroupResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTIONNAIRE_UUID = "AAAAAAAAAA";
    private static final String UPDATED_QUESTIONNAIRE_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_GROUP_UUID = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_GROUP_UUID = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;
    private static final Integer SMALLER_ORDER = 1 - 1;

    @Autowired
    private QuestionnaireQuestionGroupRepository questionnaireQuestionGroupRepository;

    @Autowired
    private QuestionnaireQuestionGroupMapper questionnaireQuestionGroupMapper;

    @Autowired
    private QuestionnaireQuestionGroupService questionnaireQuestionGroupService;

    @Autowired
    private QuestionnaireQuestionGroupQueryService questionnaireQuestionGroupQueryService;

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

    private MockMvc restQuestionnaireQuestionGroupMockMvc;

    private QuestionnaireQuestionGroup questionnaireQuestionGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionnaireQuestionGroupResource questionnaireQuestionGroupResource = new QuestionnaireQuestionGroupResource(questionnaireQuestionGroupService, questionnaireQuestionGroupQueryService);
        this.restQuestionnaireQuestionGroupMockMvc = MockMvcBuilders.standaloneSetup(questionnaireQuestionGroupResource)
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
    public static QuestionnaireQuestionGroup createEntity(EntityManager em) {
        QuestionnaireQuestionGroup questionnaireQuestionGroup = new QuestionnaireQuestionGroup()
            .uuid(DEFAULT_UUID)
            .questionnaireUuid(DEFAULT_QUESTIONNAIRE_UUID)
            .questionGroupUuid(DEFAULT_QUESTION_GROUP_UUID)
            .order(DEFAULT_ORDER);
        return questionnaireQuestionGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionnaireQuestionGroup createUpdatedEntity(EntityManager em) {
        QuestionnaireQuestionGroup questionnaireQuestionGroup = new QuestionnaireQuestionGroup()
            .uuid(UPDATED_UUID)
            .questionnaireUuid(UPDATED_QUESTIONNAIRE_UUID)
            .questionGroupUuid(UPDATED_QUESTION_GROUP_UUID)
            .order(UPDATED_ORDER);
        return questionnaireQuestionGroup;
    }

    @BeforeEach
    public void initTest() {
        questionnaireQuestionGroup = createEntity(em);
    }

    // Commented for now, as we will not be posting Questionnaires, etc.
    // @Test
    // @Transactional
    // public void createQuestionnaireQuestionGroup() throws Exception {
    //     int databaseSizeBeforeCreate = questionnaireQuestionGroupRepository.findAll().size();

    //     // Create the QuestionnaireQuestionGroup
    //     QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO = questionnaireQuestionGroupMapper.toDto(questionnaireQuestionGroup);
    //     restQuestionnaireQuestionGroupMockMvc.perform(post("/api/questionnaire-question-groups")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(questionnaireQuestionGroupDTO)))
    //         .andExpect(status().isCreated());

    //     // Validate the QuestionnaireQuestionGroup in the database
    //     List<QuestionnaireQuestionGroup> questionnaireQuestionGroupList = questionnaireQuestionGroupRepository.findAll();
    //     assertThat(questionnaireQuestionGroupList).hasSize(databaseSizeBeforeCreate + 1);
    //     QuestionnaireQuestionGroup testQuestionnaireQuestionGroup = questionnaireQuestionGroupList.get(questionnaireQuestionGroupList.size() - 1);
    //     assertThat(testQuestionnaireQuestionGroup.getUuid()).isEqualTo(DEFAULT_UUID);
    //     assertThat(testQuestionnaireQuestionGroup.getQuestionnaireUuid()).isEqualTo(DEFAULT_QUESTIONNAIRE_UUID);
    //     assertThat(testQuestionnaireQuestionGroup.getQuestionGroupUuid()).isEqualTo(DEFAULT_QUESTION_GROUP_UUID);
    //     assertThat(testQuestionnaireQuestionGroup.getOrder()).isEqualTo(DEFAULT_ORDER);
    // }

    @Test
    @Transactional
    public void createQuestionnaireQuestionGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionnaireQuestionGroupRepository.findAll().size();

        // Create the QuestionnaireQuestionGroup with an existing ID
        questionnaireQuestionGroup.setId(1L);
        QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO = questionnaireQuestionGroupMapper.toDto(questionnaireQuestionGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionnaireQuestionGroupMockMvc.perform(post("/api/questionnaire-question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireQuestionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionnaireQuestionGroup in the database
        List<QuestionnaireQuestionGroup> questionnaireQuestionGroupList = questionnaireQuestionGroupRepository.findAll();
        assertThat(questionnaireQuestionGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionnaireQuestionGroupRepository.findAll().size();
        // set the field null
        questionnaireQuestionGroup.setOrder(null);

        // Create the QuestionnaireQuestionGroup, which fails.
        QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO = questionnaireQuestionGroupMapper.toDto(questionnaireQuestionGroup);

        restQuestionnaireQuestionGroupMockMvc.perform(post("/api/questionnaire-question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireQuestionGroupDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionnaireQuestionGroup> questionnaireQuestionGroupList = questionnaireQuestionGroupRepository.findAll();
        assertThat(questionnaireQuestionGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroups() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList
        restQuestionnaireQuestionGroupMockMvc.perform(get("/api/questionnaire-question-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionnaireQuestionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getQuestionnaireQuestionGroup() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get the questionnaireQuestionGroup
        restQuestionnaireQuestionGroupMockMvc.perform(get("/api/questionnaire-question-groups/{id}", questionnaireQuestionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionnaireQuestionGroup.getId().intValue()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where uuid equals to DEFAULT_UUID
        defaultQuestionnaireQuestionGroupShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the questionnaireQuestionGroupList where uuid equals to UPDATED_UUID
        defaultQuestionnaireQuestionGroupShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultQuestionnaireQuestionGroupShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the questionnaireQuestionGroupList where uuid equals to UPDATED_UUID
        defaultQuestionnaireQuestionGroupShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where uuid is not null
        defaultQuestionnaireQuestionGroupShouldBeFound("uuid.specified=true");

        // Get all the questionnaireQuestionGroupList where uuid is null
        defaultQuestionnaireQuestionGroupShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByQuestionnaireUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where questionnaireUuid equals to DEFAULT_QUESTIONNAIRE_UUID
        defaultQuestionnaireQuestionGroupShouldBeFound("questionnaireUuid.equals=" + DEFAULT_QUESTIONNAIRE_UUID);

        // Get all the questionnaireQuestionGroupList where questionnaireUuid equals to UPDATED_QUESTIONNAIRE_UUID
        defaultQuestionnaireQuestionGroupShouldNotBeFound("questionnaireUuid.equals=" + UPDATED_QUESTIONNAIRE_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByQuestionnaireUuidIsInShouldWork() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where questionnaireUuid in DEFAULT_QUESTIONNAIRE_UUID or UPDATED_QUESTIONNAIRE_UUID
        defaultQuestionnaireQuestionGroupShouldBeFound("questionnaireUuid.in=" + DEFAULT_QUESTIONNAIRE_UUID + "," + UPDATED_QUESTIONNAIRE_UUID);

        // Get all the questionnaireQuestionGroupList where questionnaireUuid equals to UPDATED_QUESTIONNAIRE_UUID
        defaultQuestionnaireQuestionGroupShouldNotBeFound("questionnaireUuid.in=" + UPDATED_QUESTIONNAIRE_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByQuestionnaireUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where questionnaireUuid is not null
        defaultQuestionnaireQuestionGroupShouldBeFound("questionnaireUuid.specified=true");

        // Get all the questionnaireQuestionGroupList where questionnaireUuid is null
        defaultQuestionnaireQuestionGroupShouldNotBeFound("questionnaireUuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByQuestionGroupUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where questionGroupUuid equals to DEFAULT_QUESTION_GROUP_UUID
        defaultQuestionnaireQuestionGroupShouldBeFound("questionGroupUuid.equals=" + DEFAULT_QUESTION_GROUP_UUID);

        // Get all the questionnaireQuestionGroupList where questionGroupUuid equals to UPDATED_QUESTION_GROUP_UUID
        defaultQuestionnaireQuestionGroupShouldNotBeFound("questionGroupUuid.equals=" + UPDATED_QUESTION_GROUP_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByQuestionGroupUuidIsInShouldWork() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where questionGroupUuid in DEFAULT_QUESTION_GROUP_UUID or UPDATED_QUESTION_GROUP_UUID
        defaultQuestionnaireQuestionGroupShouldBeFound("questionGroupUuid.in=" + DEFAULT_QUESTION_GROUP_UUID + "," + UPDATED_QUESTION_GROUP_UUID);

        // Get all the questionnaireQuestionGroupList where questionGroupUuid equals to UPDATED_QUESTION_GROUP_UUID
        defaultQuestionnaireQuestionGroupShouldNotBeFound("questionGroupUuid.in=" + UPDATED_QUESTION_GROUP_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByQuestionGroupUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where questionGroupUuid is not null
        defaultQuestionnaireQuestionGroupShouldBeFound("questionGroupUuid.specified=true");

        // Get all the questionnaireQuestionGroupList where questionGroupUuid is null
        defaultQuestionnaireQuestionGroupShouldNotBeFound("questionGroupUuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where order equals to DEFAULT_ORDER
        defaultQuestionnaireQuestionGroupShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the questionnaireQuestionGroupList where order equals to UPDATED_ORDER
        defaultQuestionnaireQuestionGroupShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultQuestionnaireQuestionGroupShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the questionnaireQuestionGroupList where order equals to UPDATED_ORDER
        defaultQuestionnaireQuestionGroupShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where order is not null
        defaultQuestionnaireQuestionGroupShouldBeFound("order.specified=true");

        // Get all the questionnaireQuestionGroupList where order is null
        defaultQuestionnaireQuestionGroupShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where order is greater than or equal to DEFAULT_ORDER
        defaultQuestionnaireQuestionGroupShouldBeFound("order.greaterThanOrEqual=" + DEFAULT_ORDER);

        // Get all the questionnaireQuestionGroupList where order is greater than or equal to UPDATED_ORDER
        defaultQuestionnaireQuestionGroupShouldNotBeFound("order.greaterThanOrEqual=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where order is less than or equal to DEFAULT_ORDER
        defaultQuestionnaireQuestionGroupShouldBeFound("order.lessThanOrEqual=" + DEFAULT_ORDER);

        // Get all the questionnaireQuestionGroupList where order is less than or equal to SMALLER_ORDER
        defaultQuestionnaireQuestionGroupShouldNotBeFound("order.lessThanOrEqual=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where order is less than DEFAULT_ORDER
        defaultQuestionnaireQuestionGroupShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the questionnaireQuestionGroupList where order is less than UPDATED_ORDER
        defaultQuestionnaireQuestionGroupShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        // Get all the questionnaireQuestionGroupList where order is greater than DEFAULT_ORDER
        defaultQuestionnaireQuestionGroupShouldNotBeFound("order.greaterThan=" + DEFAULT_ORDER);

        // Get all the questionnaireQuestionGroupList where order is greater than SMALLER_ORDER
        defaultQuestionnaireQuestionGroupShouldBeFound("order.greaterThan=" + SMALLER_ORDER);
    }


    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByQuestionnaireIsEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);
        Questionnaire questionnaire = QuestionnaireResourceIT.createEntity(em);
        em.persist(questionnaire);
        em.flush();
        questionnaireQuestionGroup.setQuestionnaire(questionnaire);
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);
        Long questionnaireId = questionnaire.getId();

        // Get all the questionnaireQuestionGroupList where questionnaire equals to questionnaireId
        defaultQuestionnaireQuestionGroupShouldBeFound("questionnaireId.equals=" + questionnaireId);

        // Get all the questionnaireQuestionGroupList where questionnaire equals to questionnaireId + 1
        defaultQuestionnaireQuestionGroupShouldNotBeFound("questionnaireId.equals=" + (questionnaireId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionnaireQuestionGroupsByQuestionGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);
        QuestionGroup questionGroup = QuestionGroupResourceIT.createEntity(em);
        em.persist(questionGroup);
        em.flush();
        questionnaireQuestionGroup.setQuestionGroup(questionGroup);
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);
        Long questionGroupId = questionGroup.getId();

        // Get all the questionnaireQuestionGroupList where questionGroup equals to questionGroupId
        defaultQuestionnaireQuestionGroupShouldBeFound("questionGroupId.equals=" + questionGroupId);

        // Get all the questionnaireQuestionGroupList where questionGroup equals to questionGroupId + 1
        defaultQuestionnaireQuestionGroupShouldNotBeFound("questionGroupId.equals=" + (questionGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionnaireQuestionGroupShouldBeFound(String filter) throws Exception {
        restQuestionnaireQuestionGroupMockMvc.perform(get("/api/questionnaire-question-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionnaireQuestionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));

        // Check, that the count call also returns 1
        restQuestionnaireQuestionGroupMockMvc.perform(get("/api/questionnaire-question-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionnaireQuestionGroupShouldNotBeFound(String filter) throws Exception {
        restQuestionnaireQuestionGroupMockMvc.perform(get("/api/questionnaire-question-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionnaireQuestionGroupMockMvc.perform(get("/api/questionnaire-question-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQuestionnaireQuestionGroup() throws Exception {
        // Get the questionnaireQuestionGroup
        restQuestionnaireQuestionGroupMockMvc.perform(get("/api/questionnaire-question-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    // Commented for now, as we will not be updating Questionnaires, etc.
    // @Test
    // @Transactional
    // public void updateQuestionnaireQuestionGroup() throws Exception {
    //     // Initialize the database
    //     questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

    //     int databaseSizeBeforeUpdate = questionnaireQuestionGroupRepository.findAll().size();

    //     // Update the questionnaireQuestionGroup
    //     QuestionnaireQuestionGroup updatedQuestionnaireQuestionGroup = questionnaireQuestionGroupRepository.findById(questionnaireQuestionGroup.getId()).get();
    //     // Disconnect from session so that the updates on updatedQuestionnaireQuestionGroup are not directly saved in db
    //     em.detach(updatedQuestionnaireQuestionGroup);
    //     updatedQuestionnaireQuestionGroup
    //         .uuid(UPDATED_UUID)
    //         .questionnaireUuid(UPDATED_QUESTIONNAIRE_UUID)
    //         .questionGroupUuid(UPDATED_QUESTION_GROUP_UUID)
    //         .order(UPDATED_ORDER);
    //     QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO = questionnaireQuestionGroupMapper.toDto(updatedQuestionnaireQuestionGroup);

    //     restQuestionnaireQuestionGroupMockMvc.perform(put("/api/questionnaire-question-groups")
    //         .contentType(TestUtil.APPLICATION_JSON_UTF8)
    //         .content(TestUtil.convertObjectToJsonBytes(questionnaireQuestionGroupDTO)))
    //         .andExpect(status().isOk());

    //     // Validate the QuestionnaireQuestionGroup in the database
    //     List<QuestionnaireQuestionGroup> questionnaireQuestionGroupList = questionnaireQuestionGroupRepository.findAll();
    //     assertThat(questionnaireQuestionGroupList).hasSize(databaseSizeBeforeUpdate);
    //     QuestionnaireQuestionGroup testQuestionnaireQuestionGroup = questionnaireQuestionGroupList.get(questionnaireQuestionGroupList.size() - 1);
    //     assertThat(testQuestionnaireQuestionGroup.getUuid()).isEqualTo(UPDATED_UUID);
    //     assertThat(testQuestionnaireQuestionGroup.getQuestionnaireUuid()).isEqualTo(UPDATED_QUESTIONNAIRE_UUID);
    //     assertThat(testQuestionnaireQuestionGroup.getQuestionGroupUuid()).isEqualTo(UPDATED_QUESTION_GROUP_UUID);
    //     assertThat(testQuestionnaireQuestionGroup.getOrder()).isEqualTo(UPDATED_ORDER);
    // }

    @Test
    @Transactional
    public void updateNonExistingQuestionnaireQuestionGroup() throws Exception {
        int databaseSizeBeforeUpdate = questionnaireQuestionGroupRepository.findAll().size();

        // Create the QuestionnaireQuestionGroup
        QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO = questionnaireQuestionGroupMapper.toDto(questionnaireQuestionGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionnaireQuestionGroupMockMvc.perform(put("/api/questionnaire-question-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaireQuestionGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionnaireQuestionGroup in the database
        List<QuestionnaireQuestionGroup> questionnaireQuestionGroupList = questionnaireQuestionGroupRepository.findAll();
        assertThat(questionnaireQuestionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionnaireQuestionGroup() throws Exception {
        // Initialize the database
        questionnaireQuestionGroupRepository.saveAndFlush(questionnaireQuestionGroup);

        int databaseSizeBeforeDelete = questionnaireQuestionGroupRepository.findAll().size();

        // Delete the questionnaireQuestionGroup
        restQuestionnaireQuestionGroupMockMvc.perform(delete("/api/questionnaire-question-groups/{id}", questionnaireQuestionGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionnaireQuestionGroup> questionnaireQuestionGroupList = questionnaireQuestionGroupRepository.findAll();
        assertThat(questionnaireQuestionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionnaireQuestionGroup.class);
        QuestionnaireQuestionGroup questionnaireQuestionGroup1 = new QuestionnaireQuestionGroup();
        questionnaireQuestionGroup1.setId(1L);
        QuestionnaireQuestionGroup questionnaireQuestionGroup2 = new QuestionnaireQuestionGroup();
        questionnaireQuestionGroup2.setId(questionnaireQuestionGroup1.getId());
        assertThat(questionnaireQuestionGroup1).isEqualTo(questionnaireQuestionGroup2);
        questionnaireQuestionGroup2.setId(2L);
        assertThat(questionnaireQuestionGroup1).isNotEqualTo(questionnaireQuestionGroup2);
        questionnaireQuestionGroup1.setId(null);
        assertThat(questionnaireQuestionGroup1).isNotEqualTo(questionnaireQuestionGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionnaireQuestionGroupDTO.class);
        QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO1 = new QuestionnaireQuestionGroupDTO();
        questionnaireQuestionGroupDTO1.setId(1L);
        QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO2 = new QuestionnaireQuestionGroupDTO();
        assertThat(questionnaireQuestionGroupDTO1).isNotEqualTo(questionnaireQuestionGroupDTO2);
        questionnaireQuestionGroupDTO2.setId(questionnaireQuestionGroupDTO1.getId());
        assertThat(questionnaireQuestionGroupDTO1).isEqualTo(questionnaireQuestionGroupDTO2);
        questionnaireQuestionGroupDTO2.setId(2L);
        assertThat(questionnaireQuestionGroupDTO1).isNotEqualTo(questionnaireQuestionGroupDTO2);
        questionnaireQuestionGroupDTO1.setId(null);
        assertThat(questionnaireQuestionGroupDTO1).isNotEqualTo(questionnaireQuestionGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionnaireQuestionGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionnaireQuestionGroupMapper.fromId(null)).isNull();
    }
}
