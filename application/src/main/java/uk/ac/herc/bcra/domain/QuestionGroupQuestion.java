package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A QuestionGroupQuestion.
 */
@Entity
@Table(name = "question_group_question")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionGroupQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @ManyToOne
    @JsonIgnoreProperties("questionGroupQuestions")
    private QuestionGroup questionGroup;

    @ManyToOne
    @JsonIgnoreProperties("questionGroupQuestions")
    private Question question;

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

    public QuestionGroupQuestion uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getOrder() {
        return order;
    }

    public QuestionGroupQuestion order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public QuestionGroup getQuestionGroup() {
        return questionGroup;
    }

    public QuestionGroupQuestion questionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
        return this;
    }

    public void setQuestionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
    }

    public Question getQuestion() {
        return question;
    }

    public QuestionGroupQuestion question(Question question) {
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
        if (!(o instanceof QuestionGroupQuestion)) {
            return false;
        }
        return id != null && id.equals(((QuestionGroupQuestion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "QuestionGroupQuestion{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
