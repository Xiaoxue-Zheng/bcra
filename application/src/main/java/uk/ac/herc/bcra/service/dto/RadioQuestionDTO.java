package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.RadioQuestion} entity.
 */
public class RadioQuestionDTO extends QuestionDTO implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RadioQuestionDTO radioQuestionDTO = (RadioQuestionDTO) o;
        if (radioQuestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), radioQuestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RadioQuestionDTO{" +
            "id=" + getId() +
            "}";
    }
}
