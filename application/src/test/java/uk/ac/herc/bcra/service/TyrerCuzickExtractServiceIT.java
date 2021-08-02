package uk.ac.herc.bcra.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import uk.ac.herc.bcra.BcraApp;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Risk;
import uk.ac.herc.bcra.domain.RiskAssessmentResult;
import uk.ac.herc.bcra.domain.RiskFactor;
import uk.ac.herc.bcra.repository.RiskAssessmentResultRepository;
import uk.ac.herc.bcra.repository.RiskFactorRepository;
import uk.ac.herc.bcra.repository.RiskRepository;
import uk.ac.herc.bcra.service.util.TyrerCuzickPathUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@SpringBootTest(classes = BcraApp.class)
public class TyrerCuzickExtractServiceIT {

    private static String USER_IDENTIFIER_1 = "TST_USR_1";
    private static String USER_IDENTIFIER_2 = "TST_USR_2";
    private static String USER_IDENTIFIER_3 = "TST_USR_3";

    @Autowired
    private EntityManager em;

    @Autowired
    private TyrerCuzickExtractService tyrerCuzickExtractService;

    @Autowired
    private RiskAssessmentResultRepository riskAssessmentResultRepository;

    @Autowired
    private RiskRepository riskRepository;

    @Autowired
    private RiskFactorRepository riskFactorRepository;

    @Autowired
    private StudyUtil studyUtil;

    @BeforeEach
    public void setup() throws Exception {
        clearDatabase();
    }

    private void clearDatabase() {
        em.clear();
        em.flush();
    }

    private Participant createParticipant(String userIdentifier) {
        Participant p = studyUtil.createParticipant(em, userIdentifier, LocalDate.now());
        studyUtil.answerQuestionnaireWithAnything(em, p.getProcedure().getRiskAssessmentResponse());
        return p;
    }

    private RiskAssessmentResult createRiskAssessmentResultForParticipant(Participant participant) {
        RiskAssessmentResult rar = new RiskAssessmentResult();
        String userId = participant.getUser().getLogin();
        rar.setFilename("tcResult" + userId + ".txt");
        rar.setIndividualRisk(createRisk());
        rar.setPopulationRisk(createRisk());
        rar.setParticipant(participant);

        RiskAssessmentResult result = riskAssessmentResultRepository.save(rar);
        return result;
    }

    private Risk createRisk() {
        Random random = new Random();

        Risk risk = new Risk();
        risk.setLifetimeRisk(random.nextDouble());
        risk.setProbBcra1(random.nextDouble());
        risk.setProbBcra2(random.nextDouble());
        risk.setProbNotBcra(random.nextDouble());

        Set<RiskFactor> riskFactors = new HashSet<RiskFactor>();
        for (int i = 0; i < 20; i++) {
            RiskFactor rf = createRiskFactorForRisk(risk);
            riskFactors.add(rf);
        }

        risk.setRiskFactors(riskFactors);

        Risk savedRisk = riskRepository.save(risk);
        return savedRisk;
    }

    private RiskFactor createRiskFactorForRisk(Risk risk) {
        Random random = new Random();

        RiskFactor riskFactor = new RiskFactor();
        riskFactor.setRisk(risk);
        riskFactor.setFactor(random.nextDouble());

        RiskFactor savedRiskFactor = riskFactorRepository.save(riskFactor);
        return savedRiskFactor;
    }

    private void deleteExtractCsvIfExists() throws Exception {
        File csvFile = getExtractCsv();

        if (csvFile.exists()) {
            csvFile.delete();
        }
    }

    private File getExtractCsv() throws Exception {
        String sqlPath = TyrerCuzickPathUtil.getTyrerCuzickExtractSql();
        File sqlFile = new File(sqlPath);
        String csvPath = sqlFile.getParent() + "/tyrer_cuzick_extract.csv";

        return new File(csvPath);
    }

    private ArrayList<String> readFile(File file) throws Exception {
        ArrayList<String> lineData = new ArrayList<String>();
        Scanner myReader = new Scanner(file);
        while (myReader.hasNextLine()) {
            lineData.add(myReader.nextLine());
        }
        myReader.close();

        return lineData;
    }

    @Test
    @Transactional
    public void assertThatEmptyTCExtractIsCreated() throws Exception {
        deleteExtractCsvIfExists();
        tyrerCuzickExtractService.runTyrerCuzickDataExtract();

        File extractFile = getExtractCsv();
        assertThat(extractFile.exists()).isEqualTo(true);

        ArrayList<String> fileData = readFile(extractFile);
        assertThat(fileData.size()).isEqualTo(1); // Size of 1 as first row is column headers.
    }

