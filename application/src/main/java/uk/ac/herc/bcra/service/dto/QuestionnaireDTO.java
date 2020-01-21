package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Questionnaire} entity.
 */
public class QuestionnaireDTO implements Serializable {

    private static final long serialVersionUID = 590489475255490274L;

    private Long id;

    @NotNull
    private QuestionnaireType type;

    @NotNull
    private Integer version;

    private Set<QuestionSectionDTO> questionSections = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionnaireType getType() {
        return type;
    }

    public void setType(QuestionnaireType type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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
            ", type='" + getType() + "'" +
            ", version=" + getVersion() +
            ", questionSections=" + getQuestionSections() +
            "}";
    }
}
