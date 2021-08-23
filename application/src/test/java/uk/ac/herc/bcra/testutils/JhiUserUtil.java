package uk.ac.herc.bcra.testutils;

import org.apache.commons.lang3.RandomStringUtils;
import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.domain.enumeration.ParticipantContactWay;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class JhiUserUtil {

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
    static final ResponseState UPDATED_STATE = ResponseState.INVALID;
    static final String UPDATED_STATUS = "BBBBBBBBBB";
    static final Integer UPDATED_ANSWER_VALUE = 54321;

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
}
