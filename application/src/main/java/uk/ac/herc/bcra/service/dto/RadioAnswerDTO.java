package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.RadioAnswer} entity.
 */
public class RadioAnswerDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RadioAnswerDTO radioAnswerDTO = (RadioAnswerDTO) o;
        if (radioAnswerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), radioAnswerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RadioAnswerDTO{" +
            "id=" + getId() +
            "}";
    }
}
