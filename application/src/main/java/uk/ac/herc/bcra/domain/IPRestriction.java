package uk.ac.herc.bcra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A IPRestriction.
 */
@Entity
@Table(name = "iprestriction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IPRestriction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "ip_address", nullable = false, unique = true)
    private String ipAddress;

    @Min(value = 0)
    @Column(name = "times_rate_limit_broken")
    private Integer timesRateLimitBroken;

    @Column(name = "ban_date_time")
    private Instant banDateTime;

    @Column(name = "last_rate_limit_break")
    private Instant lastRateLimitBreak;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public IPRestriction ipAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getTimesRateLimitBroken() {
        return timesRateLimitBroken;
    }

    public IPRestriction timesRateLimitBroken(Integer timesRateLimitBroken) {
        this.timesRateLimitBroken = timesRateLimitBroken;
        return this;
    }

    public void setTimesRateLimitBroken(Integer timesRateLimitBroken) {
        this.timesRateLimitBroken = timesRateLimitBroken;
    }

    public Instant getBanDateTime() {
        return banDateTime;
    }

    public IPRestriction banDateTime(Instant banDateTime) {
        this.banDateTime = banDateTime;
        return this;
    }

    public void setBanDateTime(Instant banDateTime) {
        this.banDateTime = banDateTime;
    }

    public Instant getLastRateLimitBreak() {
        return lastRateLimitBreak;
    }

    public IPRestriction lastRateLimitBreak(Instant lastRateLimitBreak) {
        this.lastRateLimitBreak = lastRateLimitBreak;
        return this;
    }

    public void setLastRateLimitBreak(Instant lastRateLimitBreak) {
        this.lastRateLimitBreak = lastRateLimitBreak;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IPRestriction)) {
            return false;
        }
        return id != null && id.equals(((IPRestriction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IPRestriction{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", timesRateLimitBroken=" + getTimesRateLimitBroken() +
            ", banDateTime='" + getBanDateTime() + "'" +
            "}";
    }
}
