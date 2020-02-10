package uk.ac.herc.bcra.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

import uk.ac.herc.bcra.domain.enumeration.ResponseState;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Participant} entity.
 */
public class ParticipantDTO implements Serializable {

    private static final long serialVersionUID = 1614416367273587447L;

    // Basics
    private Long id;
    private String nhsNumber;
    private String practiceName;

    // User Row
    private Instant importedDatetime;
    private Instant registerDatetime;
    private Instant lastLoginDatetime;

    // Process
    private ResponseState consentStatus;
    private ResponseState questionnaireStatus;
    //private SwabState swabState;
    //private MammoKILOState mamoKILOState;

    // Letters
    //private Instant invitationDownloadedDatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegisterDatetime() {
        return registerDatetime;
    }

    public void setRegisterDatetime(Instant registerDatetime) {
        this.registerDatetime = registerDatetime;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public Instant getImportedDatetime() {
        return importedDatetime;
    }

    public void setImportedDatetime(Instant importedDatetime) {
        this.importedDatetime = importedDatetime;
    }

    public Instant getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(Instant lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }
    
    public ResponseState getConsentStatus() {
        return consentStatus;
    }

    public void setConsentStatus(ResponseState consentStatus) {
        this.consentStatus = consentStatus;
    }

    public ResponseState getQuestionnaireStatus() {
        return questionnaireStatus;
    }

    public void setQuestionnaireStatus(ResponseState questionnaireStatus) {
        this.questionnaireStatus = questionnaireStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParticipantDTO participantDTO = (ParticipantDTO) o;
        if (participantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), participantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    @Override
    public String toString() {
        return "ParticipantDTO [" 
            + ", id=" + id
            + ", importedDatetime=" + importedDatetime 
            + ", lastLoginDatetime=" + lastLoginDatetime 
            + ", nhsNumber=" + nhsNumber
            + ", practiceName=" + practiceName
            + ", questionnaireStatus=" + questionnaireStatus 
            + ", registerDatetime=" + registerDatetime
            + ", lastLoginDatetime=" + lastLoginDatetime
            + ", consentStatus=" + consentStatus
            + ", questionnaireStatus=" + questionnaireStatus            
            + "]";
    }
}
