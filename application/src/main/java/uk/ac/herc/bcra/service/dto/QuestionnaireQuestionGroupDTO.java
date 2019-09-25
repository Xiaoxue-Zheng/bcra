package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;

import uk.ac.herc.bcra.domain.QuestionGroup;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup} entity.
 */
public class QuestionnaireQuestionGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer order;

    private QuestionGroupDTO questionGroup;

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

    public QuestionGroupDTO getQuestionGroup() {
        return questionGroup;
    }

    public void setQuestionGroup(QuestionGroupDTO questionGroup) {
        this.questionGroup = questionGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionnaireQuestionGroupDTO questionnaireQuestionGroupDTO = (QuestionnaireQuestionGroupDTO) o;
        if (questionnaireQuestionGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionnaireQuestionGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionnaireQuestionGroupDTO{" +
            "id=" + getId() +
            ", order=" + getOrder() +
            ", questionGroup=" + getQuestionGroup() +
            "}";
    }
}
