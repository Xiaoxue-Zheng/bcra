package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.CheckboxAnswerItem} entity.
 */
public class CheckboxAnswerItemDTO implements Serializable {

    private Long id;


    private Long checkboxAnswerId;

    private Long checkboxQuestionItemId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCheckboxAnswerId() {
        return checkboxAnswerId;
    }

    public void setCheckboxAnswerId(Long checkboxAnswerId) {
        this.checkboxAnswerId = checkboxAnswerId;
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

        CheckboxAnswerItemDTO checkboxAnswerItemDTO = (CheckboxAnswerItemDTO) o;
        if (checkboxAnswerItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkboxAnswerItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CheckboxAnswerItemDTO{" +
            "id=" + getId() +
            ", checkboxAnswer=" + getCheckboxAnswerId() +
            ", checkboxQuestionItem=" + getCheckboxQuestionItemId() +
            "}";
    }
}
