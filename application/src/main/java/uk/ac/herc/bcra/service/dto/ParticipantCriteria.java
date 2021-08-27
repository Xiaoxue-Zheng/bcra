package uk.ac.herc.bcra.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;

import io.github.jhipster.service.filter.*;

/**
 * Criteria class for the {@link uk.ac.herc.bcra.domain.Participant} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.ParticipantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /participants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ParticipantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter studyId;

    private StringFilter status;

    private LocalDateFilter dateOfBirth;

    public ParticipantCriteria(){
    }

    public ParticipantCriteria(ParticipantCriteria other){
        this.studyId = other.studyId == null? null : other.studyId.copy();
        this.dateOfBirth = other.dateOfBirth == null ? null: other.dateOfBirth.copy();
        this.status = other.status == null? null: other.status.copy();
    }

    @Override
    public ParticipantCriteria copy() {
        return new ParticipantCriteria(this);
    }

    public StringFilter getStudyId() {
        return studyId;
    }

    public void setStudyId(StringFilter studyId) {
        this.studyId = studyId;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LocalDateFilter getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateFilter dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipantCriteria that = (ParticipantCriteria) o;
        return Objects.equals(studyId, that.studyId) &&
            Objects.equals(status, that.status) &&
            Objects.equals(dateOfBirth, that.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            studyId, status,
            dateOfBirth);
    }

    @Override
    public String toString() {
        return "ParticipantCriteria{" +
            ", studyCode=" + studyId +
            ", state=" + status +
            ", dateOfBirth=" + dateOfBirth +
            '}';
    }
}
