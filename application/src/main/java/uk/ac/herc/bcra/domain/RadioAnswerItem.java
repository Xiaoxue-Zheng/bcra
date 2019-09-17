package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RadioAnswerItem.
 */
@Entity
@Table(name = "radio_answer_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RadioAnswerItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private RadioAnswer radioAnswer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RadioAnswer getRadioAnswer() {
        return radioAnswer;
    }

    public RadioAnswerItem radioAnswer(RadioAnswer radioAnswer) {
        this.radioAnswer = radioAnswer;
        return this;
    }

    public void setRadioAnswer(RadioAnswer radioAnswer) {
        this.radioAnswer = radioAnswer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RadioAnswerItem)) {
            return false;
        }
        return id != null && id.equals(((RadioAnswerItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RadioAnswerItem{" +
            "id=" + getId() +
            "}";
    }
}
