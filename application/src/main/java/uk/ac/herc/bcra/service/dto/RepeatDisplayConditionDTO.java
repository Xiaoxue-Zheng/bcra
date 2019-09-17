package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.RepeatDisplayCondition} entity.
 */
public class RepeatDisplayConditionDTO implements Serializable {

    private Long id;


    private Long repeatQuestionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepeatQuestionId() {
        return repeatQuestionId;
    }

    public void setRepeatQuestionId(Long repeatQuestionId) {
        this.repeatQuestionId = repeatQuestionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RepeatDisplayConditionDTO repeatDisplayConditionDTO = (RepeatDisplayConditionDTO) o;
        if (repeatDisplayConditionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), repeatDisplayConditionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RepeatDisplayConditionDTO{" +
            "id=" + getId() +
            ", repeatQuestion=" + getRepeatQuestionId() +
            "}";
    }
}
