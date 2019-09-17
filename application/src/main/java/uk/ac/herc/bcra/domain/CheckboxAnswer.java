package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CheckboxAnswer.
 */
@Entity
@Table(name = "checkbox_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CheckboxAnswer extends Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "checkboxAnswer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CheckboxAnswerItem> checkboxAnswerItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Set<CheckboxAnswerItem> getCheckboxAnswerItems() {
        return checkboxAnswerItems;
    }

    public CheckboxAnswer checkboxAnswerItems(Set<CheckboxAnswerItem> checkboxAnswerItems) {
        this.checkboxAnswerItems = checkboxAnswerItems;
        return this;
    }

    public CheckboxAnswer addCheckboxAnswerItem(CheckboxAnswerItem checkboxAnswerItem) {
        this.checkboxAnswerItems.add(checkboxAnswerItem);
        checkboxAnswerItem.setCheckboxAnswer(this);
        return this;
    }

    public CheckboxAnswer removeCheckboxAnswerItem(CheckboxAnswerItem checkboxAnswerItem) {
        this.checkboxAnswerItems.remove(checkboxAnswerItem);
        checkboxAnswerItem.setCheckboxAnswer(null);
        return this;
    }

    public void setCheckboxAnswerItems(Set<CheckboxAnswerItem> checkboxAnswerItems) {
        this.checkboxAnswerItems = checkboxAnswerItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckboxAnswer)) {
            return false;
        }
        return getId() != null && getId().equals(((CheckboxAnswer) o).getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CheckboxAnswer{" +
            "id=" + getId() +
            "}";
    }
}
