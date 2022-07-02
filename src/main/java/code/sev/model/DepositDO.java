package code.sev.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "deposit")
public class DepositDO {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type")
    private String type;
    @Column(name = "betrag")
    private BigDecimal betrag;
    @Column(name = "depositDate")
    private LocalDate depositDate;
    @Column(name = "withdrawelDate")
    private LocalDate withdrawelDate;
    @Column(name = "kontonummer")
    private Long kontonummer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBetrag() {
        return betrag;
    }

    public void setBetrag(BigDecimal betrag) {
        this.betrag = betrag;
    }

    public LocalDate getDepositDate() {
        return depositDate;
    }

    public Long getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(Long kontonummer) {
        this.kontonummer = kontonummer;
    }

    public void setDepositDate(LocalDate depositDate) {
        this.depositDate = depositDate;
    }

    public LocalDate getWithdrawelDate() {
        return withdrawelDate;
    }

    public void setWithdrawelDate(LocalDate withdrawelDate) {
        this.withdrawelDate = withdrawelDate;
    }
}
