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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.CheckboxQuestion} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.CheckboxQuestionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /checkbox-questions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckboxQuestionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter checkboxQuestionItemId;

    public CheckboxQuestionCriteria(){
    }

    public CheckboxQuestionCriteria(CheckboxQuestionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.checkboxQuestionItemId = other.checkboxQuestionItemId == null ? null : other.checkboxQuestionItemId.copy();
    }

    @Override
    public CheckboxQuestionCriteria copy() {
        return new CheckboxQuestionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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
        final CheckboxQuestionCriteria that = (CheckboxQuestionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(checkboxQuestionItemId, that.checkboxQuestionItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        checkboxQuestionItemId
        );
    }

    @Override
    public String toString() {
        return "CheckboxQuestionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (checkboxQuestionItemId != null ? "checkboxQuestionItemId=" + checkboxQuestionItemId + ", " : "") +
            "}";
    }

}
