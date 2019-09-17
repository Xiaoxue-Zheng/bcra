package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.QuestionGroupQuestion;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.domain.Question;
import uk.ac.herc.bcra.repository.QuestionGroupQuestionRepository;
import uk.ac.herc.bcra.service.QuestionGroupQuestionService;
import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionDTO;
import uk.ac.herc.bcra.service.mapper.QuestionGroupQuestionMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.QuestionGroupQuestionCriteria;
import uk.ac.herc.bcra.service.QuestionGroupQuestionQueryService;

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
 * Integration tests for the {@link QuestionGroupQuestionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionGroupQuestionResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_GROUP_UUID = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_GROUP_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_UUID = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_UUID = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;
    private static final Integer SMALLER_ORDER = 1 - 1;

    @Autowired
    private QuestionGroupQuestionRepository questionGroupQuestionRepository;

    @Autowired
    private QuestionGroupQuestionMapper questionGroupQuestionMapper;

    @Autowired
    private QuestionGroupQuestionService questionGroupQuestionService;

    @Autowired
    private QuestionGroupQuestionQueryService questionGroupQuestionQueryService;

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

    private MockMvc restQuestionGroupQuestionMockMvc;

    private QuestionGroupQuestion questionGroupQuestion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionGroupQuestionResource questionGroupQuestionResource = new QuestionGroupQuestionResource(questionGroupQuestionService, questionGroupQuestionQueryService);
        this.restQuestionGroupQuestionMockMvc = MockMvcBuilders.standaloneSetup(questionGroupQuestionResource)
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
    public static QuestionGroupQuestion createEntity(EntityManager em) {
        QuestionGroupQuestion questionGroupQuestion = new QuestionGroupQuestion()
            .uuid(DEFAULT_UUID)
            .questionGroupUuid(DEFAULT_QUESTION_GROUP_UUID)
            .questionUuid(DEFAULT_QUESTION_UUID)
            .order(DEFAULT_ORDER);
        return questionGroupQuestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionGroupQuestion createUpdatedEntity(EntityManager em) {
        QuestionGroupQuestion questionGroupQuestion = new QuestionGroupQuestion()
            .uuid(UPDATED_UUID)
            .questionGroupUuid(UPDATED_QUESTION_GROUP_UUID)
            .questionUuid(UPDATED_QUESTION_UUID)
            .order(UPDATED_ORDER);
        return questionGroupQuestion;
    }

    @BeforeEach
    public void initTest() {
        questionGroupQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionGroupQuestion() throws Exception {
        int databaseSizeBeforeCreate = questionGroupQuestionRepository.findAll().size();

        // Create the QuestionGroupQuestion
        QuestionGroupQuestionDTO questionGroupQuestionDTO = questionGroupQuestionMapper.toDto(questionGroupQuestion);
        restQuestionGroupQuestionMockMvc.perform(post("/api/question-group-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupQuestionDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionGroupQuestion in the database
        List<QuestionGroupQuestion> questionGroupQuestionList = questionGroupQuestionRepository.findAll();
        assertThat(questionGroupQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionGroupQuestion testQuestionGroupQuestion = questionGroupQuestionList.get(questionGroupQuestionList.size() - 1);
        assertThat(testQuestionGroupQuestion.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testQuestionGroupQuestion.getQuestionGroupUuid()).isEqualTo(DEFAULT_QUESTION_GROUP_UUID);
        assertThat(testQuestionGroupQuestion.getQuestionUuid()).isEqualTo(DEFAULT_QUESTION_UUID);
        assertThat(testQuestionGroupQuestion.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createQuestionGroupQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionGroupQuestionRepository.findAll().size();

        // Create the QuestionGroupQuestion with an existing ID
        questionGroupQuestion.setId(1L);
        QuestionGroupQuestionDTO questionGroupQuestionDTO = questionGroupQuestionMapper.toDto(questionGroupQuestion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionGroupQuestionMockMvc.perform(post("/api/question-group-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionGroupQuestion in the database
        List<QuestionGroupQuestion> questionGroupQuestionList = questionGroupQuestionRepository.findAll();
        assertThat(questionGroupQuestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionGroupQuestionRepository.findAll().size();
        // set the field null
        questionGroupQuestion.setUuid(null);

        // Create the QuestionGroupQuestion, which fails.
        QuestionGroupQuestionDTO questionGroupQuestionDTO = questionGroupQuestionMapper.toDto(questionGroupQuestion);

        restQuestionGroupQuestionMockMvc.perform(post("/api/question-group-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupQuestionDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionGroupQuestion> questionGroupQuestionList = questionGroupQuestionRepository.findAll();
        assertThat(questionGroupQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionGroupUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionGroupQuestionRepository.findAll().size();
        // set the field null
        questionGroupQuestion.setQuestionGroupUuid(null);

        // Create the QuestionGroupQuestion, which fails.
        QuestionGroupQuestionDTO questionGroupQuestionDTO = questionGroupQuestionMapper.toDto(questionGroupQuestion);

        restQuestionGroupQuestionMockMvc.perform(post("/api/question-group-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupQuestionDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionGroupQuestion> questionGroupQuestionList = questionGroupQuestionRepository.findAll();
        assertThat(questionGroupQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionGroupQuestionRepository.findAll().size();
        // set the field null
        questionGroupQuestion.setQuestionUuid(null);

        // Create the QuestionGroupQuestion, which fails.
        QuestionGroupQuestionDTO questionGroupQuestionDTO = questionGroupQuestionMapper.toDto(questionGroupQuestion);

        restQuestionGroupQuestionMockMvc.perform(post("/api/question-group-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupQuestionDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionGroupQuestion> questionGroupQuestionList = questionGroupQuestionRepository.findAll();
        assertThat(questionGroupQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionGroupQuestionRepository.findAll().size();
        // set the field null
        questionGroupQuestion.setOrder(null);

        // Create the QuestionGroupQuestion, which fails.
        QuestionGroupQuestionDTO questionGroupQuestionDTO = questionGroupQuestionMapper.toDto(questionGroupQuestion);

        restQuestionGroupQuestionMockMvc.perform(post("/api/question-group-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupQuestionDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionGroupQuestion> questionGroupQuestionList = questionGroupQuestionRepository.findAll();
        assertThat(questionGroupQuestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestions() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList
        restQuestionGroupQuestionMockMvc.perform(get("/api/question-group-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionGroupQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].questionGroupUuid").value(hasItem(DEFAULT_QUESTION_GROUP_UUID.toString())))
            .andExpect(jsonPath("$.[*].questionUuid").value(hasItem(DEFAULT_QUESTION_UUID.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getQuestionGroupQuestion() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get the questionGroupQuestion
        restQuestionGroupQuestionMockMvc.perform(get("/api/question-group-questions/{id}", questionGroupQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionGroupQuestion.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.questionGroupUuid").value(DEFAULT_QUESTION_GROUP_UUID.toString()))
            .andExpect(jsonPath("$.questionUuid").value(DEFAULT_QUESTION_UUID.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where uuid equals to DEFAULT_UUID
        defaultQuestionGroupQuestionShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the questionGroupQuestionList where uuid equals to UPDATED_UUID
        defaultQuestionGroupQuestionShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultQuestionGroupQuestionShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the questionGroupQuestionList where uuid equals to UPDATED_UUID
        defaultQuestionGroupQuestionShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where uuid is not null
        defaultQuestionGroupQuestionShouldBeFound("uuid.specified=true");

        // Get all the questionGroupQuestionList where uuid is null
        defaultQuestionGroupQuestionShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByQuestionGroupUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where questionGroupUuid equals to DEFAULT_QUESTION_GROUP_UUID
        defaultQuestionGroupQuestionShouldBeFound("questionGroupUuid.equals=" + DEFAULT_QUESTION_GROUP_UUID);

        // Get all the questionGroupQuestionList where questionGroupUuid equals to UPDATED_QUESTION_GROUP_UUID
        defaultQuestionGroupQuestionShouldNotBeFound("questionGroupUuid.equals=" + UPDATED_QUESTION_GROUP_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByQuestionGroupUuidIsInShouldWork() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where questionGroupUuid in DEFAULT_QUESTION_GROUP_UUID or UPDATED_QUESTION_GROUP_UUID
        defaultQuestionGroupQuestionShouldBeFound("questionGroupUuid.in=" + DEFAULT_QUESTION_GROUP_UUID + "," + UPDATED_QUESTION_GROUP_UUID);

        // Get all the questionGroupQuestionList where questionGroupUuid equals to UPDATED_QUESTION_GROUP_UUID
        defaultQuestionGroupQuestionShouldNotBeFound("questionGroupUuid.in=" + UPDATED_QUESTION_GROUP_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByQuestionGroupUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where questionGroupUuid is not null
        defaultQuestionGroupQuestionShouldBeFound("questionGroupUuid.specified=true");

        // Get all the questionGroupQuestionList where questionGroupUuid is null
        defaultQuestionGroupQuestionShouldNotBeFound("questionGroupUuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByQuestionUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where questionUuid equals to DEFAULT_QUESTION_UUID
        defaultQuestionGroupQuestionShouldBeFound("questionUuid.equals=" + DEFAULT_QUESTION_UUID);

        // Get all the questionGroupQuestionList where questionUuid equals to UPDATED_QUESTION_UUID
        defaultQuestionGroupQuestionShouldNotBeFound("questionUuid.equals=" + UPDATED_QUESTION_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByQuestionUuidIsInShouldWork() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where questionUuid in DEFAULT_QUESTION_UUID or UPDATED_QUESTION_UUID
        defaultQuestionGroupQuestionShouldBeFound("questionUuid.in=" + DEFAULT_QUESTION_UUID + "," + UPDATED_QUESTION_UUID);

        // Get all the questionGroupQuestionList where questionUuid equals to UPDATED_QUESTION_UUID
        defaultQuestionGroupQuestionShouldNotBeFound("questionUuid.in=" + UPDATED_QUESTION_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByQuestionUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where questionUuid is not null
        defaultQuestionGroupQuestionShouldBeFound("questionUuid.specified=true");

        // Get all the questionGroupQuestionList where questionUuid is null
        defaultQuestionGroupQuestionShouldNotBeFound("questionUuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where order equals to DEFAULT_ORDER
        defaultQuestionGroupQuestionShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the questionGroupQuestionList where order equals to UPDATED_ORDER
        defaultQuestionGroupQuestionShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultQuestionGroupQuestionShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the questionGroupQuestionList where order equals to UPDATED_ORDER
        defaultQuestionGroupQuestionShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where order is not null
        defaultQuestionGroupQuestionShouldBeFound("order.specified=true");

        // Get all the questionGroupQuestionList where order is null
        defaultQuestionGroupQuestionShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where order is greater than or equal to DEFAULT_ORDER
        defaultQuestionGroupQuestionShouldBeFound("order.greaterThanOrEqual=" + DEFAULT_ORDER);

        // Get all the questionGroupQuestionList where order is greater than or equal to UPDATED_ORDER
        defaultQuestionGroupQuestionShouldNotBeFound("order.greaterThanOrEqual=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where order is less than or equal to DEFAULT_ORDER
        defaultQuestionGroupQuestionShouldBeFound("order.lessThanOrEqual=" + DEFAULT_ORDER);

        // Get all the questionGroupQuestionList where order is less than or equal to SMALLER_ORDER
        defaultQuestionGroupQuestionShouldNotBeFound("order.lessThanOrEqual=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where order is less than DEFAULT_ORDER
        defaultQuestionGroupQuestionShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the questionGroupQuestionList where order is less than UPDATED_ORDER
        defaultQuestionGroupQuestionShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        // Get all the questionGroupQuestionList where order is greater than DEFAULT_ORDER
        defaultQuestionGroupQuestionShouldNotBeFound("order.greaterThan=" + DEFAULT_ORDER);

        // Get all the questionGroupQuestionList where order is greater than SMALLER_ORDER
        defaultQuestionGroupQuestionShouldBeFound("order.greaterThan=" + SMALLER_ORDER);
    }


    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByQuestionGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);
        QuestionGroup questionGroup = QuestionGroupResourceIT.createEntity(em);
        em.persist(questionGroup);
        em.flush();
        questionGroupQuestion.setQuestionGroup(questionGroup);
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);
        Long questionGroupId = questionGroup.getId();

        // Get all the questionGroupQuestionList where questionGroup equals to questionGroupId
        defaultQuestionGroupQuestionShouldBeFound("questionGroupId.equals=" + questionGroupId);

        // Get all the questionGroupQuestionList where questionGroup equals to questionGroupId + 1
        defaultQuestionGroupQuestionShouldNotBeFound("questionGroupId.equals=" + (questionGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionGroupQuestionsByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);
        Question question = QuestionResourceIT.createEntity(em);
        em.persist(question);
        em.flush();
        questionGroupQuestion.setQuestion(question);
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);
        Long questionId = question.getId();

        // Get all the questionGroupQuestionList where question equals to questionId
        defaultQuestionGroupQuestionShouldBeFound("questionId.equals=" + questionId);

        // Get all the questionGroupQuestionList where question equals to questionId + 1
        defaultQuestionGroupQuestionShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionGroupQuestionShouldBeFound(String filter) throws Exception {
        restQuestionGroupQuestionMockMvc.perform(get("/api/question-group-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionGroupQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].questionGroupUuid").value(hasItem(DEFAULT_QUESTION_GROUP_UUID)))
            .andExpect(jsonPath("$.[*].questionUuid").value(hasItem(DEFAULT_QUESTION_UUID)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));

        // Check, that the count call also returns 1
        restQuestionGroupQuestionMockMvc.perform(get("/api/question-group-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionGroupQuestionShouldNotBeFound(String filter) throws Exception {
        restQuestionGroupQuestionMockMvc.perform(get("/api/question-group-questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionGroupQuestionMockMvc.perform(get("/api/question-group-questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQuestionGroupQuestion() throws Exception {
        // Get the questionGroupQuestion
        restQuestionGroupQuestionMockMvc.perform(get("/api/question-group-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionGroupQuestion() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        int databaseSizeBeforeUpdate = questionGroupQuestionRepository.findAll().size();

        // Update the questionGroupQuestion
        QuestionGroupQuestion updatedQuestionGroupQuestion = questionGroupQuestionRepository.findById(questionGroupQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionGroupQuestion are not directly saved in db
        em.detach(updatedQuestionGroupQuestion);
        updatedQuestionGroupQuestion
            .uuid(UPDATED_UUID)
            .questionGroupUuid(UPDATED_QUESTION_GROUP_UUID)
            .questionUuid(UPDATED_QUESTION_UUID)
            .order(UPDATED_ORDER);
        QuestionGroupQuestionDTO questionGroupQuestionDTO = questionGroupQuestionMapper.toDto(updatedQuestionGroupQuestion);

        restQuestionGroupQuestionMockMvc.perform(put("/api/question-group-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupQuestionDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionGroupQuestion in the database
        List<QuestionGroupQuestion> questionGroupQuestionList = questionGroupQuestionRepository.findAll();
        assertThat(questionGroupQuestionList).hasSize(databaseSizeBeforeUpdate);
        QuestionGroupQuestion testQuestionGroupQuestion = questionGroupQuestionList.get(questionGroupQuestionList.size() - 1);
        assertThat(testQuestionGroupQuestion.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testQuestionGroupQuestion.getQuestionGroupUuid()).isEqualTo(UPDATED_QUESTION_GROUP_UUID);
        assertThat(testQuestionGroupQuestion.getQuestionUuid()).isEqualTo(UPDATED_QUESTION_UUID);
        assertThat(testQuestionGroupQuestion.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionGroupQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionGroupQuestionRepository.findAll().size();

        // Create the QuestionGroupQuestion
        QuestionGroupQuestionDTO questionGroupQuestionDTO = questionGroupQuestionMapper.toDto(questionGroupQuestion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionGroupQuestionMockMvc.perform(put("/api/question-group-questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionGroupQuestionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionGroupQuestion in the database
        List<QuestionGroupQuestion> questionGroupQuestionList = questionGroupQuestionRepository.findAll();
        assertThat(questionGroupQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionGroupQuestion() throws Exception {
        // Initialize the database
        questionGroupQuestionRepository.saveAndFlush(questionGroupQuestion);

        int databaseSizeBeforeDelete = questionGroupQuestionRepository.findAll().size();

        // Delete the questionGroupQuestion
        restQuestionGroupQuestionMockMvc.perform(delete("/api/question-group-questions/{id}", questionGroupQuestion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionGroupQuestion> questionGroupQuestionList = questionGroupQuestionRepository.findAll();
        assertThat(questionGroupQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionGroupQuestion.class);
        QuestionGroupQuestion questionGroupQuestion1 = new QuestionGroupQuestion();
        questionGroupQuestion1.setId(1L);
        QuestionGroupQuestion questionGroupQuestion2 = new QuestionGroupQuestion();
        questionGroupQuestion2.setId(questionGroupQuestion1.getId());
        assertThat(questionGroupQuestion1).isEqualTo(questionGroupQuestion2);
        questionGroupQuestion2.setId(2L);
        assertThat(questionGroupQuestion1).isNotEqualTo(questionGroupQuestion2);
        questionGroupQuestion1.setId(null);
        assertThat(questionGroupQuestion1).isNotEqualTo(questionGroupQuestion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionGroupQuestionDTO.class);
        QuestionGroupQuestionDTO questionGroupQuestionDTO1 = new QuestionGroupQuestionDTO();
        questionGroupQuestionDTO1.setId(1L);
        QuestionGroupQuestionDTO questionGroupQuestionDTO2 = new QuestionGroupQuestionDTO();
        assertThat(questionGroupQuestionDTO1).isNotEqualTo(questionGroupQuestionDTO2);
        questionGroupQuestionDTO2.setId(questionGroupQuestionDTO1.getId());
        assertThat(questionGroupQuestionDTO1).isEqualTo(questionGroupQuestionDTO2);
        questionGroupQuestionDTO2.setId(2L);
        assertThat(questionGroupQuestionDTO1).isNotEqualTo(questionGroupQuestionDTO2);
        questionGroupQuestionDTO1.setId(null);
        assertThat(questionGroupQuestionDTO1).isNotEqualTo(questionGroupQuestionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionGroupQuestionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionGroupQuestionMapper.fromId(null)).isNull();
    }
}
