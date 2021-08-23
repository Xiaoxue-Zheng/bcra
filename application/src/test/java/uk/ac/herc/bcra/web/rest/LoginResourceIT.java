package uk.ac.herc.bcra.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.MailService;
import uk.ac.herc.bcra.service.TwoFactorAuthenticationService;
import uk.ac.herc.bcra.service.dto.TwoFactorLoginInitDTO;
import uk.ac.herc.bcra.testutils.JhiUserUtil;
import uk.ac.herc.bcra.testutils.MockMvcUtil;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;

import javax.persistence.EntityManager;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BcraApp.class)
public class LoginResourceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TwoFactorAuthenticationService twoFactorAuthenticationService;

    @Mock
    private MailService mailService;

    @Autowired
    private HttpMessageConverter<?>[] httpMessageConverters;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext context;


    private MockMvc mvc;

    private MockMvc securityRestMvc;

    @BeforeEach
    public void before(){
        MockitoAnnotations.initMocks(this);
        doNothing().when(mailService).sendEmail(anyString(), anyString(), anyString(), anyBoolean(), anyBoolean());

        LoginResource loginResource = new LoginResource(userRepository, twoFactorAuthenticationService, authenticationManager);
        this.mvc = MockMvcBuilders.standaloneSetup(loginResource)
            .setMessageConverters(httpMessageConverters)
            .setControllerAdvice(exceptionTranslator)
            .build();
        this.securityRestMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void testLoginNotFound() throws Exception {
        String login = "AuthenticationResourceT.testLoginNotFound".toLowerCase(Locale.ROOT);
        String rawPassword = "password";
        TwoFactorLoginInitDTO dto = new TwoFactorLoginInitDTO(login, rawPassword);
        securityRestMvc.perform(post("/api/login/two-factor-init")
            .with(csrf())
            .content(MockMvcUtil.convertObjectToJsonBytes(dto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void testWrongPassword() throws Exception {
        String login = "AuthenticationResourceT.testWrongPassword".toLowerCase(Locale.ROOT);
        String rawPassword = "password";
        String password = passwordEncoder.encode(rawPassword);
        JhiUserUtil.createUser(em, login, password, RoleManager.ADMIN);
        TwoFactorLoginInitDTO dto = new TwoFactorLoginInitDTO(login, "wrongPassword");
        mvc.perform(post("/api/login/two-factor-init")
            .with(csrf())
            .content(MockMvcUtil.convertObjectToJsonBytes(dto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    public void testNotAdminTwoFactorInit() throws Exception {
        String login = "AuthenticationResourceT.testNotAdminTwoFactorInit".toLowerCase(Locale.ROOT);
        String rawPassword = "password";
        String password = passwordEncoder.encode(rawPassword);
        JhiUserUtil.createUser(em, login, password, RoleManager.USER);
        TwoFactorLoginInitDTO dto = new TwoFactorLoginInitDTO(login, rawPassword);
        mvc.perform(post("/api/login/two-factor-init")
            .with(csrf())
            .content(MockMvcUtil.convertObjectToJsonBytes(dto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
    }

    @Test
    @Transactional
    public void testAdminTwoFactorInit() throws Exception {
        String login = "AuthenticationResourceT.testAdminTwoFactorInit".toLowerCase(Locale.ROOT);
        String rawPassword = "password";
        String password = passwordEncoder.encode(rawPassword);
        JhiUserUtil.createUser(em, login, password, RoleManager.ADMIN);
        TwoFactorLoginInitDTO dto = new TwoFactorLoginInitDTO(login, rawPassword);
        securityRestMvc.perform(post("/api/login/two-factor-init")
            .with(csrf())
            .content(MockMvcUtil.convertObjectToJsonBytes(dto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string("true"));

    }
}
