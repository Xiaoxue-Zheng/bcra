package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RadioAnswer.
 */
@Entity
@Table(name = "radio_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RadioAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToOne(mappedBy = "radioAnswer")
    @JsonIgnore
    private RadioAnswerItem radioAnswerItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RadioAnswerItem getRadioAnswerItem() {
        return radioAnswerItem;
    }

    public RadioAnswer radioAnswerItem(RadioAnswerItem radioAnswerItem) {
        this.radioAnswerItem = radioAnswerItem;
        return this;
    }

    public void setRadioAnswerItem(RadioAnswerItem radioAnswerItem) {
        this.radioAnswerItem = radioAnswerItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RadioAnswer)) {
            return false;
        }
        return id != null && id.equals(((RadioAnswer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RadioAnswer{" +
            "id=" + getId() +
            "}";
    }
}
