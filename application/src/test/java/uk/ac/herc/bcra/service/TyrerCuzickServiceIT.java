package uk.ac.herc.bcra.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Answer;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.AnswerItem;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Procedure;
import uk.ac.herc.bcra.domain.Question;
import uk.ac.herc.bcra.domain.QuestionGroup;
import uk.ac.herc.bcra.domain.QuestionItem;
import uk.ac.herc.bcra.domain.QuestionSection;
import uk.ac.herc.bcra.domain.Questionnaire;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.domain.enumeration.QuestionGroupIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
import uk.ac.herc.bcra.questionnaire.AnswerResponseGenerator;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.RiskAssessmentResultRepository;
import uk.ac.herc.bcra.web.rest.TestUtil;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootTest(classes = BcraApp.class)
public class TyrerCuzickServiceIT {

    private static String USER_IDENTIFIER = "TST_123";
    private static String TEST_DIRECTORY;
    private static String RESOURCES_DIRECTORY;

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

        readQuestionnaireDataFromCsv();

        AnswerResponse cr = answerResponseGenerator.generateAnswerResponseToQuestionnaire(QuestionnaireType.CONSENT_FORM);
        AnswerResponse rar = answerResponseGenerator.generateAnswerResponseToQuestionnaire(QuestionnaireType.RISK_ASSESSMENT);
        rar.setState(ResponseState.VALIDATED);
        answerQuestionnaire(rar);

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

    private void readQuestionnaireDataFromCsv() {
        String currentDir = System.getProperty("user.dir");
        currentDir = currentDir.replace('\\', '/');
        RESOURCES_DIRECTORY = currentDir + "/src/test/resources/";

        readQuestionGroupsFromCsv();
        readQuestionnairesFromCsv();
        readQuestionsFromCsv();
        readQuestionItemsFromCsv();
        readQuestionSectionsFromCsv();
    }

    private void readQuestionGroupsFromCsv() {
        List<String> groupsData = readLinesFromCsv(RESOURCES_DIRECTORY + "csv/question_group.csv");

        for (String groupData : groupsData.subList(1, groupsData.size())) {
            String[] data = groupData.split(";");
            QuestionGroup g = new QuestionGroup();
            g.setIdentifier(QuestionGroupIdentifier.valueOf(data[1]));
            
            em.persist(g);
        }
        em.flush();
    }

    private void readQuestionnairesFromCsv() {
        List<String> questionnairesData = readLinesFromCsv(RESOURCES_DIRECTORY + "csv/questionnaire.csv");

        for (String questionnaireData : questionnairesData.subList(1, questionnairesData.size())) {
            String[] data = questionnaireData.split(";");
            Questionnaire q = new Questionnaire();
            q.setType(QuestionnaireType.valueOf(data[1]));
            q.setVersion(parseIntFromString(data[2]));
            
            em.persist(q);
        }
        em.flush();
    }

    private void readQuestionsFromCsv() {
        List<QuestionGroup> questionGroups = TestUtil.findAll(em, QuestionGroup.class);

        List<String> questionsData = readLinesFromCsv(RESOURCES_DIRECTORY + "csv/question.csv");
        for (String questionData : questionsData.subList(1, questionsData.size())) {
            String[] data = questionData.split(";", -1);
            Question q = new Question();
            q.setIdentifier(QuestionIdentifier.valueOf(data[1]));
            q.setType(QuestionType.valueOf(data[2]));
            q.setOrder(parseIntFromString(data[3]));
            q.setMinimum(parseIntFromString(data[4]));
            q.setMaximum(parseIntFromString(data[5]));
            q.setQuestionGroup(questionGroups.get(Integer.parseInt(data[6])-1));
            q.setVariableName(data[7]);
            q.setText(data[8]);
            q.setHint(data[9]);
            q.setHintText(data[10]);
            q.getQuestionGroup().addQuestion(q);
            
            em.persist(q);
        }

        em.flush();
    }

    private void readQuestionItemsFromCsv() {
        List<Question> questions = TestUtil.findAll(em, Question.class);

        List<String> questionItemsData = readLinesFromCsv(RESOURCES_DIRECTORY + "csv/question_item.csv");
        for (String questionItemData : questionItemsData.subList(1, questionItemsData.size())) {
            String[] data = questionItemData.split(";", -1);
            QuestionItem qi = new QuestionItem();
            qi.setIdentifier(QuestionItemIdentifier.valueOf(data[1]));
            qi.setQuestion(questions.get(Integer.parseInt(data[2])-1));
            qi.setOrder(parseIntFromString(data[3]));
            qi.setLabel(data[4]);
            qi.setNecessary(Boolean.parseBoolean(data[5]));
            qi.setExclusive(Boolean.parseBoolean(data[6]));
            qi.getQuestion().addQuestionItem(qi);
            
            em.persist(qi);
        }

        em.flush();
    }

    private void readQuestionSectionsFromCsv() {
        List<QuestionGroup> questionGroups = TestUtil.findAll(em, QuestionGroup.class);
        List<Questionnaire> questionnaires = TestUtil.findAll(em, Questionnaire.class);

        List<String> questionSectionsData = readLinesFromCsv(RESOURCES_DIRECTORY + "csv/question_section.csv");
        for (String questionSectionData : questionSectionsData.subList(1, questionSectionsData.size())) {
            String[] data = questionSectionData.split(";");
            QuestionSection qs = new QuestionSection();
            qs.setIdentifier(QuestionSectionIdentifier.valueOf(data[1]));
            qs.setTitle(data[2]);
            qs.setOrder(parseIntFromString(data[3]));
            qs.setQuestionnaire(questionnaires.get(Integer.parseInt(data[4])-1));
            qs.setQuestionGroup(questionGroups.get(Integer.parseInt(data[5])-1));
            qs.setUrl(data[6]);
            qs.setProgress(parseIntFromString(data[7]));
            qs.getQuestionnaire().addQuestionSection(qs);
            
            em.persist(qs);
        }

        em.flush();
    }

    private Integer parseIntFromString(String str) {
        if (str.length() == 0) {
            return 0;
        } else {
            return Integer.parseInt(str);
        }
    }

    private void answerQuestionnaire(AnswerResponse res) {
        for (AnswerSection as : res.getAnswerSections()) {
            for (AnswerGroup ag : as.getAnswerGroups()) {
                for (Answer a : ag.getAnswers()) {
                    a.setTicked(true);
                    a.setNumber(3);
                    for (AnswerItem ai : a.getAnswerItems()) {
                        ai.setSelected(true);
                    }
                }
            }
        }
    }

    private List<String> readLinesFromCsv(String csvPath) {
        List<String> lines = new ArrayList<String>();

        try{
            File myObj = new File(csvPath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                lines.add(line);
            }
            myReader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        return lines;
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

    private int countFilesInDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        return dir.listFiles().length;
    }

    @Test
    @Transactional
    public void writeValidatedAnswerResponsesToFile() {
        int totalBeforeTC = countFilesInDirectory(TEST_DIRECTORY + "/input/");
        tyrerCuzickService.writeValidatedAnswerResponsesToFile();
        int totalAfterTC = countFilesInDirectory(TEST_DIRECTORY + "/input/");

        assertThat(totalAfterTC).isEqualTo(totalBeforeTC + 1);
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
