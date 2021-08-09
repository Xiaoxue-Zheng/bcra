package uk.ac.herc.bcra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CanRiskReport.
 */
@Entity
@Table(name = "can_risk_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CanRiskReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "filename", nullable = false, unique = true)
    private String filename;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private StudyId associatedStudyId;

    @OneToOne(optional = false)
    @NotNull
    private User uploadedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public CanRiskReport filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public StudyId getAssociatedStudyId() {
        return associatedStudyId;
    }

    public CanRiskReport associatedStudyId(StudyId studyId) {
        this.associatedStudyId = studyId;
        return this;
    }

    public void setAssociatedStudyId(StudyId studyId) {
        this.associatedStudyId = studyId;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public CanRiskReport uploadedBy(User user) {
        this.uploadedBy = user;
        return this;
    }

    public void setUploadedBy(User user) {
        this.uploadedBy = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CanRiskReport)) {
            return false;
        }
        return id != null && id.equals(((CanRiskReport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CanRiskReport{" +
            "id=" + getId() +
            ", filename='" + getFilename() + "'" +
            "}";
    }
}
