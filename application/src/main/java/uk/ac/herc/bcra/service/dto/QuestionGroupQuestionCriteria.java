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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.QuestionGroupQuestion} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.QuestionGroupQuestionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /question-group-questions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionGroupQuestionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uuid;

    private StringFilter questionGroupUuid;

    private StringFilter questionUuid;

    private IntegerFilter order;

    private LongFilter questionGroupId;

    private LongFilter questionId;

    public QuestionGroupQuestionCriteria(){
    }

    public QuestionGroupQuestionCriteria(QuestionGroupQuestionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.questionGroupUuid = other.questionGroupUuid == null ? null : other.questionGroupUuid.copy();
        this.questionUuid = other.questionUuid == null ? null : other.questionUuid.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.questionGroupId = other.questionGroupId == null ? null : other.questionGroupId.copy();
        this.questionId = other.questionId == null ? null : other.questionId.copy();
    }

    @Override
    public QuestionGroupQuestionCriteria copy() {
        return new QuestionGroupQuestionCriteria(this);
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

    public StringFilter getQuestionGroupUuid() {
        return questionGroupUuid;
    }

    public void setQuestionGroupUuid(StringFilter questionGroupUuid) {
        this.questionGroupUuid = questionGroupUuid;
    }

    public StringFilter getQuestionUuid() {
        return questionUuid;
    }

    public void setQuestionUuid(StringFilter questionUuid) {
        this.questionUuid = questionUuid;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public LongFilter getQuestionGroupId() {
        return questionGroupId;
    }

    public void setQuestionGroupId(LongFilter questionGroupId) {
        this.questionGroupId = questionGroupId;
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
        final QuestionGroupQuestionCriteria that = (QuestionGroupQuestionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(questionGroupUuid, that.questionGroupUuid) &&
            Objects.equals(questionUuid, that.questionUuid) &&
            Objects.equals(order, that.order) &&
            Objects.equals(questionGroupId, that.questionGroupId) &&
            Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uuid,
        questionGroupUuid,
        questionUuid,
        order,
        questionGroupId,
        questionId
        );
    }

    @Override
    public String toString() {
        return "QuestionGroupQuestionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (questionGroupUuid != null ? "questionGroupUuid=" + questionGroupUuid + ", " : "") +
                (questionUuid != null ? "questionUuid=" + questionUuid + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (questionGroupId != null ? "questionGroupId=" + questionGroupId + ", " : "") +
                (questionId != null ? "questionId=" + questionId + ", " : "") +
            "}";
    }

}
