package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionIdentifier;
import uk.ac.herc.bcra.domain.enumeration.QuestionType;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Question} entity.
 */
public class QuestionDTO implements Serializable {

    private Long id;

    @NotNull
    private QuestionIdentifier identifier;

    @NotNull
    private QuestionType type;

    @NotNull
    private Integer order;

    @NotNull
    private String text;

    private Integer minimum;

    private Integer maximum;

    private Set<QuestionItemDTO> questionItems = new HashSet<>();

    private Set<DisplayConditionDTO> displayConditions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(QuestionIdentifier identifier) {
        this.identifier = identifier;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getMinimum() {
        return minimum;
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public Integer getMaximum() {
        return maximum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Set<QuestionItemDTO> getQuestionItems() {
        return questionItems;
    }

    public void setQuestionItems(Set<QuestionItemDTO> questionItems) {
        this.questionItems = questionItems;
    }

    public Set<DisplayConditionDTO> getDisplayConditions() {
        return displayConditions;
    }

    public void setDisplayConditions(Set<DisplayConditionDTO> displayConditions) {
        this.displayConditions = displayConditions;
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
            ", identifier='" + getIdentifier() + "'" +
            ", type='" + getType() + "'" +
            ", order=" + getOrder() +
            ", text='" + getText() + "'" +
            ", minimum=" + getMinimum() +
            ", maximum=" + getMaximum() +
            ", questionItems=" + getQuestionItems() +
            ", displayConditions=" + getDisplayConditions() +
            "}";
    }
}
