package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import uk.ac.herc.bcra.domain.enumeration.DisplayConditionType;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.DisplayCondition} entity.
 */
public class DisplayConditionDTO implements Serializable {

    private Long id;

    @NotNull
    private DisplayConditionType displayConditionType;

    private QuestionSectionIdentifier conditionSectionIdentifier;

    private QuestionIdentifier conditionQuestionIdentifier;

    private QuestionItemIdentifier conditionQuestionItemIdentifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DisplayConditionType getDisplayConditionType() {
        return displayConditionType;
    }

    public void setDisplayConditionType(DisplayConditionType displayConditionType) {
        this.displayConditionType = displayConditionType;
    }

    public QuestionSectionIdentifier getConditionSectionIdentifier() {
        return conditionSectionIdentifier;
    }

    public void setConditionSectionIdentifier(QuestionSectionIdentifier conditionSectionIdentifier) {
        this.conditionSectionIdentifier = conditionSectionIdentifier;
    }

    public QuestionIdentifier getConditionQuestionIdentifier() {
        return conditionQuestionIdentifier;
    }

    public void setConditionQuestionIdentifier(QuestionIdentifier conditionQuestionIdentifier) {
        this.conditionQuestionIdentifier = conditionQuestionIdentifier;
    }

    public QuestionItemIdentifier getConditionQuestionItemIdentifier() {
        return conditionQuestionItemIdentifier;
    }

    public void setConditionQuestionItemIdentifier(QuestionItemIdentifier conditionQuestionItemIdentifier) {
        this.conditionQuestionItemIdentifier = conditionQuestionItemIdentifier;
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
            ", displayConditionType='" + getDisplayConditionType() + "'" +
            ", conditionSectionIdentifier='" + getConditionSectionIdentifier() + "'" +
            ", conditionQuestionIdentifier='" + getConditionQuestionIdentifier() + "'" +
            ", conditionQuestionItemIdentifier='" + getConditionQuestionItemIdentifier() + "'" +
            "}";
    }
}
