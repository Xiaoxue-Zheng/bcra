package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Questionnaire.
 */
@Entity
@Table(
    name = "questionnaire",
    indexes = {
        @Index(
            name = "questionnaire_uuid_index",
            columnList = "uuid",
            unique = true
        )
    }
)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Questionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @OneToMany(mappedBy = "questionnaire")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionnaireQuestionGroup> questionnaireQuestionGroups = new HashSet<>();

    @OneToMany(mappedBy = "questionnaire")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnswerResponse> answerResponses = new HashSet<>();

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

    public Questionnaire uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Set<QuestionnaireQuestionGroup> getQuestionnaireQuestionGroups() {
        return questionnaireQuestionGroups;
    }

    public Questionnaire questionnaireQuestionGroups(Set<QuestionnaireQuestionGroup> questionnaireQuestionGroups) {
        this.questionnaireQuestionGroups = questionnaireQuestionGroups;
        return this;
    }

    public Questionnaire addQuestionnaireQuestionGroup(QuestionnaireQuestionGroup questionnaireQuestionGroup) {
        this.questionnaireQuestionGroups.add(questionnaireQuestionGroup);
        questionnaireQuestionGroup.setQuestionnaire(this);
        return this;
    }

    public Questionnaire removeQuestionnaireQuestionGroup(QuestionnaireQuestionGroup questionnaireQuestionGroup) {
        this.questionnaireQuestionGroups.remove(questionnaireQuestionGroup);
        questionnaireQuestionGroup.setQuestionnaire(null);
        return this;
    }

    public void setQuestionnaireQuestionGroups(Set<QuestionnaireQuestionGroup> questionnaireQuestionGroups) {
        this.questionnaireQuestionGroups = questionnaireQuestionGroups;
    }

    public Set<AnswerResponse> getAnswerResponses() {
        return answerResponses;
    }

    public Questionnaire answerResponses(Set<AnswerResponse> answerResponses) {
        this.answerResponses = answerResponses;
        return this;
    }

    public Questionnaire addAnswerResponse(AnswerResponse answerResponse) {
        this.answerResponses.add(answerResponse);
        answerResponse.setQuestionnaire(this);
        return this;
    }

    public Questionnaire removeAnswerResponse(AnswerResponse answerResponse) {
        this.answerResponses.remove(answerResponse);
        answerResponse.setQuestionnaire(null);
        return this;
    }

    public void setAnswerResponses(Set<AnswerResponse> answerResponses) {
        this.answerResponses = answerResponses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Questionnaire)) {
            return false;
        }
        return id != null && id.equals(((Questionnaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
