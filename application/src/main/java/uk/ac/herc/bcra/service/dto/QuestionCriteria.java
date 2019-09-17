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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.Question} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.QuestionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uuid;

    private StringFilter text;

    private LongFilter questionGroupQuestionId;

    private LongFilter answerId;

    public QuestionCriteria(){
    }

    public QuestionCriteria(QuestionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.text = other.text == null ? null : other.text.copy();
        this.questionGroupQuestionId = other.questionGroupQuestionId == null ? null : other.questionGroupQuestionId.copy();
        this.answerId = other.answerId == null ? null : other.answerId.copy();
    }

    @Override
    public QuestionCriteria copy() {
        return new QuestionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUuid() {
        return uuid;
    }

    public void setUuid(StringFilter uuid) {
        this.uuid = uuid;
    }

    public StringFilter getText() {
        return text;
    }

    public void setText(StringFilter text) {
        this.text = text;
    }

    public LongFilter getQuestionGroupQuestionId() {
        return questionGroupQuestionId;
    }

    public void setQuestionGroupQuestionId(LongFilter questionGroupQuestionId) {
        this.questionGroupQuestionId = questionGroupQuestionId;
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
        final QuestionCriteria that = (QuestionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(text, that.text) &&
            Objects.equals(questionGroupQuestionId, that.questionGroupQuestionId) &&
            Objects.equals(answerId, that.answerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uuid,
        text,
        questionGroupQuestionId,
        answerId
        );
    }

    @Override
    public String toString() {
        return "QuestionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (text != null ? "text=" + text + ", " : "") +
                (questionGroupQuestionId != null ? "questionGroupQuestionId=" + questionGroupQuestionId + ", " : "") +
                (answerId != null ? "answerId=" + answerId + ", " : "") +
            "}";
    }

}
