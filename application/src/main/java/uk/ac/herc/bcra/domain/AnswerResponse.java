package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

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

    @ManyToOne
    @JsonIgnoreProperties("answerResponses")
    private Questionnaire questionnaire;

    @OneToMany(mappedBy = "answerResponse", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnswerGroup> answerGroups = new HashSet<>();

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

    public Set<AnswerGroup> getAnswerGroups() {
        return answerGroups;
    }

    public AnswerResponse answerGroups(Set<AnswerGroup> answerGroups) {
        this.answerGroups = answerGroups;
        return this;
    }

    public AnswerResponse addAnswerGroup(AnswerGroup answerGroup) {
        this.answerGroups.add(answerGroup);
        answerGroup.setAnswerResponse(this);
        return this;
    }

    public AnswerResponse removeAnswerGroup(AnswerGroup answerGroup) {
        this.answerGroups.remove(answerGroup);
        answerGroup.setAnswerResponse(null);
        return this;
    }

    public void setAnswerGroups(Set<AnswerGroup> answerGroups) {
        for (AnswerGroup answerGroup: answerGroups) {
            answerGroup.setAnswerResponse(this);
        }
        this.answerGroups = answerGroups;
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
