package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Procedure.
 */
@Entity
@Table(name = "procedure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Procedure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private AnswerResponse consentResponse;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private AnswerResponse riskAssessmentResponse;

    @OneToOne(mappedBy = "procedure")
    @JsonIgnore
    private Participant participant;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerResponse getConsentResponse() {
        return consentResponse;
    }

    public Procedure consentResponse(AnswerResponse answerResponse) {
        this.consentResponse = answerResponse;
        return this;
    }

    public void setConsentResponse(AnswerResponse answerResponse) {
        this.consentResponse = answerResponse;
    }

    public AnswerResponse getRiskAssessmentResponse() {
        return riskAssessmentResponse;
    }

    public Procedure riskAssessmentResponse(AnswerResponse answerResponse) {
        this.riskAssessmentResponse = answerResponse;
        return this;
    }

    public void setRiskAssessmentResponse(AnswerResponse answerResponse) {
        this.riskAssessmentResponse = answerResponse;
    }

    public Participant getParticipant() {
        return participant;
    }

    public Procedure participant(Participant participant) {
        this.participant = participant;
        return this;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Procedure)) {
            return false;
        }
        return id != null && id.equals(((Procedure) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Procedure{" +
            "id=" + getId() +
            "}";
    }
}
