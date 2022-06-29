package code.sev.model;

import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

@Embeddable
public class NutzerKey implements Serializable {


    private Long id;
    private Long kundennummer;

    public NutzerKey() {}

    @PrePersist
    public void prePersist() {
        kundennummer = ThreadLocalRandom.current().nextLong(20);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(Long kundennummer) {
        this.kundennummer = kundennummer;
    }
}
