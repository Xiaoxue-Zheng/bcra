package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.RadioAnswerItem} entity.
 */
public class RadioAnswerItemDTO implements Serializable {

    private Long id;


    private Long radioAnswerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRadioAnswerId() {
        return radioAnswerId;
    }

    public void setRadioAnswerId(Long radioAnswerId) {
        this.radioAnswerId = radioAnswerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RadioAnswerItemDTO radioAnswerItemDTO = (RadioAnswerItemDTO) o;
        if (radioAnswerItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), radioAnswerItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RadioAnswerItemDTO{" +
            "id=" + getId() +
            ", radioAnswer=" + getRadioAnswerId() +
            "}";
    }
}
