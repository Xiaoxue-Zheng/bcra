package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.CheckboxQuestionItem} entity.
 */
public class CheckboxQuestionItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String uuid;

    @NotNull
    private String label;

    @NotNull
    private String descriptor;


    private Long checkboxQuestionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Long getCheckboxQuestionId() {
        return checkboxQuestionId;
    }

    public void setCheckboxQuestionId(Long checkboxQuestionId) {
        this.checkboxQuestionId = checkboxQuestionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CheckboxQuestionItemDTO checkboxQuestionItemDTO = (CheckboxQuestionItemDTO) o;
        if (checkboxQuestionItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), checkboxQuestionItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CheckboxQuestionItemDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", label='" + getLabel() + "'" +
            ", descriptor='" + getDescriptor() + "'" +
            ", checkboxQuestion=" + getCheckboxQuestionId() +
            "}";
    }
}
