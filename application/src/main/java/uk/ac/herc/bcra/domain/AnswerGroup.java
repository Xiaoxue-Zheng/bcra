package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AnswerGroup.
 */
@Entity
@Table(name = "answer_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnswerGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("answerGroups")
    private AnswerResponse answerResponse;

    @ManyToOne
    @JsonIgnoreProperties("answerGroups")
    private QuestionGroup questionGroup;

    @OneToMany(mappedBy = "answerGroup")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Answer> answers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerResponse getAnswerResponse() {
        return answerResponse;
    }

    public AnswerGroup answerResponse(AnswerResponse answerResponse) {
        this.answerResponse = answerResponse;
        return this;
    }

    public void setAnswerResponse(AnswerResponse answerResponse) {
        this.answerResponse = answerResponse;
    }

    public QuestionGroup getQuestionGroup() {
        return questionGroup;
    }

    public AnswerGroup questionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
        return this;
    }

    public void setQuestionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public AnswerGroup answers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public AnswerGroup addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setAnswerGroup(this);
        return this;
    }

    public AnswerGroup removeAnswer(Answer answer) {
        this.answers.remove(answer);
        answer.setAnswerGroup(null);
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
        if (!(o instanceof AnswerGroup)) {
            return false;
        }
        return id != null && id.equals(((AnswerGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnswerGroup{" +
            "id=" + getId() +
            "}";
    }
}
