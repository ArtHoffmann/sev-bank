package code.sev.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "girokonto_kreditkarte")
public class Girokonto_kreditkarteDO {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "girokontoId")
    private BigDecimal girokontoId;
    @Column(name = "kreditkartenId")
    private BigDecimal kreditkartenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getGirokontoId() {
        return girokontoId;
    }

    public void setGirokontoId(BigDecimal girokontoId) {
        this.girokontoId = girokontoId;
    }

    public BigDecimal getKreditkartenId() {
        return kreditkartenId;
    }

    public void setKreditkartenId(BigDecimal kreditkartenId) {
        this.kreditkartenId = kreditkartenId;
    }
}
