package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Questionnaire} entity.
 */
public class QuestionnaireDTO implements Serializable {

    private Long id;

    @NotNull
    private String uuid;

    private Set<QuestionnaireQuestionGroupDTO> questionnaireQuestionGroups = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public Set<QuestionnaireQuestionGroupDTO> getQuestionnaireQuestionGroups() {
        return questionnaireQuestionGroups;
    }   

    public void setQuestionnaireQuestionGroups(Set<QuestionnaireQuestionGroupDTO> questionnaireQuestionGroups) {
        this.questionnaireQuestionGroups = questionnaireQuestionGroups;
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
            ", uuid='" + getUuid() + "'" +
            ", questionnaireQuestionGroups='" + getQuestionnaireQuestionGroups() + "'" +
            "}";
    }
}
