package uk.ac.herc.bcra.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import uk.ac.herc.bcra.domain.enumeration.QuestionItemIdentifier;

/**
 * A QuestionItem.
 */
@Entity
@Table(name = "question_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identifier", nullable = false, unique = true)
    private QuestionItemIdentifier identifier;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "necessary")
    private Boolean necessary;

    @Column(name = "exclusive")
    private Boolean exclusive;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("questionItems")
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionItemIdentifier getIdentifier() {
        return identifier;
    }

    public QuestionItem identifier(QuestionItemIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(QuestionItemIdentifier identifier) {
        this.identifier = identifier;
    }

    public Integer getOrder() {
        return order;
    }

    public QuestionItem order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getLabel() {
        return label;
    }

    public QuestionItem label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isNecessary() {
        return necessary;
    }

    public QuestionItem necessary(Boolean necessary) {
        this.necessary = necessary;
        return this;
    }

    public void setNecessary(Boolean necessary) {
        this.necessary = necessary;
    }

    public Boolean isExclusive() {
        return exclusive;
    }

    public QuestionItem exclusive(Boolean exclusive) {
        this.exclusive = exclusive;
        return this;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    public Question getQuestion() {
        return question;
    }

    public QuestionItem question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionItem)) {
            return false;
        }
        return id != null && id.equals(((QuestionItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "QuestionItem{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", order=" + getOrder() +
            ", label='" + getLabel() + "'" +
            ", necessary='" + isNecessary() + "'" +
            ", exclusive='" + isExclusive() + "'" +
            "}";
    }
}
