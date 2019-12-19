package uk.ac.herc.bcra.participant;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.herc.bcra.domain.Authority;
import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.repository.AuthorityRepository;
import uk.ac.herc.bcra.repository.CsvFileRepository;
import uk.ac.herc.bcra.repository.IdentifiableDataRepository;
import uk.ac.herc.bcra.repository.ParticipantRepository;
import uk.ac.herc.bcra.repository.UserRepository;
import uk.ac.herc.bcra.security.AuthoritiesConstants;

@Service
@Transactional
public class ParticipantImport {
    private final CsvFileRepository csvFileRepository;
    private final IdentifiableDataRepository identifiableDataRepository;
    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public ParticipantImport(
        CsvFileRepository csvFileRepository,
        IdentifiableDataRepository identifiableDataRepository,
        ParticipantRepository participantRepository,
        UserRepository userRepository,
        AuthorityRepository authorityRepository
    ) {
        this.csvFileRepository = csvFileRepository;
        this.identifiableDataRepository = identifiableDataRepository;
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    public void importParticipants(long csvFileId) {
        CsvFile csvFile = csvFileRepository.findById(csvFileId).get();
        List<ParticipantRow> participantRows = getParticipantRows(csvFile);
        checkForExistingParticipants(participantRows);
        createParticipants(participantRows, csvFile);
    }

    private List<ParticipantRow> getParticipantRows(CsvFile csvFile) {      
        return parse(
            new ByteArrayInputStream(
                csvFile.readContent()
            )
        );
    }

    private List<ParticipantRow> parse(InputStream csvStream) {
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

    private void createParticipants(List<ParticipantRow> participantRows, CsvFile csvFile) {
        List<IdentifiableData> identifiableDataList = new ArrayList<IdentifiableData>();

        for (ParticipantRow participantRow: participantRows) {
            identifiableDataList.add(processParticpantData(participantRow));
        }

        for (IdentifiableData identifiableData: identifiableDataList) {
            userRepository.save(identifiableData.getParticipant().getUser());
            participantRepository.save(identifiableData.getParticipant());
            identifiableData.setCsvFile(csvFile);
            identifiableDataRepository.save(identifiableData);
        }
    }

    private IdentifiableData processParticpantData(ParticipantRow participantRow) {

        LocalDate localDateOfBirth = 
            participantRow.getDateOfBirth()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

        IdentifiableData identifiableData = new IdentifiableData();
        identifiableData.setNhsNumber(participantRow.getNhsNumber());
        identifiableData.setDateOfBirth(localDateOfBirth);
        identifiableData.setFirstname(participantRow.getFirstname());
        identifiableData.setSurname(participantRow.getSurname());
        identifiableData.setAddress1(participantRow.getAddress1());
        identifiableData.setAddress2(participantRow.getAddress2());
        identifiableData.setAddress3(participantRow.getAddress3());
        identifiableData.setAddress4(participantRow.getAddress4());
        identifiableData.setAddress5(participantRow.getAddress5());
        identifiableData.setPostcode(participantRow.getPostcode());
        identifiableData.setPracticeName(participantRow.getPracticeName());

        User user = new User();
        user.setCreatedBy("CSV Import");
        user.setCreatedDate(Instant.now());
        user.setLogin(UUID.randomUUID().toString());

        // Imported participants do not need to
        // validate their email address.
        user.setActivated(true);
        
        Set<Authority> authorities = new HashSet<Authority>();
        authorities.add(
            authorityRepository.getOne(
                AuthoritiesConstants.PARTICIPANT
            )
        );
        user.setAuthorities(authorities);

        Participant participant = new Participant();
        participant.setUser(user);
        identifiableData.setParticipant(participant);

        return identifiableData;
    }
}
