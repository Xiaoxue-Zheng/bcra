package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CsvFile.
 */
@Entity
@Table(name = "csv_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CsvFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 5)
    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @NotNull
    @Column(name = "upload_datetime", nullable = false)
    private Instant uploadDatetime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public CsvFile fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Instant getUploadDatetime() {
        return uploadDatetime;
    }

    public CsvFile uploadDatetime(Instant uploadDatetime) {
        this.uploadDatetime = uploadDatetime;
        return this;
    }

    public void setUploadDatetime(Instant uploadDatetime) {
        this.uploadDatetime = uploadDatetime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CsvFile)) {
            return false;
        }
        return id != null && id.equals(((CsvFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CsvFile{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", uploadDatetime='" + getUploadDatetime() + "'" +
            "}";
    }
}
