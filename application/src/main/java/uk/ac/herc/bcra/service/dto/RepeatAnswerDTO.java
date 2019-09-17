package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.RepeatAnswer} entity.
 */
public class RepeatAnswerDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RepeatAnswerDTO repeatAnswerDTO = (RepeatAnswerDTO) o;
        if (repeatAnswerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), repeatAnswerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RepeatAnswerDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
