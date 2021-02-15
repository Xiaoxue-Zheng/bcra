package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.Procedure;
import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.service.ParticipantService;
import uk.ac.herc.bcra.service.dto.ParticipantDTO;
import uk.ac.herc.bcra.service.mapper.ParticipantMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.service.ParticipantQueryService;
import uk.ac.herc.bcra.service.StudyIdService;

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

/**
 * Integration tests for the {@link ParticipantResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class ParticipantResourceIT {

    private static final Instant DEFAULT_REGISTER_DATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTER_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_LOGIN_DATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_LOGIN_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private ParticipantMapper participantMapper;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ParticipantQueryService participantQueryService;

    @Autowired
    private StudyIdService studyIdService;

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

    private MockMvc restParticipantMockMvc;

    private Participant participant;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParticipantResource participantResource = new ParticipantResource(participantService, participantQueryService, studyIdService);
        this.restParticipantMockMvc = MockMvcBuilders.standaloneSetup(participantResource)
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
    public static Participant createEntity(EntityManager em) {
        Participant participant = new Participant()
            .registerDatetime(DEFAULT_REGISTER_DATETIME)
            .lastLoginDatetime(DEFAULT_LAST_LOGIN_DATETIME);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        participant.setUser(user);
        // Add required entity
        IdentifiableData identifiableData;
        identifiableData = IdentifiableDataResourceIT.createEntity(em);
        em.persist(identifiableData);
        em.flush();
        participant.setIdentifiableData(identifiableData);
        // Add required entity
        Procedure procedure;
        procedure = ProcedureResourceIT.createEntity(em);
        em.persist(procedure);
        em.flush();
        participant.setProcedure(procedure);
        return participant;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participant createUpdatedEntity(EntityManager em) {
        Participant participant = new Participant()
            .registerDatetime(UPDATED_REGISTER_DATETIME)
            .lastLoginDatetime(UPDATED_LAST_LOGIN_DATETIME);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        participant.setUser(user);
        // Add required entity
        IdentifiableData identifiableData;
        if (TestUtil.findAll(em, IdentifiableData.class).isEmpty()) {
            identifiableData = IdentifiableDataResourceIT.createUpdatedEntity(em);
            em.persist(identifiableData);
            em.flush();
        } else {
            identifiableData = TestUtil.findAll(em, IdentifiableData.class).get(0);
        }
        participant.setIdentifiableData(identifiableData);
        // Add required entity
        Procedure procedure;
        if (TestUtil.findAll(em, Procedure.class).isEmpty()) {
            procedure = ProcedureResourceIT.createUpdatedEntity(em);
            em.persist(procedure);
            em.flush();
        } else {
            procedure = TestUtil.findAll(em, Procedure.class).get(0);
        }
        participant.setProcedure(procedure);
        return participant;
    }

    @BeforeEach
    public void initTest() {
        participant = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllParticipants() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList
        restParticipantMockMvc.perform(get("/api/participants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participant.getId().intValue())))
            .andExpect(jsonPath("$.[*].registerDatetime").value(hasItem(DEFAULT_REGISTER_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].lastLoginDatetime").value(hasItem(DEFAULT_LAST_LOGIN_DATETIME.toString())));
    }
    
    @Test
    @Transactional
    public void getParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get the participant
        restParticipantMockMvc.perform(get("/api/participants/{id}", participant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(participant.getId().intValue()))
            .andExpect(jsonPath("$.registerDatetime").value(DEFAULT_REGISTER_DATETIME.toString()))
            .andExpect(jsonPath("$.lastLoginDatetime").value(DEFAULT_LAST_LOGIN_DATETIME.toString()));
    }

    @Test
    @Transactional
    public void getAllParticipantsByRegisterDatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList where registerDatetime equals to DEFAULT_REGISTER_DATETIME
        defaultParticipantShouldBeFound("registerDatetime.equals=" + DEFAULT_REGISTER_DATETIME);

        // Get all the participantList where registerDatetime equals to UPDATED_REGISTER_DATETIME
        defaultParticipantShouldNotBeFound("registerDatetime.equals=" + UPDATED_REGISTER_DATETIME);
    }

    @Test
    @Transactional
    public void getAllParticipantsByRegisterDatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList where registerDatetime in DEFAULT_REGISTER_DATETIME or UPDATED_REGISTER_DATETIME
        defaultParticipantShouldBeFound("registerDatetime.in=" + DEFAULT_REGISTER_DATETIME + "," + UPDATED_REGISTER_DATETIME);

        // Get all the participantList where registerDatetime equals to UPDATED_REGISTER_DATETIME
        defaultParticipantShouldNotBeFound("registerDatetime.in=" + UPDATED_REGISTER_DATETIME);
    }

    @Test
    @Transactional
    public void getAllParticipantsByRegisterDatetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList where registerDatetime is not null
        defaultParticipantShouldBeFound("registerDatetime.specified=true");
    }

    @Test
    @Transactional
    public void getAllParticipantsByLastLoginDatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList where lastLoginDatetime equals to DEFAULT_LAST_LOGIN_DATETIME
        defaultParticipantShouldBeFound("lastLoginDatetime.equals=" + DEFAULT_LAST_LOGIN_DATETIME);

        // Get all the participantList where lastLoginDatetime equals to UPDATED_LAST_LOGIN_DATETIME
        defaultParticipantShouldNotBeFound("lastLoginDatetime.equals=" + UPDATED_LAST_LOGIN_DATETIME);
    }

    @Test
    @Transactional
    public void getAllParticipantsByLastLoginDatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList where lastLoginDatetime in DEFAULT_LAST_LOGIN_DATETIME or UPDATED_LAST_LOGIN_DATETIME
        defaultParticipantShouldBeFound("lastLoginDatetime.in=" + DEFAULT_LAST_LOGIN_DATETIME + "," + UPDATED_LAST_LOGIN_DATETIME);

        // Get all the participantList where lastLoginDatetime equals to UPDATED_LAST_LOGIN_DATETIME
        defaultParticipantShouldNotBeFound("lastLoginDatetime.in=" + UPDATED_LAST_LOGIN_DATETIME);
    }

    @Test
    @Transactional
    public void getAllParticipantsByLastLoginDatetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList where lastLoginDatetime is not null
        defaultParticipantShouldBeFound("lastLoginDatetime.specified=true");
    }

    @Test
    @Transactional
    public void getAllParticipantsByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = participant.getUser();
        participantRepository.saveAndFlush(participant);
        Long userId = user.getId();

        // Get all the participantList where user equals to userId
        defaultParticipantShouldBeFound("userId.equals=" + userId);

        // Get all the participantList where user equals to userId + 1
        defaultParticipantShouldNotBeFound("userId.equals=" + (userId + 1000));
    }


    @Test
    @Transactional
    public void getAllParticipantsByIdentifiableDataIsEqualToSomething() throws Exception {
        // Get already existing entity
        IdentifiableData identifiableData = participant.getIdentifiableData();
        participantRepository.saveAndFlush(participant);
        Long identifiableDataId = identifiableData.getId();

        // Get all the participantList where identifiableData equals to identifiableDataId
        defaultParticipantShouldBeFound("identifiableDataId.equals=" + identifiableDataId);

        // Get all the participantList where identifiableData equals to identifiableDataId + 1
        defaultParticipantShouldNotBeFound("identifiableDataId.equals=" + (identifiableDataId + 1000));
    }


    @Test
    @Transactional
    public void getAllParticipantsByProcedureIsEqualToSomething() throws Exception {
        // Get already existing entity
        Procedure procedure = participant.getProcedure();
        participantRepository.saveAndFlush(participant);
        Long procedureId = procedure.getId();

        // Get all the participantList where procedure equals to procedureId
        defaultParticipantShouldBeFound("procedureId.equals=" + procedureId);

        // Get all the participantList where procedure equals to procedureId + 1
        defaultParticipantShouldNotBeFound("procedureId.equals=" + (procedureId + 1000));
    }


    @Test
    @Transactional
    public void getAllParticipantsByCsvFileIsEqualToSomething() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);
        CsvFile csvFile = CsvFileResourceIT.createEntity(em);
        em.persist(csvFile);
        em.flush();
        participant.setCsvFile(csvFile);
        participantRepository.saveAndFlush(participant);
        Long csvFileId = csvFile.getId();

        // Get all the participantList where csvFile equals to csvFileId
        defaultParticipantShouldBeFound("csvFileId.equals=" + csvFileId);

        // Get all the participantList where csvFile equals to csvFileId + 1
        defaultParticipantShouldNotBeFound("csvFileId.equals=" + (csvFileId + 1000));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParticipantShouldBeFound(String filter) throws Exception {
        restParticipantMockMvc.perform(get("/api/participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participant.getId().intValue())))
            .andExpect(jsonPath("$.[*].registerDatetime").value(hasItem(DEFAULT_REGISTER_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].lastLoginDatetime").value(hasItem(DEFAULT_LAST_LOGIN_DATETIME.toString())));

        // Check, that the count is greater than zero
        restParticipantMockMvc.perform(get("/api/participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string(org.hamcrest.Matchers.not("0")));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultParticipantShouldNotBeFound(String filter) throws Exception {
        restParticipantMockMvc.perform(get("/api/participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restParticipantMockMvc.perform(get("/api/participants/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingParticipant() throws Exception {
        // Get the participant
        restParticipantMockMvc.perform(get("/api/participants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void deleteParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        int databaseSizeBeforeDelete = participantRepository.findAll().size();

        // Delete the participant
        restParticipantMockMvc.perform(delete("/api/participants/{id}", participant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Participant> participantList = participantRepository.findAll();
        assertThat(participantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participant.class);
        Participant participant1 = new Participant();
        participant1.setId(1L);
        Participant participant2 = new Participant();
        participant2.setId(participant1.getId());
        assertThat(participant1).isEqualTo(participant2);
        participant2.setId(2L);
        assertThat(participant1).isNotEqualTo(participant2);
        participant1.setId(null);
        assertThat(participant1).isNotEqualTo(participant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParticipantDTO.class);
        ParticipantDTO participantDTO1 = new ParticipantDTO();
        participantDTO1.setId(1L);
        ParticipantDTO participantDTO2 = new ParticipantDTO();
        assertThat(participantDTO1).isNotEqualTo(participantDTO2);
        participantDTO2.setId(participantDTO1.getId());
        assertThat(participantDTO1).isEqualTo(participantDTO2);
        participantDTO2.setId(2L);
        assertThat(participantDTO1).isNotEqualTo(participantDTO2);
        participantDTO1.setId(null);
        assertThat(participantDTO1).isNotEqualTo(participantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(participantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(participantMapper.fromId(null)).isNull();
    }
}
