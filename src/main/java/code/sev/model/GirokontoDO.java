package code.sev.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Entity
@Table(name = "girokonto")
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
    @Column(name = "openingDate", nullable = false)
    private LocalDate openingDate;

    @PrePersist
    public void prePersist() {
        generateKontonummer();
        generateRandomPin();
        if(guthaben.doubleValue() == 0.0){
            this.guthaben = BigDecimal.ZERO;
        }
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

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }
}
