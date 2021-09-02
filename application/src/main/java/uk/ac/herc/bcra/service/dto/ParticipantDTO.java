package uk.ac.herc.bcra.service.dto;

import java.time.Instant;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Participant} entity.
 */
public class ParticipantDTO implements Serializable {

    private static final long serialVersionUID = 1614416367273587447L;

    private Long id;
    private String studyID;
    private LocalDate dateOfBirth;
    private String status;
    private Instant registerDatetime;
    private Long canRiskReportId;

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

    public String getStudyID() {
        return studyID;
    }

    public void setStudyID(String studyID) {
        this.studyID = studyID;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCanRiskReportId() {
        return canRiskReportId;
    }

    public void setCanRiskReportId(Long canRiskReportId) {
        this.canRiskReportId = canRiskReportId;
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


}
