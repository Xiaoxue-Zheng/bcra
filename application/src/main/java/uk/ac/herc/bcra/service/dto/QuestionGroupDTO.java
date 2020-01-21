package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionGroupIdentifier;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.QuestionGroup} entity.
 */
public class QuestionGroupDTO implements Serializable {

    private static final long serialVersionUID = 4393843856797568463L;

    private Long id;

    @NotNull
    private QuestionGroupIdentifier identifier;

    private Set<QuestionDTO> questions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionGroupIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(QuestionGroupIdentifier identifier) {
        this.identifier = identifier;
    }

    public Set<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionDTO> questions) {
        this.questions = questions;
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
            ", identifier='" + getIdentifier() + "'" +
            ", questions='" + getQuestions() + "'" +
            "}";
    }

}