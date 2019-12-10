package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.CsvContent;
import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.repository.CsvContentRepository;
import uk.ac.herc.bcra.service.CsvContentService;
import uk.ac.herc.bcra.service.dto.CsvContentDTO;
import uk.ac.herc.bcra.service.mapper.CsvContentMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CsvContentResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class CsvContentResourceIT {

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    @Autowired
    private CsvContentRepository csvContentRepository;

    @Autowired
    private CsvContentMapper csvContentMapper;

    @Autowired
    private CsvContentService csvContentService;

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

    private MockMvc restCsvContentMockMvc;

    private CsvContent csvContent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CsvContentResource csvContentResource = new CsvContentResource(csvContentService);
        this.restCsvContentMockMvc = MockMvcBuilders.standaloneSetup(csvContentResource)
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
    public static CsvContent createEntity(EntityManager em) {
        CsvContent csvContent = new CsvContent()
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE);
        // Add required entity
        CsvFile csvFile;
        if (TestUtil.findAll(em, CsvFile.class).isEmpty()) {
            csvFile = CsvFileResourceIT.createEntity(em);
            em.persist(csvFile);
            em.flush();
        } else {
            csvFile = TestUtil.findAll(em, CsvFile.class).get(0);
        }
        csvContent.setCsvFile(csvFile);
        return csvContent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CsvContent createUpdatedEntity(EntityManager em) {
        CsvContent csvContent = new CsvContent()
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        // Add required entity
        CsvFile csvFile;
        if (TestUtil.findAll(em, CsvFile.class).isEmpty()) {
            csvFile = CsvFileResourceIT.createUpdatedEntity(em);
            em.persist(csvFile);
            em.flush();
        } else {
            csvFile = TestUtil.findAll(em, CsvFile.class).get(0);
        }
        csvContent.setCsvFile(csvFile);
        return csvContent;
    }

    @BeforeEach
    public void initTest() {
        csvContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCsvContent() throws Exception {
        int databaseSizeBeforeCreate = csvContentRepository.findAll().size();

        // Create the CsvContent
        CsvContentDTO csvContentDTO = csvContentMapper.toDto(csvContent);
        restCsvContentMockMvc.perform(post("/api/csv-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvContentDTO)))
            .andExpect(status().isCreated());

        // Validate the CsvContent in the database
        List<CsvContent> csvContentList = csvContentRepository.findAll();
        assertThat(csvContentList).hasSize(databaseSizeBeforeCreate + 1);
        CsvContent testCsvContent = csvContentList.get(csvContentList.size() - 1);
        assertThat(testCsvContent.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testCsvContent.getDataContentType()).isEqualTo(DEFAULT_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCsvContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = csvContentRepository.findAll().size();

        // Create the CsvContent with an existing ID
        csvContent.setId(1L);
        CsvContentDTO csvContentDTO = csvContentMapper.toDto(csvContent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCsvContentMockMvc.perform(post("/api/csv-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CsvContent in the database
        List<CsvContent> csvContentList = csvContentRepository.findAll();
        assertThat(csvContentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCsvContents() throws Exception {
        // Initialize the database
        csvContentRepository.saveAndFlush(csvContent);

        // Get all the csvContentList
        restCsvContentMockMvc.perform(get("/api/csv-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(csvContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA))));
    }
    
    @Test
    @Transactional
    public void getCsvContent() throws Exception {
        // Initialize the database
        csvContentRepository.saveAndFlush(csvContent);

        // Get the csvContent
        restCsvContentMockMvc.perform(get("/api/csv-contents/{id}", csvContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(csvContent.getId().intValue()))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64Utils.encodeToString(DEFAULT_DATA)));
    }

    @Test
    @Transactional
    public void getNonExistingCsvContent() throws Exception {
        // Get the csvContent
        restCsvContentMockMvc.perform(get("/api/csv-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCsvContent() throws Exception {
        // Initialize the database
        csvContentRepository.saveAndFlush(csvContent);

        int databaseSizeBeforeUpdate = csvContentRepository.findAll().size();

        // Update the csvContent
        CsvContent updatedCsvContent = csvContentRepository.findById(csvContent.getId()).get();
        // Disconnect from session so that the updates on updatedCsvContent are not directly saved in db
        em.detach(updatedCsvContent);
        updatedCsvContent
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE);
        CsvContentDTO csvContentDTO = csvContentMapper.toDto(updatedCsvContent);

        restCsvContentMockMvc.perform(put("/api/csv-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvContentDTO)))
            .andExpect(status().isOk());

        // Validate the CsvContent in the database
        List<CsvContent> csvContentList = csvContentRepository.findAll();
        assertThat(csvContentList).hasSize(databaseSizeBeforeUpdate);
        CsvContent testCsvContent = csvContentList.get(csvContentList.size() - 1);
        assertThat(testCsvContent.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCsvContent.getDataContentType()).isEqualTo(UPDATED_DATA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCsvContent() throws Exception {
        int databaseSizeBeforeUpdate = csvContentRepository.findAll().size();

        // Create the CsvContent
        CsvContentDTO csvContentDTO = csvContentMapper.toDto(csvContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCsvContentMockMvc.perform(put("/api/csv-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CsvContent in the database
        List<CsvContent> csvContentList = csvContentRepository.findAll();
        assertThat(csvContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCsvContent() throws Exception {
        // Initialize the database
        csvContentRepository.saveAndFlush(csvContent);

        int databaseSizeBeforeDelete = csvContentRepository.findAll().size();

        // Delete the csvContent
        restCsvContentMockMvc.perform(delete("/api/csv-contents/{id}", csvContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CsvContent> csvContentList = csvContentRepository.findAll();
        assertThat(csvContentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CsvContent.class);
        CsvContent csvContent1 = new CsvContent();
        csvContent1.setId(1L);
        CsvContent csvContent2 = new CsvContent();
        csvContent2.setId(csvContent1.getId());
        assertThat(csvContent1).isEqualTo(csvContent2);
        csvContent2.setId(2L);
        assertThat(csvContent1).isNotEqualTo(csvContent2);
        csvContent1.setId(null);
        assertThat(csvContent1).isNotEqualTo(csvContent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CsvContentDTO.class);
        CsvContentDTO csvContentDTO1 = new CsvContentDTO();
        csvContentDTO1.setId(1L);
        CsvContentDTO csvContentDTO2 = new CsvContentDTO();
        assertThat(csvContentDTO1).isNotEqualTo(csvContentDTO2);
        csvContentDTO2.setId(csvContentDTO1.getId());
        assertThat(csvContentDTO1).isEqualTo(csvContentDTO2);
        csvContentDTO2.setId(2L);
        assertThat(csvContentDTO1).isNotEqualTo(csvContentDTO2);
        csvContentDTO1.setId(null);
        assertThat(csvContentDTO1).isNotEqualTo(csvContentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(csvContentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(csvContentMapper.fromId(null)).isNull();
    }
}
