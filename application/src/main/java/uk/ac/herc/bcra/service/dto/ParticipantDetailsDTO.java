package uk.ac.herc.bcra.service.dto;

import uk.ac.herc.bcra.domain.enumeration.ParticipantContactWay;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ParticipantDetailsDTO {

    @NotNull
    private String forename;

    @NotNull
    private String surname;

    @NotNull
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String addressLine5;

    @NotNull
    private String postCode;

    @NotEmpty
    List<ParticipantContactWay> preferredContactWays;

    private String homePhoneNumber;

    private String mobilePhoneNumber;

    public ParticipantDetailsDTO() {
        // Empty constructor needed for Jackson.
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public List<ParticipantContactWay> getPreferredContactWays() {
        return preferredContactWays;
    }

    public void setPreferredContactWays(List<ParticipantContactWay> preferredContactWays) {
        this.preferredContactWays = preferredContactWays;
    }

    @Override
    public String toString() {
        return "ParticipantDetailsDTO ["
            + "]";
    }
}
