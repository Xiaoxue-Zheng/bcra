package uk.ac.herc.bcra.web.rest;

import org.apache.commons.lang3.RandomStringUtils;
import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.domain.enumeration.ParticipantContactWay;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class DataFactory {

    public static final String DEFAULT_STUDY_CODE_PREFIX = "TST_";
    static final Instant DEFAULT_REGISTER_DATETIME = Instant.ofEpochMilli(0L);
    static final Instant UPDATED_REGISTER_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    static final Instant DEFAULT_LAST_LOGIN_DATETIME = Instant.ofEpochMilli(0L);
    static final Instant UPDATED_LAST_LOGIN_DATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    public static final String UPDATED_LOGIN = "jhipster";
    public static final Long DEFAULT_ID = 1L;
    private static final String DEFAULT_LOGIN = "johndoe";
    public static final String DEFAULT_PASSWORD = "passjohndoe";
    public static final String UPDATED_PASSWORD = "passjhipster";
    public static final String DEFAULT_EMAIL = "johndoe@localhost";
    public static final String UPDATED_EMAIL = "jhipster@localhost";
    public static final String DEFAULT_FIRSTNAME = "john";
    public static final String UPDATED_FIRSTNAME = "jhipsterFirstName";
    public static final String DEFAULT_LASTNAME = "doe";
    public static final String UPDATED_LASTNAME = "jhipsterLastName";
    public static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";
    public static final String UPDATED_IMAGEURL = "http://placehold.it/40x40";
    public static final String DEFAULT_LANGKEY = "en";
    public static final String UPDATED_LANGKEY = "fr";
    static final String UPDATED_IDENTIFIABLE_EMAIL = "BBBBBBBBBB";
    static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";
    static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";
    static final String DEFAULT_ADDRESS_3 = "AAAAAAAAAA";
    static final String UPDATED_ADDRESS_3 = "BBBBBBBBBB";
    static final String DEFAULT_ADDRESS_4 = "AAAAAAAAAA";
    static final String UPDATED_ADDRESS_4 = "BBBBBBBBBB";
    static final String DEFAULT_ADDRESS_5 = "AAAAAAAAAA";
    static final String UPDATED_ADDRESS_5 = "BBBBBBBBBB";
    static final String DEFAULT_POSTCODE = "AAAAAAAA";
    static final String UPDATED_POSTCODE = "BBBBBBBB";
    static final String DEFAULT_PRACTICE_NAME = "AAAAAAAAAA";
    static final String UPDATED_PRACTICE_NAME = "BBBBBBBBBB";
    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_IDENTIFIABLE_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIABLE_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIABLE_SURNAME = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIABLE_SURNAME = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTIFIABLE_EMAIL = "AAAAAAAAAA";
    private static final ResponseState DEFAULT_STATE = ResponseState.PROCESSED;
    static final ResponseState UPDATED_STATE = ResponseState.INVALID;
    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    static final String UPDATED_STATUS = "BBBBBBBBBB";
    static final Integer UPDATED_ANSWER_VALUE = 54321;
    private static int numberOfStudies = 0;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participant createParticipant(EntityManager em) {
        Participant participant = new Participant()
            .dateOfBirth(LocalDate.of(1990, 9,15))
            .registerDatetime(DEFAULT_REGISTER_DATETIME)
            .lastLoginDatetime(DEFAULT_LAST_LOGIN_DATETIME);
        // Add required entity
        User user = buildUser();
        em.persist(user);
        em.flush();
        participant.setUser(user);
        // Add required entity
        IdentifiableData identifiableData;
        identifiableData = buildIdentifiableData();
        em.persist(identifiableData);
        em.flush();
        participant.setIdentifiableData(identifiableData);
        // Add required entity
        Procedure procedure;
        procedure = createProcedure(em);
        em.persist(procedure);
        em.flush();
        participant.setProcedure(procedure);
        return participant;
    }

    public static Participant createParticipantNoIdentifiableData(EntityManager em, String login) {
        Participant participant = new Participant()
            .dateOfBirth(LocalDate.of(1990, 9,15))
            .registerDatetime(DEFAULT_REGISTER_DATETIME)
            .lastLoginDatetime(DEFAULT_LAST_LOGIN_DATETIME);
        // Add required entity
        User user = buildUser(login);
        em.persist(user);
        participant.setUser(user);
        // Add required entity
        Procedure procedure;
        procedure = createProcedure(em);
        em.persist(procedure);
        participant.setProcedure(procedure);
        em.persist(participant);
        em.flush();
        return participant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participant createUpdatedParticipant(EntityManager em) {
        Participant participant = new Participant()
            .dateOfBirth(LocalDate.of(1990, 9,15))
            .registerDatetime(UPDATED_REGISTER_DATETIME)
            .lastLoginDatetime(UPDATED_LAST_LOGIN_DATETIME);
        // Add required entity
        User user = buildUser();
        em.persist(user);
        em.flush();
        participant.setUser(user);
        // Add required entity
        IdentifiableData identifiableData;
        if (TestUtil.findAll(em, IdentifiableData.class).isEmpty()) {
            identifiableData = buildUpdatedIdentifiableData();
            em.persist(identifiableData);
            em.flush();
        } else {
            identifiableData = TestUtil.findAll(em, IdentifiableData.class).get(0);
        }
        participant.setIdentifiableData(identifiableData);
        // Add required entity
        Procedure procedure;
        if (TestUtil.findAll(em, Procedure.class).isEmpty()) {
            procedure = createProcedure(em);
            em.persist(procedure);
            em.flush();
        } else {
            procedure = TestUtil.findAll(em, Procedure.class).get(0);
        }
        participant.setProcedure(procedure);
        return participant;
    }

    public static Participant createUpdatedParticipant(EntityManager em, String login) {
        Participant participant = new Participant()
            .dateOfBirth(LocalDate.of(1990, 9,15))
            .registerDatetime(UPDATED_REGISTER_DATETIME)
            .lastLoginDatetime(UPDATED_LAST_LOGIN_DATETIME);
        // Add required entity
        User user = buildUser(login);
        em.persist(user);
        em.flush();
        participant.setUser(user);
        // Add required entity
        IdentifiableData identifiableData;

        identifiableData = buildUpdatedIdentifiableData();
        em.persist(identifiableData);
        em.flush();

        participant.setIdentifiableData(identifiableData);
        // Add required entity
        Procedure procedure;
        procedure = createProcedure(em);
        em.persist(procedure);
        em.flush();

        participant.setProcedure(procedure);
        return participant;
    }

    /**
     * Create a User.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the User entity.
     */
    public static User buildUser() {
        User user = new User();
        user.setLogin((DEFAULT_LOGIN + RandomStringUtils.randomAlphabetic(5)).toLowerCase(Locale.ROOT));
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail(RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        return user;
    }

    /**
     * Create a User.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which has a required relationship to the User entity.
     */
    public static User buildUser(String login) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail(login+"@localhost");
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        return user;
    }

    public static User createUser(EntityManager entityManager, String login, String password, String... authorities) {
        User user = buildUser(login);
        user.setPassword(password);
        user.setAuthorities(Arrays.stream(authorities).map(Authority::new).collect(Collectors.toSet()));
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdentifiableData buildIdentifiableData() {
        IdentifiableData identifiableData = new IdentifiableData()
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .firstname(DEFAULT_IDENTIFIABLE_FIRSTNAME)
            .surname(DEFAULT_IDENTIFIABLE_SURNAME)
            .email(DEFAULT_IDENTIFIABLE_EMAIL)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .address3(DEFAULT_ADDRESS_3)
            .address4(DEFAULT_ADDRESS_4)
            .address5(DEFAULT_ADDRESS_5)
            .postcode(DEFAULT_POSTCODE)
            .preferContactWay(ParticipantContactWay.calculateCodes(Arrays.asList(ParticipantContactWay.SMS)));

        return identifiableData;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IdentifiableData buildUpdatedIdentifiableData() {
        IdentifiableData identifiableData = new IdentifiableData()
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .firstname(UPDATED_IDENTIFIABLE_FIRSTNAME)
            .surname(UPDATED_IDENTIFIABLE_SURNAME)
            .email(UPDATED_IDENTIFIABLE_EMAIL)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .address5(UPDATED_ADDRESS_5)
            .postcode(UPDATED_POSTCODE)
            .preferContactWay(ParticipantContactWay.calculateCodes(Arrays.asList(ParticipantContactWay.SMS)));
        return identifiableData;
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyId createStudyId(EntityManager em) {
        StudyId studyId = new StudyId()
            .code(DEFAULT_STUDY_CODE_PREFIX + numberOfStudies);

        numberOfStudies += 1;

        AnswerResponse consentResponse = createAnswerResponse(em, QuestionnaireType.CONSENT_FORM);
        AnswerResponse riskAssessmentResponse = createAnswerResponse(em, QuestionnaireType.RISK_ASSESSMENT);

        studyId.setConsentResponse(consentResponse);
        studyId.setRiskAssessmentResponse(riskAssessmentResponse);

        em.persist(studyId);

        return studyId;
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StudyId createStudyId(EntityManager em, String studyCode) {
        StudyId studyId = new StudyId()
            .code(studyCode);

        numberOfStudies += 1;

        AnswerResponse consentResponse = createAnswerResponse(em, QuestionnaireType.CONSENT_FORM);
        AnswerResponse riskAssessmentResponse = createAnswerResponse(em, QuestionnaireType.RISK_ASSESSMENT);

        studyId.setConsentResponse(consentResponse);
        studyId.setRiskAssessmentResponse(riskAssessmentResponse);

        em.persist(studyId);
        em.flush();
        return studyId;
    }

    public static StudyId createStudyId(EntityManager em, Participant participant) {
        StudyId studyId = new StudyId()
            .code(DEFAULT_STUDY_CODE_PREFIX + numberOfStudies);
        numberOfStudies += 1;

        AnswerResponse consentResponse = createAnswerResponse(em);
        AnswerResponse riskAssessmentResponse = createAnswerResponse(em);
        studyId.setConsentResponse(consentResponse);
        studyId.setRiskAssessmentResponse(riskAssessmentResponse);
        studyId.setParticipant(participant);

        em.persist(studyId);

        return studyId;
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerResponse createAnswerResponse(EntityManager em) {
        AnswerResponse answerResponse = new AnswerResponse()
            .state(DEFAULT_STATE)
            .status(DEFAULT_STATUS);
        // Add required entity
        Questionnaire questionnaire;
        if (TestUtil.findAll(em, Questionnaire.class).isEmpty()) {
            questionnaire = QuestionnaireResourceIT.createEntity(em);
            em.persist(questionnaire);
            em.flush();
        } else {
            questionnaire = TestUtil.findAll(em, Questionnaire.class).get(0);
        }
        answerResponse.setQuestionnaire(questionnaire);
        em.persist(answerResponse);
        em.flush();

        answerResponse.addAnswerSection(
            AnswerSectionResourceIT.createEntity(em)
        );
        return answerResponse;
    }

    public static AnswerResponse createAnswerResponse(EntityManager em, QuestionnaireType type) {
        AnswerResponse answerResponse = new AnswerResponse()
            .state(DEFAULT_STATE)
            .status(DEFAULT_STATUS);
        // Add required entity
        Questionnaire questionnaire = QuestionnaireResourceIT.createEntity(em, type);
        em.persist(questionnaire);
        em.flush();

        answerResponse.setQuestionnaire(questionnaire);
        em.persist(answerResponse);
        em.flush();

        answerResponse.addAnswerSection(
            AnswerSectionResourceIT.createEntity(em)
        );
        return answerResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnswerResponse createUpdatedAnswerResponse(EntityManager em) {
        AnswerResponse answerResponse = new AnswerResponse()
            .state(UPDATED_STATE)
            .status(UPDATED_STATUS);
        // Add required entity
        Questionnaire questionnaire;
        if (TestUtil.findAll(em, Questionnaire.class).isEmpty()) {
            questionnaire = QuestionnaireResourceIT.createUpdatedEntity(em);
            em.persist(questionnaire);
            em.flush();
        } else {
            questionnaire = TestUtil.findAll(em, Questionnaire.class).get(0);
        }
        answerResponse.setQuestionnaire(questionnaire);
        return answerResponse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedure createUpdatedProcedure(EntityManager em) {
        Procedure procedure = new Procedure();
        // Add required entity
        AnswerResponse consentResponse = null;
        AnswerResponse riskAssessmentResponse = null;
        if (TestUtil.findAll(em, AnswerResponse.class).isEmpty()) {
            consentResponse = createAnswerResponse(em);
            em.persist(consentResponse);

            riskAssessmentResponse = createUpdatedAnswerResponse(em);
            em.persist(riskAssessmentResponse);

            em.flush();
        } else {
            for (AnswerResponse ar : TestUtil.findAll(em, AnswerResponse.class)) {
                if (ar.getQuestionnaire().getType() == QuestionnaireType.CONSENT_FORM) {
                    consentResponse = ar;
                } else {
                    riskAssessmentResponse = ar;
                }
            }
        }

        procedure.setConsentResponse(consentResponse);
        procedure.setRiskAssessmentResponse(riskAssessmentResponse);
        return procedure;
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedure createProcedure(EntityManager em) {
        Procedure procedure = new Procedure();
        // Add required entity
        AnswerResponse consentResponse = createAnswerResponse(em, QuestionnaireType.CONSENT_FORM);
        em.persist(consentResponse);
        em.flush();

        AnswerResponse riskAssessmentResponse = createAnswerResponse(em, QuestionnaireType.RISK_ASSESSMENT);
        em.persist(riskAssessmentResponse);
        em.flush();
        procedure.setConsentResponse(consentResponse);
        // Add required entity
        procedure.setRiskAssessmentResponse(riskAssessmentResponse);

        return procedure;
    }
}
