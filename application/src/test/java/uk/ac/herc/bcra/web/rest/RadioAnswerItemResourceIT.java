package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.RadioAnswerItem;
import uk.ac.herc.bcra.domain.RadioAnswer;
import uk.ac.herc.bcra.repository.RadioAnswerItemRepository;
import uk.ac.herc.bcra.service.RadioAnswerItemService;
import uk.ac.herc.bcra.service.dto.RadioAnswerItemDTO;
import uk.ac.herc.bcra.service.mapper.RadioAnswerItemMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.dto.RadioAnswerItemCriteria;
import uk.ac.herc.bcra.service.RadioAnswerItemQueryService;

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
 * Integration tests for the {@link RadioAnswerItemResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class RadioAnswerItemResourceIT {

    @Autowired
    private RadioAnswerItemRepository radioAnswerItemRepository;

    @Autowired
    private RadioAnswerItemMapper radioAnswerItemMapper;

    @Autowired
    private RadioAnswerItemService radioAnswerItemService;

    @Autowired
    private RadioAnswerItemQueryService radioAnswerItemQueryService;

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

    private MockMvc restRadioAnswerItemMockMvc;

    private RadioAnswerItem radioAnswerItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RadioAnswerItemResource radioAnswerItemResource = new RadioAnswerItemResource(radioAnswerItemService, radioAnswerItemQueryService);
        this.restRadioAnswerItemMockMvc = MockMvcBuilders.standaloneSetup(radioAnswerItemResource)
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
    public static RadioAnswerItem createEntity(EntityManager em) {
        RadioAnswerItem radioAnswerItem = new RadioAnswerItem();
        return radioAnswerItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RadioAnswerItem createUpdatedEntity(EntityManager em) {
        RadioAnswerItem radioAnswerItem = new RadioAnswerItem();
        return radioAnswerItem;
    }

    @BeforeEach
    public void initTest() {
        radioAnswerItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createRadioAnswerItem() throws Exception {
        int databaseSizeBeforeCreate = radioAnswerItemRepository.findAll().size();

        // Create the RadioAnswerItem
        RadioAnswerItemDTO radioAnswerItemDTO = radioAnswerItemMapper.toDto(radioAnswerItem);
        restRadioAnswerItemMockMvc.perform(post("/api/radio-answer-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioAnswerItemDTO)))
            .andExpect(status().isCreated());

        // Validate the RadioAnswerItem in the database
        List<RadioAnswerItem> radioAnswerItemList = radioAnswerItemRepository.findAll();
        assertThat(radioAnswerItemList).hasSize(databaseSizeBeforeCreate + 1);
        RadioAnswerItem testRadioAnswerItem = radioAnswerItemList.get(radioAnswerItemList.size() - 1);
    }

    @Test
    @Transactional
    public void createRadioAnswerItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = radioAnswerItemRepository.findAll().size();

        // Create the RadioAnswerItem with an existing ID
        radioAnswerItem.setId(1L);
        RadioAnswerItemDTO radioAnswerItemDTO = radioAnswerItemMapper.toDto(radioAnswerItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRadioAnswerItemMockMvc.perform(post("/api/radio-answer-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioAnswerItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RadioAnswerItem in the database
        List<RadioAnswerItem> radioAnswerItemList = radioAnswerItemRepository.findAll();
        assertThat(radioAnswerItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRadioAnswerItems() throws Exception {
        // Initialize the database
        radioAnswerItemRepository.saveAndFlush(radioAnswerItem);

        // Get all the radioAnswerItemList
        restRadioAnswerItemMockMvc.perform(get("/api/radio-answer-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radioAnswerItem.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getRadioAnswerItem() throws Exception {
        // Initialize the database
        radioAnswerItemRepository.saveAndFlush(radioAnswerItem);

        // Get the radioAnswerItem
        restRadioAnswerItemMockMvc.perform(get("/api/radio-answer-items/{id}", radioAnswerItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(radioAnswerItem.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllRadioAnswerItemsByRadioAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        radioAnswerItemRepository.saveAndFlush(radioAnswerItem);
        RadioAnswer radioAnswer = RadioAnswerResourceIT.createEntity(em);
        em.persist(radioAnswer);
        em.flush();
        radioAnswerItem.setRadioAnswer(radioAnswer);
        radioAnswerItemRepository.saveAndFlush(radioAnswerItem);
        Long radioAnswerId = radioAnswer.getId();

        // Get all the radioAnswerItemList where radioAnswer equals to radioAnswerId
        defaultRadioAnswerItemShouldBeFound("radioAnswerId.equals=" + radioAnswerId);

        // Get all the radioAnswerItemList where radioAnswer equals to radioAnswerId + 1
        defaultRadioAnswerItemShouldNotBeFound("radioAnswerId.equals=" + (radioAnswerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRadioAnswerItemShouldBeFound(String filter) throws Exception {
        restRadioAnswerItemMockMvc.perform(get("/api/radio-answer-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(radioAnswerItem.getId().intValue())));

        // Check, that the count call also returns 1
        restRadioAnswerItemMockMvc.perform(get("/api/radio-answer-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRadioAnswerItemShouldNotBeFound(String filter) throws Exception {
        restRadioAnswerItemMockMvc.perform(get("/api/radio-answer-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRadioAnswerItemMockMvc.perform(get("/api/radio-answer-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRadioAnswerItem() throws Exception {
        // Get the radioAnswerItem
        restRadioAnswerItemMockMvc.perform(get("/api/radio-answer-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRadioAnswerItem() throws Exception {
        // Initialize the database
        radioAnswerItemRepository.saveAndFlush(radioAnswerItem);

        int databaseSizeBeforeUpdate = radioAnswerItemRepository.findAll().size();

        // Update the radioAnswerItem
        RadioAnswerItem updatedRadioAnswerItem = radioAnswerItemRepository.findById(radioAnswerItem.getId()).get();
        // Disconnect from session so that the updates on updatedRadioAnswerItem are not directly saved in db
        em.detach(updatedRadioAnswerItem);
        RadioAnswerItemDTO radioAnswerItemDTO = radioAnswerItemMapper.toDto(updatedRadioAnswerItem);

        restRadioAnswerItemMockMvc.perform(put("/api/radio-answer-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioAnswerItemDTO)))
            .andExpect(status().isOk());

        // Validate the RadioAnswerItem in the database
        List<RadioAnswerItem> radioAnswerItemList = radioAnswerItemRepository.findAll();
        assertThat(radioAnswerItemList).hasSize(databaseSizeBeforeUpdate);
        RadioAnswerItem testRadioAnswerItem = radioAnswerItemList.get(radioAnswerItemList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingRadioAnswerItem() throws Exception {
        int databaseSizeBeforeUpdate = radioAnswerItemRepository.findAll().size();

        // Create the RadioAnswerItem
        RadioAnswerItemDTO radioAnswerItemDTO = radioAnswerItemMapper.toDto(radioAnswerItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRadioAnswerItemMockMvc.perform(put("/api/radio-answer-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(radioAnswerItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RadioAnswerItem in the database
        List<RadioAnswerItem> radioAnswerItemList = radioAnswerItemRepository.findAll();
        assertThat(radioAnswerItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRadioAnswerItem() throws Exception {
        // Initialize the database
        radioAnswerItemRepository.saveAndFlush(radioAnswerItem);

        int databaseSizeBeforeDelete = radioAnswerItemRepository.findAll().size();

        // Delete the radioAnswerItem
        restRadioAnswerItemMockMvc.perform(delete("/api/radio-answer-items/{id}", radioAnswerItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RadioAnswerItem> radioAnswerItemList = radioAnswerItemRepository.findAll();
        assertThat(radioAnswerItemList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RadioAnswerItem.class);
        RadioAnswerItem radioAnswerItem1 = new RadioAnswerItem();
        radioAnswerItem1.setId(1L);
        RadioAnswerItem radioAnswerItem2 = new RadioAnswerItem();
        radioAnswerItem2.setId(radioAnswerItem1.getId());
        assertThat(radioAnswerItem1).isEqualTo(radioAnswerItem2);
        radioAnswerItem2.setId(2L);
        assertThat(radioAnswerItem1).isNotEqualTo(radioAnswerItem2);
        radioAnswerItem1.setId(null);
        assertThat(radioAnswerItem1).isNotEqualTo(radioAnswerItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RadioAnswerItemDTO.class);
        RadioAnswerItemDTO radioAnswerItemDTO1 = new RadioAnswerItemDTO();
        radioAnswerItemDTO1.setId(1L);
        RadioAnswerItemDTO radioAnswerItemDTO2 = new RadioAnswerItemDTO();
        assertThat(radioAnswerItemDTO1).isNotEqualTo(radioAnswerItemDTO2);
        radioAnswerItemDTO2.setId(radioAnswerItemDTO1.getId());
        assertThat(radioAnswerItemDTO1).isEqualTo(radioAnswerItemDTO2);
        radioAnswerItemDTO2.setId(2L);
        assertThat(radioAnswerItemDTO1).isNotEqualTo(radioAnswerItemDTO2);
        radioAnswerItemDTO1.setId(null);
        assertThat(radioAnswerItemDTO1).isNotEqualTo(radioAnswerItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(radioAnswerItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(radioAnswerItemMapper.fromId(null)).isNull();
    }
}
