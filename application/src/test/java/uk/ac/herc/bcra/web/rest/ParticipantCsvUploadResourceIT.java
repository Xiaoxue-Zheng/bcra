package uk.ac.herc.bcra.web.rest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.domain.enumeration.CsvFileState;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.participant.CsvFileProcessor;
import uk.ac.herc.bcra.repository.CsvFileRepository;
import uk.ac.herc.bcra.repository.IdentifiableDataRepository;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.QuestionnaireRepository;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.service.CsvFileService;
import uk.ac.herc.bcra.web.rest.errors.ExceptionTranslator;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static uk.ac.herc.bcra.web.rest.TestUtil.createFormattingConversionService;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootTest(classes = BcraApp.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Configurable
public class ParticipantCsvUploadResourceIT {

    @Autowired
    private CsvFileService csvFileService;

    @Autowired
    private CsvFileProcessor csvFileProcessor;

    @Autowired
    private CsvFileRepository csvFileRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdentifiableDataRepository identifiableDataRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc mockMvc;

    private static final String RESOURCE_PARAMETER_NAME = "file";

    private static final String VALID_TEST_UPLOAD_FILENAME = "TestUploadFilename.csv";
    private final String VALID_TEST_CSV_FILE = "csv/test-participants.csv";
    private static Path validTestCsvUploadPath;
    private static byte[] validTestCsvUploadContent;

    private static final String INVALID_TEST_UPLOAD_FILENAME = "InvalidFilename.csv";
    private static final byte[] invalidTestCsvUploadContent = "a,b,c\n1,2,3".getBytes();

    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        Questionnaire consentQuestionnaire = new Questionnaire()
            .type(QuestionnaireType.CONSENT_FORM)
            .version(1);

        Questionnaire riskAssessmentQuestionnaire = new Questionnaire()
            .type(QuestionnaireType.RISK_ASSESSMENT)
            .version(1);

        questionnaireRepository.saveAndFlush(consentQuestionnaire);
        questionnaireRepository.saveAndFlush(riskAssessmentQuestionnaire);

        final CsvFileResource csvFileResource = new CsvFileResource(csvFileService);
        mockMvc = MockMvcBuilders.standaloneSetup(csvFileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();

        validTestCsvUploadPath = new ClassPathResource(VALID_TEST_CSV_FILE).getFile().toPath();
        validTestCsvUploadContent = Files.readAllBytes(validTestCsvUploadPath);
    }

    @Test
    public void test1FileUploadWorks() throws Exception {
        MockMultipartFile csvFile = new MockMultipartFile(
            RESOURCE_PARAMETER_NAME,
            VALID_TEST_UPLOAD_FILENAME,
            MediaType.MULTIPART_FORM_DATA.getType(),
            validTestCsvUploadContent
        );

        int databaseSizeBeforeCreate = csvFileRepository.findAll().size();

        mockMvc.perform(
                MockMvcRequestBuilders
                .multipart("/api/participant-csv")
                .file(csvFile)
            )
            .andExpect(status().is(200))
            .andExpect(jsonPath("$").value("CREATED"));

        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeCreate + 1);
    }

    @Test
    public void test2DuplicateFileIgnored() throws Exception {
        MockMultipartFile csvFile = new MockMultipartFile(
            RESOURCE_PARAMETER_NAME,
            VALID_TEST_UPLOAD_FILENAME,
            MediaType.MULTIPART_FORM_DATA.getType(),
            validTestCsvUploadContent
        );

        int databaseSizeBeforeCreate = csvFileRepository.findAll().size();

        mockMvc.perform(
            MockMvcRequestBuilders
            .multipart("/api/participant-csv")
            .file(csvFile)
        )
        .andExpect(status().is(200))
        .andExpect(jsonPath("$").value("ALREADY_EXISTS"));

        List<CsvFile> csvFileList = csvFileRepository.findAll();
        assertThat(csvFileList).hasSize(databaseSizeBeforeCreate);            
    }

    @Test
    public void test3ValidCsvFileIsProcessed() throws Exception {
        int initialParticipantCount = participantRepository.findAll().size();
        int initialUserCount = userRepository.findAll().size();
        int initialIdentifiableDataCount = identifiableDataRepository.findAll().size();

        int csvRows = 
            Math.toIntExact(
                Files.lines(
                    validTestCsvUploadPath,
                    Charset.defaultCharset()
                ).count()
            ) - 1;

        csvFileProcessor.processUploadedCsvFiles();

        List<Participant> participants = participantRepository.findAll();
        assertThat(participants).hasSize(initialParticipantCount + csvRows);
        
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(initialUserCount + csvRows);           

        List<IdentifiableData> identifiableDatas = identifiableDataRepository.findAll();
        assertThat(identifiableDatas).hasSize(initialIdentifiableDataCount + csvRows);
        
        CsvFile invalidCsvFile = csvFileRepository.findOneByFileName(VALID_TEST_UPLOAD_FILENAME).get();
        assertThat(invalidCsvFile.getState()).isEqualTo(CsvFileState.COMPLETED);
    }

    @Test
    public void test4InvalidCsvFileIsNotProcessed() throws Exception {
        MockMultipartFile csvFile = new MockMultipartFile(
            RESOURCE_PARAMETER_NAME,
            INVALID_TEST_UPLOAD_FILENAME,
            MediaType.MULTIPART_FORM_DATA.getType(),
            invalidTestCsvUploadContent
        );

        mockMvc.perform(
            MockMvcRequestBuilders
            .multipart("/api/participant-csv")
            .file(csvFile)
        )
        .andExpect(status().is(200))
        .andExpect(jsonPath("$").value("CREATED"));

        int initialParticipantCount = participantRepository.findAll().size();
        int initialUserCount = userRepository.findAll().size();
        int initialIdentifiableDataCount = identifiableDataRepository.findAll().size();

        csvFileProcessor.processUploadedCsvFiles();

        List<Participant> participants = participantRepository.findAll();
        assertThat(participants).hasSize(initialParticipantCount);
        
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(initialUserCount);           

        List<IdentifiableData> identifiableDatas = identifiableDataRepository.findAll();
        assertThat(identifiableDatas).hasSize(initialIdentifiableDataCount);
        
        CsvFile invalidCsvFile = csvFileRepository.findOneByFileName(INVALID_TEST_UPLOAD_FILENAME).get();

        assertThat(invalidCsvFile.getState()).isEqualTo(CsvFileState.INVALID);
    }   
}
