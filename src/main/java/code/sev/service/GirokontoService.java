package code.sev.service;

import code.sev.model.DepositDO;
import code.sev.model.FestgeldkontoDO;
import code.sev.model.GirokontoDO;
import code.sev.repository.GirokontoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.naming.NamingEnumeration;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class GirokontoService {

    private GirokontoRepository girokontoRepository;
    private DepositService depositService;

    public GirokontoService(GirokontoRepository girokontoRepository, DepositService depositService) {
        this.girokontoRepository = girokontoRepository;
        this.depositService = depositService;
    }


    public GirokontoDO addGirokonto(GirokontoDO girokontoDO) {
        return girokontoRepository.save(girokontoDO);
    }

    public GirokontoDO updateGirokonto(GirokontoDO festgeldkontoDO) {
        return girokontoRepository.save(festgeldkontoDO);
    }

    public void deleteFestgeldkontoById(Long kdnr) {
        GirokontoDO byId = girokontoRepository.findById(kdnr).orElseThrow(IllegalArgumentException::new);
        List<DepositDO> deposits = depositService.deposits(kdnr);
        if (!deposits.isEmpty()) {
            throw new IllegalArgumentException("Das Girokonto beinhaltet noch deposits " + deposits.size());
        }
        girokontoRepository.delete(byId);
    }

    public GirokontoDO findFestgeldkontoById(Long id) {
        Optional<GirokontoDO> first = girokontoRepository.findById(id).stream().findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        throw new IllegalArgumentException("Girokonto mit der ID " + id + " konne nicht gefunden werden");
    }

    public List<GirokontoDO> findAll() {
        List<GirokontoDO> liste = new ArrayList<>();
        girokontoRepository.findAll().forEach(liste::add);
        return liste;
    }

    public boolean withdrawMoney(BigDecimal amount, Long id, int pin) {

        if (amount.doubleValue() < 0) {
            throw new IllegalArgumentException("Beträge <= 0 können  nicht abgehoben werden");
        }

        Optional<GirokontoDO> festgeldkonto = findAll()
                .stream()
                .filter(konto -> konto.getPin() == pin)
                .filter(konto -> Objects.equals(konto.getKontonummer(), id)).findFirst();


        if (festgeldkonto.isPresent()) {
            GirokontoDO konto = festgeldkonto.get();
            BigDecimal auszahlbarerBetrag = konto.getGuthaben();
            BigDecimal subtract = auszahlbarerBetrag.subtract(amount);
            BigDecimal subtractDispo = new BigDecimal(0);
            // maximaler auszahlbetrag
            if(subtract.doubleValue() < 0) {
                 subtractDispo = subtract.add(konto.getDispolimit());
            }

            if (subtractDispo.doubleValue() < 0) {
                throw new IllegalArgumentException("Es kann nicht mehr Geld abgehoben werden als auf dem Girokonto ist!");
            }

            konto.setGuthaben(subtract);
            updateGirokonto(konto);
            return true;
        }
        return false;
    }


    public GirokontoDO depositMoney(BigDecimal amount, Long id, int pin) {
        Optional<GirokontoDO> first = findAll()
                .stream()
                .filter(konto -> konto.getPin() == pin)
                .filter(konto -> konto.getKontonummer().equals(id)).findFirst();


        if (first.isPresent() && amount.doubleValue() > 0) {
            GirokontoDO gk = first.get();
            BigDecimal guthaben = gk.getGuthaben();
            BigDecimal neuesguthaben = guthaben.add(amount);
            gk.setGuthaben(neuesguthaben);
            depositService.deposit(amount, id, "GIROKONTO");
            updateGirokonto(gk);
            return gk;
        }
        throw new IllegalArgumentException("Einzahlung nicht möglich");
    }

    public GirokontoDO setDispoLimit(Long id, BigDecimal limit, int pin) {
        Optional<GirokontoDO> first = findAll()
                .stream()
                .filter(konto -> konto.getPin() == pin)
                .filter(konto -> Objects.equals(konto.getKontonummer(), id)).findFirst();

        if (first.isPresent()) {
            GirokontoDO gk = first.get();
            gk.setDispolimit(limit);
            updateGirokonto(gk);
            return gk;
        }
        throw new IllegalArgumentException("Limit konnte nicht gesetzt werden");
    }

    public boolean changePin(Long id, int newPin, int oldPin) {
        Optional<GirokontoDO> first = findAll()
                .stream()
                .filter(konto -> konto.getPin() == oldPin)
                .filter(konto -> Objects.equals(konto.getKontonummer(), id)).findFirst();
        if (newPin == oldPin) {
            throw new IllegalArgumentException("Der neue Pin stimmt mit dem alten Pin überein");
        }

        if (first.isPresent()) {
            GirokontoDO gk = first.get();
            gk.setPin(newPin);
            updateGirokonto(gk);
            return true;
        }
        throw new IllegalArgumentException("Limit konnte nicht gesetzt werden");
    }

}
