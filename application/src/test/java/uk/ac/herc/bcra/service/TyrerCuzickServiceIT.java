package uk.ac.herc.bcra.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.repository.RiskAssessmentResultRepository;
import uk.ac.herc.bcra.service.util.FileSystemUtil;
import uk.ac.herc.bcra.service.util.OSValidator;
import uk.ac.herc.bcra.service.util.TyrerCuzickPathUtil;
import uk.ac.herc.bcra.testutils.StudyUtil;
import uk.ac.herc.bcra.testutils.TyrerCuzickTestFilesUtil;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

@SpringBootTest(classes = BcraApp.class)
public class TyrerCuzickServiceIT {

    private static String USER_IDENTIFIER = "TST_123";

    @Autowired
    private EntityManager em;

    @Autowired
    private TyrerCuzickService tyrerCuzickService;

    @Autowired
    private RiskAssessmentResultRepository riskAssessmentResultRepository;

    @Autowired
    private StudyUtil studyUtil;

    @BeforeEach
    public void setup() throws Exception {
        clearDatabase();
        configureTyrerCuzickService();
        createParticipantAndRiskAssessment();
    }

    private void clearDatabase() {
        em.clear();
        em.flush();
    }

    private void configureTyrerCuzickService() throws Exception {
        String testDirectory = TyrerCuzickTestFilesUtil.getTestDirectory();
        TyrerCuzickService.TC_EXECUTABLE_COMMAND = TyrerCuzickPathUtil.getTyrerCuzickCommand();
        if (OSValidator.isUnix()) {
            TyrerCuzickService.TC_EXECUTABLE_COMMAND = TyrerCuzickService.TC_EXECUTABLE_COMMAND.replace("/home/tyrercuzick", testDirectory);
        }

        TyrerCuzickService.TC_INPUT_FILE_LOCATION = testDirectory + "/input/";
        TyrerCuzickService.TC_OUTPUT_FILE_LOCATION = testDirectory + "/output/";
    }

    private void createParticipantAndRiskAssessment() {
        Participant p = studyUtil.createParticipant(em, USER_IDENTIFIER, LocalDate.now());
        studyUtil.answerQuestionnaireWithAnything(em, p.getProcedure().getRiskAssessmentResponse());
    }

    @Test
    @Transactional
    public void writeValidatedAnswerResponsesToFile() throws Exception {
        String testDirectory = TyrerCuzickTestFilesUtil.getTestDirectory();

        int totalBeforeTC = FileSystemUtil.countFilesInDirectory(testDirectory + "/input/");
        tyrerCuzickService.writeValidatedAnswerResponsesToFile();
        int totalAfterTC = FileSystemUtil.countFilesInDirectory(testDirectory + "/input/");

        assertThat(totalAfterTC).isEqualTo(totalBeforeTC + 1);
        TyrerCuzickTestFilesUtil.cleanUpTcFiles();
    }

    @Test
    @Transactional
    public void runTyrerCuzickExecutable() throws Exception  {
        String testDirectory = TyrerCuzickTestFilesUtil.getTestDirectory();
        TyrerCuzickTestFilesUtil.createTcInputFile();
        tyrerCuzickService.runTyrerCuzickExecutable();
        assertThat(FileSystemUtil.checkFileExists(testDirectory + "/output/TST_IN-2021-01-01.txt")).isTrue();
        TyrerCuzickTestFilesUtil.cleanUpTcFiles();
    }

    @Test
    @Transactional
    public void readTyrerCuzickOutput() throws Exception  {
        TyrerCuzickTestFilesUtil.createTcOutputFile();
        tyrerCuzickService.readTyrerCuzickOutput();
        int total = riskAssessmentResultRepository.findAll().size();
        assertThat(total).isEqualTo(1);
    }
}
