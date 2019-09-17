package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RadioQuestion.
 */
@Entity
@Table(name = "radio_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RadioQuestion extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "radioQuestion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RadioQuestionItem> radioQuestionItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Set<RadioQuestionItem> getRadioQuestionItems() {
        return radioQuestionItems;
    }

    public RadioQuestion radioQuestionItems(Set<RadioQuestionItem> radioQuestionItems) {
        this.radioQuestionItems = radioQuestionItems;
        return this;
    }

    public RadioQuestion addRadioQuestionItem(RadioQuestionItem radioQuestionItem) {
        this.radioQuestionItems.add(radioQuestionItem);
        radioQuestionItem.setRadioQuestion(this);
        return this;
    }

    public RadioQuestion removeRadioQuestionItem(RadioQuestionItem radioQuestionItem) {
        this.radioQuestionItems.remove(radioQuestionItem);
        radioQuestionItem.setRadioQuestion(null);
        return this;
    }

    public void setRadioQuestionItems(Set<RadioQuestionItem> radioQuestionItems) {
        this.radioQuestionItems = radioQuestionItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RadioQuestion)) {
            return false;
        }
        return getId() != null && getId().equals(((RadioQuestion) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RadioQuestion{" +
            "id=" + getId() +
            "}";
    }
}
