package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireType;

/**
 * A Questionnaire.
 */
@Entity
@Table(name = "questionnaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Questionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private QuestionnaireType type;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @OneToMany(mappedBy = "questionnaire")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<QuestionSection> questionSections = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionnaireType getType() {
        return type;
    }

    public Questionnaire type(QuestionnaireType type) {
        this.type = type;
        return this;
    }

    public void setType(QuestionnaireType type) {
        this.type = type;
    }

    public Integer getVersion() {
        return version;
    }

    public Questionnaire version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<QuestionSection> getQuestionSections() {
        return questionSections;
    }

    public Questionnaire questionSections(Set<QuestionSection> questionSections) {
        this.questionSections = questionSections;
        return this;
    }

    public Questionnaire addQuestionSection(QuestionSection questionSection) {
        this.questionSections.add(questionSection);
        questionSection.setQuestionnaire(this);
        return this;
    }

    public Questionnaire removeQuestionSection(QuestionSection questionSection) {
        this.questionSections.remove(questionSection);
        questionSection.setQuestionnaire(null);
        return this;
    }

    public void setQuestionSections(Set<QuestionSection> questionSections) {
        this.questionSections = questionSections;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Questionnaire)) {
            return false;
        }
        return id != null && id.equals(((Questionnaire) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Questionnaire{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
