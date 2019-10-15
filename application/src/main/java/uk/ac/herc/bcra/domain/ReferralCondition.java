package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import uk.ac.herc.bcra.domain.enumeration.ReferralConditionType;

import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;

import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;

/**
 * A ReferralCondition.
 */
@Entity
@Table(name = "referral_condition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReferralCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "and_group")
    private Integer andGroup;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ReferralConditionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_identifier")
    private QuestionIdentifier questionIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_identifier")
    private QuestionItemIdentifier itemIdentifier;

    @Column(name = "number")
    private Integer number;

    @ManyToOne
    @JsonIgnoreProperties("referralConditions")
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAndGroup() {
        return andGroup;
    }

    public ReferralCondition andGroup(Integer andGroup) {
        this.andGroup = andGroup;
        return this;
    }

    public void setAndGroup(Integer andGroup) {
        this.andGroup = andGroup;
    }

    public ReferralConditionType getType() {
        return type;
    }

    public ReferralCondition type(ReferralConditionType type) {
        this.type = type;
        return this;
    }

    public void setType(ReferralConditionType type) {
        this.type = type;
    }

    public QuestionIdentifier getQuestionIdentifier() {
        return questionIdentifier;
    }

    public ReferralCondition questionIdentifier(QuestionIdentifier questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
        return this;
    }

    public void setQuestionIdentifier(QuestionIdentifier questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public QuestionItemIdentifier getItemIdentifier() {
        return itemIdentifier;
    }

    public ReferralCondition itemIdentifier(QuestionItemIdentifier itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
        return this;
    }

    public void setItemIdentifier(QuestionItemIdentifier itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
    }

    public Integer getNumber() {
        return number;
    }

    public ReferralCondition number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Question getQuestion() {
        return question;
    }

    public ReferralCondition question(Question question) {
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
        if (!(o instanceof ReferralCondition)) {
            return false;
        }
        return id != null && id.equals(((ReferralCondition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ReferralCondition{" +
            "id=" + getId() +
            ", andGroup=" + getAndGroup() +
            ", type='" + getType() + "'" +
            ", questionIdentifier='" + getQuestionIdentifier() + "'" +
            ", itemIdentifier='" + getItemIdentifier() + "'" +
            ", number=" + getNumber() +
            "}";
    }
}
