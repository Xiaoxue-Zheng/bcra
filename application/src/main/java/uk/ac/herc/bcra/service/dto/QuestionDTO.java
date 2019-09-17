package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Question} entity.
 */
public class QuestionDTO implements Serializable {

    private Long id;

    @NotNull
    private String uuid;

    @NotNull
    private String text;


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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionDTO questionDTO = (QuestionDTO) o;
        if (questionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}
