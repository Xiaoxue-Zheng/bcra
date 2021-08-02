package uk.ac.herc.bcra.web.rest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Authority;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.MailService;
import uk.ac.herc.bcra.service.UserService;
import uk.ac.herc.bcra.service.dto.UserDTO;
import uk.ac.herc.bcra.service.mapper.UserMapper;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;
import uk.ac.herc.bcra.web.rest.vm.ManagedUserVM;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserResource} REST controller.
 */
@SpringBootTest(classes = BcraApp.class)
public class UserResourceIT {

    private static final String DEFAULT_LOGIN = "johndoe";
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private CacheManager cacheManager;

    private MockMvc restUserMockMvc;

    private MockMvc securityRestMvc;

    private User user;

    @BeforeEach
    public void setup() {
        cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).clear();
        cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).clear();
        UserResource userResource = new UserResource(userService, userRepository, mailService);

        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter)
            .build();
        this.securityRestMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @BeforeEach
    public void initTest() {
        user = DataFactory.buildUser();
    }

    @Test
    @Transactional
    public void createUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        // Create the User
        String login = "createUser"+ RandomStringUtils.randomAlphabetic(5);
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setLogin(login);
        managedUserVM.setPassword(DataFactory.DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DataFactory.DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DataFactory.DEFAULT_LASTNAME);
        managedUserVM.setEmail(login+"@localhost");
        managedUserVM.setActivated(true);
        managedUserVM.setImageUrl(DataFactory.DEFAULT_IMAGEURL);
        managedUserVM.setLangKey(DataFactory.DEFAULT_LANGKEY);
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isCreated());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate + 1);
        User testUser = userRepository.findOneByLogin(login.toLowerCase(Locale.ROOT)).get();

        assertThat(testUser.getLogin()).isEqualTo(managedUserVM.getLogin().toLowerCase(Locale.ROOT));
        assertThat(testUser.getFirstName()).isEqualTo(managedUserVM.getFirstName());
        assertThat(testUser.getLastName()).isEqualTo(managedUserVM.getLastName());
        assertThat(testUser.getEmail()).isEqualTo(managedUserVM.getEmail().toLowerCase(Locale.ROOT));
        assertThat(testUser.getImageUrl()).isEqualTo(managedUserVM.getImageUrl());
        assertThat(testUser.getLangKey()).isEqualTo(managedUserVM.getLangKey());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.MANAGER, RoleManager.USER, RoleManager.PARTICIPANT})
    public void unauthorizedCreateUser() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        // Create the User
        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setLogin(DEFAULT_LOGIN);
        managedUserVM.setPassword(DataFactory.DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DataFactory.DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DataFactory.DEFAULT_LASTNAME);
        managedUserVM.setEmail(DataFactory.DEFAULT_EMAIL);
        managedUserVM.setActivated(true);
        managedUserVM.setImageUrl(DataFactory.DEFAULT_IMAGEURL);
        managedUserVM.setLangKey(DataFactory.DEFAULT_LANGKEY);
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        securityRestMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)).with(csrf()))
            .andExpect(status().is(403));

    }

    @Test
    @Transactional
    public void createUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(1L);
        managedUserVM.setLogin(DEFAULT_LOGIN);
        managedUserVM.setPassword(DataFactory.DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DataFactory.DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DataFactory.DEFAULT_LASTNAME);
        managedUserVM.setEmail(DataFactory.DEFAULT_EMAIL);
        managedUserVM.setActivated(true);
        managedUserVM.setImageUrl(DataFactory.DEFAULT_IMAGEURL);
        managedUserVM.setLangKey(DataFactory.DEFAULT_LANGKEY);
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void createUserWithExistingLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setLogin(user.getLogin());// this login should already be used
        managedUserVM.setPassword(DataFactory.DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DataFactory.DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DataFactory.DEFAULT_LASTNAME);
        managedUserVM.setEmail("anothermail@localhost");
        managedUserVM.setActivated(true);
        managedUserVM.setImageUrl(DataFactory.DEFAULT_IMAGEURL);
        managedUserVM.setLangKey(DataFactory.DEFAULT_LANGKEY);
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        // Create the User
        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void createUserWithExistingEmail() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeCreate = userRepository.findAll().size();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setLogin("anotherlogin");
        managedUserVM.setPassword(DataFactory.DEFAULT_PASSWORD);
        managedUserVM.setFirstName(DataFactory.DEFAULT_FIRSTNAME);
        managedUserVM.setLastName(DataFactory.DEFAULT_LASTNAME);
        managedUserVM.setEmail(user.getEmail());// this email should already be used
        managedUserVM.setActivated(true);
        managedUserVM.setImageUrl(DataFactory.DEFAULT_IMAGEURL);
        managedUserVM.setLangKey(DataFactory.DEFAULT_LANGKEY);
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        // Create the User
        restUserMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isBadRequest());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get all the users
        restUserMockMvc.perform(get("/api/users?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].login").value(hasItem(user.getLogin())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DataFactory.DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DataFactory.DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(user.getEmail())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DataFactory.DEFAULT_IMAGEURL)))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DataFactory.DEFAULT_LANGKEY)));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.MANAGER, RoleManager.USER, RoleManager.PARTICIPANT})
    public void unauthorizedGetAllUsers() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get all the users
        restUserMockMvc.perform(get("/api/users?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON).with(csrf()))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    public void getUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).get(user.getLogin())).isNull();

        // Get the user
        restUserMockMvc.perform(get("/api/users/{login}", user.getLogin()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.login").value(user.getLogin()))
            .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
            .andExpect(jsonPath("$.lastName").value(user.getLastName()))
            .andExpect(jsonPath("$.email").value(user.getEmail()))
            .andExpect(jsonPath("$.imageUrl").value(user.getImageUrl()))
            .andExpect(jsonPath("$.langKey").value(user.getLangKey()));

        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).get(user.getLogin())).isNotNull();
    }

    @Test
    @WithMockUser(authorities = {RoleManager.MANAGER, RoleManager.USER, RoleManager.PARTICIPANT})
    public void unauthorizedGetUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).get(user.getLogin())).isNull();

        // Get the user
        securityRestMvc.perform(get("/api/users/{login}", user.getLogin()).with(csrf()))
            .andExpect(status().is(403));
    }

    @Test
    @Transactional
    public void getNonExistingUser() throws Exception {
        restUserMockMvc.perform(get("/api/users/unknown"))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setLogin(updatedUser.getLogin());
        managedUserVM.setPassword(DataFactory.UPDATED_PASSWORD);
        managedUserVM.setFirstName(DataFactory.UPDATED_FIRSTNAME);
        managedUserVM.setLastName(DataFactory.UPDATED_LASTNAME);
        managedUserVM.setEmail(DataFactory.UPDATED_EMAIL);
        managedUserVM.setActivated(updatedUser.getActivated());
        managedUserVM.setImageUrl(DataFactory.UPDATED_IMAGEURL);
        managedUserVM.setLangKey(DataFactory.UPDATED_LANGKEY);
        managedUserVM.setCreatedBy(updatedUser.getCreatedBy());
        managedUserVM.setCreatedDate(updatedUser.getCreatedDate());
        managedUserVM.setLastModifiedBy(updatedUser.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(updatedUser.getLastModifiedDate());
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        restUserMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isOk());

        // Validate the User in the database
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeUpdate);
        User testUser = userRepository.findById(user.getId()).get();
        assertThat(testUser.getFirstName()).isEqualTo(managedUserVM.getFirstName());
        assertThat(testUser.getLastName()).isEqualTo(managedUserVM.getLastName());
        assertThat(testUser.getEmail()).isEqualTo(managedUserVM.getEmail());
        assertThat(testUser.getImageUrl()).isEqualTo(managedUserVM.getImageUrl());
        assertThat(testUser.getLangKey()).isEqualTo(managedUserVM.getLangKey());
    }

    @Test
    @Transactional
    public void updateUserLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setLogin(DataFactory.UPDATED_LOGIN);
        managedUserVM.setPassword(DataFactory.UPDATED_PASSWORD);
        managedUserVM.setFirstName(DataFactory.UPDATED_FIRSTNAME);
        managedUserVM.setLastName(DataFactory.UPDATED_LASTNAME);
        managedUserVM.setEmail(DataFactory.UPDATED_EMAIL);
        managedUserVM.setActivated(updatedUser.getActivated());
        managedUserVM.setImageUrl(DataFactory.UPDATED_IMAGEURL);
        managedUserVM.setLangKey(DataFactory.UPDATED_LANGKEY);
        managedUserVM.setCreatedBy(updatedUser.getCreatedBy());
        managedUserVM.setCreatedDate(updatedUser.getCreatedDate());
        managedUserVM.setLastModifiedBy(updatedUser.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(updatedUser.getLastModifiedDate());
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        restUserMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isOk());

        // Validate the User in the database
        User testUser = userRepository.findById(user.getId()).get();
        assertThat(testUser.getLogin()).isEqualTo(managedUserVM.getLogin());
        assertThat(testUser.getFirstName()).isEqualTo(DataFactory.UPDATED_FIRSTNAME);
        assertThat(testUser.getLastName()).isEqualTo(DataFactory.UPDATED_LASTNAME);
        assertThat(testUser.getEmail()).isEqualTo(DataFactory.UPDATED_EMAIL);
        assertThat(testUser.getImageUrl()).isEqualTo(DataFactory.UPDATED_IMAGEURL);
        assertThat(testUser.getLangKey()).isEqualTo(DataFactory.UPDATED_LANGKEY);
    }

    @Test
    @Transactional
    public void updateUserExistingEmail() throws Exception {
        // Initialize the database with 2 users
        userRepository.saveAndFlush(user);

        User anotherUser = new User();
        anotherUser.setLogin("jhipster");
        anotherUser.setPassword(RandomStringUtils.random(60));
        anotherUser.setActivated(true);
        anotherUser.setEmail("jhipster@localhost");
        anotherUser.setFirstName("java");
        anotherUser.setLastName("hipster");
        anotherUser.setImageUrl("");
        anotherUser.setLangKey("en");
        userRepository.saveAndFlush(anotherUser);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setLogin(updatedUser.getLogin());
        managedUserVM.setPassword(updatedUser.getPassword());
        managedUserVM.setFirstName(updatedUser.getFirstName());
        managedUserVM.setLastName(updatedUser.getLastName());
        managedUserVM.setEmail("jhipster@localhost");// this email should already be used by anotherUser
        managedUserVM.setActivated(updatedUser.getActivated());
        managedUserVM.setImageUrl(updatedUser.getImageUrl());
        managedUserVM.setLangKey(updatedUser.getLangKey());
        managedUserVM.setCreatedBy(updatedUser.getCreatedBy());
        managedUserVM.setCreatedDate(updatedUser.getCreatedDate());
        managedUserVM.setLastModifiedBy(updatedUser.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(updatedUser.getLastModifiedDate());
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        restUserMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void updateUserExistingLogin() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        User anotherUser = new User();
        anotherUser.setLogin("jhipster");
        anotherUser.setPassword(RandomStringUtils.random(60));
        anotherUser.setActivated(true);
        anotherUser.setEmail("jhipster@localhost");
        anotherUser.setFirstName("java");
        anotherUser.setLastName("hipster");
        anotherUser.setImageUrl("");
        anotherUser.setLangKey("en");
        userRepository.saveAndFlush(anotherUser);

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setLogin("jhipster");// this login should already be used by anotherUser
        managedUserVM.setPassword(updatedUser.getPassword());
        managedUserVM.setFirstName(updatedUser.getFirstName());
        managedUserVM.setLastName(updatedUser.getLastName());
        managedUserVM.setEmail(updatedUser.getEmail());
        managedUserVM.setActivated(updatedUser.getActivated());
        managedUserVM.setImageUrl(updatedUser.getImageUrl());
        managedUserVM.setLangKey(updatedUser.getLangKey());
        managedUserVM.setCreatedBy(updatedUser.getCreatedBy());
        managedUserVM.setCreatedDate(updatedUser.getCreatedDate());
        managedUserVM.setLastModifiedBy(updatedUser.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(updatedUser.getLastModifiedDate());
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        restUserMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.MANAGER, RoleManager.USER, RoleManager.PARTICIPANT})
    public void unauthorizedUpdateUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeUpdate = userRepository.findAll().size();

        // Update the user
        User updatedUser = userRepository.findById(user.getId()).get();

        ManagedUserVM managedUserVM = new ManagedUserVM();
        managedUserVM.setId(updatedUser.getId());
        managedUserVM.setLogin(updatedUser.getLogin());
        managedUserVM.setPassword(DataFactory.UPDATED_PASSWORD);
        managedUserVM.setFirstName(DataFactory.UPDATED_FIRSTNAME);
        managedUserVM.setLastName(DataFactory.UPDATED_LASTNAME);
        managedUserVM.setEmail(DataFactory.UPDATED_EMAIL);
        managedUserVM.setActivated(updatedUser.getActivated());
        managedUserVM.setImageUrl(DataFactory.UPDATED_IMAGEURL);
        managedUserVM.setLangKey(DataFactory.UPDATED_LANGKEY);
        managedUserVM.setCreatedBy(updatedUser.getCreatedBy());
        managedUserVM.setCreatedDate(updatedUser.getCreatedDate());
        managedUserVM.setLastModifiedBy(updatedUser.getLastModifiedBy());
        managedUserVM.setLastModifiedDate(updatedUser.getLastModifiedDate());
        managedUserVM.setAuthorities(Collections.singleton(RoleManager.USER));

        securityRestMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(managedUserVM)).with(csrf()))
            .andExpect(status().is(403));
    }

    @Test
    @Transactional
    public void deleteUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Delete the user
        restUserMockMvc.perform(delete("/api/users/{login}", user.getLogin())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        assertThat(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).get(user.getLogin())).isNull();

        // Validate the database is empty
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.MANAGER, RoleManager.USER, RoleManager.PARTICIPANT})
    public void unauthorizedDeleteUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);
        int databaseSizeBeforeDelete = userRepository.findAll().size();

        // Delete the user
        securityRestMvc.perform(delete("/api/users/{login}", user.getLogin())
            .accept(TestUtil.APPLICATION_JSON_UTF8).with(csrf()))
            .andExpect(status().is(403));

    }

    @Test
    @Transactional
    public void getAllAuthorities() throws Exception {
        restUserMockMvc.perform(get("/api/users/authorities")
            .accept(TestUtil.APPLICATION_JSON_UTF8)
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").value(hasItems(RoleManager.USER, RoleManager.ADMIN)));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.MANAGER, RoleManager.USER, RoleManager.PARTICIPANT})
    public void unauthorizedGetAllAuthorities() throws Exception {
        securityRestMvc.perform(get("/api/users/authorities")
            .accept(TestUtil.APPLICATION_JSON_UTF8)
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().is(403));
    }

    @Test
    @Transactional
    public void testUserEquals() throws Exception {
        TestUtil.equalsVerifier(User.class);
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(user1.getId());
        assertThat(user1).isEqualTo(user2);
        user2.setId(2L);
        assertThat(user1).isNotEqualTo(user2);
        user1.setId(null);
        assertThat(user1).isNotEqualTo(user2);
    }

    @Test
    @Transactional
    public void testUserDTOtoUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(DataFactory.DEFAULT_ID);
        userDTO.setLogin(DEFAULT_LOGIN);
        userDTO.setFirstName(DataFactory.DEFAULT_FIRSTNAME);
        userDTO.setLastName(DataFactory.DEFAULT_LASTNAME);
        userDTO.setEmail(DataFactory.DEFAULT_EMAIL);
        userDTO.setActivated(true);
        userDTO.setImageUrl(DataFactory.DEFAULT_IMAGEURL);
        userDTO.setLangKey(DataFactory.DEFAULT_LANGKEY);
        userDTO.setCreatedBy(DEFAULT_LOGIN);
        userDTO.setLastModifiedBy(DEFAULT_LOGIN);
        userDTO.setAuthorities(Collections.singleton(RoleManager.USER));

        User user = userMapper.userDTOToUser(userDTO);
        assertThat(user.getId()).isEqualTo(DataFactory.DEFAULT_ID);
        assertThat(user.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(user.getFirstName()).isEqualTo(DataFactory.DEFAULT_FIRSTNAME);
        assertThat(user.getLastName()).isEqualTo(DataFactory.DEFAULT_LASTNAME);
        assertThat(user.getEmail()).isEqualTo(DataFactory.DEFAULT_EMAIL);
        assertThat(user.getActivated()).isEqualTo(true);
        assertThat(user.getImageUrl()).isEqualTo(DataFactory.DEFAULT_IMAGEURL);
        assertThat(user.getLangKey()).isEqualTo(DataFactory.DEFAULT_LANGKEY);
        assertThat(user.getCreatedBy()).isNull();
        assertThat(user.getCreatedDate()).isNotNull();
        assertThat(user.getLastModifiedBy()).isNull();
        assertThat(user.getLastModifiedDate()).isNotNull();
        assertThat(user.getAuthorities()).extracting("name").containsExactly(RoleManager.USER);
    }

    @Test
    @Transactional
    public void testUserToUserDTO() {
        user.setId(DataFactory.DEFAULT_ID);
        user.setCreatedBy(DEFAULT_LOGIN);
        user.setCreatedDate(Instant.now());
        user.setLastModifiedBy(DEFAULT_LOGIN);
        user.setLastModifiedDate(Instant.now());
        Set<Authority> authorities = new HashSet<>();
        Authority authority = new Authority();
        authority.setName(RoleManager.USER);
        authorities.add(authority);
        user.setAuthorities(authorities);

        UserDTO userDTO = userMapper.userToUserDTO(user);

        assertThat(userDTO.getId()).isEqualTo(DataFactory.DEFAULT_ID);
        assertThat(userDTO.getLogin()).isEqualTo(user.getLogin());
        assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userDTO.getLastName()).isEqualTo(user.getLastName());
        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDTO.isActivated()).isEqualTo(true);
        assertThat(userDTO.getImageUrl()).isEqualTo(user.getImageUrl());
        assertThat(userDTO.getLangKey()).isEqualTo(user.getLangKey());
        assertThat(userDTO.getCreatedBy()).isEqualTo(user.getCreatedBy());
        assertThat(userDTO.getCreatedDate()).isEqualTo(user.getCreatedDate());
        assertThat(userDTO.getLastModifiedBy()).isEqualTo(user.getLastModifiedBy());
        assertThat(userDTO.getLastModifiedDate()).isEqualTo(user.getLastModifiedDate());
        assertThat(userDTO.getAuthorities()).containsExactly(RoleManager.USER);
        assertThat(userDTO.toString()).isNotNull();
    }

    @Test
    @Transactional
    public void testAuthorityEquals() {
        Authority authorityA = new Authority();
        assertThat(authorityA).isEqualTo(authorityA);
        assertThat(authorityA).isNotEqualTo(null);
        assertThat(authorityA).isNotEqualTo(new Object());
        assertThat(authorityA.hashCode()).isEqualTo(0);
        assertThat(authorityA.toString()).isNotNull();

        Authority authorityB = new Authority();
        assertThat(authorityA).isEqualTo(authorityB);

        authorityB.setName(RoleManager.ADMIN);
        assertThat(authorityA).isNotEqualTo(authorityB);

        authorityA.setName(RoleManager.USER);
        assertThat(authorityA).isNotEqualTo(authorityB);

        authorityB.setName(RoleManager.USER);
        assertThat(authorityA).isEqualTo(authorityB);
        assertThat(authorityA.hashCode()).isEqualTo(authorityB.hashCode());
    }
}
