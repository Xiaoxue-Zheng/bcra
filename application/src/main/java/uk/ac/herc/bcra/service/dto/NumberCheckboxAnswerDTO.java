package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.NumberCheckboxAnswer} entity.
 */
public class NumberCheckboxAnswerDTO implements Serializable {

    private Long id;

    private Integer number;

    private Boolean check;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NumberCheckboxAnswerDTO numberCheckboxAnswerDTO = (NumberCheckboxAnswerDTO) o;
        if (numberCheckboxAnswerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), numberCheckboxAnswerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NumberCheckboxAnswerDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", check='" + isCheck() + "'" +
            "}";
    }
}
