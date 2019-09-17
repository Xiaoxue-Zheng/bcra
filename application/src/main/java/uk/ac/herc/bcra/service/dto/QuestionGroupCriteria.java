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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.QuestionGroup} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.QuestionGroupResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /question-groups?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionGroupCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uuid;

    private LongFilter displayConditionId;

    private LongFilter questionnaireQuestionGroupId;

    private LongFilter questionGroupQuestionId;

    private LongFilter answerGroupId;

    public QuestionGroupCriteria(){
    }

    public QuestionGroupCriteria(QuestionGroupCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.displayConditionId = other.displayConditionId == null ? null : other.displayConditionId.copy();
        this.questionnaireQuestionGroupId = other.questionnaireQuestionGroupId == null ? null : other.questionnaireQuestionGroupId.copy();
        this.questionGroupQuestionId = other.questionGroupQuestionId == null ? null : other.questionGroupQuestionId.copy();
        this.answerGroupId = other.answerGroupId == null ? null : other.answerGroupId.copy();
    }

    @Override
    public QuestionGroupCriteria copy() {
        return new QuestionGroupCriteria(this);
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

    public LongFilter getDisplayConditionId() {
        return displayConditionId;
    }

    public void setDisplayConditionId(LongFilter displayConditionId) {
        this.displayConditionId = displayConditionId;
    }

    public LongFilter getQuestionnaireQuestionGroupId() {
        return questionnaireQuestionGroupId;
    }

    public void setQuestionnaireQuestionGroupId(LongFilter questionnaireQuestionGroupId) {
        this.questionnaireQuestionGroupId = questionnaireQuestionGroupId;
    }

    public LongFilter getQuestionGroupQuestionId() {
        return questionGroupQuestionId;
    }

    public void setQuestionGroupQuestionId(LongFilter questionGroupQuestionId) {
        this.questionGroupQuestionId = questionGroupQuestionId;
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
        final QuestionGroupCriteria that = (QuestionGroupCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(displayConditionId, that.displayConditionId) &&
            Objects.equals(questionnaireQuestionGroupId, that.questionnaireQuestionGroupId) &&
            Objects.equals(questionGroupQuestionId, that.questionGroupQuestionId) &&
            Objects.equals(answerGroupId, that.answerGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uuid,
        displayConditionId,
        questionnaireQuestionGroupId,
        questionGroupQuestionId,
        answerGroupId
        );
    }

    @Override
    public String toString() {
        return "QuestionGroupCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (displayConditionId != null ? "displayConditionId=" + displayConditionId + ", " : "") +
                (questionnaireQuestionGroupId != null ? "questionnaireQuestionGroupId=" + questionnaireQuestionGroupId + ", " : "") +
                (questionGroupQuestionId != null ? "questionGroupQuestionId=" + questionGroupQuestionId + ", " : "") +
                (answerGroupId != null ? "answerGroupId=" + answerGroupId + ", " : "") +
            "}";
    }

}
