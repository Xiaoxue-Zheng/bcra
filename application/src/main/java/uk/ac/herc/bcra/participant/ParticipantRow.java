package uk.ac.herc.bcra.participant;

import java.util.Date;

import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.annotations.Validate;
import com.univocity.parsers.conversions.DateConversion;

public class ParticipantRow {

    @Parsed(field = "nhs")
    @Validate(validators = ValidateNhsNumber.class)
    private String nhsNumber;

    @Parsed(field = "birth")
    @Validate
    @Convert(conversionClass = DateConversion.class, args = { "dd/MM/yyyy" })
    private Date dateOfBirth;

    @Parsed(field = "firstname")
    @Validate
    private String firstname;

    @Parsed(field = "surname")
    @Validate
    private String surname;

    @Parsed(field = "address1")
    @Validate
    private String address1;

    @Parsed(field = "address2")
    private String address2;

    @Parsed(field = "address3")
    private String address3;

    @Parsed(field = "address4")
    private String address4;

    @Parsed(field = "address5")
    private String address5;

    @Parsed(field = "postcode")
    @Validate(validators = ValidatePostcode.class)
    private String postcode;

    @Parsed(field = "practice")
    @Validate
    private String practiceName;

    public String getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "ParticipantCsv [" 
            + " nhsNumber=" + nhsNumber
            + ", dateOfBirth=" + dateOfBirth
            + ", firstname=" + firstname
            + ", surname=" + surname
            + ", address1=" + address1 
            + ", address2=" + address2 
            + ", address3=" + address3
            + ", address4=" + address4
            + ", address5=" + address5
            + ", postcode=" + postcode
            + ", practiceName=" + practiceName
        + "]";
    }


}
