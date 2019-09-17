package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.CheckboxAnswerItem;
import uk.ac.herc.bcra.domain.CheckboxAnswer;
import uk.ac.herc.bcra.domain.CheckboxQuestionItem;
import uk.ac.herc.bcra.repository.CheckboxAnswerItemRepository;
import uk.ac.herc.bcra.service.CheckboxAnswerItemService;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerItemDTO;
import uk.ac.herc.bcra.service.mapper.CheckboxAnswerItemMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.CheckboxAnswerItemCriteria;
import uk.ac.herc.bcra.service.CheckboxAnswerItemQueryService;

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
 * Integration tests for the {@link CheckboxAnswerItemResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class CheckboxAnswerItemResourceIT {

    @Autowired
    private CheckboxAnswerItemRepository checkboxAnswerItemRepository;

    @Autowired
    private CheckboxAnswerItemMapper checkboxAnswerItemMapper;

    @Autowired
    private CheckboxAnswerItemService checkboxAnswerItemService;

    @Autowired
    private CheckboxAnswerItemQueryService checkboxAnswerItemQueryService;

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

    private MockMvc restCheckboxAnswerItemMockMvc;

    private CheckboxAnswerItem checkboxAnswerItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CheckboxAnswerItemResource checkboxAnswerItemResource = new CheckboxAnswerItemResource(checkboxAnswerItemService, checkboxAnswerItemQueryService);
        this.restCheckboxAnswerItemMockMvc = MockMvcBuilders.standaloneSetup(checkboxAnswerItemResource)
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
    public static CheckboxAnswerItem createEntity(EntityManager em) {
        CheckboxAnswerItem checkboxAnswerItem = new CheckboxAnswerItem();
        return checkboxAnswerItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckboxAnswerItem createUpdatedEntity(EntityManager em) {
        CheckboxAnswerItem checkboxAnswerItem = new CheckboxAnswerItem();
        return checkboxAnswerItem;
    }

    @BeforeEach
    public void initTest() {
        checkboxAnswerItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckboxAnswerItem() throws Exception {
        int databaseSizeBeforeCreate = checkboxAnswerItemRepository.findAll().size();

        // Create the CheckboxAnswerItem
        CheckboxAnswerItemDTO checkboxAnswerItemDTO = checkboxAnswerItemMapper.toDto(checkboxAnswerItem);
        restCheckboxAnswerItemMockMvc.perform(post("/api/checkbox-answer-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxAnswerItemDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckboxAnswerItem in the database
        List<CheckboxAnswerItem> checkboxAnswerItemList = checkboxAnswerItemRepository.findAll();
        assertThat(checkboxAnswerItemList).hasSize(databaseSizeBeforeCreate + 1);
        CheckboxAnswerItem testCheckboxAnswerItem = checkboxAnswerItemList.get(checkboxAnswerItemList.size() - 1);
    }

    @Test
    @Transactional
    public void createCheckboxAnswerItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkboxAnswerItemRepository.findAll().size();

        // Create the CheckboxAnswerItem with an existing ID
        checkboxAnswerItem.setId(1L);
        CheckboxAnswerItemDTO checkboxAnswerItemDTO = checkboxAnswerItemMapper.toDto(checkboxAnswerItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckboxAnswerItemMockMvc.perform(post("/api/checkbox-answer-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxAnswerItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxAnswerItem in the database
        List<CheckboxAnswerItem> checkboxAnswerItemList = checkboxAnswerItemRepository.findAll();
        assertThat(checkboxAnswerItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCheckboxAnswerItems() throws Exception {
        // Initialize the database
        checkboxAnswerItemRepository.saveAndFlush(checkboxAnswerItem);

        // Get all the checkboxAnswerItemList
        restCheckboxAnswerItemMockMvc.perform(get("/api/checkbox-answer-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxAnswerItem.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCheckboxAnswerItem() throws Exception {
        // Initialize the database
        checkboxAnswerItemRepository.saveAndFlush(checkboxAnswerItem);

        // Get the checkboxAnswerItem
        restCheckboxAnswerItemMockMvc.perform(get("/api/checkbox-answer-items/{id}", checkboxAnswerItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkboxAnswerItem.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllCheckboxAnswerItemsByCheckboxAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxAnswerItemRepository.saveAndFlush(checkboxAnswerItem);
        CheckboxAnswer checkboxAnswer = CheckboxAnswerResourceIT.createEntity(em);
        em.persist(checkboxAnswer);
        em.flush();
        checkboxAnswerItem.setCheckboxAnswer(checkboxAnswer);
        checkboxAnswerItemRepository.saveAndFlush(checkboxAnswerItem);
        Long checkboxAnswerId = checkboxAnswer.getId();

        // Get all the checkboxAnswerItemList where checkboxAnswer equals to checkboxAnswerId
        defaultCheckboxAnswerItemShouldBeFound("checkboxAnswerId.equals=" + checkboxAnswerId);

        // Get all the checkboxAnswerItemList where checkboxAnswer equals to checkboxAnswerId + 1
        defaultCheckboxAnswerItemShouldNotBeFound("checkboxAnswerId.equals=" + (checkboxAnswerId + 1));
    }


    @Test
    @Transactional
    public void getAllCheckboxAnswerItemsByCheckboxQuestionItemIsEqualToSomething() throws Exception {
        // Initialize the database
        checkboxAnswerItemRepository.saveAndFlush(checkboxAnswerItem);
        CheckboxQuestionItem checkboxQuestionItem = CheckboxQuestionItemResourceIT.createEntity(em);
        em.persist(checkboxQuestionItem);
        em.flush();
        checkboxAnswerItem.setCheckboxQuestionItem(checkboxQuestionItem);
        checkboxAnswerItemRepository.saveAndFlush(checkboxAnswerItem);
        Long checkboxQuestionItemId = checkboxQuestionItem.getId();

        // Get all the checkboxAnswerItemList where checkboxQuestionItem equals to checkboxQuestionItemId
        defaultCheckboxAnswerItemShouldBeFound("checkboxQuestionItemId.equals=" + checkboxQuestionItemId);

        // Get all the checkboxAnswerItemList where checkboxQuestionItem equals to checkboxQuestionItemId + 1
        defaultCheckboxAnswerItemShouldNotBeFound("checkboxQuestionItemId.equals=" + (checkboxQuestionItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckboxAnswerItemShouldBeFound(String filter) throws Exception {
        restCheckboxAnswerItemMockMvc.perform(get("/api/checkbox-answer-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkboxAnswerItem.getId().intValue())));

        // Check, that the count call also returns 1
        restCheckboxAnswerItemMockMvc.perform(get("/api/checkbox-answer-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckboxAnswerItemShouldNotBeFound(String filter) throws Exception {
        restCheckboxAnswerItemMockMvc.perform(get("/api/checkbox-answer-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckboxAnswerItemMockMvc.perform(get("/api/checkbox-answer-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCheckboxAnswerItem() throws Exception {
        // Get the checkboxAnswerItem
        restCheckboxAnswerItemMockMvc.perform(get("/api/checkbox-answer-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckboxAnswerItem() throws Exception {
        // Initialize the database
        checkboxAnswerItemRepository.saveAndFlush(checkboxAnswerItem);

        int databaseSizeBeforeUpdate = checkboxAnswerItemRepository.findAll().size();

        // Update the checkboxAnswerItem
        CheckboxAnswerItem updatedCheckboxAnswerItem = checkboxAnswerItemRepository.findById(checkboxAnswerItem.getId()).get();
        // Disconnect from session so that the updates on updatedCheckboxAnswerItem are not directly saved in db
        em.detach(updatedCheckboxAnswerItem);
        CheckboxAnswerItemDTO checkboxAnswerItemDTO = checkboxAnswerItemMapper.toDto(updatedCheckboxAnswerItem);

        restCheckboxAnswerItemMockMvc.perform(put("/api/checkbox-answer-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxAnswerItemDTO)))
            .andExpect(status().isOk());

        // Validate the CheckboxAnswerItem in the database
        List<CheckboxAnswerItem> checkboxAnswerItemList = checkboxAnswerItemRepository.findAll();
        assertThat(checkboxAnswerItemList).hasSize(databaseSizeBeforeUpdate);
        CheckboxAnswerItem testCheckboxAnswerItem = checkboxAnswerItemList.get(checkboxAnswerItemList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckboxAnswerItem() throws Exception {
        int databaseSizeBeforeUpdate = checkboxAnswerItemRepository.findAll().size();

        // Create the CheckboxAnswerItem
        CheckboxAnswerItemDTO checkboxAnswerItemDTO = checkboxAnswerItemMapper.toDto(checkboxAnswerItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckboxAnswerItemMockMvc.perform(put("/api/checkbox-answer-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkboxAnswerItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckboxAnswerItem in the database
        List<CheckboxAnswerItem> checkboxAnswerItemList = checkboxAnswerItemRepository.findAll();
        assertThat(checkboxAnswerItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckboxAnswerItem() throws Exception {
        // Initialize the database
        checkboxAnswerItemRepository.saveAndFlush(checkboxAnswerItem);

        int databaseSizeBeforeDelete = checkboxAnswerItemRepository.findAll().size();

        // Delete the checkboxAnswerItem
        restCheckboxAnswerItemMockMvc.perform(delete("/api/checkbox-answer-items/{id}", checkboxAnswerItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckboxAnswerItem> checkboxAnswerItemList = checkboxAnswerItemRepository.findAll();
        assertThat(checkboxAnswerItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxAnswerItem.class);
        CheckboxAnswerItem checkboxAnswerItem1 = new CheckboxAnswerItem();
        checkboxAnswerItem1.setId(1L);
        CheckboxAnswerItem checkboxAnswerItem2 = new CheckboxAnswerItem();
        checkboxAnswerItem2.setId(checkboxAnswerItem1.getId());
        assertThat(checkboxAnswerItem1).isEqualTo(checkboxAnswerItem2);
        checkboxAnswerItem2.setId(2L);
        assertThat(checkboxAnswerItem1).isNotEqualTo(checkboxAnswerItem2);
        checkboxAnswerItem1.setId(null);
        assertThat(checkboxAnswerItem1).isNotEqualTo(checkboxAnswerItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckboxAnswerItemDTO.class);
        CheckboxAnswerItemDTO checkboxAnswerItemDTO1 = new CheckboxAnswerItemDTO();
        checkboxAnswerItemDTO1.setId(1L);
        CheckboxAnswerItemDTO checkboxAnswerItemDTO2 = new CheckboxAnswerItemDTO();
        assertThat(checkboxAnswerItemDTO1).isNotEqualTo(checkboxAnswerItemDTO2);
        checkboxAnswerItemDTO2.setId(checkboxAnswerItemDTO1.getId());
        assertThat(checkboxAnswerItemDTO1).isEqualTo(checkboxAnswerItemDTO2);
        checkboxAnswerItemDTO2.setId(2L);
        assertThat(checkboxAnswerItemDTO1).isNotEqualTo(checkboxAnswerItemDTO2);
        checkboxAnswerItemDTO1.setId(null);
        assertThat(checkboxAnswerItemDTO1).isNotEqualTo(checkboxAnswerItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(checkboxAnswerItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(checkboxAnswerItemMapper.fromId(null)).isNull();
    }
}
