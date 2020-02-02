package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.AnswerUnits;

/**
 * A Answer.
 */
@Entity
@Table(name = "answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "ticked")
    private Boolean ticked;

    @Enumerated(EnumType.STRING)
    @Column(name = "units")
    private AnswerUnits units;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("answers")
    private AnswerGroup answerGroup;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("answers")
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnswerItem> answerItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Answer number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Boolean isTicked() {
        return ticked;
    }

    public Answer ticked(Boolean ticked) {
        this.ticked = ticked;
        return this;
    }

    public void setTicked(Boolean ticked) {
        this.ticked = ticked;
    }

    public AnswerUnits getUnits() {
        return units;
    }

    public Answer units(AnswerUnits units) {
        this.units = units;
        return this;
    }

    public void setUnits(AnswerUnits units) {
        this.units = units;
    }

    public AnswerGroup getAnswerGroup() {
        return answerGroup;
    }

    public Answer answerGroup(AnswerGroup answerGroup) {
        this.answerGroup = answerGroup;
        return this;
    }

    public void setAnswerGroup(AnswerGroup answerGroup) {
        this.answerGroup = answerGroup;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Set<AnswerItem> getAnswerItems() {
        return answerItems;
    }

    public Answer answerItems(Set<AnswerItem> answerItems) {
        this.answerItems = answerItems;
        return this;
    }

    public Answer addAnswerItem(AnswerItem answerItem) {
        this.answerItems.add(answerItem);
        answerItem.setAnswer(this);
        return this;
    }

    public Answer removeAnswerItem(AnswerItem answerItem) {
        this.answerItems.remove(answerItem);
        answerItem.setAnswer(null);
        return this;
    }

    public void setAnswerItems(Set<AnswerItem> answerItems) {
        for (AnswerItem answerItem: answerItems) {
            answerItem.setAnswer(this);
        }
        this.answerItems = answerItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        return id != null && id.equals(((Answer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", ticked='" + isTicked() + "'" +
            ", units='" + getUnits() + "'" +
            "}";
    }
}
