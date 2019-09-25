package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.QuestionGroup} entity.
 */
public class QuestionGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String uuid;

    private Set<QuestionGroupQuestionDTO> questionGroupQuestions = new HashSet<>();

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

    public Set<QuestionGroupQuestionDTO> getQuestionGroupQuestions() {
        return questionGroupQuestions;
    }

    public void setQuestionGroupQuestions(Set<QuestionGroupQuestionDTO> questionGroupQuestions) {
        this.questionGroupQuestions = questionGroupQuestions;
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
            ", uuid='" + getUuid() + "'" +
            ", questionGroupQuestions='" + getQuestionGroupQuestions() + "'" +
            "}";
    }
}
