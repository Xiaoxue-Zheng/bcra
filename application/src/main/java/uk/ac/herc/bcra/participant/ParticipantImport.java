package uk.ac.herc.bcra.participant;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.repository.CsvFileRepository;
import uk.ac.herc.bcra.repository.IdentifiableDataRepository;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.ProcedureRepository;
import uk.ac.herc.bcra.repository.UserRepository;

@Service
@Transactional
public class ParticipantImport {
    private final CsvFileRepository csvFileRepository;
    private final IdentifiableDataRepository identifiableDataRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ProcedureRepository procedureRepository;
    private final ParticipantCreator participantCreator;

    public ParticipantImport(
        CsvFileRepository csvFileRepository,
        IdentifiableDataRepository identifiableDataRepository,
        ParticipantRepository participantRepository,
        UserRepository userRepository,
        ProcedureRepository procedureRepository,
        ParticipantCreator participantCreator
    ) {
        this.csvFileRepository = csvFileRepository;
        this.identifiableDataRepository = identifiableDataRepository;
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
        this.procedureRepository = procedureRepository;
        this.participantCreator = participantCreator;
    }

    public void importParticipants(long csvFileId) {
        CsvFile csvFile = csvFileRepository.findById(csvFileId).get();
        List<ParticipantRow> participantRows = getParticipantRows(csvFile);
        checkForExistingParticipants(participantRows);
        
        List<Participant> participants = createParticipants(participantRows, csvFile);
        saveParticipants(participants);
    }

    private List<ParticipantRow> getParticipantRows(CsvFile csvFile) {

        InputStream csvStream = new ByteArrayInputStream(
            csvFile.readContent()
        );

        BeanListProcessor<ParticipantRow> rowProcessor = 
            new BeanListProcessor<ParticipantRow>(ParticipantRow.class);

        CsvParserSettings parserSettings = new CsvParserSettings();
        parserSettings.setProcessor(rowProcessor);
        parserSettings.setHeaderExtractionEnabled(true);
        parserSettings.setLineSeparatorDetectionEnabled(true);

        CsvParser parser = new CsvParser(parserSettings);
        parser.parse(csvStream);
        return rowProcessor.getBeans();
    }

    private void checkForExistingParticipants(List<ParticipantRow> participantRows) {
        for (ParticipantRow participantRow: participantRows) {
            if (
                identifiableDataRepository.findOneByNhsNumber(
                    participantRow.getNhsNumber()
                ).isPresent()
            ) {
                throw new RuntimeException(
                    "NHS Number already exists in database: " 
                    + participantRow.getNhsNumber()
                );
            }
        }
    }

    private List<Participant> createParticipants(List<ParticipantRow> participantRows, CsvFile csvFile) {
        List<Participant> participants = new ArrayList<Participant>();

        for (ParticipantRow participantRow: participantRows) {
            participants.add(
                participantCreator.createParticipant(participantRow, csvFile)
            );
        }

        return participants;
    }

    private void saveParticipants(List<Participant> participants) {
        for (Participant participant: participants) {
            userRepository.save(participant.getUser());
            procedureRepository.save(participant.getProcedure());
            identifiableDataRepository.save(participant.getIdentifiableData());
            participantRepository.save(participant);
        }
    }
}
