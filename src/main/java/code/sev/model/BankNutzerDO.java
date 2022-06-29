package code.sev.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Table(name = "SEV_NUTZER")
@Entity
public class BankNutzerDO {

    @EmbeddedId
    private NutzerKey nutzerKey;

    @Column(name = "vorname")
    private String vorname;
    @Column(name = "nachname")
    private String nachname;
    @Column(name = "geschlecht")
    private String geschlecht;
    @Column(name = "geburtsdatum")
    private LocalDate geburtsdatum;

    @PrePersist
    public void prePersist() {
        nutzerKey.setKundennummer(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public NutzerKey getNutzerKey() {
        return nutzerKey;
    }

    public void setNutzerKey(NutzerKey nutzerKey) {
        this.nutzerKey = nutzerKey;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }
}
