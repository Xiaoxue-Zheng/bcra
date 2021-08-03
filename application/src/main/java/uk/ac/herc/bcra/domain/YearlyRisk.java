package uk.ac.herc.bcra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A YearlyRisk.
 */
@Entity
@Table(name = "yearly_risk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YearlyRisk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "risk_factor", nullable = false)
    private Double riskFactor;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

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

    public Double getRiskFactor() {
        return riskFactor;
    }

    public YearlyRisk riskFactor(Double riskFactor) {
        this.riskFactor = riskFactor;
        return this;
    }

    public void setRiskFactor(Double riskFactor) {
        this.riskFactor = riskFactor;
    }

    public Integer getYear() {
        return year;
    }

    public YearlyRisk year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
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
        if (!(o instanceof YearlyRisk)) {
            return false;
        }
        return id != null && id.equals(((YearlyRisk) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "YearlyRisk{" +
            "id=" + getId() +
            ", factor=" + getRiskFactor() +
            ", year=" + getYear() + 
            "}";
    }
}
