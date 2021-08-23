package uk.ac.herc.bcra.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.AnswerUnits;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Answer} entity.
 */
public class AnswerDTO implements Serializable {

    private static final long serialVersionUID = 4252959733760901017L;

    private Long id;

    private Integer number;

    private Boolean dontKnow;

    private Boolean ticked;

    private AnswerUnits units;

    private Long answerGroupId;

    private Long questionId;

    private Set<AnswerItemDTO> answerItems = new HashSet<>();

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

    public Boolean isTicked() {
        return ticked;
    }

    public void setTicked(Boolean ticked) {
        this.ticked = ticked;
    }

    public AnswerUnits getUnits() {
        return units;
    }

    public void setUnits(AnswerUnits units) {
        this.units = units;
    }

    public Long getAnswerGroupId() {
        return answerGroupId;
    }

    public void setAnswerGroupId(Long answerGroupId) {
        this.answerGroupId = answerGroupId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Set<AnswerItemDTO> getAnswerItems() {
        return answerItems;
    }

    public void setAnswerItems(Set<AnswerItemDTO> answerItems) {
        this.answerItems = answerItems;
    }

    public Boolean getDontKnow() {
        return dontKnow;
    }

    public void setDontKnow(Boolean dontKnow) {
        this.dontKnow = dontKnow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerDTO answerDTO = (AnswerDTO) o;
        if (answerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", ticked='" + isTicked() + "'" +
            ", units='" + getUnits() + "'" +
            ", answerGroup=" + getAnswerGroupId() +
            ", question=" + getQuestionId() +
            ", answerItems=" + getAnswerItems() +
            "}";
    }
}
