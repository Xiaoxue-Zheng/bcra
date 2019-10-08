package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionnaireIdentifier;

import uk.ac.herc.bcra.domain.enumeration.Algorithm;

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
    @Column(name = "identifier", nullable = false, unique = true)
    private QuestionnaireIdentifier identifier;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", nullable = false)
    private Algorithm algorithm;

    @NotNull
    @Column(name = "algorithm_minimum", nullable = false)
    private Integer algorithmMinimum;

    @NotNull
    @Column(name = "algorithm_maximum", nullable = false)
    private Integer algorithmMaximum;

    @NotNull
    @Column(name = "implementation_version", nullable = false)
    private Integer implementationVersion;

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

    public QuestionnaireIdentifier getIdentifier() {
        return identifier;
    }

    public Questionnaire identifier(QuestionnaireIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(QuestionnaireIdentifier identifier) {
        this.identifier = identifier;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Questionnaire algorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public Integer getAlgorithmMinimum() {
        return algorithmMinimum;
    }

    public Questionnaire algorithmMinimum(Integer algorithmMinimum) {
        this.algorithmMinimum = algorithmMinimum;
        return this;
    }

    public void setAlgorithmMinimum(Integer algorithmMinimum) {
        this.algorithmMinimum = algorithmMinimum;
    }

    public Integer getAlgorithmMaximum() {
        return algorithmMaximum;
    }

    public Questionnaire algorithmMaximum(Integer algorithmMaximum) {
        this.algorithmMaximum = algorithmMaximum;
        return this;
    }

    public void setAlgorithmMaximum(Integer algorithmMaximum) {
        this.algorithmMaximum = algorithmMaximum;
    }

    public Integer getImplementationVersion() {
        return implementationVersion;
    }

    public Questionnaire implementationVersion(Integer implementationVersion) {
        this.implementationVersion = implementationVersion;
        return this;
    }

    public void setImplementationVersion(Integer implementationVersion) {
        this.implementationVersion = implementationVersion;
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
            ", identifier='" + getIdentifier() + "'" +
            ", algorithm='" + getAlgorithm() + "'" +
            ", algorithmMinimum=" + getAlgorithmMinimum() +
            ", algorithmMaximum=" + getAlgorithmMaximum() +
            ", implementationVersion=" + getImplementationVersion() +
            "}";
    }
}
