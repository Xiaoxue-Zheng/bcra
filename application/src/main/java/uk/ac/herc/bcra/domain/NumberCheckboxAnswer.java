package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A NumberCheckboxAnswer.
 */
@Entity
@Table(name = "number_checkbox_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NumberCheckboxAnswer extends Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "number")
    private Integer number;

    @Column(name = "jhi_check")
    private Boolean check;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getNumber() {
        return number;
    }

    public NumberCheckboxAnswer number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isCheck() {
        return check;
    }

    public NumberCheckboxAnswer check(Boolean check) {
        this.check = check;
        return this;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberCheckboxAnswer)) {
            return false;
        }
        return getId() != null && getId().equals(((NumberCheckboxAnswer) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NumberCheckboxAnswer{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", check='" + isCheck() + "'" +
            "}";
    }
}
