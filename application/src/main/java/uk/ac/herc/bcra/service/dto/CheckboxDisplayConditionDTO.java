package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.CheckboxDisplayCondition} entity.
 */
public class CheckboxDisplayConditionDTO implements Serializable {

    private Long id;


    private Long checkboxQuestionItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCheckboxQuestionItemId() {
        return checkboxQuestionItemId;
    }

    public void setCheckboxQuestionItemId(Long checkboxQuestionItemId) {
        this.checkboxQuestionItemId = checkboxQuestionItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CheckboxDisplayConditionDTO checkboxDisplayConditionDTO = (CheckboxDisplayConditionDTO) o;
        if (checkboxDisplayConditionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkboxDisplayConditionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CheckboxDisplayConditionDTO{" +
            "id=" + getId() +
            ", checkboxQuestionItem=" + getCheckboxQuestionItemId() +
            "}";
    }
}
