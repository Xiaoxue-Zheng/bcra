package uk.ac.herc.bcra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Risk.
 */
@Entity
@Table(name = "risk")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Risk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "lifetime_risk", nullable = false)
    private Double lifetimeRisk;

    @NotNull
    @Column(name = "prob_not_bcra", nullable = false)
    private Double probNotBcra;

    @NotNull
    @Column(name = "prob_bcra_1", nullable = false)
    private Double probBcra1;

    @NotNull
    @Column(name = "prob_bcra_2", nullable = false)
    private Double probBcra2;

    @OneToMany(mappedBy = "risk")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RiskFactor> riskFactors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLifetimeRisk() {
        return lifetimeRisk;
    }

    public Risk lifetimeRisk(Double lifetimeRisk) {
        this.lifetimeRisk = lifetimeRisk;
        return this;
    }

    public void setLifetimeRisk(Double lifetimeRisk) {
        this.lifetimeRisk = lifetimeRisk;
    }

    public Double getProbNotBcra() {
        return probNotBcra;
    }

    public Risk probNotBcra(Double probNotBcra) {
        this.probNotBcra = probNotBcra;
        return this;
    }

    public void setProbNotBcra(Double probNotBcra) {
        this.probNotBcra = probNotBcra;
    }

    public Double getProbBcra1() {
        return probBcra1;
    }

    public Risk probBcra1(Double probBcra1) {
        this.probBcra1 = probBcra1;
        return this;
    }

    public void setProbBcra1(Double probBcra1) {
        this.probBcra1 = probBcra1;
    }

    public Double getProbBcra2() {
        return probBcra2;
    }

    public Risk probBcra2(Double probBcra2) {
        this.probBcra2 = probBcra2;
        return this;
    }

    public void setProbBcra2(Double probBcra2) {
        this.probBcra2 = probBcra2;
    }

    public Set<RiskFactor> getRiskFactors() {
        return riskFactors;
    }

    public Risk riskFactors(Set<RiskFactor> riskFactors) {
        this.riskFactors = riskFactors;
        return this;
    }

    public Risk addRiskFactor(RiskFactor riskFactor) {
        this.riskFactors.add(riskFactor);
        riskFactor.setRisk(this);
        return this;
    }

    public Risk removeRiskFactor(RiskFactor riskFactor) {
        this.riskFactors.remove(riskFactor);
        riskFactor.setRisk(null);
        return this;
    }

    public void setRiskFactors(Set<RiskFactor> riskFactors) {
        this.riskFactors = riskFactors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Risk)) {
            return false;
        }
        return id != null && id.equals(((Risk) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Risk{" +
            "id=" + getId() +
            ", lifetimeRisk=" + getLifetimeRisk() +
            ", probNotBcra=" + getProbNotBcra() +
            ", probBcra1=" + getProbBcra1() +
            ", probBcra2=" + getProbBcra2() +
            "}";
    }
}
