package uk.ac.herc.bcra.service.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class ParticipantActivationDTO {
    
    @NotNull
    private String emailAddress;

    @NotNull
    private String password;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private String nhsNumber;


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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateofBirth) {
        this.dateOfBirth = dateofBirth;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    @Override
    public String toString() {
        return "ParticipantActivationDTO ["
            + " dateOfBirth=" + dateOfBirth
            + ", emailAddress=" + emailAddress
            + ", nhsNumber=" + nhsNumber
            + ", password=" + password
            + "]";
    }
}
