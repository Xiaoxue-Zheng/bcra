package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.CheckboxQuestionItem;
import uk.ac.herc.bcra.domain.CheckboxQuestion;
import uk.ac.herc.bcra.domain.CheckboxAnswerItem;
import uk.ac.herc.bcra.domain.CheckboxDisplayCondition;
import uk.ac.herc.bcra.repository.CheckboxQuestionItemRepository;
import uk.ac.herc.bcra.service.CheckboxQuestionItemService;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionItemDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxQuestionItemMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.CheckboxQuestionItemCriteria;
import uk.ac.herc.bcra.service.CheckboxQuestionItemQueryService;

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
 * Integration tests for the {@link CheckboxQuestionItemResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class CheckboxQuestionItemResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTOR = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTOR = "BBBBBBBBBB";

    @Autowired
    private CheckboxQuestionItemRepository checkboxQuestionItemRepository;

    @Autowired
    private CheckboxQuestionItemMapper checkboxQuestionItemMapper;

    @Autowired
    private CheckboxQuestionItemService checkboxQuestionItemService;

    @Autowired
    private CheckboxQuestionItemQueryService checkboxQuestionItemQueryService;

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

    private MockMvc restCheckboxQuestionItemMockMvc;

    private CheckboxQuestionItem checkboxQuestionItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CheckboxQuestionItemResource checkboxQuestionItemResource = new CheckboxQuestionItemResource(checkboxQuestionItemService, checkboxQuestionItemQueryService);
        this.restCheckboxQuestionItemMockMvc = MockMvcBuilders.standaloneSetup(checkboxQuestionItemResource)
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
    public static CheckboxQuestionItem createEntity(EntityManager em) {
        CheckboxQuestionItem checkboxQuestionItem = new CheckboxQuestionItem()
            .uuid(DEFAULT_UUID)
            .label(DEFAULT_LABEL)
            .descriptor(DEFAULT_DESCRIPTOR);
        return checkboxQuestionItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckboxQuestionItem createUpdatedEntity(EntityManager em) {
        CheckboxQuestionItem checkboxQuestionItem = new CheckboxQuestionItem()
            .uuid(UPDATED_UUID)
            .label(UPDATED_LABEL)
            .descriptor(UPDATED_DESCRIPTOR);
        return checkboxQuestionItem;
    }

    @BeforeEach
    public void initTest() {
        checkboxQuestionItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckboxQuestionItem() throws Exception {
        int databaseSizeBeforeCreate = checkboxQuestionItemRepository.findAll().size();

        // Create the CheckboxQuestionItem
        CheckboxQuestionItemDTO checkboxQuestionItemDTO = checkboxQuestionItemMapper.toDto(checkboxQuestionItem);
        restCheckboxQuestionItemMockMvc.perform(post("/api/checkbox-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckboxQuestionItem in the database
        List<CheckboxQuestionItem> checkboxQuestionItemList = checkboxQuestionItemRepository.findAll();
        assertThat(checkboxQuestionItemList).hasSize(databaseSizeBeforeCreate + 1);
        CheckboxQuestionItem testCheckboxQuestionItem = checkboxQuestionItemList.get(checkboxQuestionItemList.size() - 1);
        assertThat(testCheckboxQuestionItem.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCheckboxQuestionItem.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testCheckboxQuestionItem.getDescriptor()).isEqualTo(DEFAULT_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void createCheckboxQuestionItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkboxQuestionItemRepository.findAll().size();

        // Create the CheckboxQuestionItem with an existing ID
        checkboxQuestionItem.setId(1L);
        CheckboxQuestionItemDTO checkboxQuestionItemDTO = checkboxQuestionItemMapper.toDto(checkboxQuestionItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckboxQuestionItemMockMvc.perform(post("/api/checkbox-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxQuestionItem in the database
        List<CheckboxQuestionItem> checkboxQuestionItemList = checkboxQuestionItemRepository.findAll();
        assertThat(checkboxQuestionItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkboxQuestionItemRepository.findAll().size();
        // set the field null
        checkboxQuestionItem.setUuid(null);

        // Create the CheckboxQuestionItem, which fails.
        CheckboxQuestionItemDTO checkboxQuestionItemDTO = checkboxQuestionItemMapper.toDto(checkboxQuestionItem);

        restCheckboxQuestionItemMockMvc.perform(post("/api/checkbox-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        List<CheckboxQuestionItem> checkboxQuestionItemList = checkboxQuestionItemRepository.findAll();
        assertThat(checkboxQuestionItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkboxQuestionItemRepository.findAll().size();
        // set the field null
        checkboxQuestionItem.setLabel(null);

        // Create the CheckboxQuestionItem, which fails.
        CheckboxQuestionItemDTO checkboxQuestionItemDTO = checkboxQuestionItemMapper.toDto(checkboxQuestionItem);

        restCheckboxQuestionItemMockMvc.perform(post("/api/checkbox-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        List<CheckboxQuestionItem> checkboxQuestionItemList = checkboxQuestionItemRepository.findAll();
        assertThat(checkboxQuestionItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptorIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkboxQuestionItemRepository.findAll().size();
        // set the field null
        checkboxQuestionItem.setDescriptor(null);

        // Create the CheckboxQuestionItem, which fails.
        CheckboxQuestionItemDTO checkboxQuestionItemDTO = checkboxQuestionItemMapper.toDto(checkboxQuestionItem);

        restCheckboxQuestionItemMockMvc.perform(post("/api/checkbox-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        List<CheckboxQuestionItem> checkboxQuestionItemList = checkboxQuestionItemRepository.findAll();
        assertThat(checkboxQuestionItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItems() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList
        restCheckboxQuestionItemMockMvc.perform(get("/api/checkbox-question-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxQuestionItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].descriptor").value(hasItem(DEFAULT_DESCRIPTOR.toString())));
    }
    
    @Test
    @Transactional
    public void getCheckboxQuestionItem() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get the checkboxQuestionItem
        restCheckboxQuestionItemMockMvc.perform(get("/api/checkbox-question-items/{id}", checkboxQuestionItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkboxQuestionItem.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.descriptor").value(DEFAULT_DESCRIPTOR.toString()));
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList where uuid equals to DEFAULT_UUID
        defaultCheckboxQuestionItemShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the checkboxQuestionItemList where uuid equals to UPDATED_UUID
        defaultCheckboxQuestionItemShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultCheckboxQuestionItemShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the checkboxQuestionItemList where uuid equals to UPDATED_UUID
        defaultCheckboxQuestionItemShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList where uuid is not null
        defaultCheckboxQuestionItemShouldBeFound("uuid.specified=true");

        // Get all the checkboxQuestionItemList where uuid is null
        defaultCheckboxQuestionItemShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList where label equals to DEFAULT_LABEL
        defaultCheckboxQuestionItemShouldBeFound("label.equals=" + DEFAULT_LABEL);

        // Get all the checkboxQuestionItemList where label equals to UPDATED_LABEL
        defaultCheckboxQuestionItemShouldNotBeFound("label.equals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByLabelIsInShouldWork() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList where label in DEFAULT_LABEL or UPDATED_LABEL
        defaultCheckboxQuestionItemShouldBeFound("label.in=" + DEFAULT_LABEL + "," + UPDATED_LABEL);

        // Get all the checkboxQuestionItemList where label equals to UPDATED_LABEL
        defaultCheckboxQuestionItemShouldNotBeFound("label.in=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList where label is not null
        defaultCheckboxQuestionItemShouldBeFound("label.specified=true");

        // Get all the checkboxQuestionItemList where label is null
        defaultCheckboxQuestionItemShouldNotBeFound("label.specified=false");
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByDescriptorIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList where descriptor equals to DEFAULT_DESCRIPTOR
        defaultCheckboxQuestionItemShouldBeFound("descriptor.equals=" + DEFAULT_DESCRIPTOR);

        // Get all the checkboxQuestionItemList where descriptor equals to UPDATED_DESCRIPTOR
        defaultCheckboxQuestionItemShouldNotBeFound("descriptor.equals=" + UPDATED_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByDescriptorIsInShouldWork() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList where descriptor in DEFAULT_DESCRIPTOR or UPDATED_DESCRIPTOR
        defaultCheckboxQuestionItemShouldBeFound("descriptor.in=" + DEFAULT_DESCRIPTOR + "," + UPDATED_DESCRIPTOR);

        // Get all the checkboxQuestionItemList where descriptor equals to UPDATED_DESCRIPTOR
        defaultCheckboxQuestionItemShouldNotBeFound("descriptor.in=" + UPDATED_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByDescriptorIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        // Get all the checkboxQuestionItemList where descriptor is not null
        defaultCheckboxQuestionItemShouldBeFound("descriptor.specified=true");

        // Get all the checkboxQuestionItemList where descriptor is null
        defaultCheckboxQuestionItemShouldNotBeFound("descriptor.specified=false");
    }

    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByCheckboxQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);
        CheckboxQuestion checkboxQuestion = CheckboxQuestionResourceIT.createEntity(em);
        em.persist(checkboxQuestion);
        em.flush();
        checkboxQuestionItem.setCheckboxQuestion(checkboxQuestion);
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);
        Long checkboxQuestionId = checkboxQuestion.getId();

        // Get all the checkboxQuestionItemList where checkboxQuestion equals to checkboxQuestionId
        defaultCheckboxQuestionItemShouldBeFound("checkboxQuestionId.equals=" + checkboxQuestionId);

        // Get all the checkboxQuestionItemList where checkboxQuestion equals to checkboxQuestionId + 1
        defaultCheckboxQuestionItemShouldNotBeFound("checkboxQuestionId.equals=" + (checkboxQuestionId + 1));
    }


    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByCheckboxAnswerItemIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);
        CheckboxAnswerItem checkboxAnswerItem = CheckboxAnswerItemResourceIT.createEntity(em);
        em.persist(checkboxAnswerItem);
        em.flush();
        checkboxQuestionItem.addCheckboxAnswerItem(checkboxAnswerItem);
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);
        Long checkboxAnswerItemId = checkboxAnswerItem.getId();

        // Get all the checkboxQuestionItemList where checkboxAnswerItem equals to checkboxAnswerItemId
        defaultCheckboxQuestionItemShouldBeFound("checkboxAnswerItemId.equals=" + checkboxAnswerItemId);

        // Get all the checkboxQuestionItemList where checkboxAnswerItem equals to checkboxAnswerItemId + 1
        defaultCheckboxQuestionItemShouldNotBeFound("checkboxAnswerItemId.equals=" + (checkboxAnswerItemId + 1));
    }


    @Test
    @Transactional
    public void getAllCheckboxQuestionItemsByCheckboxDisplayConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);
        CheckboxDisplayCondition checkboxDisplayCondition = CheckboxDisplayConditionResourceIT.createEntity(em);
        em.persist(checkboxDisplayCondition);
        em.flush();
        checkboxQuestionItem.addCheckboxDisplayCondition(checkboxDisplayCondition);
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);
        Long checkboxDisplayConditionId = checkboxDisplayCondition.getId();

        // Get all the checkboxQuestionItemList where checkboxDisplayCondition equals to checkboxDisplayConditionId
        defaultCheckboxQuestionItemShouldBeFound("checkboxDisplayConditionId.equals=" + checkboxDisplayConditionId);

        // Get all the checkboxQuestionItemList where checkboxDisplayCondition equals to checkboxDisplayConditionId + 1
        defaultCheckboxQuestionItemShouldNotBeFound("checkboxDisplayConditionId.equals=" + (checkboxDisplayConditionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckboxQuestionItemShouldBeFound(String filter) throws Exception {
        restCheckboxQuestionItemMockMvc.perform(get("/api/checkbox-question-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxQuestionItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].descriptor").value(hasItem(DEFAULT_DESCRIPTOR)));

        // Check, that the count call also returns 1
        restCheckboxQuestionItemMockMvc.perform(get("/api/checkbox-question-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckboxQuestionItemShouldNotBeFound(String filter) throws Exception {
        restCheckboxQuestionItemMockMvc.perform(get("/api/checkbox-question-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckboxQuestionItemMockMvc.perform(get("/api/checkbox-question-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCheckboxQuestionItem() throws Exception {
        // Get the checkboxQuestionItem
        restCheckboxQuestionItemMockMvc.perform(get("/api/checkbox-question-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckboxQuestionItem() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        int databaseSizeBeforeUpdate = checkboxQuestionItemRepository.findAll().size();

        // Update the checkboxQuestionItem
        CheckboxQuestionItem updatedCheckboxQuestionItem = checkboxQuestionItemRepository.findById(checkboxQuestionItem.getId()).get();
        // Disconnect from session so that the updates on updatedCheckboxQuestionItem are not directly saved in db
        em.detach(updatedCheckboxQuestionItem);
        updatedCheckboxQuestionItem
            .uuid(UPDATED_UUID)
            .label(UPDATED_LABEL)
            .descriptor(UPDATED_DESCRIPTOR);
        CheckboxQuestionItemDTO checkboxQuestionItemDTO = checkboxQuestionItemMapper.toDto(updatedCheckboxQuestionItem);

        restCheckboxQuestionItemMockMvc.perform(put("/api/checkbox-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionItemDTO)))
            .andExpect(status().isOk());

        // Validate the CheckboxQuestionItem in the database
        List<CheckboxQuestionItem> checkboxQuestionItemList = checkboxQuestionItemRepository.findAll();
        assertThat(checkboxQuestionItemList).hasSize(databaseSizeBeforeUpdate);
        CheckboxQuestionItem testCheckboxQuestionItem = checkboxQuestionItemList.get(checkboxQuestionItemList.size() - 1);
        assertThat(testCheckboxQuestionItem.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testCheckboxQuestionItem.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testCheckboxQuestionItem.getDescriptor()).isEqualTo(UPDATED_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckboxQuestionItem() throws Exception {
        int databaseSizeBeforeUpdate = checkboxQuestionItemRepository.findAll().size();

        // Create the CheckboxQuestionItem
        CheckboxQuestionItemDTO checkboxQuestionItemDTO = checkboxQuestionItemMapper.toDto(checkboxQuestionItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckboxQuestionItemMockMvc.perform(put("/api/checkbox-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxQuestionItem in the database
        List<CheckboxQuestionItem> checkboxQuestionItemList = checkboxQuestionItemRepository.findAll();
        assertThat(checkboxQuestionItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckboxQuestionItem() throws Exception {
        // Initialize the database
        checkboxQuestionItemRepository.saveAndFlush(checkboxQuestionItem);

        int databaseSizeBeforeDelete = checkboxQuestionItemRepository.findAll().size();

        // Delete the checkboxQuestionItem
        restCheckboxQuestionItemMockMvc.perform(delete("/api/checkbox-question-items/{id}", checkboxQuestionItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckboxQuestionItem> checkboxQuestionItemList = checkboxQuestionItemRepository.findAll();
        assertThat(checkboxQuestionItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxQuestionItem.class);
        CheckboxQuestionItem checkboxQuestionItem1 = new CheckboxQuestionItem();
        checkboxQuestionItem1.setId(1L);
        CheckboxQuestionItem checkboxQuestionItem2 = new CheckboxQuestionItem();
        checkboxQuestionItem2.setId(checkboxQuestionItem1.getId());
        assertThat(checkboxQuestionItem1).isEqualTo(checkboxQuestionItem2);
        checkboxQuestionItem2.setId(2L);
        assertThat(checkboxQuestionItem1).isNotEqualTo(checkboxQuestionItem2);
        checkboxQuestionItem1.setId(null);
        assertThat(checkboxQuestionItem1).isNotEqualTo(checkboxQuestionItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxQuestionItemDTO.class);
        CheckboxQuestionItemDTO checkboxQuestionItemDTO1 = new CheckboxQuestionItemDTO();
        checkboxQuestionItemDTO1.setId(1L);
        CheckboxQuestionItemDTO checkboxQuestionItemDTO2 = new CheckboxQuestionItemDTO();
        assertThat(checkboxQuestionItemDTO1).isNotEqualTo(checkboxQuestionItemDTO2);
        checkboxQuestionItemDTO2.setId(checkboxQuestionItemDTO1.getId());
        assertThat(checkboxQuestionItemDTO1).isEqualTo(checkboxQuestionItemDTO2);
        checkboxQuestionItemDTO2.setId(2L);
        assertThat(checkboxQuestionItemDTO1).isNotEqualTo(checkboxQuestionItemDTO2);
        checkboxQuestionItemDTO1.setId(null);
        assertThat(checkboxQuestionItemDTO1).isNotEqualTo(checkboxQuestionItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(checkboxQuestionItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(checkboxQuestionItemMapper.fromId(null)).isNull();
    }
}