    @Test
    @Transactional
    public void assertThatTCExtractIsCreatedWithOneRiskAssessmentResult() throws Exception {
        deleteExtractCsvIfExists();
        Participant p = createParticipant(USER_IDENTIFIER_1);
        createRiskAssessmentResultForParticipant(p);

        tyrerCuzickExtractService.runTyrerCuzickDataExtract();

        File extractFile = getExtractCsv();
        assertThat(extractFile.exists()).isEqualTo(true);

        ArrayList<String> fileData = readFile(extractFile);
        assertThat(fileData.size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void assertThatTCExtractIsCreatedWithThreeRiskAssessmentResult() throws Exception {
        deleteExtractCsvIfExists();
        Participant p1 = createParticipant(USER_IDENTIFIER_1);
        createRiskAssessmentResultForParticipant(p1);
        Participant p2 = createParticipant(USER_IDENTIFIER_2);
        createRiskAssessmentResultForParticipant(p2);
        Participant p3 = createParticipant(USER_IDENTIFIER_3);
        createRiskAssessmentResultForParticipant(p3);

        tyrerCuzickExtractService.runTyrerCuzickDataExtract();

        File extractFile = getExtractCsv();
        assertThat(extractFile.exists()).isEqualTo(true);

        ArrayList<String> fileData = readFile(extractFile);
        assertThat(fileData.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    public void assertThatTCExtractDataMatchesOneRiskAssessmentResultData() throws Exception {
        deleteExtractCsvIfExists();
        Participant p = createParticipant(USER_IDENTIFIER_1);
        RiskAssessmentResult rar = createRiskAssessmentResultForParticipant(p);

        tyrerCuzickExtractService.runTyrerCuzickDataExtract();

        File extractFile = getExtractCsv();
        assertThat(extractFile.exists()).isEqualTo(true);

        ArrayList<String> fileData = readFile(extractFile);

        String extractLine = fileData.get(1);
        assertThatRiskAssessmentResultMatchesDataExtract(
            USER_IDENTIFIER_1, rar, extractLine
        );
    }

    @Test
    @Transactional
    public void assertThatTCExtractDataMatchesThreeRiskAssessmentResultData() throws Exception {
        deleteExtractCsvIfExists();
        Participant p1 = createParticipant(USER_IDENTIFIER_1);
        RiskAssessmentResult rar1 = createRiskAssessmentResultForParticipant(p1);
        Participant p2 = createParticipant(USER_IDENTIFIER_2);
        RiskAssessmentResult rar2 = createRiskAssessmentResultForParticipant(p2);
        Participant p3 = createParticipant(USER_IDENTIFIER_3);
        RiskAssessmentResult rar3 = createRiskAssessmentResultForParticipant(p3);

        tyrerCuzickExtractService.runTyrerCuzickDataExtract();

        File extractFile = getExtractCsv();
        assertThat(extractFile.exists()).isEqualTo(true);

        ArrayList<String> fileData = readFile(extractFile);

        String extractLine = fileData.get(1);
        assertThatRiskAssessmentResultMatchesDataExtract(
            USER_IDENTIFIER_1, rar1, extractLine
        );

        extractLine = fileData.get(2);
        assertThatRiskAssessmentResultMatchesDataExtract(
            USER_IDENTIFIER_2, rar2, extractLine
        );

        extractLine = fileData.get(3);
        assertThatRiskAssessmentResultMatchesDataExtract(
            USER_IDENTIFIER_3, rar3, extractLine
        );

    }

    private void assertThatRiskAssessmentResultMatchesDataExtract(String userIdentifier, RiskAssessmentResult rar, String extractLine) {
        String[] extractLineData = extractLine.split(",");

        assertThat(extractLineData[0]).isEqualTo(userIdentifier);

        assertThat(Long.parseLong(extractLineData[1])).isEqualTo(rar.getIndividualRisk().getId());
        assertThat(Double.parseDouble(extractLineData[2])).isEqualTo(rar.getIndividualRisk().getLifetimeRisk());
        assertThat(Double.parseDouble(extractLineData[3])).isEqualTo(rar.getIndividualRisk().getProbNotBcra());
        assertThat(Double.parseDouble(extractLineData[4])).isEqualTo(rar.getIndividualRisk().getProbBcra1());
        assertThat(Double.parseDouble(extractLineData[5])).isEqualTo(rar.getIndividualRisk().getProbBcra2());

        Set<RiskFactor> indiRiskFactors = rar.getIndividualRisk().getRiskFactors();
        for (int riskFactorIndex = 0; riskFactorIndex < 20; riskFactorIndex++) {
            double factor = Double.parseDouble(extractLineData[6 + riskFactorIndex]);
            Optional<RiskFactor> riskFactor = indiRiskFactors.stream().filter(rf -> rf.getFactor().equals(factor)).findFirst();
            assertThat(riskFactor.isPresent()).isEqualTo(true);
        }

        assertThat(Long.parseLong(extractLineData[26])).isEqualTo(rar.getPopulationRisk().getId());
        assertThat(Double.parseDouble(extractLineData[27])).isEqualTo(rar.getPopulationRisk().getLifetimeRisk());
        assertThat(Double.parseDouble(extractLineData[28])).isEqualTo(rar.getPopulationRisk().getProbNotBcra());
        assertThat(Double.parseDouble(extractLineData[29])).isEqualTo(rar.getPopulationRisk().getProbBcra1());
        assertThat(Double.parseDouble(extractLineData[30])).isEqualTo(rar.getPopulationRisk().getProbBcra2());

        Set<RiskFactor> popRiskFactors = rar.getPopulationRisk().getRiskFactors();
        for (int riskFactorIndex = 0; riskFactorIndex < 20; riskFactorIndex++) {
            double factor = Double.parseDouble(extractLineData[31 + riskFactorIndex]);
            Optional<RiskFactor> riskFactor = popRiskFactors.stream().filter(rf -> rf.getFactor().equals(factor)).findFirst();
            assertThat(riskFactor.isPresent()).isEqualTo(true);
        }
    }

}
