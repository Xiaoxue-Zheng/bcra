package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.NumberCheckboxQuestion} entity.
 */
public class NumberCheckboxQuestionDTO extends QuestionDTO implements Serializable {

    @NotNull
    private Integer minimum;

    @NotNull
    private Integer maximum;

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

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

        NumberCheckboxQuestionDTO numberCheckboxQuestionDTO = (NumberCheckboxQuestionDTO) o;
        if (numberCheckboxQuestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), numberCheckboxQuestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NumberCheckboxQuestionDTO{" +
            "id=" + getId() +
            ", minimum=" + getMinimum() +
            ", maximum=" + getMaximum() +
            "}";
    }
}
