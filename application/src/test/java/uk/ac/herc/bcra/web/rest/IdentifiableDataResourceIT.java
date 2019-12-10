package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.repository.IdentifiableDataRepository;
import uk.ac.herc.bcra.service.IdentifiableDataService;
import uk.ac.herc.bcra.service.dto.IdentifiableDataDTO;
import uk.ac.herc.bcra.service.mapper.IdentifiableDataMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IdentifiableDataResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class IdentifiableDataResourceIT {

    private static final String DEFAULT_NHS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NHS_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE_OF_BIRTH = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_4 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_5 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_5 = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBB";

    private static final String DEFAULT_PRACTICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRACTICE_NAME = "BBBBBBBBBB";

    @Autowired
    private IdentifiableDataRepository identifiableDataRepository;

    @Autowired
    private IdentifiableDataMapper identifiableDataMapper;

    @Autowired
    private IdentifiableDataService identifiableDataService;

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

    private MockMvc restIdentifiableDataMockMvc;

    private IdentifiableData identifiableData;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IdentifiableDataResource identifiableDataResource = new IdentifiableDataResource(identifiableDataService);
        this.restIdentifiableDataMockMvc = MockMvcBuilders.standaloneSetup(identifiableDataResource)
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
    public static IdentifiableData createEntity(EntityManager em) {
        IdentifiableData identifiableData = new IdentifiableData()
            .nhsNumber(DEFAULT_NHS_NUMBER)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .firstname(DEFAULT_FIRSTNAME)
            .surname(DEFAULT_SURNAME)
            .email(DEFAULT_EMAIL)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .address3(DEFAULT_ADDRESS_3)
            .address4(DEFAULT_ADDRESS_4)
            .address5(DEFAULT_ADDRESS_5)
            .postcode(DEFAULT_POSTCODE)
            .practiceName(DEFAULT_PRACTICE_NAME);
        // Add required entity
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }
        identifiableData.setParticipant(participant);
        return identifiableData;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdentifiableData createUpdatedEntity(EntityManager em) {
        IdentifiableData identifiableData = new IdentifiableData()
            .nhsNumber(UPDATED_NHS_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .firstname(UPDATED_FIRSTNAME)
            .surname(UPDATED_SURNAME)
            .email(UPDATED_EMAIL)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .address5(UPDATED_ADDRESS_5)
            .postcode(UPDATED_POSTCODE)
            .practiceName(UPDATED_PRACTICE_NAME);
        // Add required entity
        Participant participant;
        if (TestUtil.findAll(em, Participant.class).isEmpty()) {
            participant = ParticipantResourceIT.createUpdatedEntity(em);
            em.persist(participant);
            em.flush();
        } else {
            participant = TestUtil.findAll(em, Participant.class).get(0);
        }
        identifiableData.setParticipant(participant);
        return identifiableData;
    }

    @BeforeEach
    public void initTest() {
        identifiableData = createEntity(em);
    }

    @Test
    @Transactional
    public void createIdentifiableData() throws Exception {
        int databaseSizeBeforeCreate = identifiableDataRepository.findAll().size();

        // Create the IdentifiableData
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);
        restIdentifiableDataMockMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isCreated());

        // Validate the IdentifiableData in the database
        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeCreate + 1);
        IdentifiableData testIdentifiableData = identifiableDataList.get(identifiableDataList.size() - 1);
        assertThat(testIdentifiableData.getNhsNumber()).isEqualTo(DEFAULT_NHS_NUMBER);
        assertThat(testIdentifiableData.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testIdentifiableData.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testIdentifiableData.getSurname()).isEqualTo(DEFAULT_SURNAME);
        assertThat(testIdentifiableData.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testIdentifiableData.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testIdentifiableData.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testIdentifiableData.getAddress3()).isEqualTo(DEFAULT_ADDRESS_3);
        assertThat(testIdentifiableData.getAddress4()).isEqualTo(DEFAULT_ADDRESS_4);
        assertThat(testIdentifiableData.getAddress5()).isEqualTo(DEFAULT_ADDRESS_5);
        assertThat(testIdentifiableData.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testIdentifiableData.getPracticeName()).isEqualTo(DEFAULT_PRACTICE_NAME);
    }

    @Test
    @Transactional
    public void createIdentifiableDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = identifiableDataRepository.findAll().size();

        // Create the IdentifiableData with an existing ID
        identifiableData.setId(1L);
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentifiableDataMockMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IdentifiableData in the database
        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNhsNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = identifiableDataRepository.findAll().size();
        // set the field null
        identifiableData.setNhsNumber(null);

        // Create the IdentifiableData, which fails.
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);

        restIdentifiableDataMockMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isBadRequest());

        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = identifiableDataRepository.findAll().size();
        // set the field null
        identifiableData.setDateOfBirth(null);

        // Create the IdentifiableData, which fails.
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);

        restIdentifiableDataMockMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isBadRequest());

        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = identifiableDataRepository.findAll().size();
        // set the field null
        identifiableData.setFirstname(null);

        // Create the IdentifiableData, which fails.
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);

        restIdentifiableDataMockMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isBadRequest());

        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSurnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = identifiableDataRepository.findAll().size();
        // set the field null
        identifiableData.setSurname(null);

        // Create the IdentifiableData, which fails.
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);

        restIdentifiableDataMockMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isBadRequest());

        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAddress1IsRequired() throws Exception {
        int databaseSizeBeforeTest = identifiableDataRepository.findAll().size();
        // set the field null
        identifiableData.setAddress1(null);

        // Create the IdentifiableData, which fails.
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);

        restIdentifiableDataMockMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isBadRequest());

        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPostcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = identifiableDataRepository.findAll().size();
        // set the field null
        identifiableData.setPostcode(null);

        // Create the IdentifiableData, which fails.
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);

        restIdentifiableDataMockMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isBadRequest());

        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPracticeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = identifiableDataRepository.findAll().size();
        // set the field null
        identifiableData.setPracticeName(null);

        // Create the IdentifiableData, which fails.
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);

        restIdentifiableDataMockMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isBadRequest());

        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIdentifiableData() throws Exception {
        // Initialize the database
        identifiableDataRepository.saveAndFlush(identifiableData);

        // Get all the identifiableDataList
        restIdentifiableDataMockMvc.perform(get("/api/identifiable-data?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identifiableData.getId().intValue())))
            .andExpect(jsonPath("$.[*].nhsNumber").value(hasItem(DEFAULT_NHS_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME.toString())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(DEFAULT_SURNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3.toString())))
            .andExpect(jsonPath("$.[*].address4").value(hasItem(DEFAULT_ADDRESS_4.toString())))
            .andExpect(jsonPath("$.[*].address5").value(hasItem(DEFAULT_ADDRESS_5.toString())))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
            .andExpect(jsonPath("$.[*].practiceName").value(hasItem(DEFAULT_PRACTICE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getIdentifiableData() throws Exception {
        // Initialize the database
        identifiableDataRepository.saveAndFlush(identifiableData);

        // Get the identifiableData
        restIdentifiableDataMockMvc.perform(get("/api/identifiable-data/{id}", identifiableData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(identifiableData.getId().intValue()))
            .andExpect(jsonPath("$.nhsNumber").value(DEFAULT_NHS_NUMBER.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME.toString()))
            .andExpect(jsonPath("$.surname").value(DEFAULT_SURNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.address3").value(DEFAULT_ADDRESS_3.toString()))
            .andExpect(jsonPath("$.address4").value(DEFAULT_ADDRESS_4.toString()))
            .andExpect(jsonPath("$.address5").value(DEFAULT_ADDRESS_5.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.practiceName").value(DEFAULT_PRACTICE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIdentifiableData() throws Exception {
        // Get the identifiableData
        restIdentifiableDataMockMvc.perform(get("/api/identifiable-data/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIdentifiableData() throws Exception {
        // Initialize the database
        identifiableDataRepository.saveAndFlush(identifiableData);

        int databaseSizeBeforeUpdate = identifiableDataRepository.findAll().size();

        // Update the identifiableData
        IdentifiableData updatedIdentifiableData = identifiableDataRepository.findById(identifiableData.getId()).get();
        // Disconnect from session so that the updates on updatedIdentifiableData are not directly saved in db
        em.detach(updatedIdentifiableData);
        updatedIdentifiableData
            .nhsNumber(UPDATED_NHS_NUMBER)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .firstname(UPDATED_FIRSTNAME)
            .surname(UPDATED_SURNAME)
            .email(UPDATED_EMAIL)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .address5(UPDATED_ADDRESS_5)
            .postcode(UPDATED_POSTCODE)
            .practiceName(UPDATED_PRACTICE_NAME);
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(updatedIdentifiableData);

        restIdentifiableDataMockMvc.perform(put("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isOk());

        // Validate the IdentifiableData in the database
        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeUpdate);
        IdentifiableData testIdentifiableData = identifiableDataList.get(identifiableDataList.size() - 1);
        assertThat(testIdentifiableData.getNhsNumber()).isEqualTo(UPDATED_NHS_NUMBER);
        assertThat(testIdentifiableData.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIdentifiableData.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testIdentifiableData.getSurname()).isEqualTo(UPDATED_SURNAME);
        assertThat(testIdentifiableData.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testIdentifiableData.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testIdentifiableData.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testIdentifiableData.getAddress3()).isEqualTo(UPDATED_ADDRESS_3);
        assertThat(testIdentifiableData.getAddress4()).isEqualTo(UPDATED_ADDRESS_4);
        assertThat(testIdentifiableData.getAddress5()).isEqualTo(UPDATED_ADDRESS_5);
        assertThat(testIdentifiableData.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testIdentifiableData.getPracticeName()).isEqualTo(UPDATED_PRACTICE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingIdentifiableData() throws Exception {
        int databaseSizeBeforeUpdate = identifiableDataRepository.findAll().size();

        // Create the IdentifiableData
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentifiableDataMockMvc.perform(put("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the IdentifiableData in the database
        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIdentifiableData() throws Exception {
        // Initialize the database
        identifiableDataRepository.saveAndFlush(identifiableData);

        int databaseSizeBeforeDelete = identifiableDataRepository.findAll().size();

        // Delete the identifiableData
        restIdentifiableDataMockMvc.perform(delete("/api/identifiable-data/{id}", identifiableData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IdentifiableData> identifiableDataList = identifiableDataRepository.findAll();
        assertThat(identifiableDataList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdentifiableData.class);
        IdentifiableData identifiableData1 = new IdentifiableData();
        identifiableData1.setId(1L);
        IdentifiableData identifiableData2 = new IdentifiableData();
        identifiableData2.setId(identifiableData1.getId());
        assertThat(identifiableData1).isEqualTo(identifiableData2);
        identifiableData2.setId(2L);
        assertThat(identifiableData1).isNotEqualTo(identifiableData2);
        identifiableData1.setId(null);
        assertThat(identifiableData1).isNotEqualTo(identifiableData2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdentifiableDataDTO.class);
        IdentifiableDataDTO identifiableDataDTO1 = new IdentifiableDataDTO();
        identifiableDataDTO1.setId(1L);
        IdentifiableDataDTO identifiableDataDTO2 = new IdentifiableDataDTO();
        assertThat(identifiableDataDTO1).isNotEqualTo(identifiableDataDTO2);
        identifiableDataDTO2.setId(identifiableDataDTO1.getId());
        assertThat(identifiableDataDTO1).isEqualTo(identifiableDataDTO2);
        identifiableDataDTO2.setId(2L);
        assertThat(identifiableDataDTO1).isNotEqualTo(identifiableDataDTO2);
        identifiableDataDTO1.setId(null);
        assertThat(identifiableDataDTO1).isNotEqualTo(identifiableDataDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(identifiableDataMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(identifiableDataMapper.fromId(null)).isNull();
    }
}
