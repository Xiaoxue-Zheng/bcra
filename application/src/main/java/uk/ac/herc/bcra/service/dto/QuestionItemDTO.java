package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.QuestionItem} entity.
 */
public class QuestionItemDTO implements Serializable {

    private static final long serialVersionUID = 9151093627723920982L;

    private Long id;

    @NotNull
    private QuestionItemIdentifier identifier;

    @NotNull
    private Integer order;

    @NotNull
    private String label;

    private Boolean necessary;

    private Boolean exclusive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionItemIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(QuestionItemIdentifier identifier) {
        this.identifier = identifier;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isNecessary() {
        return necessary;
    }

    public void setNecessary(Boolean necessary) {
        this.necessary = necessary;
    }

    public Boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionItemDTO questionItemDTO = (QuestionItemDTO) o;
        if (questionItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionItemDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", order=" + getOrder() +
            ", label='" + getLabel() + "'" +
            ", necessary='" + isNecessary() + "'" +
            ", exclusive='" + isExclusive() + "'" +
            "}";
    }
}
