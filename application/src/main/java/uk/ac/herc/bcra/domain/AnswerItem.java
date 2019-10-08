package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A AnswerItem.
 */
@Entity
@Table(name = "answer_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnswerItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "selected", nullable = false)
    private Boolean selected;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("answerItems")
    private Answer answer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("answerItems")
    private QuestionItem questionItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isSelected() {
        return selected;
    }

    public AnswerItem selected(Boolean selected) {
        this.selected = selected;
        return this;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Answer getAnswer() {
        return answer;
    }

    public AnswerItem answer(Answer answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public QuestionItem getQuestionItem() {
        return questionItem;
    }

    public AnswerItem questionItem(QuestionItem questionItem) {
        this.questionItem = questionItem;
        return this;
    }

    public void setQuestionItem(QuestionItem questionItem) {
        this.questionItem = questionItem;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerItem)) {
            return false;
        }
        return id != null && id.equals(((AnswerItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnswerItem{" +
            "id=" + getId() +
            ", selected='" + isSelected() + "'" +
            "}";
    }
}
