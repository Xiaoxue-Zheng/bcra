package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Participant.
 */
@Entity
@Table(name = "participant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Participant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "register_datetime")
    private Instant registerDatetime;

    @Column(name = "last_login_datetime")
    private Instant lastLoginDatetime;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private User user;

    @OneToOne(optional = true)
    @JoinColumn(unique = true)
    private IdentifiableData identifiableData;

    @OneToOne(optional = false)    @NotNull

    @JoinColumn(unique = true)
    private Procedure procedure;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRegisterDatetime() {
        return registerDatetime;
    }

    public Participant registerDatetime(Instant registerDatetime) {
        this.registerDatetime = registerDatetime;
        return this;
    }

    public void setRegisterDatetime(Instant registerDatetime) {
        this.registerDatetime = registerDatetime;
    }

    public Instant getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public Participant lastLoginDatetime(Instant lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
        return this;
    }

    public void setLastLoginDatetime(Instant lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

    public User getUser() {
        return user;
    }

    public Participant user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public IdentifiableData getIdentifiableData() {
        return identifiableData;
    }

    public Participant identifiableData(IdentifiableData identifiableData) {
        this.identifiableData = identifiableData;
        return this;
    }

    public void setIdentifiableData(IdentifiableData identifiableData) {
        this.identifiableData = identifiableData;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public Participant procedure(Procedure procedure) {
        this.procedure = procedure;
        return this;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Participant)) {
            return false;
        }
        return id != null && id.equals(((Participant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Participant{" +
            "id=" + getId() +
            ", registerDatetime='" + getRegisterDatetime() + "'" +
            ", lastLoginDatetime='" + getLastLoginDatetime() + "'" +
            "}";
    }
}
