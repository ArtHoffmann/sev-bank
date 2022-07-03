package code.sev.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "kreditkarte")
public class KreditkartenDO {

    @Id
    @Column(name = "kreditkartennummer")
    private Long kreditkartennummer;

    @Column(name = "kreditlimit")
    private BigDecimal kreditlimit;


    public Long generateKreditkartennummer() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);
        setKreditkartennummer((long) number);
        return getKreditkartennummer();
    }

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
