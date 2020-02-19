package uk.ac.herc.bcra.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

/**
 * A QuestionSection.
 */
@Entity
@Table(name = "question_section")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class QuestionSection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "identifier", nullable = false, unique = true)
    private QuestionSectionIdentifier identifier;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Column(name = "jhi_order", nullable = false)
    private Integer order;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Column(name = "progress", nullable = false)
    private Integer progress;

    @OneToMany(mappedBy = "questionSection")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DisplayCondition> displayConditions = new HashSet<>();

    @OneToMany(mappedBy = "questionSection")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ReferralCondition> referralConditions = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("questionSections")
    private Questionnaire questionnaire;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("questionSections")
    private QuestionGroup questionGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionSectionIdentifier getIdentifier() {
        return identifier;
    }

    public QuestionSection identifier(QuestionSectionIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(QuestionSectionIdentifier identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public QuestionSection title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public QuestionSection order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public QuestionSection url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProgress() {
        return progress;
    }

    public QuestionSection progress(Integer progress) {
        this.progress = progress;
        return this;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Set<DisplayCondition> getDisplayConditions() {
        return displayConditions;
    }

    public QuestionSection displayConditions(Set<DisplayCondition> displayConditions) {
        this.displayConditions = displayConditions;
        return this;
    }

    public QuestionSection addDisplayCondition(DisplayCondition displayCondition) {
        this.displayConditions.add(displayCondition);
        displayCondition.setQuestionSection(this);
        return this;
    }

    public QuestionSection removeDisplayCondition(DisplayCondition displayCondition) {
        this.displayConditions.remove(displayCondition);
        displayCondition.setQuestionSection(null);
        return this;
    }

    public void setDisplayConditions(Set<DisplayCondition> displayConditions) {
        this.displayConditions = displayConditions;
    }

    public Set<ReferralCondition> getReferralConditions() {
        return referralConditions;
    }

    public QuestionSection referralConditions(Set<ReferralCondition> referralConditions) {
        this.referralConditions = referralConditions;
        return this;
    }

    public QuestionSection addReferralCondition(ReferralCondition referralCondition) {
        this.referralConditions.add(referralCondition);
        referralCondition.setQuestionSection(this);
        return this;
    }

    public QuestionSection removeReferralCondition(ReferralCondition referralCondition) {
        this.referralConditions.remove(referralCondition);
        referralCondition.setQuestionSection(null);
        return this;
    }

    public void setReferralConditions(Set<ReferralCondition> referralConditions) {
        this.referralConditions = referralConditions;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public QuestionSection questionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        return this;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public QuestionGroup getQuestionGroup() {
        return questionGroup;
    }

    public QuestionSection questionGroup(QuestionGroup questionGroup) {
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
        if (!(o instanceof QuestionSection)) {
            return false;
        }
        return id != null && id.equals(((QuestionSection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "QuestionSection{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", title='" + getTitle() + "'" +
            ", order=" + getOrder() +
            ", url='" + getUrl() + "'" +
            ", progress=" + getProgress() +
            "}";
    }
}
