package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CheckboxQuestionItem.
 */
@Entity
@Table(name = "checkbox_question_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CheckboxQuestionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @NotNull
    @Column(name = "descriptor", nullable = false)
    private String descriptor;

    @ManyToOne
    @JsonIgnoreProperties("checkboxQuestionItems")
    private CheckboxQuestion checkboxQuestion;

    @OneToMany(mappedBy = "checkboxQuestionItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CheckboxAnswerItem> checkboxAnswerItems = new HashSet<>();

    @OneToMany(mappedBy = "checkboxQuestionItem")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CheckboxDisplayCondition> checkboxDisplayConditions = new HashSet<>();

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

    public CheckboxQuestionItem uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLabel() {
        return label;
    }

    public CheckboxQuestionItem label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public CheckboxQuestionItem descriptor(String descriptor) {
        this.descriptor = descriptor;
        return this;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public CheckboxQuestion getCheckboxQuestion() {
        return checkboxQuestion;
    }

    public CheckboxQuestionItem checkboxQuestion(CheckboxQuestion checkboxQuestion) {
        this.checkboxQuestion = checkboxQuestion;
        return this;
    }

    public void setCheckboxQuestion(CheckboxQuestion checkboxQuestion) {
        this.checkboxQuestion = checkboxQuestion;
    }

    public Set<CheckboxAnswerItem> getCheckboxAnswerItems() {
        return checkboxAnswerItems;
    }

    public CheckboxQuestionItem checkboxAnswerItems(Set<CheckboxAnswerItem> checkboxAnswerItems) {
        this.checkboxAnswerItems = checkboxAnswerItems;
        return this;
    }

    public CheckboxQuestionItem addCheckboxAnswerItem(CheckboxAnswerItem checkboxAnswerItem) {
        this.checkboxAnswerItems.add(checkboxAnswerItem);
        checkboxAnswerItem.setCheckboxQuestionItem(this);
        return this;
    }

    public CheckboxQuestionItem removeCheckboxAnswerItem(CheckboxAnswerItem checkboxAnswerItem) {
        this.checkboxAnswerItems.remove(checkboxAnswerItem);
        checkboxAnswerItem.setCheckboxQuestionItem(null);
        return this;
    }

    public void setCheckboxAnswerItems(Set<CheckboxAnswerItem> checkboxAnswerItems) {
        this.checkboxAnswerItems = checkboxAnswerItems;
    }

    public Set<CheckboxDisplayCondition> getCheckboxDisplayConditions() {
        return checkboxDisplayConditions;
    }

    public CheckboxQuestionItem checkboxDisplayConditions(Set<CheckboxDisplayCondition> checkboxDisplayConditions) {
        this.checkboxDisplayConditions = checkboxDisplayConditions;
        return this;
    }

    public CheckboxQuestionItem addCheckboxDisplayCondition(CheckboxDisplayCondition checkboxDisplayCondition) {
        this.checkboxDisplayConditions.add(checkboxDisplayCondition);
        checkboxDisplayCondition.setCheckboxQuestionItem(this);
        return this;
    }

    public CheckboxQuestionItem removeCheckboxDisplayCondition(CheckboxDisplayCondition checkboxDisplayCondition) {
        this.checkboxDisplayConditions.remove(checkboxDisplayCondition);
        checkboxDisplayCondition.setCheckboxQuestionItem(null);
        return this;
    }

    public void setCheckboxDisplayConditions(Set<CheckboxDisplayCondition> checkboxDisplayConditions) {
        this.checkboxDisplayConditions = checkboxDisplayConditions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckboxQuestionItem)) {
            return false;
        }
        return id != null && id.equals(((CheckboxQuestionItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CheckboxQuestionItem{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", label='" + getLabel() + "'" +
            ", descriptor='" + getDescriptor() + "'" +
            "}";
    }
}
