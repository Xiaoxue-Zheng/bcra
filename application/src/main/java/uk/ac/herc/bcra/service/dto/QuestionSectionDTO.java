package uk.ac.herc.bcra.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.QuestionSection} entity.
 */
public class QuestionSectionDTO implements Serializable {

    private static final long serialVersionUID = 2084223380440030877L;

    private Long id;

    @NotNull
    private QuestionSectionIdentifier identifier;

    @NotNull
    private String title;

    @NotNull
    private Integer order;

    @NotNull
    private String url;

    @NotNull
    private Integer progress;

    private QuestionGroupDTO questionGroup;

    private Set<DisplayConditionDTO> displayConditions = new HashSet<>();

    private Set<ReferralConditionDTO> referralConditions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionSectionIdentifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(QuestionSectionIdentifier identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public QuestionGroupDTO getQuestionGroup() {
        return questionGroup;
    }

    public void setQuestionGroup(QuestionGroupDTO questionGroup) {
        this.questionGroup = questionGroup;
    }    

    public Set<DisplayConditionDTO> getDisplayConditions() {
        return displayConditions;
    }

    public void setDisplayConditions(Set<DisplayConditionDTO> displayConditions) {
        this.displayConditions = displayConditions;
    }
    
    public Set<ReferralConditionDTO> getReferralConditions() {
        return referralConditions;
    }

    public void setReferralConditions(Set<ReferralConditionDTO> referralConditions) {
        this.referralConditions = referralConditions;
    }  

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionSectionDTO questionSectionDTO = (QuestionSectionDTO) o;
        if (questionSectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionSectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "QuestionSectionDTO{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", title='" + getTitle() + "'" +
            ", order=" + getOrder() +
            ", url='" + getUrl() + "'" +
            ", progress=" + getProgress() +
            ", questionGroup=" + getQuestionGroup() +
            ", displayConditions=" + getDisplayConditions() +
            ", referralConditions=" + getReferralConditions() +
            "}";
    }
}
