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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.CheckboxAnswerItem} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.CheckboxAnswerItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /checkbox-answer-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckboxAnswerItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter checkboxAnswerId;

    private LongFilter checkboxQuestionItemId;

    public CheckboxAnswerItemCriteria(){
    }

    public CheckboxAnswerItemCriteria(CheckboxAnswerItemCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.checkboxAnswerId = other.checkboxAnswerId == null ? null : other.checkboxAnswerId.copy();
        this.checkboxQuestionItemId = other.checkboxQuestionItemId == null ? null : other.checkboxQuestionItemId.copy();
    }

    @Override
    public CheckboxAnswerItemCriteria copy() {
        return new CheckboxAnswerItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCheckboxAnswerId() {
        return checkboxAnswerId;
    }

    public void setCheckboxAnswerId(LongFilter checkboxAnswerId) {
        this.checkboxAnswerId = checkboxAnswerId;
    }

    public LongFilter getCheckboxQuestionItemId() {
        return checkboxQuestionItemId;
    }

    public void setCheckboxQuestionItemId(LongFilter checkboxQuestionItemId) {
        this.checkboxQuestionItemId = checkboxQuestionItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CheckboxAnswerItemCriteria that = (CheckboxAnswerItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(checkboxAnswerId, that.checkboxAnswerId) &&
            Objects.equals(checkboxQuestionItemId, that.checkboxQuestionItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        checkboxAnswerId,
        checkboxQuestionItemId
        );
    }

    @Override
    public String toString() {
        return "CheckboxAnswerItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (checkboxAnswerId != null ? "checkboxAnswerId=" + checkboxAnswerId + ", " : "") +
                (checkboxQuestionItemId != null ? "checkboxQuestionItemId=" + checkboxQuestionItemId + ", " : "") +
            "}";
    }

}
