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
 * Criteria class for the {@link uk.ac.herc.bcra.domain.Questionnaire} entity. This class is used
 * in {@link uk.ac.herc.bcra.web.rest.QuestionnaireResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questionnaires?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionnaireCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uuid;

    private LongFilter questionnaireQuestionGroupId;

    private LongFilter answerResponseId;

    public QuestionnaireCriteria(){
    }

    public QuestionnaireCriteria(QuestionnaireCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.questionnaireQuestionGroupId = other.questionnaireQuestionGroupId == null ? null : other.questionnaireQuestionGroupId.copy();
        this.answerResponseId = other.answerResponseId == null ? null : other.answerResponseId.copy();
    }

    @Override
    public QuestionnaireCriteria copy() {
        return new QuestionnaireCriteria(this);
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

    public LongFilter getQuestionnaireQuestionGroupId() {
        return questionnaireQuestionGroupId;
    }

    public void setQuestionnaireQuestionGroupId(LongFilter questionnaireQuestionGroupId) {
        this.questionnaireQuestionGroupId = questionnaireQuestionGroupId;
    }

    public LongFilter getAnswerResponseId() {
        return answerResponseId;
    }

    public void setAnswerResponseId(LongFilter answerResponseId) {
        this.answerResponseId = answerResponseId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuestionnaireCriteria that = (QuestionnaireCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(questionnaireQuestionGroupId, that.questionnaireQuestionGroupId) &&
            Objects.equals(answerResponseId, that.answerResponseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uuid,
        questionnaireQuestionGroupId,
        answerResponseId
        );
    }

    @Override
    public String toString() {
        return "QuestionnaireCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (questionnaireQuestionGroupId != null ? "questionnaireQuestionGroupId=" + questionnaireQuestionGroupId + ", " : "") +
                (answerResponseId != null ? "answerResponseId=" + answerResponseId + ", " : "") +
            "}";
    }

}
