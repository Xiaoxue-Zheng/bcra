package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RepeatDisplayCondition.
 */
@Entity
@Table(name = "repeat_display_condition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RepeatDisplayCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("repeatDisplayConditions")
    private RepeatQuestion repeatQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RepeatQuestion getRepeatQuestion() {
        return repeatQuestion;
    }

    public RepeatDisplayCondition repeatQuestion(RepeatQuestion repeatQuestion) {
        this.repeatQuestion = repeatQuestion;
        return this;
    }

    public void setRepeatQuestion(RepeatQuestion repeatQuestion) {
        this.repeatQuestion = repeatQuestion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepeatDisplayCondition)) {
            return false;
        }
        return id != null && id.equals(((RepeatDisplayCondition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RepeatDisplayCondition{" +
            "id=" + getId() +
            "}";
    }
}
