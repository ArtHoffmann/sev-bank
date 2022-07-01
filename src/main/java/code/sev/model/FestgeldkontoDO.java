package code.sev.model;

import org.hibernate.internal.util.MathHelper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Table(name = "festgeldkonto")
@Entity
public class FestgeldkontoDO {

    @Id
    @Column(name = "kontonummer")
    private Long kontonummer;
    @Column(name = "name")
    private String name;
    @Column(name = "guthaben")
    private BigDecimal guthaben;
    @Column(name = "guthaben_edit")
    private boolean guthaben_edit;
    @Column(name = "pin", length = 4)
    private int pin;
    @Column(name = "dispolimit")
    private BigDecimal dispolimit;
    @Column(name = "zins", nullable = false)
    private BigDecimal zins;

    @PrePersist
    public void prePersist() {
        generateKontonummer();
        this.guthaben = BigDecimal.ZERO;
    }

    private void generateKontonummer() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);
        setKontonummer(Long.valueOf(number));
    }

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

    public BigDecimal getZins() {
        return zins;
    }

    public void setZins(BigDecimal zins) {
        this.zins = zins;
    }

    public boolean isGuthaben_edit() {
        return guthaben_edit;
    }

    public void setGuthaben_edit(boolean guthaben_edit) {
        this.guthaben_edit = guthaben_edit;
    }
}
