package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import uk.ac.herc.bcra.domain.enumeration.ReferralConditionType;
import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.ReferralCondition} entity.
 */
public class ReferralConditionDTO implements Serializable {

    private static final long serialVersionUID = 3133538860823470889L;

    private Long id;

    private Integer andGroup;

    @NotNull
    private ReferralConditionType type;

    private QuestionIdentifier questionIdentifier;

    private QuestionItemIdentifier itemIdentifier;

    private Integer number;

    private String reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAndGroup() {
        return andGroup;
    }

    public void setAndGroup(Integer andGroup) {
        this.andGroup = andGroup;
    }

    public ReferralConditionType getType() {
        return type;
    }

    public void setType(ReferralConditionType type) {
        this.type = type;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReferralConditionDTO referralConditionDTO = (ReferralConditionDTO) o;
        if (referralConditionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), referralConditionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReferralConditionDTO{" +
            "id=" + getId() +
            ", andGroup=" + getAndGroup() +
            ", type='" + getType() + "'" +
            ", questionIdentifier='" + getQuestionIdentifier() + "'" +
            ", itemIdentifier='" + getItemIdentifier() + "'" +
            ", number=" + getNumber() +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
