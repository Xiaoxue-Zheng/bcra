package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Question;
import uk.ac.herc.bcra.domain.QuestionGroupQuestion;
import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.repository.QuestionRepository;
import uk.ac.herc.bcra.service.QuestionService;
import uk.ac.herc.bcra.service.dto.QuestionDTO;
import uk.ac.herc.bcra.service.mapper.QuestionMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.QuestionCriteria;
import uk.ac.herc.bcra.service.QuestionQueryService;

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
 * Integration tests for the {@link QuestionResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class QuestionResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionQueryService questionQueryService;

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

    private MockMvc restQuestionMockMvc;

    private Question question;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionResource questionResource = new QuestionResource(questionService, questionQueryService);
        this.restQuestionMockMvc = MockMvcBuilders.standaloneSetup(questionResource)
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
    public static Question createEntity(EntityManager em) {
        Question question = new Question()
            .uuid(DEFAULT_UUID)
            .text(DEFAULT_TEXT);
        return question;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createUpdatedEntity(EntityManager em) {
        Question question = new Question()
            .uuid(UPDATED_UUID)
            .text(UPDATED_TEXT);
        return question;
    }

    @BeforeEach
    public void initTest() {
        question = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestion() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isCreated());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate + 1);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testQuestion.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    public void createQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // Create the Question with an existing ID
        question.setId(1L);
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionRepository.findAll().size();
        // set the field null
        question.setUuid(null);

        // Create the Question, which fails.
        QuestionDTO questionDTO = questionMapper.toDto(question);

        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionRepository.findAll().size();
        // set the field null
        question.setText(null);

        // Create the Question, which fails.
        QuestionDTO questionDTO = questionMapper.toDto(question);

        restQuestionMockMvc.perform(post("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList
        restQuestionMockMvc.perform(get("/api/questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())));
    }
    
    @Test
    @Transactional
    public void getQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(question.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()));
    }

    @Test
    @Transactional
    public void getAllQuestionsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where uuid equals to DEFAULT_UUID
        defaultQuestionShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the questionList where uuid equals to UPDATED_UUID
        defaultQuestionShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultQuestionShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the questionList where uuid equals to UPDATED_UUID
        defaultQuestionShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllQuestionsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where uuid is not null
        defaultQuestionShouldBeFound("uuid.specified=true");

        // Get all the questionList where uuid is null
        defaultQuestionShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionsByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where text equals to DEFAULT_TEXT
        defaultQuestionShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the questionList where text equals to UPDATED_TEXT
        defaultQuestionShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllQuestionsByTextIsInShouldWork() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultQuestionShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the questionList where text equals to UPDATED_TEXT
        defaultQuestionShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllQuestionsByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questionList where text is not null
        defaultQuestionShouldBeFound("text.specified=true");

        // Get all the questionList where text is null
        defaultQuestionShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    public void getAllQuestionsByQuestionGroupQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);
        QuestionGroupQuestion questionGroupQuestion = QuestionGroupQuestionResourceIT.createEntity(em);
        em.persist(questionGroupQuestion);
        em.flush();
        question.addQuestionGroupQuestion(questionGroupQuestion);
        questionRepository.saveAndFlush(question);
        Long questionGroupQuestionId = questionGroupQuestion.getId();

        // Get all the questionList where questionGroupQuestion equals to questionGroupQuestionId
        defaultQuestionShouldBeFound("questionGroupQuestionId.equals=" + questionGroupQuestionId);

        // Get all the questionList where questionGroupQuestion equals to questionGroupQuestionId + 1
        defaultQuestionShouldNotBeFound("questionGroupQuestionId.equals=" + (questionGroupQuestionId + 1));
    }


    @Test
    @Transactional
    public void getAllQuestionsByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);
        Answer answer = AnswerResourceIT.createEntity(em);
        em.persist(answer);
        em.flush();
        question.addAnswer(answer);
        questionRepository.saveAndFlush(question);
        Long answerId = answer.getId();

        // Get all the questionList where answer equals to answerId
        defaultQuestionShouldBeFound("answerId.equals=" + answerId);

        // Get all the questionList where answer equals to answerId + 1
        defaultQuestionShouldNotBeFound("answerId.equals=" + (answerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuestionShouldBeFound(String filter) throws Exception {
        restQuestionMockMvc.perform(get("/api/questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));

        // Check, that the count call also returns 1
        restQuestionMockMvc.perform(get("/api/questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuestionShouldNotBeFound(String filter) throws Exception {
        restQuestionMockMvc.perform(get("/api/questions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuestionMockMvc.perform(get("/api/questions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQuestion() throws Exception {
        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question
        Question updatedQuestion = questionRepository.findById(question.getId()).get();
        // Disconnect from session so that the updates on updatedQuestion are not directly saved in db
        em.detach(updatedQuestion);
        updatedQuestion
            .uuid(UPDATED_UUID)
            .text(UPDATED_TEXT);
        QuestionDTO questionDTO = questionMapper.toDto(updatedQuestion);

        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testQuestion.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc.perform(put("/api/questions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        int databaseSizeBeforeDelete = questionRepository.findAll().size();

        // Delete the question
        restQuestionMockMvc.perform(delete("/api/questions/{id}", question.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Question.class);
        Question question1 = new Question();
        question1.setId(1L);
        Question question2 = new Question();
        question2.setId(question1.getId());
        assertThat(question1).isEqualTo(question2);
        question2.setId(2L);
        assertThat(question1).isNotEqualTo(question2);
        question1.setId(null);
        assertThat(question1).isNotEqualTo(question2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionDTO.class);
        QuestionDTO questionDTO1 = new QuestionDTO();
        questionDTO1.setId(1L);
        QuestionDTO questionDTO2 = new QuestionDTO();
        assertThat(questionDTO1).isNotEqualTo(questionDTO2);
        questionDTO2.setId(questionDTO1.getId());
        assertThat(questionDTO1).isEqualTo(questionDTO2);
        questionDTO2.setId(2L);
        assertThat(questionDTO1).isNotEqualTo(questionDTO2);
        questionDTO1.setId(null);
        assertThat(questionDTO1).isNotEqualTo(questionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(questionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(questionMapper.fromId(null)).isNull();
    }
}
