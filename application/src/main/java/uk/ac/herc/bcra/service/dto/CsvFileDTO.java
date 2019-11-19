package uk.ac.herc.bcra.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uk.ac.herc.bcra.domain.CsvFile} entity.
 */
public class CsvFileDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5)
    private String fileName;

    @NotNull
    private Instant uploadDatetime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            ", fileName='" + getFileName() + "'" +
            ", uploadDatetime='" + getUploadDatetime() + "'" +
            "}";
    }
}
