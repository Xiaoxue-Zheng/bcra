package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A QuestionGroup.
 */
@Entity
@Table(
    name = "question_group",
    indexes = {
        @Index(
            name = "question_group_uuid_index",
            columnList = "uuid",
            unique = true
        )
    }
)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @OneToMany(mappedBy = "questionGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DisplayCondition> displayConditions = new HashSet<>();

    @OneToMany(mappedBy = "questionGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionnaireQuestionGroup> questionnaireQuestionGroups = new HashSet<>();

    @OneToMany(mappedBy = "questionGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionGroupQuestion> questionGroupQuestions = new HashSet<>();

    @OneToMany(mappedBy = "questionGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnswerGroup> answerGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public QuestionGroup uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Set<DisplayCondition> getDisplayConditions() {
        return displayConditions;
    }

    public QuestionGroup displayConditions(Set<DisplayCondition> displayConditions) {
        this.displayConditions = displayConditions;
        return this;
    }

    public QuestionGroup addDisplayCondition(DisplayCondition displayCondition) {
        this.displayConditions.add(displayCondition);
        displayCondition.setQuestionGroup(this);
        return this;
    }

    public QuestionGroup removeDisplayCondition(DisplayCondition displayCondition) {
        this.displayConditions.remove(displayCondition);
        displayCondition.setQuestionGroup(null);
        return this;
    }

    public void setDisplayConditions(Set<DisplayCondition> displayConditions) {
        this.displayConditions = displayConditions;
    }

    public Set<QuestionnaireQuestionGroup> getQuestionnaireQuestionGroups() {
        return questionnaireQuestionGroups;
    }

    public QuestionGroup questionnaireQuestionGroups(Set<QuestionnaireQuestionGroup> questionnaireQuestionGroups) {
        this.questionnaireQuestionGroups = questionnaireQuestionGroups;
        return this;
    }

    public QuestionGroup addQuestionnaireQuestionGroup(QuestionnaireQuestionGroup questionnaireQuestionGroup) {
        this.questionnaireQuestionGroups.add(questionnaireQuestionGroup);
        questionnaireQuestionGroup.setQuestionGroup(this);
        return this;
    }

    public QuestionGroup removeQuestionnaireQuestionGroup(QuestionnaireQuestionGroup questionnaireQuestionGroup) {
        this.questionnaireQuestionGroups.remove(questionnaireQuestionGroup);
        questionnaireQuestionGroup.setQuestionGroup(null);
        return this;
    }

    public void setQuestionnaireQuestionGroups(Set<QuestionnaireQuestionGroup> questionnaireQuestionGroups) {
        this.questionnaireQuestionGroups = questionnaireQuestionGroups;
    }

    public Set<QuestionGroupQuestion> getQuestionGroupQuestions() {
        return questionGroupQuestions;
    }

    public QuestionGroup questionGroupQuestions(Set<QuestionGroupQuestion> questionGroupQuestions) {
        this.questionGroupQuestions = questionGroupQuestions;
        return this;
    }

    public QuestionGroup addQuestionGroupQuestion(QuestionGroupQuestion questionGroupQuestion) {
        this.questionGroupQuestions.add(questionGroupQuestion);
        questionGroupQuestion.setQuestionGroup(this);
        return this;
    }

    public QuestionGroup removeQuestionGroupQuestion(QuestionGroupQuestion questionGroupQuestion) {
        this.questionGroupQuestions.remove(questionGroupQuestion);
        questionGroupQuestion.setQuestionGroup(null);
        return this;
    }

    public void setQuestionGroupQuestions(Set<QuestionGroupQuestion> questionGroupQuestions) {
        this.questionGroupQuestions = questionGroupQuestions;
    }

    public Set<AnswerGroup> getAnswerGroups() {
        return answerGroups;
    }

    public QuestionGroup answerGroups(Set<AnswerGroup> answerGroups) {
        this.answerGroups = answerGroups;
        return this;
    }

    public QuestionGroup addAnswerGroup(AnswerGroup answerGroup) {
        this.answerGroups.add(answerGroup);
        answerGroup.setQuestionGroup(this);
        return this;
    }

    public QuestionGroup removeAnswerGroup(AnswerGroup answerGroup) {
        this.answerGroups.remove(answerGroup);
        answerGroup.setQuestionGroup(null);
        return this;
    }

    public void setAnswerGroups(Set<AnswerGroup> answerGroups) {
        this.answerGroups = answerGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionGroup)) {
            return false;
        }
        return id != null && id.equals(((QuestionGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "QuestionGroup{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
