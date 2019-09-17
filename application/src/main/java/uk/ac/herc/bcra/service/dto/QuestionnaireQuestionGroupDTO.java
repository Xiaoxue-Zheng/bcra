package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.QuestionnaireQuestionGroup} entity.
 */
public class QuestionnaireQuestionGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String uuid;

    @NotNull
    private String questionnaireUuid;

    @NotNull
    private String questionGroupUuid;

    @NotNull
    private Integer order;


    private Long questionnaireId;

    private Long questionGroupId;

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

    public String getQuestionnaireUuid() {
        return questionnaireUuid;
    }

    public void setQuestionnaireUuid(String questionnaireUuid) {
        this.questionnaireUuid = questionnaireUuid;
    }

    public String getQuestionGroupUuid() {
        return questionGroupUuid;
    }

    public void setQuestionGroupUuid(String questionGroupUuid) {
        this.questionGroupUuid = questionGroupUuid;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Long getQuestionGroupId() {
        return questionGroupId;
    }

    public void setQuestionGroupId(Long questionGroupId) {
        this.questionGroupId = questionGroupId;
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
            ", uuid='" + getUuid() + "'" +
            ", questionnaireUuid='" + getQuestionnaireUuid() + "'" +
            ", questionGroupUuid='" + getQuestionGroupUuid() + "'" +
            ", order=" + getOrder() +
            ", questionnaire=" + getQuestionnaireId() +
            ", questionGroup=" + getQuestionGroupId() +
            "}";
    }
}
