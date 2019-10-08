package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import uk.ac.herc.bcra.domain.enumeration.DisplayConditionType;

import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "display_condition_type", nullable = false)
    private DisplayConditionType displayConditionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_section_identifier")
    private QuestionSectionIdentifier conditionSectionIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_question_identifier")
    private QuestionIdentifier conditionQuestionIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_question_item_identifier")
    private QuestionItemIdentifier conditionQuestionItemIdentifier;

    @ManyToOne
    @JsonIgnoreProperties("displayConditions")
    private QuestionSection displayQuestionSection;

    @ManyToOne
    @JsonIgnoreProperties("displayConditions")
    private Question displayQuestion;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DisplayConditionType getDisplayConditionType() {
        return displayConditionType;
    }

    public DisplayCondition displayConditionType(DisplayConditionType displayConditionType) {
        this.displayConditionType = displayConditionType;
        return this;
    }

    public void setDisplayConditionType(DisplayConditionType displayConditionType) {
        this.displayConditionType = displayConditionType;
    }

    public QuestionSectionIdentifier getConditionSectionIdentifier() {
        return conditionSectionIdentifier;
    }

    public DisplayCondition conditionSectionIdentifier(QuestionSectionIdentifier conditionSectionIdentifier) {
        this.conditionSectionIdentifier = conditionSectionIdentifier;
        return this;
    }

    public void setConditionSectionIdentifier(QuestionSectionIdentifier conditionSectionIdentifier) {
        this.conditionSectionIdentifier = conditionSectionIdentifier;
    }

    public QuestionIdentifier getConditionQuestionIdentifier() {
        return conditionQuestionIdentifier;
    }

    public DisplayCondition conditionQuestionIdentifier(QuestionIdentifier conditionQuestionIdentifier) {
        this.conditionQuestionIdentifier = conditionQuestionIdentifier;
        return this;
    }

    public void setConditionQuestionIdentifier(QuestionIdentifier conditionQuestionIdentifier) {
        this.conditionQuestionIdentifier = conditionQuestionIdentifier;
    }

    public QuestionItemIdentifier getConditionQuestionItemIdentifier() {
        return conditionQuestionItemIdentifier;
    }

    public DisplayCondition conditionQuestionItemIdentifier(QuestionItemIdentifier conditionQuestionItemIdentifier) {
        this.conditionQuestionItemIdentifier = conditionQuestionItemIdentifier;
        return this;
    }

    public void setConditionQuestionItemIdentifier(QuestionItemIdentifier conditionQuestionItemIdentifier) {
        this.conditionQuestionItemIdentifier = conditionQuestionItemIdentifier;
    }

    public QuestionSection getDisplayQuestionSection() {
        return displayQuestionSection;
    }

    public DisplayCondition displayQuestionSection(QuestionSection questionSection) {
        this.displayQuestionSection = questionSection;
        return this;
    }

    public void setDisplayQuestionSection(QuestionSection questionSection) {
        this.displayQuestionSection = questionSection;
    }

    public Question getDisplayQuestion() {
        return displayQuestion;
    }

    public DisplayCondition displayQuestion(Question question) {
        this.displayQuestion = question;
        return this;
    }

    public void setDisplayQuestion(Question question) {
        this.displayQuestion = question;
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
            ", displayConditionType='" + getDisplayConditionType() + "'" +
            ", conditionSectionIdentifier='" + getConditionSectionIdentifier() + "'" +
            ", conditionQuestionIdentifier='" + getConditionQuestionIdentifier() + "'" +
            ", conditionQuestionItemIdentifier='" + getConditionQuestionItemIdentifier() + "'" +
            "}";
    }
}
