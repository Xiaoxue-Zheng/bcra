package uk.ac.herc.bcra.web.rest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.domain.enumeration.ParticipantContactWay;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.IdentifiableDataService;
import uk.ac.herc.bcra.service.ParticipantService;
import uk.ac.herc.bcra.service.dto.ParticipantActivationDTO;
import uk.ac.herc.bcra.service.dto.ParticipantDTO;
import uk.ac.herc.bcra.service.dto.ParticipantDetailsDTO;
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
import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
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

    private MockMvc restParticipantMockMvc;

    private MockMvc securityRestMvc;

    private Participant participant;

    @Autowired
    private WebApplicationContext context;

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
        this.securityRestMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @BeforeEach
    public void initTest() {
        participant = DataFactory.createParticipant(em);
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
            .andExpect(jsonPath("$.[*].registerDatetime").value(hasItem(DataFactory.DEFAULT_REGISTER_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].lastLoginDatetime").value(hasItem(DataFactory.DEFAULT_LAST_LOGIN_DATETIME.toString())));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.PARTICIPANT, RoleManager.USER})
    public void unauthorizedGetAllParticipants() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList
        securityRestMvc.perform(get("/api/participants?sort=id,desc")
        .with(csrf()))
            .andExpect(status().is(403));
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
            .andExpect(jsonPath("$.registerDatetime").value(DataFactory.DEFAULT_REGISTER_DATETIME.toString()))
            .andExpect(jsonPath("$.lastLoginDatetime").value(DataFactory.DEFAULT_LAST_LOGIN_DATETIME.toString()));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.USER, RoleManager.MANAGER, RoleManager.PARTICIPANT})
    public void unauthorizedGetParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get the participant
        securityRestMvc.perform(get("/api/participants/{id}", participant.getId()).with(csrf()))
            .andExpect(status().is(403));
    }

    @Test
    @Transactional
    public void getAllParticipantsByRegisterDatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList where registerDatetime equals to DEFAULT_REGISTER_DATETIME
        defaultParticipantShouldBeFound("registerDatetime.equals=" + DataFactory.DEFAULT_REGISTER_DATETIME);

        // Get all the participantList where registerDatetime equals to UPDATED_REGISTER_DATETIME
        defaultParticipantShouldNotBeFound("registerDatetime.equals=" + DataFactory.UPDATED_REGISTER_DATETIME);
    }

    @Test
    @Transactional
    public void getAllParticipantsByRegisterDatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList where registerDatetime in DEFAULT_REGISTER_DATETIME or UPDATED_REGISTER_DATETIME
        defaultParticipantShouldBeFound("registerDatetime.in=" + DataFactory.DEFAULT_REGISTER_DATETIME + "," + DataFactory.UPDATED_REGISTER_DATETIME);

        // Get all the participantList where registerDatetime equals to UPDATED_REGISTER_DATETIME
        defaultParticipantShouldNotBeFound("registerDatetime.in=" + DataFactory.UPDATED_REGISTER_DATETIME);
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
        defaultParticipantShouldBeFound("lastLoginDatetime.equals=" + DataFactory.DEFAULT_LAST_LOGIN_DATETIME);

        // Get all the participantList where lastLoginDatetime equals to UPDATED_LAST_LOGIN_DATETIME
        defaultParticipantShouldNotBeFound("lastLoginDatetime.equals=" + DataFactory.UPDATED_LAST_LOGIN_DATETIME);
    }

    @Test
    @Transactional
    public void getAllParticipantsByLastLoginDatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        // Get all the participantList where lastLoginDatetime in DEFAULT_LAST_LOGIN_DATETIME or UPDATED_LAST_LOGIN_DATETIME
        defaultParticipantShouldBeFound("lastLoginDatetime.in=" + DataFactory.DEFAULT_LAST_LOGIN_DATETIME + "," + DataFactory.UPDATED_LAST_LOGIN_DATETIME);

        // Get all the participantList where lastLoginDatetime equals to UPDATED_LAST_LOGIN_DATETIME
        defaultParticipantShouldNotBeFound("lastLoginDatetime.in=" + DataFactory.UPDATED_LAST_LOGIN_DATETIME);
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

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultParticipantShouldBeFound(String filter) throws Exception {
        restParticipantMockMvc.perform(get("/api/participants?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participant.getId().intValue())))
            .andExpect(jsonPath("$.[*].registerDatetime").value(hasItem(DataFactory.DEFAULT_REGISTER_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].lastLoginDatetime").value(hasItem(DataFactory.DEFAULT_LAST_LOGIN_DATETIME.toString())));

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
    @WithMockUser(authorities = {RoleManager.PARTICIPANT, RoleManager.USER, RoleManager.MANAGER})
    public void unauthorizedDeleteParticipant() throws Exception {
        // Initialize the database
        participantRepository.saveAndFlush(participant);

        int databaseSizeBeforeDelete = participantRepository.findAll().size();

        // Delete the participant
        securityRestMvc.perform(delete("/api/participants/{id}", participant.getId()).with(csrf())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().is(403));
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

    @Test
    @Transactional
    public void testActivation() throws Exception {
        String studyCode = "testActivation-1"+ RandomStringUtils.randomAlphabetic(5);
        String emailAddress = "participant@localhost.com";
        LocalDate dateOfBirth = LocalDate.of(1990, 9, 15);
        StudyId studyId = DataFactory.createStudyId(em, studyCode);
        ParticipantActivationDTO request = new ParticipantActivationDTO();
        request.setDateOfBirth(dateOfBirth);
        request.setEmailAddress(emailAddress);
        request.setPassword(RandomStringUtils.randomAlphabetic(6));
        request.setStudyCode(studyCode);
        request.setConsentResponse(studyIdService.getConsentResponseFromStudyCode(studyCode));
        restParticipantMockMvc.perform(post("/api/participants/activate")
            .content(TestUtil.convertObjectToJsonBytes(request))
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated());
        Optional<Participant> participantOptional = participantRepository.findOneByUserLogin(studyCode);
        assertThat(participantOptional.isPresent()).isTrue();
        Participant participant = participantOptional.get();
        assertThat(participant.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(participant.getUser()).isNotNull();
        User user = participant.getUser();
        assertThat(user.getEmail()).isEqualTo(emailAddress);
        assertThat(user.getLogin()).isEqualTo(studyCode);
        assertThat(participant.getProcedure()).isNotNull();
        assertThat(participant.getIdentifiableData()).isNull();
    }

    @Test
    @Transactional
    public void testUpdateParticipantDetail() throws Exception {
        String login = "testUpdateParticipantDetail-1"+ RandomStringUtils.randomAlphabetic(5);
        Participant participant = DataFactory.createParticipantNoIdentifiableData(em, login);
        ParticipantDetailsDTO participantDetailsDTO = new ParticipantDetailsDTO();
        participantDetailsDTO.setAddressLine1("test");
        participantDetailsDTO.setForename("test");
        participantDetailsDTO.setHomePhoneNumber("11111111111");
        participantDetailsDTO.setMobilePhoneNumber("11111111111");
        participantDetailsDTO.setSurname("test");
        participantDetailsDTO.setPostCode("M156QQ");
        participantDetailsDTO.setPreferredContactWays(Arrays.asList(ParticipantContactWay.SMS, ParticipantContactWay.CALL));
        restParticipantMockMvc.perform(post("/api/participants/details")
            .principal(new Principal() {
                @Override
                public String getName() {
                    return participant.getUser().getLogin();
                }
            })
            .content(TestUtil.convertObjectToJsonBytes(participantDetailsDTO))
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());
        //testUpdateParticipantDetail-1IDmUz@localhost
        //testUpdateParticipantDetail-1IDmUz@localhost
        Optional<IdentifiableData> identifiableDataOptional = identifiableDataService.findOne(participant.getUser().getEmail());
        assertThat(identifiableDataOptional.isPresent()).isTrue();
        IdentifiableData identifiableData = identifiableDataOptional.get();
        assertThat(identifiableData.getPreferContactWay()).isEqualTo(6);
    }
}
