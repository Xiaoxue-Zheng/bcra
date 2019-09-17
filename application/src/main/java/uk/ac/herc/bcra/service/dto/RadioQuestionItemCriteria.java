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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.RadioQuestionItem} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.RadioQuestionItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /radio-question-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RadioQuestionItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uuid;

    private StringFilter label;

    private StringFilter descriptor;

    private LongFilter radioQuestionId;

    public RadioQuestionItemCriteria(){
    }

    public RadioQuestionItemCriteria(RadioQuestionItemCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.label = other.label == null ? null : other.label.copy();
        this.descriptor = other.descriptor == null ? null : other.descriptor.copy();
        this.radioQuestionId = other.radioQuestionId == null ? null : other.radioQuestionId.copy();
    }

    @Override
    public RadioQuestionItemCriteria copy() {
        return new RadioQuestionItemCriteria(this);
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

    public LongFilter getRadioQuestionId() {
        return radioQuestionId;
    }

    public void setRadioQuestionId(LongFilter radioQuestionId) {
        this.radioQuestionId = radioQuestionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RadioQuestionItemCriteria that = (RadioQuestionItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(label, that.label) &&
            Objects.equals(descriptor, that.descriptor) &&
            Objects.equals(radioQuestionId, that.radioQuestionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uuid,
        label,
        descriptor,
        radioQuestionId
        );
    }

    @Override
    public String toString() {
        return "RadioQuestionItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (label != null ? "label=" + label + ", " : "") +
                (descriptor != null ? "descriptor=" + descriptor + ", " : "") +
                (radioQuestionId != null ? "radioQuestionId=" + radioQuestionId + ", " : "") +
            "}";
    }

}
