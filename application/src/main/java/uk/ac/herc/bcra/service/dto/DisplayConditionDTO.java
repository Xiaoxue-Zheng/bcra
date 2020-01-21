package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.DisplayCondition} entity.
 */
public class DisplayConditionDTO implements Serializable {

    private static final long serialVersionUID = -3750583351633480361L;

    private Long id;

    private QuestionIdentifier questionIdentifier;

    private QuestionItemIdentifier itemIdentifier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionIdentifier getQuestionIdentifier() {
        return questionIdentifier;
    }

    public void setQuestionIdentifier(QuestionIdentifier questionIdentifier) {
        this.questionIdentifier = questionIdentifier;
    }

    public QuestionItemIdentifier getItemIdentifier() {
        return itemIdentifier;
    }

    public void setItemIdentifier(QuestionItemIdentifier itemIdentifier) {
        this.itemIdentifier = itemIdentifier;
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
            ", questionIdentifier='" + getQuestionIdentifier() + "'" +
            ", itemIdentifier='" + getItemIdentifier() + "'" +
            "}";
    }
}
