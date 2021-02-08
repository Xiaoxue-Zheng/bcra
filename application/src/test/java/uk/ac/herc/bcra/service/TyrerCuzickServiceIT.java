package uk.ac.herc.bcra.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Procedure;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.questionnaire.AnswerResponseGenerator;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.RiskAssessmentResultRepository;
import uk.ac.herc.bcra.web.rest.QuestionnaireResourceIT;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;

@SpringBootTest(classes = BcraApp.class)
public class TyrerCuzickServiceIT {

    private static String USER_IDENTIFIER = "TST_123";
    private static String TEST_DIRECTORY;

    @Autowired
    private EntityManager em;

    @Autowired
    private TyrerCuzickService tyrerCuzickService;

    @Autowired
    private RiskAssessmentResultRepository riskAssessmentResultRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private AnswerResponseGenerator answerResponseGenerator;

    @BeforeEach
    public void setup() {
        clearDatabase();
        configureTyrerCuzickService();
        createEntities();
    }

    private void clearDatabase() {
        em.clear();
        em.flush();
    }

    private void createEntities() {
        User u = new User();
        u.setLogin(USER_IDENTIFIER);
        u.setPassword("PASSWORDPASSWORDPASSWORDPASSWORDPASSWORDPASSWORDPASSWORDPASS");
        u.setActivated(true);

        em.persist(u);

        IdentifiableData id = new IdentifiableData();
        id.setNhsNumber("NHS_NUMBER");
        id.setDateOfBirth(LocalDate.now());
        id.setFirstname("FIRSTNAME");
        id.setSurname("SURNAME");
        id.setAddress1("ADDRESS_1");
        id.setPostcode("POSTCODE");
        id.setPracticeName("PRACTICE_NAME");

        em.persist(id);

        Questionnaire cq = QuestionnaireResourceIT.createEntity(em);
        Questionnaire raq = QuestionnaireResourceIT.createUpdatedEntity(em);
    
        em.persist(cq);
        em.persist(raq);

        AnswerResponse cr = answerResponseGenerator.generateAnswerResponseToQuestionnaire(
            QuestionnaireType.CONSENT_FORM
        );

        AnswerResponse rar = answerResponseGenerator.generateAnswerResponseToQuestionnaire(
            QuestionnaireType.RISK_ASSESSMENT
        );

        em.persist(cr);
        em.persist(rar);

        Procedure pr = new Procedure();
        pr.setConsentResponse(cr);
        pr.setRiskAssessmentResponse(rar);

        em.persist(pr);

        Participant p = new Participant();
        p.setUser(u);
        p.setIdentifiableData(id);
        p.setProcedure(pr);
        pr.setParticipant(p);

        participantRepository.save(p);
        em.persist(p);
        em.persist(pr);

        em.flush();
    }

    private void configureTyrerCuzickService() {
        String currentDir = System.getProperty("user.dir");
        currentDir = currentDir.replace('\\', '/');
        TEST_DIRECTORY = currentDir + "/src/test/java/uk/ac/herc/bcra/service/tc_test_files/";
        TyrerCuzickService.TC_EXECUTABLE_FILE_LOCATION = TEST_DIRECTORY + "tyrercuzick.exe";
        TyrerCuzickService.TC_INPUT_FILE_LOCATION = TEST_DIRECTORY + "/input/";
        TyrerCuzickService.TC_OUTPUT_FILE_LOCATION = TEST_DIRECTORY + "/output/";
    }

    private void createTcInputFile() {
        try {
            copyFile(TEST_DIRECTORY + "tc_in.txt", TEST_DIRECTORY + "input/tc_in.txt");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createTcOutputFile() {
        try {
            copyFile(TEST_DIRECTORY + "tc_out.txt", TEST_DIRECTORY + "output/tc_out.txt");
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cleanUpTcFiles() {
        try {
            File tcInputDirectory = new File(TEST_DIRECTORY + "input");
            for (File fileEntry : tcInputDirectory.listFiles()) {
                fileEntry.delete();
            }

            File tcOutputDirectory = new File(TEST_DIRECTORY + "output");
            for (File fileEntry : tcOutputDirectory.listFiles()) {
                fileEntry.delete();
            }
        } catch(Exception ex) {

        }
    }

    private void copyFile(String originalPath, String copyPath) throws Exception {
        try (
        InputStream in = new BufferedInputStream(
            new FileInputStream(originalPath));
        OutputStream out = new BufferedOutputStream(
            new FileOutputStream(copyPath))) {
    
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
    }

    private void checkFileExists(String filepath) {
        File file = new File(filepath);
        assertThat(file.exists()).isTrue();
    }

    //@Test
    //@Transactional
    public void writeValidatedAnswerResponsesToFile() {
        tyrerCuzickService.writeValidatedAnswerResponsesToFile();
        checkFileExists(TEST_DIRECTORY + "/input/tc_in.txt");
    }

    @Test
    @Transactional
    public void runTyrerCuzickExecutable() {
        createTcInputFile();
        tyrerCuzickService.runTyrerCuzickExecutable();
        checkFileExists(TEST_DIRECTORY + "/output/tc_in.txt");
        cleanUpTcFiles();
    }

    @Test
    @Transactional
    public void readTyrerCuzickOutput() {
        createTcOutputFile();

        tyrerCuzickService.readTyrerCuzickOutput();

        int total = riskAssessmentResultRepository.findAll().size();

        assertThat(total).isEqualTo(1);
    }
}
