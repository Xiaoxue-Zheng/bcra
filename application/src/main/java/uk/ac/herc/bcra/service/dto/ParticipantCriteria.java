package uk.ac.herc.bcra.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

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

    private LongFilter id;

    private InstantFilter registerDatetime;

    private InstantFilter lastLoginDatetime;

    private LongFilter userId;

    private LongFilter identifiableDataId;

    private LongFilter procedureId;

    private LongFilter csvFileId;

    private StringFilter nhsNumber;

    public ParticipantCriteria(){
    }

    public ParticipantCriteria(ParticipantCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.registerDatetime = other.registerDatetime == null ? null : other.registerDatetime.copy();
        this.lastLoginDatetime = other.lastLoginDatetime == null ? null : other.lastLoginDatetime.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.nhsNumber = other.nhsNumber == null ? null : other.nhsNumber.copy();
        this.identifiableDataId = other.identifiableDataId == null ? null : other.identifiableDataId.copy();
        this.procedureId = other.procedureId == null ? null : other.procedureId.copy();
        this.csvFileId = other.csvFileId == null ? null : other.csvFileId.copy();
    }

    @Override
    public ParticipantCriteria copy() {
        return new ParticipantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getRegisterDatetime() {
        return registerDatetime;
    }

    public void setRegisterDatetime(InstantFilter registerDatetime) {
        this.registerDatetime = registerDatetime;
    }

    public InstantFilter getLastLoginDatetime() {
        return lastLoginDatetime;
    }

    public void setLastLoginDatetime(InstantFilter lastLoginDatetime) {
        this.lastLoginDatetime = lastLoginDatetime;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getIdentifiableDataId() {
        return identifiableDataId;
    }

    public void setIdentifiableDataId(LongFilter identifiableDataId) {
        this.identifiableDataId = identifiableDataId;
    }

    public LongFilter getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(LongFilter procedureId) {
        this.procedureId = procedureId;
    }

    public LongFilter getCsvFileId() {
        return csvFileId;
    }

    public void setCsvFileId(LongFilter csvFileId) {
        this.csvFileId = csvFileId;
    }

    public StringFilter getNhsNumber() {
        return nhsNumber;
    }

    public void setNhsNumber(StringFilter nhsNumber) {
        this.nhsNumber = nhsNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ParticipantCriteria that = (ParticipantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(registerDatetime, that.registerDatetime) &&
            Objects.equals(lastLoginDatetime, that.lastLoginDatetime) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(identifiableDataId, that.identifiableDataId) &&
            Objects.equals(procedureId, that.procedureId) &&
            Objects.equals(csvFileId, that.csvFileId) &&
            Objects.equals(nhsNumber, that.nhsNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        registerDatetime,
        lastLoginDatetime,
        userId,
        identifiableDataId,
        procedureId,
        csvFileId,
        nhsNumber
        );
    }

    @Override
    public String toString() {
        return "ParticipantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (registerDatetime != null ? "registerDatetime=" + registerDatetime + ", " : "") +
                (lastLoginDatetime != null ? "lastLoginDatetime=" + lastLoginDatetime + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (identifiableDataId != null ? "identifiableDataId=" + identifiableDataId + ", " : "") +
                (procedureId != null ? "procedureId=" + procedureId + ", " : "") +
                (csvFileId != null ? "csvFileId=" + csvFileId + ", " : "") +
                (nhsNumber != null ? "nhsNumber=" + nhsNumber + ", " : "") +
            "}";
    }



}
