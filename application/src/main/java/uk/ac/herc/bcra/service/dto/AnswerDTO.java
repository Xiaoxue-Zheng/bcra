package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Answer} entity.
 */
public class AnswerDTO implements Serializable {

    private Long id;


    private Long answerGroupId;

    private Long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerGroupId() {
        return answerGroupId;
    }

    public void setAnswerGroupId(Long answerGroupId) {
        this.answerGroupId = answerGroupId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerDTO answerDTO = (AnswerDTO) o;
        if (answerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
            "id=" + getId() +
            ", answerGroup=" + getAnswerGroupId() +
            ", question=" + getQuestionId() +
            "}";
    }
}
