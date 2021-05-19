package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A IdentifiableData.
 */
@Entity
@Table(name = "identifiable_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IdentifiableData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 10, max = 10)
    @Column(name = "nhs_number", length = 10, nullable = false, unique = true)
    private String nhsNumber;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @Size(max = 254)
    @Column(name = "email", length = 254)
    private String email;

    @NotNull
    @Column(name = "address_1", nullable = false)
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    @Column(name = "address_4")
    private String address4;

    @Column(name = "address_5")
    private String address5;

    @NotNull
    @Size(min = 6, max = 8)
    @Column(name = "postcode", length = 8, nullable = false)
    private String postcode;

    @NotNull
    @Column(name = "practice_name", nullable = false)
    private String practiceName;

    @Column(name = "home_phone_number")
    private String homePhoneNumber;

    @Column(name = "mobile_phone_number")
    private String mobilePhoneNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNhsNumber() {
        return nhsNumber;
    }

    public IdentifiableData nhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
        return this;
    }

    public void setNhsNumber(String nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public IdentifiableData dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstname() {
        return firstname;
    }

    public IdentifiableData firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public IdentifiableData surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public IdentifiableData email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public IdentifiableData address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public IdentifiableData address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public IdentifiableData address3(String address3) {
        this.address3 = address3;
        return this;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return address4;
    }

    public IdentifiableData address4(String address4) {
        this.address4 = address4;
        return this;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return address5;
    }

    public IdentifiableData address5(String address5) {
        this.address5 = address5;
        return this;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getPostcode() {
        return postcode;
    }

    public IdentifiableData postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public IdentifiableData practiceName(String practiceName) {
        this.practiceName = practiceName;
        return this;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public IdentifiableData homePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
        return this;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public IdentifiableData mobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
        return this;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IdentifiableData)) {
            return false;
        }
        return id != null && id.equals(((IdentifiableData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IdentifiableData{" +
            "id=" + getId() +
            ", nhsNumber='" + getNhsNumber() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", surname='" + getSurname() + "'" +
            ", email='" + getEmail() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", address3='" + getAddress3() + "'" +
            ", address4='" + getAddress4() + "'" +
            ", address5='" + getAddress5() + "'" +
            ", postcode='" + getPostcode() + "'" +
            ", homePhoneNumber='" + getHomePhoneNumber() + "'" + 
            ", mobilePhoneNumber='" + getMobilePhoneNumber() + "'" +
            ", practiceName='" + getPracticeName() + "'" +
            "}";
    }
}
