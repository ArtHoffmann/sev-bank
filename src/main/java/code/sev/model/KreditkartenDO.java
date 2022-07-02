package code.sev.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "kreditkarte")
public class KreditkartenDO {

    @Id
    @Column(name = "kreditkartennummer")
    private Long kreditkartennummer;

    @Column(name = "kreditlimit")
    private BigDecimal kreditlimit;

    public Long getKreditkartennummer() {
        return kreditkartennummer;
    }

    public void setKreditkartennummer(Long kreditkartennummer) {
        this.kreditkartennummer = kreditkartennummer;
    }

    public BigDecimal getKreditlimit() {
        return kreditlimit;
    }

    public void setKreditlimit(BigDecimal kreditlimit) {
        this.kreditlimit = kreditlimit;
    }
}
