package uk.ac.herc.bcra.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class CanRiskReportDTO implements Serializable {
    private static final long serialVersionUID = 5209269230202582222L;

    private Long id;

    private String filename;

    private String studyId;

    private String uploadedBy;

    private byte[] fileData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getStudyId() {
        return studyId;
    }
    
    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }
    
    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CanRiskReportDTO canRiskReportDTO = (CanRiskReportDTO) o;
        if (canRiskReportDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), canRiskReportDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CanRiskReportDTO{" +
            "id=" + getId() +
            ", filename=" + getFilename() +
            "}";
    }
}
