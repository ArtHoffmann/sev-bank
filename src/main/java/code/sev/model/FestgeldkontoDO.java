package code.sev.model;

import net.bytebuddy.asm.Advice;
import org.hibernate.internal.util.MathHelper;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    @Column(name = "pin", length = 4)
    private int pin;
    @Column(name = "dispolimit")
    private BigDecimal dispolimit;
    @Column(name = "zins", nullable = false)
    private BigDecimal zins;
    @Column(name = "openingDate", nullable = false)
    private LocalDate openingDate;

    @PrePersist
    public void prePersist() {
        generateKontonummer();
        generateRandomPin();
        this.guthaben = BigDecimal.ZERO;
        this.openingDate = LocalDate.now();
    }

    private void generateKontonummer() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999999);
        setKontonummer((long) number);
    }

    private void generateRandomPin() {
        int min = 9999;
        int max = 1000;
        int number = (int) (Math.random() * (max - min) + min);
        setPin(number);
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

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }
}
