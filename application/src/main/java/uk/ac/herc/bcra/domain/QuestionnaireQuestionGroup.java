package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A QuestionnaireQuestionGroup.
 */
@Entity
@Table(name = "questionnaire_question_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionnaireQuestionGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Column(name = "questionnaire_uuid", nullable = false)
    private String questionnaireUuid;

    @NotNull
    @Column(name = "question_group_uuid", nullable = false)
    private String questionGroupUuid;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @ManyToOne
    @JsonIgnoreProperties("questionnaireQuestionGroups")
    private Questionnaire questionnaire;

    @ManyToOne
    @JsonIgnoreProperties("questionnaireQuestionGroups")
    private QuestionGroup questionGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public QuestionnaireQuestionGroup uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQuestionnaireUuid() {
        return questionnaireUuid;
    }

    public QuestionnaireQuestionGroup questionnaireUuid(String questionnaireUuid) {
        this.questionnaireUuid = questionnaireUuid;
        return this;
    }

    public void setQuestionnaireUuid(String questionnaireUuid) {
        this.questionnaireUuid = questionnaireUuid;
    }

    public String getQuestionGroupUuid() {
        return questionGroupUuid;
    }

    public QuestionnaireQuestionGroup questionGroupUuid(String questionGroupUuid) {
        this.questionGroupUuid = questionGroupUuid;
        return this;
    }

    public void setQuestionGroupUuid(String questionGroupUuid) {
        this.questionGroupUuid = questionGroupUuid;
    }

    public Integer getOrder() {
        return order;
    }

    public QuestionnaireQuestionGroup order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public QuestionnaireQuestionGroup questionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public QuestionGroup getQuestionGroup() {
        return questionGroup;
    }

    public QuestionnaireQuestionGroup questionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
        return this;
    }

    public void setQuestionGroup(QuestionGroup questionGroup) {
        this.questionGroup = questionGroup;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionnaireQuestionGroup)) {
            return false;
        }
        return id != null && id.equals(((QuestionnaireQuestionGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "QuestionnaireQuestionGroup{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", questionnaireUuid='" + getQuestionnaireUuid() + "'" +
            ", questionGroupUuid='" + getQuestionGroupUuid() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}
