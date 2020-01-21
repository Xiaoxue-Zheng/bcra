package uk.ac.herc.bcra.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.Procedure} entity.
 */
public class ProcedureDTO implements Serializable {

    private static final long serialVersionUID = 4584162217874220059L;

    private Long id;


    private Long consentResponseId;

    private Long riskAssesmentResponseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConsentResponseId() {
        return consentResponseId;
    }

    public void setConsentResponseId(Long answerResponseId) {
        this.consentResponseId = answerResponseId;
    }

    public Long getRiskAssesmentResponseId() {
        return riskAssesmentResponseId;
    }

    public void setRiskAssesmentResponseId(Long answerResponseId) {
        this.riskAssesmentResponseId = answerResponseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProcedureDTO procedureDTO = (ProcedureDTO) o;
        if (procedureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), procedureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcedureDTO{" +
            "id=" + getId() +
            ", consentResponse=" + getConsentResponseId() +
            ", riskAssesmentResponse=" + getRiskAssesmentResponseId() +
            "}";
    }
}
