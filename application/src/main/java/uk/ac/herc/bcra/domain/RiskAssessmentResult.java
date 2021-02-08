package uk.ac.herc.bcra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A RiskAssessmentResult.
 */
@Entity
@Table(name = "risk_assessment_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RiskAssessmentResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "filename", nullable = false, unique = true)
    private String filename;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Participant participant;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Risk individualRisk;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Risk populationRisk;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public RiskAssessmentResult filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Participant getParticipant() {
        return participant;
    }

    public RiskAssessmentResult participant(Participant participant) {
        this.participant = participant;
        return this;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Risk getIndividualRisk() {
        return individualRisk;
    }

    public RiskAssessmentResult individualRisk(Risk risk) {
        this.individualRisk = risk;
        return this;
    }

    public void setIndividualRisk(Risk risk) {
        this.individualRisk = risk;
    }

    public Risk getPopulationRisk() {
        return populationRisk;
    }

    public RiskAssessmentResult populationRisk(Risk risk) {
        this.populationRisk = risk;
        return this;
    }

    public void setPopulationRisk(Risk risk) {
        this.populationRisk = risk;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiskAssessmentResult)) {
            return false;
        }
        return id != null && id.equals(((RiskAssessmentResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RiskAssessmentResult{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            "}";
    }
}
