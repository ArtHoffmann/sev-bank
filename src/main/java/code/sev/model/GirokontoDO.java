package code.sev.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "Girokonto")
public class GirokontoDO {

    @Id
    @Column(name = "kontonummer")
    private Long kontonummer;
    @Column(name = "name")
    private String name;
    @Column(name = "guthaben")
    private BigDecimal guthaben;
    @Column(name = "pin", length = 4)
    private int pin;
    @Column(name = "dispolimit")
    private BigDecimal dispolimit;

    public Long getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(Long kontonummer) {
        this.kontonummer = kontonummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getGuthaben() {
        return guthaben;
    }

    public void setGuthaben(BigDecimal guthaben) {
        this.guthaben = guthaben;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public BigDecimal getDispolimit() {
        return dispolimit;
    }

    public void setDispolimit(BigDecimal dispolimit) {
        this.dispolimit = dispolimit;
    }


}
