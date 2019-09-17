package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CheckboxQuestion.
 */
@Entity
@Table(name = "checkbox_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CheckboxQuestion extends Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "checkboxQuestion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CheckboxQuestionItem> checkboxQuestionItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Set<CheckboxQuestionItem> getCheckboxQuestionItems() {
        return checkboxQuestionItems;
    }

    public CheckboxQuestion checkboxQuestionItems(Set<CheckboxQuestionItem> checkboxQuestionItems) {
        this.checkboxQuestionItems = checkboxQuestionItems;
        return this;
    }

    public CheckboxQuestion addCheckboxQuestionItem(CheckboxQuestionItem checkboxQuestionItem) {
        this.checkboxQuestionItems.add(checkboxQuestionItem);
        checkboxQuestionItem.setCheckboxQuestion(this);
        return this;
    }

    public CheckboxQuestion removeCheckboxQuestionItem(CheckboxQuestionItem checkboxQuestionItem) {
        this.checkboxQuestionItems.remove(checkboxQuestionItem);
        checkboxQuestionItem.setCheckboxQuestion(null);
        return this;
    }

    public void setCheckboxQuestionItems(Set<CheckboxQuestionItem> checkboxQuestionItems) {
        this.checkboxQuestionItems = checkboxQuestionItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckboxQuestion)) {
            return false;
        }
        return getId() != null && getId().equals(((CheckboxQuestion) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CheckboxQuestion{" +
            "id=" + getId() +
            "}";
    }
}
