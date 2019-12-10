package uk.ac.herc.bcra.participant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.domain.enumeration.CsvFileState;
import uk.ac.herc.bcra.repository.CsvFileRepository;

@Service
@Transactional
public class SetCsvFileState {
    private final CsvFileRepository csvFileRepository;

    public SetCsvFileState(CsvFileRepository csvFileRepository) {
        this.csvFileRepository = csvFileRepository;
    }

    public void setStateAndStatus(long csvFileId, CsvFileState state, String status) {
        CsvFile csvFile = csvFileRepository.findById(csvFileId).get();

        if (status != null) {
            if (status.length() > 250) {
                status = status.substring(0, 250);
            }
            status = status.replaceAll("\\p{C}", "?");
        }

        csvFile.setState(state);
        csvFile.setStatus(status);

        if (state == CsvFileState.INVALID) {
            csvFile.writeContent("".getBytes());
        }

        csvFileRepository.save(csvFile);
    }
}
