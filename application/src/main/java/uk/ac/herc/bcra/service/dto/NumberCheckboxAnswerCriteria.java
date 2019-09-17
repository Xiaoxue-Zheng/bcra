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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.NumberCheckboxAnswer} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.NumberCheckboxAnswerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /number-checkbox-answers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NumberCheckboxAnswerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter number;

    private BooleanFilter check;

    public NumberCheckboxAnswerCriteria(){
    }

    public NumberCheckboxAnswerCriteria(NumberCheckboxAnswerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.number = other.number == null ? null : other.number.copy();
        this.check = other.check == null ? null : other.check.copy();
    }

    @Override
    public NumberCheckboxAnswerCriteria copy() {
        return new NumberCheckboxAnswerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNumber() {
        return number;
    }

    public void setNumber(IntegerFilter number) {
        this.number = number;
    }

    public BooleanFilter getCheck() {
        return check;
    }

    public void setCheck(BooleanFilter check) {
        this.check = check;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NumberCheckboxAnswerCriteria that = (NumberCheckboxAnswerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(number, that.number) &&
            Objects.equals(check, that.check);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        number,
        check
        );
    }

    @Override
    public String toString() {
        return "NumberCheckboxAnswerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (number != null ? "number=" + number + ", " : "") +
                (check != null ? "check=" + check + ", " : "") +
            "}";
    }

}
