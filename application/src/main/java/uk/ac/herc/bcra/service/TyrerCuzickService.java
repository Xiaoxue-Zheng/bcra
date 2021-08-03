package uk.ac.herc.bcra.service;

import uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8.Mapper;

import uk.ac.herc.bcra.domain.Procedure;
import uk.ac.herc.bcra.domain.Risk;
import uk.ac.herc.bcra.domain.RiskAssessmentResult;
import uk.ac.herc.bcra.domain.YearlyRisk;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.enumeration.ResponseState;
import uk.ac.herc.bcra.repository.AnswerResponseRepository;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.ProcedureRepository;
import uk.ac.herc.bcra.repository.RiskAssessmentResultRepository;
import uk.ac.herc.bcra.repository.RiskRepository;
import uk.ac.herc.bcra.service.util.TyrerCuzickPathUtil;
import uk.ac.herc.bcra.repository.YearlyRiskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class TyrerCuzickService {
    private final Logger log = LoggerFactory.getLogger(TyrerCuzickService.class);

    public static String TC_EXECUTABLE_COMMAND;
    public static String TC_INPUT_FILE_LOCATION;
    public static String TC_OUTPUT_FILE_LOCATION;

    private AnswerResponseRepository answerResponseRepository;
    private ProcedureRepository procedureRepository;
    private ParticipantRepository participantRepository;
    private RiskAssessmentResultRepository riskAssessmentResultRepository;
    private RiskRepository riskRepository;
    private YearlyRiskRepository yearlyRiskRepository;

    private Mapper mapper;

    public TyrerCuzickService(
        AnswerResponseRepository answerResponseRepository, 
        Mapper mapper, 
        ProcedureRepository procedureRepository, 
        ParticipantRepository participantRepository,
        RiskAssessmentResultRepository riskAssessmentResultRepository, 
        RiskRepository riskRepository,
        YearlyRiskRepository yearlyRiskRepository) {

        this.answerResponseRepository = answerResponseRepository;
        this.mapper = mapper;
        this.procedureRepository = procedureRepository;
        this.participantRepository = participantRepository;
        this.riskAssessmentResultRepository = riskAssessmentResultRepository;
        this.riskRepository = riskRepository;
        this.yearlyRiskRepository = yearlyRiskRepository;

        setUpPathVariables();
    }

    private void setUpPathVariables() {
        try {
            String tyrerCuzickRoot = TyrerCuzickPathUtil.getTyrerCuzickPath();
            String tyrerCuzickCommand = TyrerCuzickPathUtil.getTyrerCuzickCommand();
            TC_EXECUTABLE_COMMAND = tyrerCuzickCommand;
            TC_INPUT_FILE_LOCATION = tyrerCuzickRoot + "input/";
            TC_OUTPUT_FILE_LOCATION = tyrerCuzickRoot + "output/";
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void writeValidatedAnswerResponsesToFile() {
        log.info("Processing validated answer responses");

        List<AnswerResponse> answerResponses = getValidatedAnswerResponses();
        for (AnswerResponse answerResponse : answerResponses) {
            Optional<Procedure> procedure = procedureRepository.findByRiskAssessmentResponse(answerResponse);
            if (procedure.isPresent()) {
                Participant participant = procedure.get().getParticipant();
                String participantIdentifier = participant.getUser().getLogin();
                LocalDate dateOfBirth = participant.getIdentifiableData().getDateOfBirth();
                
                try {
                    String fileOutput = mapper.map(participantIdentifier, dateOfBirth, answerResponse);
                    writeDataToFile(participantIdentifier, fileOutput);
                    answerResponse.setState(ResponseState.PROCESSED);
                } catch (Exception ex) {
                    log.error("Failed to process risk assessment for participant: " + participant.getUser().getLogin());
                    log.error(ex.getMessage());
                    answerResponse.setState(ResponseState.FAILED);
                }

                answerResponseRepository.save(answerResponse);
            }
        }   
    }

    private List<AnswerResponse> getValidatedAnswerResponses() {
        return answerResponseRepository.findByState(ResponseState.VALIDATED);
    }

    private void writeDataToFile(String participantIdentifier, String data) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(new Date());
        String filename = participantIdentifier + "-" + dateString + ".txt";

        String fullFilePath = TC_INPUT_FILE_LOCATION + filename;
        try (PrintWriter out = new PrintWriter(fullFilePath)) {
            out.println(data);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Scheduled(cron = "0 5 0 * * *")
    public void runTyrerCuzickExecutable() {
        log.info("Running tyrercuzick executable");

        File tcInputDirectory = new File(TC_INPUT_FILE_LOCATION);
        for (File fileEntry : tcInputDirectory.listFiles()) {
            if (isTCInputFile(fileEntry)) {
                String fileName = fileEntry.getName();
                String inputLocation = new File(TC_INPUT_FILE_LOCATION + fileName).getAbsolutePath();
                String outputLocation = new File(TC_OUTPUT_FILE_LOCATION + fileName).getAbsolutePath();

                try {                    
                    String tcCommand = TC_EXECUTABLE_COMMAND
                        .replace("<INPUT>", inputLocation)
                        .replace("<OUTPUT>", outputLocation);
                    Runtime rt = Runtime.getRuntime();
                    Process p = rt.exec(tcCommand);
                    p.waitFor();

                    fileEntry.delete();
                    log.info("Executed tyrercuzick command: " + tcCommand);
                } catch(Exception ex) {
                    log.info("Failed to execute tyrercuzick executable, reason: " + ex.getMessage());
                }
            }
        }
    }

    private boolean isTCInputFile(File file) {
        // TC Files are in the format <STUDY_CODE>-<DATE yyyy-MM-dd>.txt
        String filename = file.getName();

        boolean isTxtFile = filename.contains(".txt");
        boolean isDateValid = false;
        try {
            String dateString = filename.substring(filename.length() - 14, filename.length() - 4);
            new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            isDateValid = true;
        } catch(Exception ex) {
            isDateValid = false;
        }
        
        return isTxtFile && isDateValid;
    }

    @Scheduled(cron = "0 10 0 * * *")
    public void readTyrerCuzickOutput() {
        log.info("Processing tyrercuzick output");

        File tcOutputDirectory = new File(TC_OUTPUT_FILE_LOCATION);
        for (File fileEntry : tcOutputDirectory.listFiles()) {
            String filename = fileEntry.getAbsolutePath();
            try {
                String fileData = readFile(fileEntry);
                String[] riskData = fileData.split(",");
                RiskAssessmentResult result = createRiskAssessmentResult(riskData);
                result.setFilename(filename);

                riskAssessmentResultRepository.save(result);

                fileEntry.delete();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    private String readFile(File file) throws Exception {
        Scanner fileReader = new Scanner(file);
        String fileData = "";

        while(fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            if (data.length() > 2)
                fileData += data;
        }

        fileReader.close();

        return fileData;
    }

    private RiskAssessmentResult createRiskAssessmentResult(String[] riskData) {
        String login = riskData[0];
        Participant participant = participantRepository.findOneByUserLogin(login).get();

        RiskAssessmentResult result = new RiskAssessmentResult();
        result.setParticipant(participant);

        Risk individualRisk = parseRisk(riskData, 21);
        Risk populationRisk = parseRisk(riskData, 45);

        individualRisk = riskRepository.save(individualRisk);
        Set<YearlyRisk> individualYearlyRisks = parseYearlyRisks(individualRisk, riskData, 1);
        individualRisk.setYearlyRisks(individualYearlyRisks);

        populationRisk = riskRepository.save(populationRisk);
        Set<YearlyRisk> populationYearlyRisks = parseYearlyRisks(populationRisk, riskData, 25);
        populationRisk.setYearlyRisks(populationYearlyRisks);

        yearlyRiskRepository.saveAll(individualYearlyRisks);
        yearlyRiskRepository.saveAll(populationYearlyRisks);

        result.setIndividualRisk(individualRisk);
        result.setPopulationRisk(populationRisk);

        return result;
    }

    private Risk parseRisk(String[] riskData, int index) {
        Risk risk = new Risk();
        risk.setLifetimeRisk(Double.parseDouble(riskData[index]));
        risk.setProbNotBcra(Double.parseDouble(riskData[index+1]));
        risk.setProbBcra1(Double.parseDouble(riskData[index+2]));
        risk.setProbBcra2(Double.parseDouble(riskData[index+3]));

        return risk;
    }

    private Set<YearlyRisk> parseYearlyRisks(Risk risk, String[] riskData, int index) {
        Set<YearlyRisk> yearlyRisks = new HashSet<>();
        int year = 1;

        for (int i = index; i < index + 20; i++) {
            Double riskFactor = Double.parseDouble(riskData[i]);
            YearlyRisk yearlyRisk = new YearlyRisk();
            yearlyRisk.setRiskFactor(riskFactor);
            yearlyRisk.setYear(year);
            yearlyRisk.setRisk(risk);
            year += 1;

            yearlyRisks.add(yearlyRisk);
        }

        return yearlyRisks;
    }
}