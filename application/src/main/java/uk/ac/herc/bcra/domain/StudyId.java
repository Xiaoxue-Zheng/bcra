package uk.ac.herc.bcra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A StudyId.
 */
@Entity
@Table(name = "study_id")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StudyId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @OneToOne
    @JoinColumn(unique = true)
    private Participant participant;

    @OneToOne
    @JoinColumn(unique = true)
    private AnswerResponse consentResponse;

    @OneToOne
    @JoinColumn(unique = true)
    private AnswerResponse riskAssessmentResponse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public StudyId code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Participant getParticipant() {
        return participant;
    }

    public StudyId participant(Participant participant) {
        this.participant = participant;
        return this;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public AnswerResponse getConsentResponse() {
        return consentResponse;
    }

    public void setConsentResponse(AnswerResponse consentResponse) {
        this.consentResponse = consentResponse;
    }

    public AnswerResponse getRiskAssessmentResponse() {
        return riskAssessmentResponse;
    }

    public void setRiskAssessmentResponse(AnswerResponse riskAssessmentResponse) {
        this.riskAssessmentResponse = riskAssessmentResponse;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudyId)) {
            return false;
        }
        return id != null && id.equals(((StudyId) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "StudyId{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            "}";
    }
}
