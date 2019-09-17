package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.RadioQuestionItem;
import uk.ac.herc.bcra.domain.RadioQuestion;
import uk.ac.herc.bcra.repository.RadioQuestionItemRepository;
import uk.ac.herc.bcra.service.RadioQuestionItemService;
import uk.ac.herc.bcra.service.dto.RadioQuestionItemDTO;
import uk.ac.herc.bcra.service.mapper.RadioQuestionItemMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.RadioQuestionItemCriteria;
import uk.ac.herc.bcra.service.RadioQuestionItemQueryService;

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
 * Integration tests for the {@link RadioQuestionItemResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class RadioQuestionItemResourceIT {

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION_UUID = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTOR = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTOR = "BBBBBBBBBB";

    @Autowired
    private RadioQuestionItemRepository radioQuestionItemRepository;

    @Autowired
    private RadioQuestionItemMapper radioQuestionItemMapper;

    @Autowired
    private RadioQuestionItemService radioQuestionItemService;

    @Autowired
    private RadioQuestionItemQueryService radioQuestionItemQueryService;

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

    private MockMvc restRadioQuestionItemMockMvc;

    private RadioQuestionItem radioQuestionItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RadioQuestionItemResource radioQuestionItemResource = new RadioQuestionItemResource(radioQuestionItemService, radioQuestionItemQueryService);
        this.restRadioQuestionItemMockMvc = MockMvcBuilders.standaloneSetup(radioQuestionItemResource)
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
    public static RadioQuestionItem createEntity(EntityManager em) {
        RadioQuestionItem radioQuestionItem = new RadioQuestionItem()
            .uuid(DEFAULT_UUID)
            .questionUuid(DEFAULT_QUESTION_UUID)
            .label(DEFAULT_LABEL)
            .descriptor(DEFAULT_DESCRIPTOR);
        return radioQuestionItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RadioQuestionItem createUpdatedEntity(EntityManager em) {
        RadioQuestionItem radioQuestionItem = new RadioQuestionItem()
            .uuid(UPDATED_UUID)
            .questionUuid(UPDATED_QUESTION_UUID)
            .label(UPDATED_LABEL)
            .descriptor(UPDATED_DESCRIPTOR);
        return radioQuestionItem;
    }

    @BeforeEach
    public void initTest() {
        radioQuestionItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createRadioQuestionItem() throws Exception {
        int databaseSizeBeforeCreate = radioQuestionItemRepository.findAll().size();

        // Create the RadioQuestionItem
        RadioQuestionItemDTO radioQuestionItemDTO = radioQuestionItemMapper.toDto(radioQuestionItem);
        restRadioQuestionItemMockMvc.perform(post("/api/radio-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionItemDTO)))
            .andExpect(status().isCreated());

        // Validate the RadioQuestionItem in the database
        List<RadioQuestionItem> radioQuestionItemList = radioQuestionItemRepository.findAll();
        assertThat(radioQuestionItemList).hasSize(databaseSizeBeforeCreate + 1);
        RadioQuestionItem testRadioQuestionItem = radioQuestionItemList.get(radioQuestionItemList.size() - 1);
        assertThat(testRadioQuestionItem.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testRadioQuestionItem.getQuestionUuid()).isEqualTo(DEFAULT_QUESTION_UUID);
        assertThat(testRadioQuestionItem.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testRadioQuestionItem.getDescriptor()).isEqualTo(DEFAULT_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void createRadioQuestionItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = radioQuestionItemRepository.findAll().size();

        // Create the RadioQuestionItem with an existing ID
        radioQuestionItem.setId(1L);
        RadioQuestionItemDTO radioQuestionItemDTO = radioQuestionItemMapper.toDto(radioQuestionItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRadioQuestionItemMockMvc.perform(post("/api/radio-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RadioQuestionItem in the database
        List<RadioQuestionItem> radioQuestionItemList = radioQuestionItemRepository.findAll();
        assertThat(radioQuestionItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = radioQuestionItemRepository.findAll().size();
        // set the field null
        radioQuestionItem.setUuid(null);

        // Create the RadioQuestionItem, which fails.
        RadioQuestionItemDTO radioQuestionItemDTO = radioQuestionItemMapper.toDto(radioQuestionItem);

        restRadioQuestionItemMockMvc.perform(post("/api/radio-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        List<RadioQuestionItem> radioQuestionItemList = radioQuestionItemRepository.findAll();
        assertThat(radioQuestionItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuestionUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = radioQuestionItemRepository.findAll().size();
        // set the field null
        radioQuestionItem.setQuestionUuid(null);

        // Create the RadioQuestionItem, which fails.
        RadioQuestionItemDTO radioQuestionItemDTO = radioQuestionItemMapper.toDto(radioQuestionItem);

        restRadioQuestionItemMockMvc.perform(post("/api/radio-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        List<RadioQuestionItem> radioQuestionItemList = radioQuestionItemRepository.findAll();
        assertThat(radioQuestionItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = radioQuestionItemRepository.findAll().size();
        // set the field null
        radioQuestionItem.setLabel(null);

        // Create the RadioQuestionItem, which fails.
        RadioQuestionItemDTO radioQuestionItemDTO = radioQuestionItemMapper.toDto(radioQuestionItem);

        restRadioQuestionItemMockMvc.perform(post("/api/radio-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        List<RadioQuestionItem> radioQuestionItemList = radioQuestionItemRepository.findAll();
        assertThat(radioQuestionItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptorIsRequired() throws Exception {
        int databaseSizeBeforeTest = radioQuestionItemRepository.findAll().size();
        // set the field null
        radioQuestionItem.setDescriptor(null);

        // Create the RadioQuestionItem, which fails.
        RadioQuestionItemDTO radioQuestionItemDTO = radioQuestionItemMapper.toDto(radioQuestionItem);

        restRadioQuestionItemMockMvc.perform(post("/api/radio-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        List<RadioQuestionItem> radioQuestionItemList = radioQuestionItemRepository.findAll();
        assertThat(radioQuestionItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItems() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList
        restRadioQuestionItemMockMvc.perform(get("/api/radio-question-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radioQuestionItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].questionUuid").value(hasItem(DEFAULT_QUESTION_UUID.toString())))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL.toString())))
            .andExpect(jsonPath("$.[*].descriptor").value(hasItem(DEFAULT_DESCRIPTOR.toString())));
    }
    
    @Test
    @Transactional
    public void getRadioQuestionItem() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get the radioQuestionItem
        restRadioQuestionItemMockMvc.perform(get("/api/radio-question-items/{id}", radioQuestionItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(radioQuestionItem.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.questionUuid").value(DEFAULT_QUESTION_UUID.toString()))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL.toString()))
            .andExpect(jsonPath("$.descriptor").value(DEFAULT_DESCRIPTOR.toString()));
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where uuid equals to DEFAULT_UUID
        defaultRadioQuestionItemShouldBeFound("uuid.equals=" + DEFAULT_UUID);

        // Get all the radioQuestionItemList where uuid equals to UPDATED_UUID
        defaultRadioQuestionItemShouldNotBeFound("uuid.equals=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByUuidIsInShouldWork() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where uuid in DEFAULT_UUID or UPDATED_UUID
        defaultRadioQuestionItemShouldBeFound("uuid.in=" + DEFAULT_UUID + "," + UPDATED_UUID);

        // Get all the radioQuestionItemList where uuid equals to UPDATED_UUID
        defaultRadioQuestionItemShouldNotBeFound("uuid.in=" + UPDATED_UUID);
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where uuid is not null
        defaultRadioQuestionItemShouldBeFound("uuid.specified=true");

        // Get all the radioQuestionItemList where uuid is null
        defaultRadioQuestionItemShouldNotBeFound("uuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByQuestionUuidIsEqualToSomething() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where questionUuid equals to DEFAULT_QUESTION_UUID
        defaultRadioQuestionItemShouldBeFound("questionUuid.equals=" + DEFAULT_QUESTION_UUID);

        // Get all the radioQuestionItemList where questionUuid equals to UPDATED_QUESTION_UUID
        defaultRadioQuestionItemShouldNotBeFound("questionUuid.equals=" + UPDATED_QUESTION_UUID);
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByQuestionUuidIsInShouldWork() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where questionUuid in DEFAULT_QUESTION_UUID or UPDATED_QUESTION_UUID
        defaultRadioQuestionItemShouldBeFound("questionUuid.in=" + DEFAULT_QUESTION_UUID + "," + UPDATED_QUESTION_UUID);

        // Get all the radioQuestionItemList where questionUuid equals to UPDATED_QUESTION_UUID
        defaultRadioQuestionItemShouldNotBeFound("questionUuid.in=" + UPDATED_QUESTION_UUID);
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByQuestionUuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where questionUuid is not null
        defaultRadioQuestionItemShouldBeFound("questionUuid.specified=true");

        // Get all the radioQuestionItemList where questionUuid is null
        defaultRadioQuestionItemShouldNotBeFound("questionUuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where label equals to DEFAULT_LABEL
        defaultRadioQuestionItemShouldBeFound("label.equals=" + DEFAULT_LABEL);

        // Get all the radioQuestionItemList where label equals to UPDATED_LABEL
        defaultRadioQuestionItemShouldNotBeFound("label.equals=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByLabelIsInShouldWork() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where label in DEFAULT_LABEL or UPDATED_LABEL
        defaultRadioQuestionItemShouldBeFound("label.in=" + DEFAULT_LABEL + "," + UPDATED_LABEL);

        // Get all the radioQuestionItemList where label equals to UPDATED_LABEL
        defaultRadioQuestionItemShouldNotBeFound("label.in=" + UPDATED_LABEL);
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByLabelIsNullOrNotNull() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where label is not null
        defaultRadioQuestionItemShouldBeFound("label.specified=true");

        // Get all the radioQuestionItemList where label is null
        defaultRadioQuestionItemShouldNotBeFound("label.specified=false");
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByDescriptorIsEqualToSomething() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where descriptor equals to DEFAULT_DESCRIPTOR
        defaultRadioQuestionItemShouldBeFound("descriptor.equals=" + DEFAULT_DESCRIPTOR);

        // Get all the radioQuestionItemList where descriptor equals to UPDATED_DESCRIPTOR
        defaultRadioQuestionItemShouldNotBeFound("descriptor.equals=" + UPDATED_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByDescriptorIsInShouldWork() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where descriptor in DEFAULT_DESCRIPTOR or UPDATED_DESCRIPTOR
        defaultRadioQuestionItemShouldBeFound("descriptor.in=" + DEFAULT_DESCRIPTOR + "," + UPDATED_DESCRIPTOR);

        // Get all the radioQuestionItemList where descriptor equals to UPDATED_DESCRIPTOR
        defaultRadioQuestionItemShouldNotBeFound("descriptor.in=" + UPDATED_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByDescriptorIsNullOrNotNull() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        // Get all the radioQuestionItemList where descriptor is not null
        defaultRadioQuestionItemShouldBeFound("descriptor.specified=true");

        // Get all the radioQuestionItemList where descriptor is null
        defaultRadioQuestionItemShouldNotBeFound("descriptor.specified=false");
    }

    @Test
    @Transactional
    public void getAllRadioQuestionItemsByRadioQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);
        RadioQuestion radioQuestion = RadioQuestionResourceIT.createEntity(em);
        em.persist(radioQuestion);
        em.flush();
        radioQuestionItem.setRadioQuestion(radioQuestion);
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);
        Long radioQuestionId = radioQuestion.getId();

        // Get all the radioQuestionItemList where radioQuestion equals to radioQuestionId
        defaultRadioQuestionItemShouldBeFound("radioQuestionId.equals=" + radioQuestionId);

        // Get all the radioQuestionItemList where radioQuestion equals to radioQuestionId + 1
        defaultRadioQuestionItemShouldNotBeFound("radioQuestionId.equals=" + (radioQuestionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRadioQuestionItemShouldBeFound(String filter) throws Exception {
        restRadioQuestionItemMockMvc.perform(get("/api/radio-question-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radioQuestionItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID)))
            .andExpect(jsonPath("$.[*].questionUuid").value(hasItem(DEFAULT_QUESTION_UUID)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].descriptor").value(hasItem(DEFAULT_DESCRIPTOR)));

        // Check, that the count call also returns 1
        restRadioQuestionItemMockMvc.perform(get("/api/radio-question-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRadioQuestionItemShouldNotBeFound(String filter) throws Exception {
        restRadioQuestionItemMockMvc.perform(get("/api/radio-question-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRadioQuestionItemMockMvc.perform(get("/api/radio-question-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRadioQuestionItem() throws Exception {
        // Get the radioQuestionItem
        restRadioQuestionItemMockMvc.perform(get("/api/radio-question-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRadioQuestionItem() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        int databaseSizeBeforeUpdate = radioQuestionItemRepository.findAll().size();

        // Update the radioQuestionItem
        RadioQuestionItem updatedRadioQuestionItem = radioQuestionItemRepository.findById(radioQuestionItem.getId()).get();
        // Disconnect from session so that the updates on updatedRadioQuestionItem are not directly saved in db
        em.detach(updatedRadioQuestionItem);
        updatedRadioQuestionItem
            .uuid(UPDATED_UUID)
            .questionUuid(UPDATED_QUESTION_UUID)
            .label(UPDATED_LABEL)
            .descriptor(UPDATED_DESCRIPTOR);
        RadioQuestionItemDTO radioQuestionItemDTO = radioQuestionItemMapper.toDto(updatedRadioQuestionItem);

        restRadioQuestionItemMockMvc.perform(put("/api/radio-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionItemDTO)))
            .andExpect(status().isOk());

        // Validate the RadioQuestionItem in the database
        List<RadioQuestionItem> radioQuestionItemList = radioQuestionItemRepository.findAll();
        assertThat(radioQuestionItemList).hasSize(databaseSizeBeforeUpdate);
        RadioQuestionItem testRadioQuestionItem = radioQuestionItemList.get(radioQuestionItemList.size() - 1);
        assertThat(testRadioQuestionItem.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testRadioQuestionItem.getQuestionUuid()).isEqualTo(UPDATED_QUESTION_UUID);
        assertThat(testRadioQuestionItem.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testRadioQuestionItem.getDescriptor()).isEqualTo(UPDATED_DESCRIPTOR);
    }

    @Test
    @Transactional
    public void updateNonExistingRadioQuestionItem() throws Exception {
        int databaseSizeBeforeUpdate = radioQuestionItemRepository.findAll().size();

        // Create the RadioQuestionItem
        RadioQuestionItemDTO radioQuestionItemDTO = radioQuestionItemMapper.toDto(radioQuestionItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRadioQuestionItemMockMvc.perform(put("/api/radio-question-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioQuestionItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RadioQuestionItem in the database
        List<RadioQuestionItem> radioQuestionItemList = radioQuestionItemRepository.findAll();
        assertThat(radioQuestionItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRadioQuestionItem() throws Exception {
        // Initialize the database
        radioQuestionItemRepository.saveAndFlush(radioQuestionItem);

        int databaseSizeBeforeDelete = radioQuestionItemRepository.findAll().size();

        // Delete the radioQuestionItem
        restRadioQuestionItemMockMvc.perform(delete("/api/radio-question-items/{id}", radioQuestionItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RadioQuestionItem> radioQuestionItemList = radioQuestionItemRepository.findAll();
        assertThat(radioQuestionItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RadioQuestionItem.class);
        RadioQuestionItem radioQuestionItem1 = new RadioQuestionItem();
        radioQuestionItem1.setId(1L);
        RadioQuestionItem radioQuestionItem2 = new RadioQuestionItem();
        radioQuestionItem2.setId(radioQuestionItem1.getId());
        assertThat(radioQuestionItem1).isEqualTo(radioQuestionItem2);
        radioQuestionItem2.setId(2L);
        assertThat(radioQuestionItem1).isNotEqualTo(radioQuestionItem2);
        radioQuestionItem1.setId(null);
        assertThat(radioQuestionItem1).isNotEqualTo(radioQuestionItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RadioQuestionItemDTO.class);
        RadioQuestionItemDTO radioQuestionItemDTO1 = new RadioQuestionItemDTO();
        radioQuestionItemDTO1.setId(1L);
        RadioQuestionItemDTO radioQuestionItemDTO2 = new RadioQuestionItemDTO();
        assertThat(radioQuestionItemDTO1).isNotEqualTo(radioQuestionItemDTO2);
        radioQuestionItemDTO2.setId(radioQuestionItemDTO1.getId());
        assertThat(radioQuestionItemDTO1).isEqualTo(radioQuestionItemDTO2);
        radioQuestionItemDTO2.setId(2L);
        assertThat(radioQuestionItemDTO1).isNotEqualTo(radioQuestionItemDTO2);
        radioQuestionItemDTO1.setId(null);
        assertThat(radioQuestionItemDTO1).isNotEqualTo(radioQuestionItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(radioQuestionItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(radioQuestionItemMapper.fromId(null)).isNull();
    }
}
