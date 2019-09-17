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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.RadioAnswer} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.RadioAnswerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /radio-answers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RadioAnswerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter radioAnswerItemId;

    public RadioAnswerCriteria(){
    }

    public RadioAnswerCriteria(RadioAnswerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.radioAnswerItemId = other.radioAnswerItemId == null ? null : other.radioAnswerItemId.copy();
    }

    @Override
    public RadioAnswerCriteria copy() {
        return new RadioAnswerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getRadioAnswerItemId() {
        return radioAnswerItemId;
    }

    public void setRadioAnswerItemId(LongFilter radioAnswerItemId) {
        this.radioAnswerItemId = radioAnswerItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RadioAnswerCriteria that = (RadioAnswerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(radioAnswerItemId, that.radioAnswerItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        radioAnswerItemId
        );
    }

    @Override
    public String toString() {
        return "RadioAnswerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (radioAnswerItemId != null ? "radioAnswerItemId=" + radioAnswerItemId + ", " : "") +
            "}";
    }

}
