package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.repository.CsvFileRepository;
import uk.ac.herc.bcra.service.CsvFileService;
import uk.ac.herc.bcra.service.dto.CsvFileDTO;
import uk.ac.herc.bcra.service.mapper.CsvFileMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import uk.ac.herc.bcra.domain.enumeration.CsvFileState;
/**
 * Integration tests for the {@link CsvFileResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class CsvFileResourceIT {

    private static final CsvFileState DEFAULT_STATE = CsvFileState.UPLOADED;
    private static final CsvFileState UPDATED_STATE = CsvFileState.INVALID;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPLOAD_DATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    
    @Autowired
    private CsvFileRepository csvFileRepository;

    @Autowired
    private CsvFileMapper csvFileMapper;

    @Autowired
    private CsvFileService csvFileService;

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

    private MockMvc restCsvFileMockMvc;

    private CsvFile csvFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CsvFileResource csvFileResource = new CsvFileResource(csvFileService);
        this.restCsvFileMockMvc = MockMvcBuilders.standaloneSetup(csvFileResource)
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
    public static CsvFile createEntity(EntityManager em) {
        CsvFile csvFile = new CsvFile()
            .state(DEFAULT_STATE)
            .status(DEFAULT_STATUS)
            .fileName(DEFAULT_FILE_NAME)
            .uploadDatetime(DEFAULT_UPLOAD_DATETIME);
        return csvFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CsvFile createUpdatedEntity(EntityManager em) {
        CsvFile csvFile = new CsvFile()
            .state(UPDATED_STATE)
            .status(UPDATED_STATUS)
            .fileName(UPDATED_FILE_NAME)
            .uploadDatetime(UPDATED_UPLOAD_DATETIME);
        return csvFile;
    }

    @BeforeEach
    public void initTest() {
        csvFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createCsvFile() throws Exception {
        int databaseSizeBeforeCreate = csvFileRepository.findAll().size();

        // Create the CsvFile
        CsvFileDTO csvFileDTO = csvFileMapper.toDto(csvFile);
        restCsvFileMockMvc.perform(post("/api/csv-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvFileDTO)))
            .andExpect(status().isCreated());

        // Validate the CsvFile in the database
        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeCreate + 1);
        CsvFile testCsvFile = csvFileList.get(csvFileList.size() - 1);
        assertThat(testCsvFile.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testCsvFile.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCsvFile.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testCsvFile.getUploadDatetime()).isEqualTo(DEFAULT_UPLOAD_DATETIME);
    }

    @Test
    @Transactional
    public void createCsvFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = csvFileRepository.findAll().size();

        // Create the CsvFile with an existing ID
        csvFile.setId(1L);
        CsvFileDTO csvFileDTO = csvFileMapper.toDto(csvFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCsvFileMockMvc.perform(post("/api/csv-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CsvFile in the database
        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = csvFileRepository.findAll().size();
        // set the field null
        csvFile.setState(null);

        // Create the CsvFile, which fails.
        CsvFileDTO csvFileDTO = csvFileMapper.toDto(csvFile);

        restCsvFileMockMvc.perform(post("/api/csv-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvFileDTO)))
            .andExpect(status().isBadRequest());

        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = csvFileRepository.findAll().size();
        // set the field null
        csvFile.setFileName(null);

        // Create the CsvFile, which fails.
        CsvFileDTO csvFileDTO = csvFileMapper.toDto(csvFile);

        restCsvFileMockMvc.perform(post("/api/csv-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvFileDTO)))
            .andExpect(status().isBadRequest());

        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUploadDatetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = csvFileRepository.findAll().size();
        // set the field null
        csvFile.setUploadDatetime(null);

        // Create the CsvFile, which fails.
        CsvFileDTO csvFileDTO = csvFileMapper.toDto(csvFile);

        restCsvFileMockMvc.perform(post("/api/csv-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvFileDTO)))
            .andExpect(status().isBadRequest());

        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCsvFiles() throws Exception {
        // Initialize the database
        csvFileRepository.saveAndFlush(csvFile);

        // Get all the csvFileList
        restCsvFileMockMvc.perform(get("/api/csv-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(csvFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].uploadDatetime").value(hasItem(DEFAULT_UPLOAD_DATETIME.toString())));
    }
    
    @Test
    @Transactional
    public void getCsvFile() throws Exception {
        // Initialize the database
        csvFileRepository.saveAndFlush(csvFile);

        // Get the csvFile
        restCsvFileMockMvc.perform(get("/api/csv-files/{id}", csvFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(csvFile.getId().intValue()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.uploadDatetime").value(DEFAULT_UPLOAD_DATETIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCsvFile() throws Exception {
        // Get the csvFile
        restCsvFileMockMvc.perform(get("/api/csv-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCsvFile() throws Exception {
        // Initialize the database
        csvFileRepository.saveAndFlush(csvFile);

        int databaseSizeBeforeUpdate = csvFileRepository.findAll().size();

        // Update the csvFile
        CsvFile updatedCsvFile = csvFileRepository.findById(csvFile.getId()).get();
        // Disconnect from session so that the updates on updatedCsvFile are not directly saved in db
        em.detach(updatedCsvFile);
        updatedCsvFile
            .state(UPDATED_STATE)
            .status(UPDATED_STATUS)
            .fileName(UPDATED_FILE_NAME)
            .uploadDatetime(UPDATED_UPLOAD_DATETIME);
        CsvFileDTO csvFileDTO = csvFileMapper.toDto(updatedCsvFile);

        restCsvFileMockMvc.perform(put("/api/csv-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvFileDTO)))
            .andExpect(status().isOk());

        // Validate the CsvFile in the database
        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeUpdate);
        CsvFile testCsvFile = csvFileList.get(csvFileList.size() - 1);
        assertThat(testCsvFile.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testCsvFile.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCsvFile.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testCsvFile.getUploadDatetime()).isEqualTo(UPDATED_UPLOAD_DATETIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCsvFile() throws Exception {
        int databaseSizeBeforeUpdate = csvFileRepository.findAll().size();

        // Create the CsvFile
        CsvFileDTO csvFileDTO = csvFileMapper.toDto(csvFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCsvFileMockMvc.perform(put("/api/csv-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csvFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CsvFile in the database
        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCsvFile() throws Exception {
        // Initialize the database
        csvFileRepository.saveAndFlush(csvFile);

        int databaseSizeBeforeDelete = csvFileRepository.findAll().size();

        // Delete the csvFile
        restCsvFileMockMvc.perform(delete("/api/csv-files/{id}", csvFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CsvFile.class);
        CsvFile csvFile1 = new CsvFile();
        csvFile1.setId(1L);
        CsvFile csvFile2 = new CsvFile();
        csvFile2.setId(csvFile1.getId());
        assertThat(csvFile1).isEqualTo(csvFile2);
        csvFile2.setId(2L);
        assertThat(csvFile1).isNotEqualTo(csvFile2);
        csvFile1.setId(null);
        assertThat(csvFile1).isNotEqualTo(csvFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CsvFileDTO.class);
        CsvFileDTO csvFileDTO1 = new CsvFileDTO();
        csvFileDTO1.setId(1L);
        CsvFileDTO csvFileDTO2 = new CsvFileDTO();
        assertThat(csvFileDTO1).isNotEqualTo(csvFileDTO2);
        csvFileDTO2.setId(csvFileDTO1.getId());
        assertThat(csvFileDTO1).isEqualTo(csvFileDTO2);
        csvFileDTO2.setId(2L);
        assertThat(csvFileDTO1).isNotEqualTo(csvFileDTO2);
        csvFileDTO1.setId(null);
        assertThat(csvFileDTO1).isNotEqualTo(csvFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(csvFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(csvFileMapper.fromId(null)).isNull();
    }
}
