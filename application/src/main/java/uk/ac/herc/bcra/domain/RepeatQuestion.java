package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RepeatQuestion.
 */
@Entity
@Table(name = "repeat_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RepeatQuestion extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "maximum", nullable = false)
    private Integer maximum;

    @OneToMany(mappedBy = "repeatQuestion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RepeatDisplayCondition> repeatDisplayConditions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Integer getMaximum() {
        return maximum;
    }

    public RepeatQuestion maximum(Integer maximum) {
        this.maximum = maximum;
        return this;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Set<RepeatDisplayCondition> getRepeatDisplayConditions() {
        return repeatDisplayConditions;
    }

    public RepeatQuestion repeatDisplayConditions(Set<RepeatDisplayCondition> repeatDisplayConditions) {
        this.repeatDisplayConditions = repeatDisplayConditions;
        return this;
    }

    public RepeatQuestion addRepeatDisplayCondition(RepeatDisplayCondition repeatDisplayCondition) {
        this.repeatDisplayConditions.add(repeatDisplayCondition);
        repeatDisplayCondition.setRepeatQuestion(this);
        return this;
    }

    public RepeatQuestion removeRepeatDisplayCondition(RepeatDisplayCondition repeatDisplayCondition) {
        this.repeatDisplayConditions.remove(repeatDisplayCondition);
        repeatDisplayCondition.setRepeatQuestion(null);
        return this;
    }

    public void setRepeatDisplayConditions(Set<RepeatDisplayCondition> repeatDisplayConditions) {
        this.repeatDisplayConditions = repeatDisplayConditions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepeatQuestion)) {
            return false;
        }
        return getId() != null && getId().equals(((RepeatQuestion) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RepeatQuestion{" +
            "id=" + getId() +
            ", maximum=" + getMaximum() +
            "}";
    }
}
