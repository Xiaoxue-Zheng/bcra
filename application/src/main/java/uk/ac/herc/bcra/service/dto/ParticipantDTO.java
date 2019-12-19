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
    //private Instant consentDatetime;
    private ResponseState questionnaireStatus;
    //private SwabState swabState;
    //private MammogramState mamogramState;

    // Letters
    //private Instant invitationDownloadedDatetime;

    // Unused
    private Long userId;
    private String userLogin;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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
            + ", userId=" + userId
            + ", userLogin=" + userLogin
            + "]";
    }

}
