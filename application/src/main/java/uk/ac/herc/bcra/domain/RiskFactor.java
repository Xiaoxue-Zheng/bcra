package uk.ac.herc.bcra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A RiskFactor.
 */
@Entity
@Table(name = "risk_factor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RiskFactor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "factor", nullable = false)
    private Double factor;

    @ManyToOne()
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Risk risk;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFactor() {
        return factor;
    }

    public RiskFactor factor(Double factor) {
        this.factor = factor;
        return this;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    public Risk getRisk() {
        return risk;
    }

    public void setRisk(Risk risk) {
        this.risk = risk;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RiskFactor)) {
            return false;
        }
        return id != null && id.equals(((RiskFactor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RiskFactor{" +
            "id=" + getId() +
            ", factor=" + getFactor() +
            "}";
    }
}
