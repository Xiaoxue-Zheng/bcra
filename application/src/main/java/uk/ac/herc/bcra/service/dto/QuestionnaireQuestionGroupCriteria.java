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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.QuestionnaireQuestionGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questionnaire-question-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionnaireQuestionGroupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uuid;

    private IntegerFilter order;

    private LongFilter questionnaireId;

    private LongFilter questionGroupId;

    public QuestionnaireQuestionGroupCriteria(){
    }

    public QuestionnaireQuestionGroupCriteria(QuestionnaireQuestionGroupCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.order = other.order == null ? null : other.order.copy();
        this.questionnaireId = other.questionnaireId == null ? null : other.questionnaireId.copy();
        this.questionGroupId = other.questionGroupId == null ? null : other.questionGroupId.copy();
    }

    @Override
    public QuestionnaireQuestionGroupCriteria copy() {
        return new QuestionnaireQuestionGroupCriteria(this);
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

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public LongFilter getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(LongFilter questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public LongFilter getQuestionGroupId() {
        return questionGroupId;
    }

    public void setQuestionGroupId(LongFilter questionGroupId) {
        this.questionGroupId = questionGroupId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuestionnaireQuestionGroupCriteria that = (QuestionnaireQuestionGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(order, that.order) &&
            Objects.equals(questionnaireId, that.questionnaireId) &&
            Objects.equals(questionGroupId, that.questionGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uuid,
        order,
        questionnaireId,
        questionGroupId
        );
    }

    @Override
    public String toString() {
        return "QuestionnaireQuestionGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (questionnaireId != null ? "questionnaireId=" + questionnaireId + ", " : "") +
                (questionGroupId != null ? "questionGroupId=" + questionGroupId + ", " : "") +
            "}";
    }

}
