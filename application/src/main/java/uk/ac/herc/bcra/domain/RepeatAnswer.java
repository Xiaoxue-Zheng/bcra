package uk.ac.herc.bcra.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A RepeatAnswer.
 */
@Entity
@Table(name = "repeat_answer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RepeatAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public RepeatAnswer quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RepeatAnswer)) {
            return false;
        }
        return id != null && id.equals(((RepeatAnswer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RepeatAnswer{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            "}";
    }
}
