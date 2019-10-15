package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;

import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;

/**
 * A DisplayCondition.
 */
@Entity
@Table(name = "display_condition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DisplayCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_identifier")
    private QuestionIdentifier questionIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_identifier")
    private QuestionItemIdentifier itemIdentifier;

    @ManyToOne
    @JsonIgnoreProperties("displayConditions")
    private QuestionSection questionSection;

    @ManyToOne
    @JsonIgnoreProperties("displayConditions")
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionIdentifier getQuestionIdentifier() {
        return questionIdentifier;
    }

    public DisplayCondition questionIdentifier(QuestionIdentifier questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
        return this;
    }

    public void setQuestionIdentifier(QuestionIdentifier questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public QuestionItemIdentifier getItemIdentifier() {
        return itemIdentifier;
    }

    public DisplayCondition itemIdentifier(QuestionItemIdentifier itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
        return this;
    }

    public void setItemIdentifier(QuestionItemIdentifier itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    public QuestionSection getQuestionSection() {
        return questionSection;
    }

    public DisplayCondition questionSection(QuestionSection questionSection) {
        this.questionSection = questionSection;
        return this;
    }

    public void setQuestionSection(QuestionSection questionSection) {
        this.questionSection = questionSection;
    }

    public Question getQuestion() {
        return question;
    }

    public DisplayCondition question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisplayCondition)) {
            return false;
        }
        return id != null && id.equals(((DisplayCondition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DisplayCondition{" +
            "id=" + getId() +
            ", questionIdentifier='" + getQuestionIdentifier() + "'" +
            ", itemIdentifier='" + getItemIdentifier() + "'" +
            "}";
    }
}
