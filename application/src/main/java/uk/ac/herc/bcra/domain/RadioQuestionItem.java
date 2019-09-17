package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A RadioQuestionItem.
 */
@Entity
@Table(name = "radio_question_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RadioQuestionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Column(name = "question_uuid", nullable = false)
    private String questionUuid;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "descriptor", nullable = false)
    private String descriptor;

    @ManyToOne
    @JsonIgnoreProperties("radioQuestionItems")
    private RadioQuestion radioQuestion;

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

    public RadioQuestionItem uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQuestionUuid() {
        return questionUuid;
    }

    public RadioQuestionItem questionUuid(String questionUuid) {
        this.questionUuid = questionUuid;
        return this;
    }

    public void setQuestionUuid(String questionUuid) {
        this.questionUuid = questionUuid;
    }

    public String getLabel() {
        return label;
    }

    public RadioQuestionItem label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public RadioQuestionItem descriptor(String descriptor) {
        this.descriptor = descriptor;
        return this;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public RadioQuestion getRadioQuestion() {
        return radioQuestion;
    }

    public RadioQuestionItem radioQuestion(RadioQuestion radioQuestion) {
        this.radioQuestion = radioQuestion;
        return this;
    }

    public void setRadioQuestion(RadioQuestion radioQuestion) {
        this.radioQuestion = radioQuestion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RadioQuestionItem)) {
            return false;
        }
        return id != null && id.equals(((RadioQuestionItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RadioQuestionItem{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", questionUuid='" + getQuestionUuid() + "'" +
            ", label='" + getLabel() + "'" +
            ", descriptor='" + getDescriptor() + "'" +
            "}";
    }
}
