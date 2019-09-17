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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.RepeatAnswer} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.RepeatAnswerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /repeat-answers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RepeatAnswerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter quantity;

    public RepeatAnswerCriteria(){
    }

    public RepeatAnswerCriteria(RepeatAnswerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
    }

    @Override
    public RepeatAnswerCriteria copy() {
        return new RepeatAnswerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RepeatAnswerCriteria that = (RepeatAnswerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantity
        );
    }

    @Override
    public String toString() {
        return "RepeatAnswerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
            "}";
    }

}
