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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.CheckboxAnswer} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.CheckboxAnswerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /checkbox-answers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckboxAnswerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter checkboxAnswerItemId;

    public CheckboxAnswerCriteria(){
    }

    public CheckboxAnswerCriteria(CheckboxAnswerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.checkboxAnswerItemId = other.checkboxAnswerItemId == null ? null : other.checkboxAnswerItemId.copy();
    }

    @Override
    public CheckboxAnswerCriteria copy() {
        return new CheckboxAnswerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCheckboxAnswerItemId() {
        return checkboxAnswerItemId;
    }

    public void setCheckboxAnswerItemId(LongFilter checkboxAnswerItemId) {
        this.checkboxAnswerItemId = checkboxAnswerItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CheckboxAnswerCriteria that = (CheckboxAnswerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(checkboxAnswerItemId, that.checkboxAnswerItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        checkboxAnswerItemId
        );
    }

    @Override
    public String toString() {
        return "CheckboxAnswerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (checkboxAnswerItemId != null ? "checkboxAnswerItemId=" + checkboxAnswerItemId + ", " : "") +
            "}";
    }

}
