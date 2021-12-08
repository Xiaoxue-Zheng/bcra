package uk.ac.herc.bcra.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "page_view")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PageView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn
    private PageIdentifier pageIdentifier;

    @ManyToOne(optional = true)
    @JoinColumn
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public PageView date(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public PageIdentifier getPageIdentifier() {
        return pageIdentifier;
    }

    public PageView pageIdentifier(PageIdentifier pageIdentifier) {
        this.pageIdentifier = pageIdentifier;
        return this;
    }

    public void setPageIdentifier(PageIdentifier pageIdentifier) {
        this.pageIdentifier = pageIdentifier;
    }

    public User getUser() {
        return user;
    }

    public PageView user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PageView)) {
            return false;
        }
        return id != null && id.equals(((PageView) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PageView{" +
            "id=" + getId() +
            ", page_identifier=" + getPageIdentifier() +
            ", user=" + getUser() + 
            ", date='" + getDate() + "'" +
            "}";
    }
}
