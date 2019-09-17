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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.AnswerGroup} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.AnswerGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /answer-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnswerGroupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter answerResponseId;

    private LongFilter questionGroupId;

    private LongFilter answerId;

    public AnswerGroupCriteria(){
    }

    public AnswerGroupCriteria(AnswerGroupCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.answerResponseId = other.answerResponseId == null ? null : other.answerResponseId.copy();
        this.questionGroupId = other.questionGroupId == null ? null : other.questionGroupId.copy();
        this.answerId = other.answerId == null ? null : other.answerId.copy();
    }

    @Override
    public AnswerGroupCriteria copy() {
        return new AnswerGroupCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getAnswerResponseId() {
        return answerResponseId;
    }

    public void setAnswerResponseId(LongFilter answerResponseId) {
        this.answerResponseId = answerResponseId;
    }

    public LongFilter getQuestionGroupId() {
        return questionGroupId;
    }

    public void setQuestionGroupId(LongFilter questionGroupId) {
        this.questionGroupId = questionGroupId;
    }

    public LongFilter getAnswerId() {
        return answerId;
    }

    public void setAnswerId(LongFilter answerId) {
        this.answerId = answerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnswerGroupCriteria that = (AnswerGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(answerResponseId, that.answerResponseId) &&
            Objects.equals(questionGroupId, that.questionGroupId) &&
            Objects.equals(answerId, that.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        answerResponseId,
        questionGroupId,
        answerId
        );
    }

    @Override
    public String toString() {
        return "AnswerGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (answerResponseId != null ? "answerResponseId=" + answerResponseId + ", " : "") +
                (questionGroupId != null ? "questionGroupId=" + questionGroupId + ", " : "") +
                (answerId != null ? "answerId=" + answerId + ", " : "") +
            "}";
    }

}
