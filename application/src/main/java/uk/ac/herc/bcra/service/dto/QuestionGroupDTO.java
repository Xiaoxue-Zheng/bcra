package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.QuestionGroup} entity.
 */
public class QuestionGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String uuid;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionGroupDTO questionGroupDTO = (QuestionGroupDTO) o;
        if (questionGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionGroupDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
