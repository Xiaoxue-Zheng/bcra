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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.AnswerResponse} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.AnswerResponseResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /answer-responses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnswerResponseCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter questionnaireId;

    private LongFilter answerGroupId;

    public AnswerResponseCriteria(){
    }

    public AnswerResponseCriteria(AnswerResponseCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.questionnaireId = other.questionnaireId == null ? null : other.questionnaireId.copy();
        this.answerGroupId = other.answerGroupId == null ? null : other.answerGroupId.copy();
    }

    @Override
    public AnswerResponseCriteria copy() {
        return new AnswerResponseCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(LongFilter questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public LongFilter getAnswerGroupId() {
        return answerGroupId;
    }

    public void setAnswerGroupId(LongFilter answerGroupId) {
        this.answerGroupId = answerGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnswerResponseCriteria that = (AnswerResponseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(questionnaireId, that.questionnaireId) &&
            Objects.equals(answerGroupId, that.answerGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        questionnaireId,
        answerGroupId
        );
    }

    @Override
    public String toString() {
        return "AnswerResponseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (questionnaireId != null ? "questionnaireId=" + questionnaireId + ", " : "") +
                (answerGroupId != null ? "answerGroupId=" + answerGroupId + ", " : "") +
            "}";
    }

}
