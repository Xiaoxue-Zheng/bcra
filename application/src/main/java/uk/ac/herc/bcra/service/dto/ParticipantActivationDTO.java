package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ParticipantActivationDTO {

    @NotNull
    private String emailAddress;

    @NotNull
    private String password;

    @NotNull
    private AnswerResponseDTO consentResponse;

    @NotNull
    private String studyCode;

    @NotNull
    private LocalDate dateOfBirth;

    public ParticipantActivationDTO() {
        // Empty constructor needed for Jackson.
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AnswerResponseDTO getConsentResponse() {
        return consentResponse;
    }

    public void setConsentResponse(AnswerResponseDTO consentResponse) {
        this.consentResponse = consentResponse;
    }

    public String getStudyCode() {
        return studyCode;
    }

    public void setStudyCode(String studyCode) {
        this.studyCode = studyCode;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "ParticipantActivationDTO ["
            + " studyCode=" + studyCode
            + ", emailAddress=" + emailAddress
            + ", password=" + password
            + "]";
    }
}
