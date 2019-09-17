package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.DisplayCondition} entity.
 */
public class DisplayConditionDTO implements Serializable {

    private Long id;


    private Long questionGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionGroupId() {
        return questionGroupId;
    }

    public void setQuestionGroupId(Long questionGroupId) {
        this.questionGroupId = questionGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DisplayConditionDTO displayConditionDTO = (DisplayConditionDTO) o;
        if (displayConditionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), displayConditionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DisplayConditionDTO{" +
            "id=" + getId() +
            ", questionGroup=" + getQuestionGroupId() +
            "}";
    }
}
