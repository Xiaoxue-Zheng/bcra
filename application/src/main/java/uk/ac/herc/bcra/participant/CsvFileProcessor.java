package uk.ac.herc.bcra.participant;

import java.util.List;

import com.univocity.parsers.common.DataValidationException;
import com.univocity.parsers.common.TextParsingException;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.domain.enumeration.CsvFileState;
import uk.ac.herc.bcra.repository.CsvFileRepository;

@Service
public class CsvFileProcessor {

    private final CsvFileRepository csvFileRepository;
    private final ParticipantImport participantImport;
    private final SetCsvFileState setCsvFileState;

    public CsvFileProcessor(
        CsvFileRepository csvFileRepository,
        ParticipantImport participantImport,
        SetCsvFileState setCsvFileState
    ) {
        this.csvFileRepository = csvFileRepository;
        this.participantImport = participantImport;
        this.setCsvFileState = setCsvFileState;
    }

    public void processUploadedCsvFiles() {
        List<CsvFile> csvFiles = getUploadedCsvFiles();

        for (CsvFile csvFile: csvFiles) {
            processUploadedCsvFile(csvFile.getId());
        }
    }

    private List<CsvFile> getUploadedCsvFiles() {
        CsvFile uploadedCsvFile = new CsvFile();
        uploadedCsvFile.setState(CsvFileState.UPLOADED);
        Example<CsvFile> uploadedCsvFileExample = Example.of(uploadedCsvFile);
        return csvFileRepository.findAll(uploadedCsvFileExample);
    }

    private void processUploadedCsvFile(long csvFileId) {
        CsvFileState state = CsvFileState.COMPLETED;
        String status = null;

        try {
            participantImport.importParticipants(csvFileId);
        }
        catch (DataValidationException exception) {
            state = CsvFileState.INVALID; 
            status =
                "Error parsing CSV value for "
                + exception.getColumnName()
                + " on line "
                + exception.getLineIndex()
                + ". Value is ["
                + exception.getValue()
                + "]. Message: "
                + exception.getMessage();
        }
        catch (TextParsingException exception) {
            state = CsvFileState.INVALID; 
            status =
                "Error parsing CSV at line "
                + exception.getLineIndex()
                + ", column "
                + exception.getColumnIndex()
                + " (character "
                + exception.getCharIndex()
                + "). Message: "
                + exception.getMessage();
        }
        catch (Exception exception) {
            state = CsvFileState.INVALID; 
            status = exception.getMessage();
        }

        setCsvFileState.setStateAndStatus(csvFileId, state, status);
    }
}
