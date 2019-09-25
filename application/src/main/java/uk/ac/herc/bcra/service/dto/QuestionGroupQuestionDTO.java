package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.QuestionGroupQuestion} entity.
 */
public class QuestionGroupQuestionDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer order;

    private QuestionDTO question;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public QuestionDTO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionDTO question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionGroupQuestionDTO questionGroupQuestionDTO = (QuestionGroupQuestionDTO) o;
        if (questionGroupQuestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionGroupQuestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionGroupQuestionDTO{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            ", question=" + getQuestion() +
            "}";
    }
}
