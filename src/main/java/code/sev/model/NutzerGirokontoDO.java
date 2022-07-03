package code.sev.model;

import javax.persistence.*;

@Entity
@Table(name = "nutzer_girokonto")
public class NutzerGirokontoDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "girokontoId")
    private Long girokontoId;
    @Column(name = "nutzerId")
    private Long nutzerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGirokontoId() {
        return girokontoId;
    }

    public void setGirokontoId(Long girokontoId) {
        this.girokontoId = girokontoId;
    }

    public Long getNutzerId() {
        return nutzerId;
    }

    public void setNutzerId(Long nutzerId) {
        this.nutzerId = nutzerId;
    }
}
