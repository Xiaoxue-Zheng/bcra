package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import uk.ac.herc.bcra.domain.enumeration.CsvFileState;

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
    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private CsvFileState state;

    @Column(name = "status")
    private String status;

    @NotNull
    @Size(min = 5)
    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @NotNull
    @Column(name = "upload_datetime", nullable = false)
    private Instant uploadDatetime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private CsvContent content;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CsvFileState getState() {
        return state;
    }

    public CsvFile state(CsvFileState state) {
        this.state = state;
        return this;
    }

    public void setState(CsvFileState state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public CsvFile status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public byte[] readContent() {
        return content.getData();
    }

    public void writeContent(byte[] data) {
        if (this.content == null) {
            this.content = new CsvContent();
        }
        this.content.setData(data);
        this.content.setDataContentType("csv");
        this.content.setCsvFile(this);
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
            ", state='" + getState() + "'" +
            ", status='" + getStatus() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", uploadDatetime='" + getUploadDatetime() + "'" +
            "}";
    }
}
