package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;

import uk.ac.herc.bcra.domain.enumeration.QuestionType;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identifier", nullable = false, unique = true)
    private QuestionIdentifier identifier;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private QuestionType type;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "minimum")
    private Integer minimum;

    @Column(name = "maximum")
    private Integer maximum;

    @OneToMany(mappedBy = "displayQuestion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DisplayCondition> displayConditions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("questions")
    private QuestionGroup questionGroup;

    @OneToMany(mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionItem> questionItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionIdentifier getIdentifier() {
        return identifier;
    }

    public Question identifier(QuestionIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(QuestionIdentifier identifier) {
        this.identifier = identifier;
    }

    public QuestionType getType() {
        return type;
    }

    public Question type(QuestionType type) {
        this.type = type;
        return this;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public Question order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public Question text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public Question minimum(Integer minimum) {
        this.minimum = minimum;
        return this;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public Question maximum(Integer maximum) {
        this.maximum = maximum;
        return this;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Set<DisplayCondition> getDisplayConditions() {
        return displayConditions;
    }

    public Question displayConditions(Set<DisplayCondition> displayConditions) {
        this.displayConditions = displayConditions;
        return this;
    }

    public Question addDisplayCondition(DisplayCondition displayCondition) {
        this.displayConditions.add(displayCondition);
        displayCondition.setDisplayQuestion(this);
        return this;
    }

    public Question removeDisplayCondition(DisplayCondition displayCondition) {
        this.displayConditions.remove(displayCondition);
        displayCondition.setDisplayQuestion(null);
        return this;
    }

    public void setDisplayConditions(Set<DisplayCondition> displayConditions) {
        this.displayConditions = displayConditions;
    }

    public QuestionGroup getQuestionGroup() {
        return questionGroup;
    }

    public Question questionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
        return this;
    }

    public void setQuestionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
    }

    public Set<QuestionItem> getQuestionItems() {
        return questionItems;
    }

    public Question questionItems(Set<QuestionItem> questionItems) {
        this.questionItems = questionItems;
        return this;
    }

    public Question addQuestionItem(QuestionItem questionItem) {
        this.questionItems.add(questionItem);
        questionItem.setQuestion(this);
        return this;
    }

    public Question removeQuestionItem(QuestionItem questionItem) {
        this.questionItems.remove(questionItem);
        questionItem.setQuestion(null);
        return this;
    }

    public void setQuestionItems(Set<QuestionItem> questionItems) {
        this.questionItems = questionItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", type='" + getType() + "'" +
            ", order=" + getOrder() +
            ", text='" + getText() + "'" +
            ", minimum=" + getMinimum() +
            ", maximum=" + getMaximum() +
            "}";
    }
}
