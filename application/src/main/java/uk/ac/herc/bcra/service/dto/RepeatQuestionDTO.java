package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.RepeatQuestion} entity.
 */
public class RepeatQuestionDTO extends QuestionDTO implements Serializable {

    @NotNull
    private Integer maximum;

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RepeatQuestionDTO repeatQuestionDTO = (RepeatQuestionDTO) o;
        if (repeatQuestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), repeatQuestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RepeatQuestionDTO{" +
            "id=" + getId() +
            ", maximum=" + getMaximum() +
            "}";
    }
}
