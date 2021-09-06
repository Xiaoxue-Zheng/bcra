package uk.ac.herc.bcra.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.CanRiskReport;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.CanRiskReportRepository;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.service.CanRiskReportService;
import uk.ac.herc.bcra.service.StudyIdService;
import uk.ac.herc.bcra.service.UserService;
import uk.ac.herc.bcra.service.dto.UserDTO;
import uk.ac.herc.bcra.service.util.OSValidator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = BcraApp.class)
@Transactional
public class CanRiskReportResourceIT {

    @Autowired
    private CanRiskReportService canRiskReportService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudyIdService studyIdService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private CanRiskReportRepository canRiskReportRepository;

    @Autowired
    @Qualifier("mvcConversionService")
    private FormattingConversionService formattingConversionService;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAuditMockMvc;

    private User adminUser;
    private User managerUser;
    private User participantUser;

    private CanRiskReport canRiskReport;

    private String[] studyCodes = {"TST_1", "TST_2", "TST_3"};

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CanRiskReportResource canRiskReportResource = new CanRiskReportResource(canRiskReportService, userService, studyIdService, canRiskReportRepository);
        this.restAuditMockMvc = MockMvcBuilders.standaloneSetup(canRiskReportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setConversionService(formattingConversionService)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @BeforeEach
    public void initTest() throws Exception {
        for (String code : studyCodes) {
            studyIdService.createStudyIdFromCode(code);
        }

        final PageRequest pageable = PageRequest.of(0, (int) userRepository.count());
        final List<UserDTO> allManagedUsers = userService.getAllManagedUsers(pageable).getContent();
        for(UserDTO user : allManagedUsers) {
            if (user.getAuthorities().contains("ROLE_ADMIN") && adminUser == null) {
                adminUser = userRepository.getOne(user.getId());
            } else if (user.getAuthorities().contains("ROLE_MANAGER") && managerUser == null) {
                managerUser = userRepository.getOne(user.getId());
            } else if (user.getAuthorities().contains("ROLE_PARTICIPANT") && participantUser == null) {
                participantUser = userRepository.getOne(user.getId());
            }
        }

        String filename = studyCodes[0] + ".pdf";
        MultipartFile file = new MockMultipartFile(filename, filename, "text/plain", new byte[0]);
        canRiskReportService.createCanRiskReportFromUserAndFile(adminUser, file);
        canRiskReport = canRiskReportService.findAll().get(0);
    }

    @Test
    @Transactional
    public void getCanRiskReportById() throws Exception {
        restAuditMockMvc.perform(get("/api/can-risk-report/")
            .param("canRiskReportId", canRiskReport.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.filename").value(canRiskReport.getFilename()))
            .andExpect(jsonPath("$.studyId").value(canRiskReport.getAssociatedStudyId().getCode()))
            .andExpect(jsonPath("$.uploadedBy").value(canRiskReport.getUploadedBy().getLogin()));
    }

    @Test
    @Transactional
    public void countCanRiskReports() throws Exception {
        MvcResult result = restAuditMockMvc.perform(get("/api/can-risk-reports/count"))
            .andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("1");
    }

    @Test
    @Transactional
    public void getAllCanRiskReports() throws Exception {
        restAuditMockMvc.perform(get("/api/can-risk-reports/")
            .param("page", "0")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(canRiskReport.getFilename())));
    }

    @Test
    @Transactional
    public void uploadCanRiskReports() throws Exception {
        String filename = studyCodes[1] + ".pdf";
        MockMultipartFile file = new MockMultipartFile("file", filename, MediaType.MULTIPART_FORM_DATA_VALUE, filename.getBytes());

        restAuditMockMvc.perform(multipart("/api/can-risk-reports/")
            .file(file)
            .principal(new Principal() {
                @Override
                public String getName() {
                    return adminUser.getLogin();
                }
            }))
            .andExpect(status().isOk());

        List<CanRiskReport> canRiskReports = canRiskReportService.findAll();
        assertThat(canRiskReports.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void canUseStudyIdAvailable() throws Exception {
        String newStudyCode = "NOT_IN_USE_STUDY_CODE";
        studyIdService.createStudyIdFromCode(newStudyCode);
        MvcResult result = restAuditMockMvc.perform(get("/api/can-risk-reports/study-id")
            .param("studyCode", newStudyCode))
            .andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("true");
    }

    @Test
    @Transactional
    public void canUseStudyIdNoSuchStudyId() throws Exception {
        MvcResult result = restAuditMockMvc.perform(get("/api/can-risk-reports/study-id")
            .param("studyCode", "NO_SUCH_STUDY_CODE"))
            .andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("false");
    }

    @Test
    @Transactional
    public void canUseStudyIdAlreadyInUse() throws Exception {
        // TST_1 is used by default canRiskReport and so cannot be used.
        MvcResult result = restAuditMockMvc.perform(get("/api/can-risk-reports/study-id")
            .param("studyCode", canRiskReport.getAssociatedStudyId().getCode()))
            .andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo("false");
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
