package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireIdentifier;
import uk.ac.herc.bcra.domain.enumeration.Algorithm;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Questionnaire} entity.
 */
public class QuestionnaireDTO implements Serializable {

    private Long id;

    @NotNull
    private QuestionnaireIdentifier identifier;

    private Set<QuestionSectionDTO> questionSections = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionnaireIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(QuestionnaireIdentifier identifier) {
        this.identifier = identifier;
    }

    public Set<QuestionSectionDTO> getQuestionSections() {
        return questionSections;
    }

    public void setQuestionSections(Set<QuestionSectionDTO> questionSections) {
        this.questionSections = questionSections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionnaireDTO questionnaireDTO = (QuestionnaireDTO) o;
        if (questionnaireDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionnaireDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionnaireDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", questionSections=" + getQuestionSections() +
            "}";
    }
}
