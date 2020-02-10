package uk.ac.herc.bcra.participant;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import uk.ac.herc.bcra.domain.Authority;
import uk.ac.herc.bcra.domain.CsvFile;
import uk.ac.herc.bcra.domain.IdentifiableData;
import uk.ac.herc.bcra.domain.Participant;
import uk.ac.herc.bcra.domain.Procedure;
import uk.ac.herc.bcra.domain.User;
import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;
import uk.ac.herc.bcra.questionnaire.AnswerResponseGenerator;
import uk.ac.herc.bcra.repository.AuthorityRepository;
import uk.ac.herc.bcra.security.AuthoritiesConstants;

@Service
@Transactional
public class ParticipantCreator {
    private final AuthorityRepository authorityRepository;
    private final AnswerResponseGenerator answerResponseGenerator;

    public ParticipantCreator(
        AuthorityRepository authorityRepository,
        AnswerResponseGenerator answerResponseGenerator
    ) {
        this.authorityRepository = authorityRepository;
        this.answerResponseGenerator = answerResponseGenerator;
    }
    
    public Participant createParticipant(ParticipantRow participantRow, CsvFile csvFile) {
        Participant participant = new Participant();
        participant.setUser(createUser());
        participant.setIdentifiableData(createIdentifiableData(participantRow));
        participant.setProcedure(createProcedure());
        participant.setCsvFile(csvFile);
        return participant;
    }

    private IdentifiableData createIdentifiableData(ParticipantRow participantRow) {
        LocalDate localDateOfBirth = 
            participantRow
                .getDateOfBirth()
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

        return identifiableData;
    }

    private User createUser() {
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

        return user;
    }

    private Procedure createProcedure() {
        Procedure procedure = new Procedure();

        procedure.setConsentResponse(
            answerResponseGenerator
                .generateAnswerResponseToQuestionnaire(
                    QuestionnaireType.CONSENT_FORM
                )
        );

        procedure.setRiskAssessmentResponse(
            answerResponseGenerator
                .generateAnswerResponseToQuestionnaire(
                    QuestionnaireType.RISK_ASSESSMENT
                )
        );

        return procedure;
    }
}