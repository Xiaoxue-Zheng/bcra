package uk.ac.herc.bcra.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import uk.ac.herc.bcra.domain.enumeration.CsvFileState;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.CsvFile} entity.
 */
public class CsvFileDTO implements Serializable {

    private Long id;

    @NotNull
    private CsvFileState state;

    private String status;

    @NotNull
    @Size(min = 5)
    private String fileName;

    @NotNull
    private Instant uploadDatetime;


    private Long contentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CsvFileState getState() {
        return state;
    }

    public void setState(CsvFileState state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Instant getUploadDatetime() {
        return uploadDatetime;
    }

    public void setUploadDatetime(Instant uploadDatetime) {
        this.uploadDatetime = uploadDatetime;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long csvContentId) {
        this.contentId = csvContentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CsvFileDTO csvFileDTO = (CsvFileDTO) o;
        if (csvFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), csvFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CsvFileDTO{" +
            "id=" + getId() +
            ", state='" + getState() + "'" +
            ", status='" + getStatus() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", uploadDatetime='" + getUploadDatetime() + "'" +
            ", content=" + getContentId() +
            "}";
    }
}
