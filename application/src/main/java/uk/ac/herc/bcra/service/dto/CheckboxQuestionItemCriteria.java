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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.CheckboxQuestionItem} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.CheckboxQuestionItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /checkbox-question-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckboxQuestionItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uuid;

    private StringFilter questionUuid;

    private StringFilter label;

    private StringFilter descriptor;

    private LongFilter checkboxQuestionId;

    private LongFilter checkboxAnswerItemId;

    private LongFilter checkboxDisplayConditionId;

    public CheckboxQuestionItemCriteria(){
    }

    public CheckboxQuestionItemCriteria(CheckboxQuestionItemCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.questionUuid = other.questionUuid == null ? null : other.questionUuid.copy();
        this.label = other.label == null ? null : other.label.copy();
        this.descriptor = other.descriptor == null ? null : other.descriptor.copy();
        this.checkboxQuestionId = other.checkboxQuestionId == null ? null : other.checkboxQuestionId.copy();
        this.checkboxAnswerItemId = other.checkboxAnswerItemId == null ? null : other.checkboxAnswerItemId.copy();
        this.checkboxDisplayConditionId = other.checkboxDisplayConditionId == null ? null : other.checkboxDisplayConditionId.copy();
    }

    @Override
    public CheckboxQuestionItemCriteria copy() {
        return new CheckboxQuestionItemCriteria(this);
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

    public StringFilter getQuestionUuid() {
        return questionUuid;
    }

    public void setQuestionUuid(StringFilter questionUuid) {
        this.questionUuid = questionUuid;
    }

    public StringFilter getLabel() {
        return label;
    }

    public void setLabel(StringFilter label) {
        this.label = label;
    }

    public StringFilter getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(StringFilter descriptor) {
        this.descriptor = descriptor;
    }

    public LongFilter getCheckboxQuestionId() {
        return checkboxQuestionId;
    }

    public void setCheckboxQuestionId(LongFilter checkboxQuestionId) {
        this.checkboxQuestionId = checkboxQuestionId;
    }

    public LongFilter getCheckboxAnswerItemId() {
        return checkboxAnswerItemId;
    }

    public void setCheckboxAnswerItemId(LongFilter checkboxAnswerItemId) {
        this.checkboxAnswerItemId = checkboxAnswerItemId;
    }

    public LongFilter getCheckboxDisplayConditionId() {
        return checkboxDisplayConditionId;
    }

    public void setCheckboxDisplayConditionId(LongFilter checkboxDisplayConditionId) {
        this.checkboxDisplayConditionId = checkboxDisplayConditionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CheckboxQuestionItemCriteria that = (CheckboxQuestionItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(questionUuid, that.questionUuid) &&
            Objects.equals(label, that.label) &&
            Objects.equals(descriptor, that.descriptor) &&
            Objects.equals(checkboxQuestionId, that.checkboxQuestionId) &&
            Objects.equals(checkboxAnswerItemId, that.checkboxAnswerItemId) &&
            Objects.equals(checkboxDisplayConditionId, that.checkboxDisplayConditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uuid,
        questionUuid,
        label,
        descriptor,
        checkboxQuestionId,
        checkboxAnswerItemId,
        checkboxDisplayConditionId
        );
    }

    @Override
    public String toString() {
        return "CheckboxQuestionItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (questionUuid != null ? "questionUuid=" + questionUuid + ", " : "") +
                (label != null ? "label=" + label + ", " : "") +
                (descriptor != null ? "descriptor=" + descriptor + ", " : "") +
                (checkboxQuestionId != null ? "checkboxQuestionId=" + checkboxQuestionId + ", " : "") +
                (checkboxAnswerItemId != null ? "checkboxAnswerItemId=" + checkboxAnswerItemId + ", " : "") +
                (checkboxDisplayConditionId != null ? "checkboxDisplayConditionId=" + checkboxDisplayConditionId + ", " : "") +
            "}";
    }

}
