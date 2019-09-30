package uk.ac.herc.bcra.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uk.ac.herc.bcra.domain.AnswerGroup;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.AnswerResponse} entity.
 */
public class AnswerResponseDTO implements Serializable {

    private Long id;

    private Long questionnaireId;

    private Set<AnswerGroupDTO> answerGroups = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Set<AnswerGroupDTO> getAnswerGroups() {
        return answerGroups;
    }

    public void setAnswerGroups(Set<AnswerGroupDTO> answerGroups) {
        this.answerGroups = answerGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerResponseDTO answerResponseDTO = (AnswerResponseDTO) o;
        if (answerResponseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerResponseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerResponseDTO{" +
            "id=" + getId() +
            ", questionnaire=" + getQuestionnaireId() +
            ", answerGroups=" + getAnswerGroups() +
            "}";
    }
}
