package uk.ac.herc.bcra.participant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CsvScheduledTask {

    private final CsvFileProcessor csvFileProcessor;

    public CsvScheduledTask(
        CsvFileProcessor csvFileProcessor
    ) {
        this.csvFileProcessor = csvFileProcessor;
    }

    @Scheduled(fixedDelay = 10000)
    public void processUploadedCsvFiles() {
        csvFileProcessor.processUploadedCsvFiles();
    }
}
