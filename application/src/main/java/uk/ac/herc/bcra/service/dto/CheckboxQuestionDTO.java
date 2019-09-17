package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.CheckboxQuestion} entity.
 */
public class CheckboxQuestionDTO extends QuestionDTO implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CheckboxQuestionDTO checkboxQuestionDTO = (CheckboxQuestionDTO) o;
        if (checkboxQuestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkboxQuestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CheckboxQuestionDTO{" +
            "id=" + getId() +
            "}";
    }
}
