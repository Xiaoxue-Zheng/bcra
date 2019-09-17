package uk.ac.herc.bcra.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link uk.ac.herc.bcra.domain.Answer} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.AnswerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /answers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnswerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter answerGroupId;

    private LongFilter questionId;

    public AnswerCriteria(){
    }

    public AnswerCriteria(AnswerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.answerGroupId = other.answerGroupId == null ? null : other.answerGroupId.copy();
        this.questionId = other.questionId == null ? null : other.questionId.copy();
    }

    @Override
    public AnswerCriteria copy() {
        return new AnswerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getAnswerGroupId() {
        return answerGroupId;
    }

    public void setAnswerGroupId(LongFilter answerGroupId) {
        this.answerGroupId = answerGroupId;
    }

    public LongFilter getQuestionId() {
        return questionId;
    }

    public void setQuestionId(LongFilter questionId) {
        this.questionId = questionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnswerCriteria that = (AnswerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(answerGroupId, that.answerGroupId) &&
            Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        answerGroupId,
        questionId
        );
    }

    @Override
    public String toString() {
        return "AnswerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (answerGroupId != null ? "answerGroupId=" + answerGroupId + ", " : "") +
                (questionId != null ? "questionId=" + questionId + ", " : "") +
            "}";
    }

}
