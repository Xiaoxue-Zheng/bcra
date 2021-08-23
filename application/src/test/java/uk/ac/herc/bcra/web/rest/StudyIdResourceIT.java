package uk.ac.herc.bcra.web.rest;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.security.RoleManager;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.testutils.MockMvcUtil;
import uk.ac.herc.bcra.testutils.StudyUtil;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static uk.ac.herc.bcra.testutils.MockMvcUtil.createFormattingConversionService;

import uk.ac.herc.bcra.domain.StudyId;
import uk.ac.herc.bcra.repository.StudyIdRepository;

@SpringBootTest(classes = BcraApp.class)
public class StudyIdResourceIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private StudyIdRepository studyIdRepository;

    @Autowired
    private StudyIdService studyIdService;

    @Autowired
    private StudyUtil studyUtil;

    private StudyId studyId;

    private Participant participant;

    private MockMvc restStudyIdMockMvc;

    private MockMvc securityRestMvc;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudyIdResource studyIdResource = new StudyIdResource(
            studyIdService
        );
        this.restStudyIdMockMvc = MockMvcBuilders
            .standaloneSetup(studyIdResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator)
            .build();
        this.securityRestMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @BeforeEach
    public void initTest() {
        String studyCode = "TST_101";
        participant = studyUtil.createParticipant(em, studyCode, LocalDate.now());
        studyId = studyUtil.createStudyIdForParticipant(em, participant);
    }

    @Test
    @Transactional
    public void isStudyIdInUse() throws Exception {
        // Call to see if study id that doesn't exist is available - should be false
        MvcResult result1 = restStudyIdMockMvc.perform(get("/api/study-ids/TST_DOES_NOT_EXIST"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();

        String content1 = result1.getResponse().getContentAsString();
        assertThat(content1).isEqualTo("false");

        // Add the default study id to the database.
        studyIdRepository.saveAndFlush(studyId);

        // Try call again, this time with a study that exists - should be true.
        MvcResult result2 = restStudyIdMockMvc.perform(get("/api/study-ids/" + studyId.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();

        String content2 = result2.getResponse().getContentAsString();
        assertThat(content2).isEqualTo("true");

        // Add an assigned study id to the database.
        studyId.setParticipant(participant);

        studyIdRepository.saveAndFlush(studyId);

        // Try to call a final time, this time study is assigned - should be false.
        MvcResult result3 = restStudyIdMockMvc.perform(get("/api/study-ids/" + studyId.getCode()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andReturn();

        String content3 = result3.getResponse().getContentAsString();
        assertThat(content3).isEqualTo("false");
    }

    @Test
    @Transactional
    public void getStudyCode() throws Exception {
        MvcResult result = restStudyIdMockMvc.perform(
            get("/api/study-ids/current")
                .principal(new Principal() {
                    @Override
                    public String getName() {
                        return participant.getUser().getLogin();
                    }
                })
            )
            .andExpect(status().isOk())
            .andReturn();

        String returnedStudyCode = result.getResponse().getContentAsString();
        assertThat(returnedStudyCode).isEqualTo("\"" + studyId.getCode() + "\"");
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.PARTICIPANT, RoleManager.USER, RoleManager.MANAGER})
    public void unauthorizedCreateStudyId() throws Exception {
        List<String> studyIdList = new ArrayList<>();
        studyIdList.add("unauthorizedCreateSturdyId");
        securityRestMvc.perform(post("/api/study-ids")
            .contentType(MockMvcUtil.APPLICATION_JSON_UTF8)
            .content(MockMvcUtil.convertObjectToJsonBytes(studyIdList)).with(csrf()))
            .andExpect(status().is(403));
    }

    @Test
    @Transactional
    @WithMockUser(authorities = {RoleManager.PARTICIPANT, RoleManager.USER, RoleManager.MANAGER})
    public void unauthorizedGetAllSturdyId() throws Exception {
        securityRestMvc.perform(get("/api/study-ids")
            .with(csrf()))
            .andExpect(status().is(403));
    }

}
