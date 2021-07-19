package uk.ac.herc.bcra.web.rest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.repository.IdentifiableDataRepository;
import uk.ac.herc.bcra.security.RoleManager;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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

    private static final String UPDATED_NHS_NUMBER = "BBBBBBBBBB";

    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String UPDATED_SURNAME = "BBBBBBBBBB";

    @Autowired
    private WebApplicationContext context;

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

    private MockMvc securityRestMvc;

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
        this.securityRestMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @BeforeEach
    public void initTest() {
        identifiableData = DataFactory.buildIdentifiableData();
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
        assertThat(testIdentifiableData.getNhsNumber()).isEqualTo(identifiableData.getNhsNumber());
        assertThat(testIdentifiableData.getDateOfBirth()).isEqualTo(identifiableData.getDateOfBirth());
        assertThat(testIdentifiableData.getFirstname()).isEqualTo(identifiableData.getFirstname());
        assertThat(testIdentifiableData.getSurname()).isEqualTo(identifiableData.getSurname());
        assertThat(testIdentifiableData.getEmail()).isEqualTo(identifiableData.getEmail());
        assertThat(testIdentifiableData.getAddress1()).isEqualTo(identifiableData.getAddress1());
        assertThat(testIdentifiableData.getAddress2()).isEqualTo(identifiableData.getAddress2());
        assertThat(testIdentifiableData.getAddress3()).isEqualTo(identifiableData.getAddress3());
        assertThat(testIdentifiableData.getAddress4()).isEqualTo(identifiableData.getAddress4());
        assertThat(testIdentifiableData.getAddress5()).isEqualTo(identifiableData.getAddress5());
        assertThat(testIdentifiableData.getPostcode()).isEqualTo(identifiableData.getPostcode());
        assertThat(testIdentifiableData.getPracticeName()).isEqualTo(identifiableData.getPracticeName());
    }

    @Test
    @Transactional
    public void createIdentifiableDataWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = identifiableDataRepository.findAll().size();

        // FIXME Create the IdentifiableData with an existing ID, this depends on the create test case?
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
    @WithMockUser(authorities = {RoleManager.PARTICIPANT, RoleManager.USER, RoleManager.MANAGER})
    public void unauthorizedCreateIdentifiableData() throws Exception {
        // Create the IdentifiableData
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(identifiableData);
        securityRestMvc.perform(post("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)).with(csrf()))
            .andExpect(status().is(403));
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
            .andExpect(jsonPath("$.[*].nhsNumber").value(hasItem(identifiableData.getNhsNumber())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(identifiableData.getDateOfBirth().toString())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(identifiableData.getFirstname())))
            .andExpect(jsonPath("$.[*].surname").value(hasItem(identifiableData.getSurname())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(identifiableData.getEmail())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(identifiableData.getAddress1())))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(identifiableData.getAddress2())))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(identifiableData.getAddress3())))
            .andExpect(jsonPath("$.[*].address4").value(hasItem(identifiableData.getAddress4())))
            .andExpect(jsonPath("$.[*].address5").value(hasItem(identifiableData.getAddress5())))
            .andExpect(jsonPath("$.[*].postcode").value(hasItem(identifiableData.getPostcode())))
            .andExpect(jsonPath("$.[*].practiceName").value(hasItem(identifiableData.getPracticeName())));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.PARTICIPANT, RoleManager.USER, RoleManager.MANAGER})
    public void unauthorizedGetAllIdentifiableData() throws Exception {
        // Initialize the database
        identifiableDataRepository.saveAndFlush(identifiableData);

        // Get all the identifiableDataList
        securityRestMvc.perform(get("/api/identifiable-data?sort=id,desc")
            .with(csrf()))
            .andExpect(status().is(403));
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
            .andExpect(jsonPath("$.nhsNumber").value(identifiableData.getNhsNumber()))
            .andExpect(jsonPath("$.dateOfBirth").value(identifiableData.getDateOfBirth().toString()))
            .andExpect(jsonPath("$.firstname").value(identifiableData.getFirstname()))
            .andExpect(jsonPath("$.surname").value(identifiableData.getSurname()))
            .andExpect(jsonPath("$.email").value(identifiableData.getEmail()))
            .andExpect(jsonPath("$.address1").value(identifiableData.getAddress1()))
            .andExpect(jsonPath("$.address2").value(identifiableData.getAddress2()))
            .andExpect(jsonPath("$.address3").value(identifiableData.getAddress3()))
            .andExpect(jsonPath("$.address4").value(identifiableData.getAddress4()))
            .andExpect(jsonPath("$.address5").value(identifiableData.getAddress5()))
            .andExpect(jsonPath("$.postcode").value(identifiableData.getPostcode()))
            .andExpect(jsonPath("$.practiceName").value(identifiableData.getPracticeName()));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.PARTICIPANT, RoleManager.USER, RoleManager.MANAGER})
    public void unauthorizedGetIdentifiableData() throws Exception {
        // Initialize the database
        identifiableDataRepository.saveAndFlush(identifiableData);

        // Get the identifiableData
        securityRestMvc.perform(get("/api/identifiable-data/{id}", identifiableData.getId())
            .with(csrf()))
            .andExpect(status().is(403));
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
            .email(DataFactory.UPDATED_EMAIL)
            .address1(DataFactory.UPDATED_ADDRESS_1)
            .address2(DataFactory.UPDATED_ADDRESS_2)
            .address3(DataFactory.UPDATED_ADDRESS_3)
            .address4(DataFactory.UPDATED_ADDRESS_4)
            .address5(DataFactory.UPDATED_ADDRESS_5)
            .postcode(DataFactory.UPDATED_POSTCODE)
            .practiceName(DataFactory.UPDATED_PRACTICE_NAME);
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
        assertThat(testIdentifiableData.getEmail()).isEqualTo(DataFactory.UPDATED_EMAIL);
        assertThat(testIdentifiableData.getAddress1()).isEqualTo(DataFactory.UPDATED_ADDRESS_1);
        assertThat(testIdentifiableData.getAddress2()).isEqualTo(DataFactory.UPDATED_ADDRESS_2);
        assertThat(testIdentifiableData.getAddress3()).isEqualTo(DataFactory.UPDATED_ADDRESS_3);
        assertThat(testIdentifiableData.getAddress4()).isEqualTo(DataFactory.UPDATED_ADDRESS_4);
        assertThat(testIdentifiableData.getAddress5()).isEqualTo(DataFactory.UPDATED_ADDRESS_5);
        assertThat(testIdentifiableData.getPostcode()).isEqualTo(DataFactory.UPDATED_POSTCODE);
        assertThat(testIdentifiableData.getPracticeName()).isEqualTo(DataFactory.UPDATED_PRACTICE_NAME);
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
    @WithMockUser(authorities = {RoleManager.PARTICIPANT, RoleManager.USER, RoleManager.MANAGER})
    public void unauthorizedUpdateIdentifiableData() throws Exception {
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
            .email(DataFactory.UPDATED_EMAIL)
            .address1(DataFactory.UPDATED_ADDRESS_1)
            .address2(DataFactory.UPDATED_ADDRESS_2)
            .address3(DataFactory.UPDATED_ADDRESS_3)
            .address4(DataFactory.UPDATED_ADDRESS_4)
            .address5(DataFactory.UPDATED_ADDRESS_5)
            .postcode(DataFactory.UPDATED_POSTCODE)
            .practiceName(DataFactory.UPDATED_PRACTICE_NAME);
        IdentifiableDataDTO identifiableDataDTO = identifiableDataMapper.toDto(updatedIdentifiableData);

        securityRestMvc.perform(put("/api/identifiable-data")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(identifiableDataDTO)).with(csrf()))
            .andExpect(status().is(403));
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
    @WithMockUser(authorities = {RoleManager.PARTICIPANT, RoleManager.USER, RoleManager.MANAGER})
    public void unauthorizedDeleteIdentifiableData() throws Exception {
        // Initialize the database
        identifiableDataRepository.saveAndFlush(identifiableData);

        int databaseSizeBeforeDelete = identifiableDataRepository.findAll().size();

        // Delete the identifiableData
        securityRestMvc.perform(delete("/api/identifiable-data/{id}", identifiableData.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8).with(csrf()))
            .andExpect(status().is(403));
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
