package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CheckboxAnswerItem.
 */
@Entity
@Table(name = "checkbox_answer_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CheckboxAnswerItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("checkboxAnswerItems")
    private CheckboxAnswer checkboxAnswer;

    @ManyToOne
    @JsonIgnoreProperties("checkboxAnswerItems")
    private CheckboxQuestionItem checkboxQuestionItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckboxAnswer getCheckboxAnswer() {
        return checkboxAnswer;
    }

    public CheckboxAnswerItem checkboxAnswer(CheckboxAnswer checkboxAnswer) {
        this.checkboxAnswer = checkboxAnswer;
        return this;
    }

    public void setCheckboxAnswer(CheckboxAnswer checkboxAnswer) {
        this.checkboxAnswer = checkboxAnswer;
    }

    public CheckboxQuestionItem getCheckboxQuestionItem() {
        return checkboxQuestionItem;
    }

    public CheckboxAnswerItem checkboxQuestionItem(CheckboxQuestionItem checkboxQuestionItem) {
        this.checkboxQuestionItem = checkboxQuestionItem;
        return this;
    }

    public void setCheckboxQuestionItem(CheckboxQuestionItem checkboxQuestionItem) {
        this.checkboxQuestionItem = checkboxQuestionItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckboxAnswerItem)) {
            return false;
        }
        return id != null && id.equals(((CheckboxAnswerItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CheckboxAnswerItem{" +
            "id=" + getId() +
            "}";
    }
}
