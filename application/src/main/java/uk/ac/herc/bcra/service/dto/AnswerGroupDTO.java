package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.AnswerGroup} entity.
 */
public class AnswerGroupDTO implements Serializable {

    private Long id;


    private Long answerResponseId;

    private Long questionGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerResponseId() {
        return answerResponseId;
    }

    public void setAnswerResponseId(Long answerResponseId) {
        this.answerResponseId = answerResponseId;
    }

    public Long getQuestionGroupId() {
        return questionGroupId;
    }

    public void setQuestionGroupId(Long questionGroupId) {
        this.questionGroupId = questionGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerGroupDTO answerGroupDTO = (AnswerGroupDTO) o;
        if (answerGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerGroupDTO{" +
            "id=" + getId() +
            ", answerResponse=" + getAnswerResponseId() +
            ", questionGroup=" + getQuestionGroupId() +
            "}";
    }
}
