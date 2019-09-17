package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Question.
 */
@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy=InheritanceType.JOINED)
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @OneToMany(mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionGroupQuestion> questionGroupQuestions = new HashSet<>();

    @OneToMany(mappedBy = "question")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answer> answers = new HashSet<>();

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

    public Question uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Set<QuestionGroupQuestion> getQuestionGroupQuestions() {
        return questionGroupQuestions;
    }

    public Question questionGroupQuestions(Set<QuestionGroupQuestion> questionGroupQuestions) {
        this.questionGroupQuestions = questionGroupQuestions;
        return this;
    }

    public Question addQuestionGroupQuestion(QuestionGroupQuestion questionGroupQuestion) {
        this.questionGroupQuestions.add(questionGroupQuestion);
        questionGroupQuestion.setQuestion(this);
        return this;
    }

    public Question removeQuestionGroupQuestion(QuestionGroupQuestion questionGroupQuestion) {
        this.questionGroupQuestions.remove(questionGroupQuestion);
        questionGroupQuestion.setQuestion(null);
        return this;
    }

    public void setQuestionGroupQuestions(Set<QuestionGroupQuestion> questionGroupQuestions) {
        this.questionGroupQuestions = questionGroupQuestions;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public Question answers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public Question addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
        return this;
    }

    public Question removeAnswer(Answer answer) {
        this.answers.remove(answer);
        answer.setQuestion(null);
        return this;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
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
            ", uuid='" + getUuid() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
