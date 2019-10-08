package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.AnswerSection} entity.
 */
public class AnswerSectionDTO implements Serializable {

    private Long id;

    private Long answerResponseId;

    private Long questionSectionId;

    private Set<AnswerGroupDTO> answerGroups = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnswerResponseId() {
        return answerResponseId;
    }

    public void setAnswerResponseId(Long answerResponseId) {
        this.answerResponseId = answerResponseId;
    }

    public Long getQuestionSectionId() {
        return questionSectionId;
    }

    public void setQuestionSectionId(Long questionSectionId) {
        this.questionSectionId = questionSectionId;
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

        AnswerSectionDTO answerSectionDTO = (AnswerSectionDTO) o;
        if (answerSectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), answerSectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnswerSectionDTO{" +
            "id=" + getId() +
            ", answerResponse=" + getAnswerResponseId() +
            ", questionSection=" + getQuestionSectionId() +
            ", answerGroups=" + getAnswerGroups() +
            "}";
    }

}
