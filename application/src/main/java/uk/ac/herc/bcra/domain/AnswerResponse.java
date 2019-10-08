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
 * A AnswerResponse.
 */
@Entity
@Table(name = "answer_response")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnswerResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("answerResponses")
    private Questionnaire questionnaire;

    @OneToMany(mappedBy = "answerResponse", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnswerSection> answerSections = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public AnswerResponse questionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public Set<AnswerSection> getAnswerSections() {
        return answerSections;
    }

    public AnswerResponse answerSections(Set<AnswerSection> answerSections) {
        this.answerSections = answerSections;
        return this;
    }

    public AnswerResponse addAnswerSection(AnswerSection answerSection) {
        this.answerSections.add(answerSection);
        answerSection.setAnswerResponse(this);
        return this;
    }

    public AnswerResponse removeAnswerSection(AnswerSection answerSection) {
        this.answerSections.remove(answerSection);
        answerSection.setAnswerResponse(null);
        return this;
    }

    public void setAnswerSections(Set<AnswerSection> answerSections) {
        for (AnswerSection answerSection: answerSections) {
            answerSection.setAnswerResponse(this);
        }
        this.answerSections = answerSections;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerResponse)) {
            return false;
        }
        return id != null && id.equals(((AnswerResponse) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnswerResponse{" +
            "id=" + getId() +
            "}";
    }
}
