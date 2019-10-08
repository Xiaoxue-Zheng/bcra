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
 * A AnswerSection.
 */
@Entity
@Table(name = "answer_section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnswerSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("answerSections")
    private AnswerResponse answerResponse;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("answerSections")
    private QuestionSection questionSection;

    @OneToMany(mappedBy = "answerSection", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnswerGroup> answerGroups = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerResponse getAnswerResponse() {
        return answerResponse;
    }

    public AnswerSection answerResponse(AnswerResponse answerResponse) {
        this.answerResponse = answerResponse;
        return this;
    }

    public void setAnswerResponse(AnswerResponse answerResponse) {
        this.answerResponse = answerResponse;
    }

    public QuestionSection getQuestionSection() {
        return questionSection;
    }

    public AnswerSection questionSection(QuestionSection questionSection) {
        this.questionSection = questionSection;
        return this;
    }

    public void setQuestionSection(QuestionSection questionSection) {
        this.questionSection = questionSection;
    }

    public Set<AnswerGroup> getAnswerGroups() {
        return answerGroups;
    }

    public AnswerSection answerGroups(Set<AnswerGroup> answerGroups) {
        this.answerGroups = answerGroups;
        return this;
    }

    public AnswerSection addAnswerGroup(AnswerGroup answerGroup) {
        this.answerGroups.add(answerGroup);
        answerGroup.setAnswerSection(this);
        return this;
    }

    public AnswerSection removeAnswerGroup(AnswerGroup answerGroup) {
        this.answerGroups.remove(answerGroup);
        answerGroup.setAnswerSection(null);
        return this;
    }

    public void setAnswerGroups(Set<AnswerGroup> answerGroups) {
        for (AnswerGroup answerGroup: answerGroups) {
            answerGroup.setAnswerSection(this);
        }
        this.answerGroups = answerGroups;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerSection)) {
            return false;
        }
        return id != null && id.equals(((AnswerSection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnswerSection{" +
            "id=" + getId() +
            "}";
    }
}
