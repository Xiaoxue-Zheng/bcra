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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.RepeatQuestion} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.RepeatQuestionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /repeat-questions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RepeatQuestionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter maximum;

    private LongFilter repeatDisplayConditionId;

    public RepeatQuestionCriteria(){
    }

    public RepeatQuestionCriteria(RepeatQuestionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.maximum = other.maximum == null ? null : other.maximum.copy();
        this.repeatDisplayConditionId = other.repeatDisplayConditionId == null ? null : other.repeatDisplayConditionId.copy();
    }

    @Override
    public RepeatQuestionCriteria copy() {
        return new RepeatQuestionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getMaximum() {
        return maximum;
    }

    public void setMaximum(IntegerFilter maximum) {
        this.maximum = maximum;
    }

    public LongFilter getRepeatDisplayConditionId() {
        return repeatDisplayConditionId;
    }

    public void setRepeatDisplayConditionId(LongFilter repeatDisplayConditionId) {
        this.repeatDisplayConditionId = repeatDisplayConditionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RepeatQuestionCriteria that = (RepeatQuestionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(maximum, that.maximum) &&
            Objects.equals(repeatDisplayConditionId, that.repeatDisplayConditionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        maximum,
        repeatDisplayConditionId
        );
    }

    @Override
    public String toString() {
        return "RepeatQuestionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (maximum != null ? "maximum=" + maximum + ", " : "") +
                (repeatDisplayConditionId != null ? "repeatDisplayConditionId=" + repeatDisplayConditionId + ", " : "") +
            "}";
    }

}
