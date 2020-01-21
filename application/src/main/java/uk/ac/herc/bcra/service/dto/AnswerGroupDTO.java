package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.AnswerGroup} entity.
 */
public class AnswerGroupDTO implements Serializable {

    private static final long serialVersionUID = 3170335748708421879L;

    private Long id;

    @NotNull
    private Integer order;

    private Long answerSectionId;

    private Set<AnswerDTO> answers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getAnswerSectionId() {
        return answerSectionId;
    }

    public void setAnswerSectionId(Long answerSectionId) {
        this.answerSectionId = answerSectionId;
    }

    public Set<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerDTO> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerGroupDTO answerGroupDTO = (AnswerGroupDTO) o;
        if (answerGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerGroupDTO{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            ", answerSection=" + getAnswerSectionId() +
            ", answers=" + getAnswers() +
            "}";
    }
}
