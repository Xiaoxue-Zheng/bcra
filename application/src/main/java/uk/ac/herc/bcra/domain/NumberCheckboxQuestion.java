package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A NumberCheckboxQuestion.
 */
@Entity
@Table(name = "number_checkbox_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NumberCheckboxQuestion extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "minimum", nullable = false)
    private Integer minimum;

    @NotNull
    @Column(name = "maximum", nullable = false)
    private Integer maximum;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getMinimum() {
        return minimum;
    }

    public NumberCheckboxQuestion minimum(Integer minimum) {
        this.minimum = minimum;
        return this;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public NumberCheckboxQuestion maximum(Integer maximum) {
        this.maximum = maximum;
        return this;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberCheckboxQuestion)) {
            return false;
        }
        return getId() != null && getId().equals(((NumberCheckboxQuestion) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NumberCheckboxQuestion{" +
            "id=" + getId() +
            ", minimum=" + getMinimum() +
            ", maximum=" + getMaximum() +
            "}";
    }
}
