package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.AnswerItem} entity.
 */
public class AnswerItemDTO implements Serializable {

    private static final long serialVersionUID = 2293829797812414191L;

    private Long id;

    @NotNull
    private Boolean selected;


    private Long answerId;

    private Long questionItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Long getQuestionItemId() {
        return questionItemId;
    }

    public void setQuestionItemId(Long questionItemId) {
        this.questionItemId = questionItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerItemDTO answerItemDTO = (AnswerItemDTO) o;
        if (answerItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerItemDTO{" +
            "id=" + getId() +
            ", selected='" + isSelected() + "'" +
            ", answer=" + getAnswerId() +
            ", questionItem=" + getQuestionItemId() +
            "}";
    }
}
