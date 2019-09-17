package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.repository.AnswerGroupRepository;
import uk.ac.herc.bcra.service.AnswerGroupService;
import uk.ac.herc.bcra.service.dto.AnswerGroupDTO;
import uk.ac.herc.bcra.service.mapper.AnswerGroupMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.AnswerGroupCriteria;
import uk.ac.herc.bcra.service.AnswerGroupQueryService;

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
 * Integration tests for the {@link AnswerGroupResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class AnswerGroupResourceIT {

    @Autowired
    private AnswerGroupRepository answerGroupRepository;

    @Autowired
    private AnswerGroupMapper answerGroupMapper;

    @Autowired
    private AnswerGroupService answerGroupService;

    @Autowired
    private AnswerGroupQueryService answerGroupQueryService;

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

    private MockMvc restAnswerGroupMockMvc;

    private AnswerGroup answerGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnswerGroupResource answerGroupResource = new AnswerGroupResource(answerGroupService, answerGroupQueryService);
        this.restAnswerGroupMockMvc = MockMvcBuilders.standaloneSetup(answerGroupResource)
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
    public static AnswerGroup createEntity(EntityManager em) {
        AnswerGroup answerGroup = new AnswerGroup();
        return answerGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerGroup createUpdatedEntity(EntityManager em) {
        AnswerGroup answerGroup = new AnswerGroup();
        return answerGroup;
    }

    @BeforeEach
    public void initTest() {
        answerGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnswerGroup() throws Exception {
        int databaseSizeBeforeCreate = answerGroupRepository.findAll().size();

        // Create the AnswerGroup
        AnswerGroupDTO answerGroupDTO = answerGroupMapper.toDto(answerGroup);
        restAnswerGroupMockMvc.perform(post("/api/answer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the AnswerGroup in the database
        List<AnswerGroup> answerGroupList = answerGroupRepository.findAll();
        assertThat(answerGroupList).hasSize(databaseSizeBeforeCreate + 1);
        AnswerGroup testAnswerGroup = answerGroupList.get(answerGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void createAnswerGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = answerGroupRepository.findAll().size();

        // Create the AnswerGroup with an existing ID
        answerGroup.setId(1L);
        AnswerGroupDTO answerGroupDTO = answerGroupMapper.toDto(answerGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerGroupMockMvc.perform(post("/api/answer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerGroup in the database
        List<AnswerGroup> answerGroupList = answerGroupRepository.findAll();
        assertThat(answerGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnswerGroups() throws Exception {
        // Initialize the database
        answerGroupRepository.saveAndFlush(answerGroup);

        // Get all the answerGroupList
        restAnswerGroupMockMvc.perform(get("/api/answer-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerGroup.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getAnswerGroup() throws Exception {
        // Initialize the database
        answerGroupRepository.saveAndFlush(answerGroup);

        // Get the answerGroup
        restAnswerGroupMockMvc.perform(get("/api/answer-groups/{id}", answerGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(answerGroup.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllAnswerGroupsByAnswerResponseIsEqualToSomething() throws Exception {
        // Initialize the database
        answerGroupRepository.saveAndFlush(answerGroup);
        AnswerResponse answerResponse = AnswerResponseResourceIT.createEntity(em);
        em.persist(answerResponse);
        em.flush();
        answerGroup.setAnswerResponse(answerResponse);
        answerGroupRepository.saveAndFlush(answerGroup);
        Long answerResponseId = answerResponse.getId();

        // Get all the answerGroupList where answerResponse equals to answerResponseId
        defaultAnswerGroupShouldBeFound("answerResponseId.equals=" + answerResponseId);

        // Get all the answerGroupList where answerResponse equals to answerResponseId + 1
        defaultAnswerGroupShouldNotBeFound("answerResponseId.equals=" + (answerResponseId + 1));
    }


    @Test
    @Transactional
    public void getAllAnswerGroupsByQuestionGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        answerGroupRepository.saveAndFlush(answerGroup);
        QuestionGroup questionGroup = QuestionGroupResourceIT.createEntity(em);
        em.persist(questionGroup);
        em.flush();
        answerGroup.setQuestionGroup(questionGroup);
        answerGroupRepository.saveAndFlush(answerGroup);
        Long questionGroupId = questionGroup.getId();

        // Get all the answerGroupList where questionGroup equals to questionGroupId
        defaultAnswerGroupShouldBeFound("questionGroupId.equals=" + questionGroupId);

        // Get all the answerGroupList where questionGroup equals to questionGroupId + 1
        defaultAnswerGroupShouldNotBeFound("questionGroupId.equals=" + (questionGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllAnswerGroupsByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        answerGroupRepository.saveAndFlush(answerGroup);
        Answer answer = AnswerResourceIT.createEntity(em);
        em.persist(answer);
        em.flush();
        answerGroup.addAnswer(answer);
        answerGroupRepository.saveAndFlush(answerGroup);
        Long answerId = answer.getId();

        // Get all the answerGroupList where answer equals to answerId
        defaultAnswerGroupShouldBeFound("answerId.equals=" + answerId);

        // Get all the answerGroupList where answer equals to answerId + 1
        defaultAnswerGroupShouldNotBeFound("answerId.equals=" + (answerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnswerGroupShouldBeFound(String filter) throws Exception {
        restAnswerGroupMockMvc.perform(get("/api/answer-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answerGroup.getId().intValue())));

        // Check, that the count call also returns 1
        restAnswerGroupMockMvc.perform(get("/api/answer-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnswerGroupShouldNotBeFound(String filter) throws Exception {
        restAnswerGroupMockMvc.perform(get("/api/answer-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnswerGroupMockMvc.perform(get("/api/answer-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAnswerGroup() throws Exception {
        // Get the answerGroup
        restAnswerGroupMockMvc.perform(get("/api/answer-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnswerGroup() throws Exception {
        // Initialize the database
        answerGroupRepository.saveAndFlush(answerGroup);

        int databaseSizeBeforeUpdate = answerGroupRepository.findAll().size();

        // Update the answerGroup
        AnswerGroup updatedAnswerGroup = answerGroupRepository.findById(answerGroup.getId()).get();
        // Disconnect from session so that the updates on updatedAnswerGroup are not directly saved in db
        em.detach(updatedAnswerGroup);
        AnswerGroupDTO answerGroupDTO = answerGroupMapper.toDto(updatedAnswerGroup);

        restAnswerGroupMockMvc.perform(put("/api/answer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerGroupDTO)))
            .andExpect(status().isOk());

        // Validate the AnswerGroup in the database
        List<AnswerGroup> answerGroupList = answerGroupRepository.findAll();
        assertThat(answerGroupList).hasSize(databaseSizeBeforeUpdate);
        AnswerGroup testAnswerGroup = answerGroupList.get(answerGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAnswerGroup() throws Exception {
        int databaseSizeBeforeUpdate = answerGroupRepository.findAll().size();

        // Create the AnswerGroup
        AnswerGroupDTO answerGroupDTO = answerGroupMapper.toDto(answerGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerGroupMockMvc.perform(put("/api/answer-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(answerGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnswerGroup in the database
        List<AnswerGroup> answerGroupList = answerGroupRepository.findAll();
        assertThat(answerGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnswerGroup() throws Exception {
        // Initialize the database
        answerGroupRepository.saveAndFlush(answerGroup);

        int databaseSizeBeforeDelete = answerGroupRepository.findAll().size();

        // Delete the answerGroup
        restAnswerGroupMockMvc.perform(delete("/api/answer-groups/{id}", answerGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnswerGroup> answerGroupList = answerGroupRepository.findAll();
        assertThat(answerGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerGroup.class);
        AnswerGroup answerGroup1 = new AnswerGroup();
        answerGroup1.setId(1L);
        AnswerGroup answerGroup2 = new AnswerGroup();
        answerGroup2.setId(answerGroup1.getId());
        assertThat(answerGroup1).isEqualTo(answerGroup2);
        answerGroup2.setId(2L);
        assertThat(answerGroup1).isNotEqualTo(answerGroup2);
        answerGroup1.setId(null);
        assertThat(answerGroup1).isNotEqualTo(answerGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnswerGroupDTO.class);
        AnswerGroupDTO answerGroupDTO1 = new AnswerGroupDTO();
        answerGroupDTO1.setId(1L);
        AnswerGroupDTO answerGroupDTO2 = new AnswerGroupDTO();
        assertThat(answerGroupDTO1).isNotEqualTo(answerGroupDTO2);
        answerGroupDTO2.setId(answerGroupDTO1.getId());
        assertThat(answerGroupDTO1).isEqualTo(answerGroupDTO2);
        answerGroupDTO2.setId(2L);
        assertThat(answerGroupDTO1).isNotEqualTo(answerGroupDTO2);
        answerGroupDTO1.setId(null);
        assertThat(answerGroupDTO1).isNotEqualTo(answerGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(answerGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(answerGroupMapper.fromId(null)).isNull();
    }
}
