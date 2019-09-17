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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.RadioAnswerItem} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.RadioAnswerItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /radio-answer-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RadioAnswerItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter radioAnswerId;

    public RadioAnswerItemCriteria(){
    }

    public RadioAnswerItemCriteria(RadioAnswerItemCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.radioAnswerId = other.radioAnswerId == null ? null : other.radioAnswerId.copy();
    }

    @Override
    public RadioAnswerItemCriteria copy() {
        return new RadioAnswerItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getRadioAnswerId() {
        return radioAnswerId;
    }

    public void setRadioAnswerId(LongFilter radioAnswerId) {
        this.radioAnswerId = radioAnswerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RadioAnswerItemCriteria that = (RadioAnswerItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(radioAnswerId, that.radioAnswerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        radioAnswerId
        );
    }

    @Override
    public String toString() {
        return "RadioAnswerItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (radioAnswerId != null ? "radioAnswerId=" + radioAnswerId + ", " : "") +
            "}";
    }

}
