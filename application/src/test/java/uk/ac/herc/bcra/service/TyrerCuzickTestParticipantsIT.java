package uk.ac.herc.bcra.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.algorithm.AlgorithmException;
import uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8.Mapper;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.service.util.OSValidator;
import uk.ac.herc.bcra.service.util.TyrerCuzickPathUtil;
import uk.ac.herc.bcra.testutils.QuestionnaireUtil;
import uk.ac.herc.bcra.testutils.StudyUtil;
import uk.ac.herc.bcra.testutils.TyrerCuzickTestFilesUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BcraApp.class)
public class TyrerCuzickTestParticipantsIT {
    private ArrayList<Participant> testParticipants;

    @Autowired
    private EntityManager em;

    @Autowired
    private StudyUtil studyUtil;

    @Autowired
    private QuestionnaireUtil questionnaireUtil;

    @Autowired
    private TyrerCuzickService tyrerCuzickService;

    @BeforeEach
    public void setup() throws Exception {
        clearDatabase();
        createTestParticipants();
        answerParticipantQuestionnaires();
        configureTyrerCuzickService();
    }

    private void createTestParticipants() {
        /*
            The following participants reflect test participant information that
            was provided by the clinical team working on the HRYWS project.
            The participant information provided can be found in the following
            directory in this project:
                <PROJECT_ROOT>/data/test-participant-information/
        */
        testParticipants = new ArrayList<Participant>();
        testParticipants.add(createTestParticipant("TST_1", 35));
        testParticipants.add(createTestParticipant("TST_2", 35));
        testParticipants.add(createTestParticipant("TST_3", 30));
        testParticipants.add(createTestParticipant("TST_4", 33));
        testParticipants.add(createTestParticipant("TST_5", 36));
        testParticipants.add(createTestParticipant("TST_6", 32));
        testParticipants.add(createTestParticipant("TST_7", 39));
        testParticipants.add(createTestParticipant("TST_8", 39));
        testParticipants.add(createTestParticipant("TST_9", 35));
        testParticipants.add(createTestParticipant("TST_10", 39));
    }

    private Participant createTestParticipant(String identifier, int age) {
        LocalDate dateOfBirth = LocalDate.now().minusYears(age);
        return studyUtil.createParticipant(em, identifier, dateOfBirth);
    }

