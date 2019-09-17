package uk.ac.herc.bcra.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.QuestionGroupQuestion} entity.
 */
public class QuestionGroupQuestionDTO implements Serializable {

    private Long id;

    @NotNull
    private String uuid;

    @NotNull
    private String questionGroupUuid;

    @NotNull
    private String questionUuid;

    @NotNull
    private Integer order;


    private Long questionGroupId;

    private Long questionId;

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

    public String getQuestionGroupUuid() {
        return questionGroupUuid;
    }

    public void setQuestionGroupUuid(String questionGroupUuid) {
        this.questionGroupUuid = questionGroupUuid;
    }

    public String getQuestionUuid() {
        return questionUuid;
    }

    public void setQuestionUuid(String questionUuid) {
        this.questionUuid = questionUuid;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Long getQuestionGroupId() {
        return questionGroupId;
    }

    public void setQuestionGroupId(Long questionGroupId) {
        this.questionGroupId = questionGroupId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionGroupQuestionDTO questionGroupQuestionDTO = (QuestionGroupQuestionDTO) o;
        if (questionGroupQuestionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionGroupQuestionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionGroupQuestionDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", questionGroupUuid='" + getQuestionGroupUuid() + "'" +
            ", questionUuid='" + getQuestionUuid() + "'" +
            ", order=" + getOrder() +
            ", questionGroup=" + getQuestionGroupId() +
            ", question=" + getQuestionId() +
            "}";
    }
}
