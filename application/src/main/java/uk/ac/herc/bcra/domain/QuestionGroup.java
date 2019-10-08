package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionGroupIdentifier;

/**
 * A QuestionGroup.
 */
@Entity
@Table(name = "question_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identifier", nullable = false, unique = true)
    private QuestionGroupIdentifier identifier;

    @OneToMany(mappedBy = "questionGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionSection> questionSections = new HashSet<>();

    @OneToMany(mappedBy = "questionGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Question> questions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionGroupIdentifier getIdentifier() {
        return identifier;
    }

    public QuestionGroup identifier(QuestionGroupIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(QuestionGroupIdentifier identifier) {
        this.identifier = identifier;
    }

    public Set<QuestionSection> getQuestionSections() {
        return questionSections;
    }

    public QuestionGroup questionSections(Set<QuestionSection> questionSections) {
        this.questionSections = questionSections;
        return this;
    }

    public QuestionGroup addQuestionSection(QuestionSection questionSection) {
        this.questionSections.add(questionSection);
        questionSection.setQuestionGroup(this);
        return this;
    }

    public QuestionGroup removeQuestionSection(QuestionSection questionSection) {
        this.questionSections.remove(questionSection);
        questionSection.setQuestionGroup(null);
        return this;
    }

    public void setQuestionSections(Set<QuestionSection> questionSections) {
        this.questionSections = questionSections;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public QuestionGroup questions(Set<Question> questions) {
        this.questions = questions;
        return this;
    }

    public QuestionGroup addQuestion(Question question) {
        this.questions.add(question);
        question.setQuestionGroup(this);
        return this;
    }

    public QuestionGroup removeQuestion(Question question) {
        this.questions.remove(question);
        question.setQuestionGroup(null);
        return this;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
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
            ", identifier='" + getIdentifier() + "'" +
            "}";
    }
}
