package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.RadioQuestionItem} entity.
 */
public class RadioQuestionItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String uuid;

    @NotNull
    private String label;

    @NotNull
    private String descriptor;


    private Long radioQuestionId;

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

    public Long getRadioQuestionId() {
        return radioQuestionId;
    }

    public void setRadioQuestionId(Long radioQuestionId) {
        this.radioQuestionId = radioQuestionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RadioQuestionItemDTO radioQuestionItemDTO = (RadioQuestionItemDTO) o;
        if (radioQuestionItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), radioQuestionItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RadioQuestionItemDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", label='" + getLabel() + "'" +
            ", descriptor='" + getDescriptor() + "'" +
            ", radioQuestion=" + getRadioQuestionId() +
            "}";
    }
}