    private void answerParticipantQuestionnaires() {
        Participant tst1 = testParticipants.get(0);
        AnswerResponse rar1 = tst1.getStudyId().getRiskAssessmentResponse();
        questionnaireUtil.selectFamilyBreastAffected(em, rar1, QuestionnaireUtil.AUNT, true, 55);
        questionnaireUtil.selectFamilyMembersAffected(em, rar1, QuestionnaireUtil.AUNT, true);

        questionnaireUtil.setHeightAndWeight(em, rar1, 170, 60);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar1, 12);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar1, 0, 0);
        questionnaireUtil.setPremenopauseStatus(em, rar1, QuestionnaireUtil.YES, 0);
        questionnaireUtil.setIsAshkenaziJewish(em, rar1, QuestionnaireUtil.NO);
        questionnaireUtil.setBreastBiopsyStatus(em, rar1, QuestionnaireUtil.NO, "doesn't matter", "doesn't matter");

        Participant tst2 = testParticipants.get(1);
        AnswerResponse rar2 = tst2.getStudyId().getRiskAssessmentResponse();

        questionnaireUtil.setHeightAndWeight(em, rar2, 160, 60);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar2, 12);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar2, 1, 33);
        questionnaireUtil.setPremenopauseStatus(em, rar2, QuestionnaireUtil.YES, 0);
        questionnaireUtil.setIsAshkenaziJewish(em, rar2, QuestionnaireUtil.YES);
        questionnaireUtil.setBreastBiopsyStatus(em, rar2, QuestionnaireUtil.NO, "doesn't matter", "doesn't matter");

        Participant tst3 = testParticipants.get(2);
        AnswerResponse rar3 = tst3.getStudyId().getRiskAssessmentResponse();
        questionnaireUtil.selectFamilyBreastAffected(em, rar3, QuestionnaireUtil.GRANDMOTHER, true, 50);
        questionnaireUtil.selectFamilyMembersAffected(em, rar3, QuestionnaireUtil.GRANDMOTHER, true);

        questionnaireUtil.setHeightAndWeight(em, rar3, 150, 60);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar3, 11);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar3, 1, 23);
        questionnaireUtil.setPremenopauseStatus(em, rar3, QuestionnaireUtil.YES, 0);
        questionnaireUtil.setIsAshkenaziJewish(em, rar3, QuestionnaireUtil.YES);
        questionnaireUtil.setBreastBiopsyStatus(em, rar3, QuestionnaireUtil.NO, "doesn't matter", "doesn't matter");

        Participant tst4 = testParticipants.get(3);
        AnswerResponse rar4 = tst4.getStudyId().getRiskAssessmentResponse();
        questionnaireUtil.selectFamilyBreastAffected(em, rar4, QuestionnaireUtil.GRANDMOTHER, true, 50);
        questionnaireUtil.selectFamilyMembersAffected(em, rar4, QuestionnaireUtil.GRANDMOTHER, true);

        questionnaireUtil.setHeightAndWeight(em, rar4, 160, 60);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar4, 12);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar4, 0, 0);
        questionnaireUtil.setPremenopauseStatus(em, rar4, QuestionnaireUtil.YES, 0);
        questionnaireUtil.setIsAshkenaziJewish(em, rar4, QuestionnaireUtil.NO);
        questionnaireUtil.setBreastBiopsyStatus(em, rar4, QuestionnaireUtil.YES, QuestionnaireUtil.YES, QuestionnaireUtil.DONT_KNOW);

        Participant tst5 = testParticipants.get(4);
        AnswerResponse rar5 = tst5.getStudyId().getRiskAssessmentResponse();
        questionnaireUtil.selectFamilyBreastAffected(em, rar5, QuestionnaireUtil.MOTHER, true, 40);
        questionnaireUtil.selectFamilyBreastAffected(em, rar5, QuestionnaireUtil.GRANDMOTHER, true, 50);
        questionnaireUtil.selectFamilyMembersAffected(em, rar5, QuestionnaireUtil.GRANDMOTHER, true);

        questionnaireUtil.setHeightAndWeight(em, rar5, 160, 60);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar5, 12);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar5, 1, 30);
        questionnaireUtil.setPremenopauseStatus(em, rar5, QuestionnaireUtil.YES, 0);
        questionnaireUtil.setIsAshkenaziJewish(em, rar5, QuestionnaireUtil.NO);
        questionnaireUtil.setBreastBiopsyStatus(em, rar5, QuestionnaireUtil.NO, "doesn't matter", "doesn't matter");

        Participant tst6 = testParticipants.get(5);
        AnswerResponse rar6 = tst6.getStudyId().getRiskAssessmentResponse();

        questionnaireUtil.setHeightAndWeight(em, rar6, 160, 65);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar6, 12);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar6, 0, 0);
        questionnaireUtil.setPremenopauseStatus(em, rar6, QuestionnaireUtil.NO, 30);
        questionnaireUtil.setIsAshkenaziJewish(em, rar6, QuestionnaireUtil.NO);
        questionnaireUtil.setBreastBiopsyStatus(em, rar6, QuestionnaireUtil.NO, "doesn't matter", "doesn't matter");

        Participant tst7 = testParticipants.get(6);
        AnswerResponse rar7 = tst7.getStudyId().getRiskAssessmentResponse();
        questionnaireUtil.selectFamilyBreastAffected(em, rar7, QuestionnaireUtil.AUNT, true, 40);
        questionnaireUtil.selectFamilyMembersAffected(em, rar7, QuestionnaireUtil.AUNT, true);

        questionnaireUtil.setHeightAndWeight(em, rar7, 160, 65);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar7, 12);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar7, 1, 18);
        questionnaireUtil.setPremenopauseStatus(em, rar7, QuestionnaireUtil.YES, 0);
        questionnaireUtil.setIsAshkenaziJewish(em, rar7, QuestionnaireUtil.NO);
        questionnaireUtil.setBreastBiopsyStatus(em, rar7, QuestionnaireUtil.NO, "doesn't matter", "doesn't matter");

        Participant tst8 = testParticipants.get(7);
        AnswerResponse rar8 = tst8.getStudyId().getRiskAssessmentResponse();

        questionnaireUtil.setHeightAndWeight(em, rar8, 160, 65);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar8, 15);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar8, 1, 25);
        questionnaireUtil.setPremenopauseStatus(em, rar8, QuestionnaireUtil.YES, 0);
        questionnaireUtil.setIsAshkenaziJewish(em, rar8, QuestionnaireUtil.NO);
        questionnaireUtil.setBreastBiopsyStatus(em, rar8, QuestionnaireUtil.YES, QuestionnaireUtil.YES, QuestionnaireUtil.DIAG_ALH);

        Participant tst9 = testParticipants.get(8);
        AnswerResponse rar9 = tst9.getStudyId().getRiskAssessmentResponse();
        questionnaireUtil.selectFamilyBreastAffected(em, rar9, QuestionnaireUtil.AUNT, true, 0);
        questionnaireUtil.selectFamilyMembersAffected(em, rar9, QuestionnaireUtil.AUNT, false);

        questionnaireUtil.setHeightAndWeight(em, rar9, 170, 60);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar9, 13);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar9, 0, 0);
        questionnaireUtil.setPremenopauseStatus(em, rar9, QuestionnaireUtil.YES, 0);
        questionnaireUtil.setIsAshkenaziJewish(em, rar9, QuestionnaireUtil.NO);
        questionnaireUtil.setBreastBiopsyStatus(em, rar9, QuestionnaireUtil.NO, "doesn't matter", "doesn't matter");

        Participant tst10 = testParticipants.get(9);
        AnswerResponse rar10 = tst10.getStudyId().getRiskAssessmentResponse();
        questionnaireUtil.selectFamilyOvarianAffected(em, rar10, QuestionnaireUtil.GRANDMOTHER, true, 50);
        questionnaireUtil.selectFamilyMembersAffected(em, rar10, QuestionnaireUtil.GRANDMOTHER, true);

        questionnaireUtil.setHeightAndWeight(em, rar10, 170, 60);
        questionnaireUtil.setAgeOfFirstPeriod(em, rar10, 12);
        questionnaireUtil.setThirdTrimesterPregnancies(em, rar10, 1, 30);
        questionnaireUtil.setPremenopauseStatus(em, rar10, QuestionnaireUtil.YES, 0);
        questionnaireUtil.setIsAshkenaziJewish(em, rar10, QuestionnaireUtil.YES);
        questionnaireUtil.setBreastBiopsyStatus(em, rar10, QuestionnaireUtil.NO, "doesn't matter", "doesn't matter");
    }

    private void clearDatabase() {
        em.clear();
        em.flush();
    }

    private void configureTyrerCuzickService() throws Exception {
        String testDir = TyrerCuzickTestFilesUtil.getTestDirectory();
        TyrerCuzickService.TC_EXECUTABLE_COMMAND = TyrerCuzickPathUtil.getTyrerCuzickCommand();
        if (OSValidator.isUnix()) {
            TyrerCuzickService.TC_EXECUTABLE_COMMAND = TyrerCuzickService.TC_EXECUTABLE_COMMAND.replace("/home/tyrercuzick", testDir);
        }

        TyrerCuzickService.TC_INPUT_FILE_LOCATION = testDir + "/input/";
        TyrerCuzickService.TC_OUTPUT_FILE_LOCATION = testDir + "/output/";
    }

    @Test
    @Transactional
    public void assertThatEachTestParticipantProducesCorrectAlgorithmOutput() {
        Mapper mapper = new Mapper();

        for (Participant participant : testParticipants) {
            String identifier = participant.getUser().getLogin();

            LocalDate dateOfBirth = participant.getIdentifiableData().getDateOfBirth();
            AnswerResponse response = participant.getStudyId().getRiskAssessmentResponse();

            if (!identifier.equals("TST_5")) {
                String expectedOutput = getExpectedAlgorithmOutputForParticipant(identifier);
                String actualOutput = mapper.map(identifier, dateOfBirth, response);
                assertThat(actualOutput).isEqualTo(expectedOutput);
            } else {
                assertThrows(AlgorithmException.class, () -> {
                    mapper.map(identifier, dateOfBirth, response);
                });
            }
        }
    }

    private String getExpectedAlgorithmOutputForParticipant(String identifier) {
        String testDir = TyrerCuzickTestFilesUtil.getTestDirectory();
        return readFile(testDir + "/test_cases/algorithmoutputs/" + identifier + ".txt");
    }

    @Test
    @Transactional
    public void assertThatEachTestParticipantProducesCorrectTyrerCuzickOutput() throws Exception  {
        tyrerCuzickService.writeValidatedAnswerResponsesToFile();
        tyrerCuzickService.runTyrerCuzickExecutable();

        for (Participant participant : testParticipants) {
            String identifier = participant.getUser().getLogin();
            if (identifier.equals("TST_4") || identifier.equals("TST_5")) {
                continue;
            }

            String expectedOutput = getExpectedTyrerCuzickOutputForParticipant(identifier);
            String actualOutput = getTyrerCuzickOutputForParticipant(identifier);

            assertThat(actualOutput).isNotEqualTo("");
            assertThat(actualOutput).isEqualTo(expectedOutput);
        }

        TyrerCuzickTestFilesUtil.cleanUpTcFiles();
    }

    private String getExpectedTyrerCuzickOutputForParticipant(String identifier) {
        String testDir = TyrerCuzickTestFilesUtil.getTestDirectory();
        return readFile(testDir + "/test_cases/tyrercuzickoutputs/" + identifier + ".txt");
    }

    private String getTyrerCuzickOutputForParticipant(String identifier) {
        String testDir = TyrerCuzickTestFilesUtil.getTestDirectory();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        return readFile(testDir + "/output/" + identifier + "-" + dtf.format(now) + ".txt");
    }

    private String readFile(String filePath) {
        File file = new File(filePath);
        String fileData = "";
        Scanner fileReader = null;

        try {
            fileReader = new Scanner(file);
            while(fileReader.hasNextLine()) {
                fileData += fileReader.nextLine() + "\n";
            }
            fileReader.close();

        } catch(Exception ex) {
            // Do nothing
            System.out.println(ex);
        }

        return fileData;
    }
}
