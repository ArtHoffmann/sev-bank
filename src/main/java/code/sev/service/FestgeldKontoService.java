package code.sev.service;

import code.sev.model.FestgeldkontoDO;
import code.sev.model.KreditkartenDO;
import code.sev.repository.FestgeldkontoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class FestgeldKontoService {

    private FestgeldkontoRepository festgeldkontoRepository;

    public FestgeldKontoService(FestgeldkontoRepository festgeldkontoRepository) {
        this.festgeldkontoRepository = festgeldkontoRepository;
    }

    private FestgeldkontoDO addFestgeldkonto(FestgeldkontoDO festgeldkontoDO) {
        return festgeldkontoRepository.save(festgeldkontoDO);
    }

    private void deleteFestgeldkontoById(FestgeldkontoDO konto) {
        festgeldkontoRepository.delete(konto);
    }

    private FestgeldkontoDO findFestgeldkontoById(Long id) {
        Optional<FestgeldkontoDO> first = festgeldkontoRepository.findById(id).stream().findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        throw new IllegalArgumentException("Festgeldkonto mit der ID " + id + " konne nicht gefunden werden");
    }

    private List<FestgeldkontoDO> findAll() {
        List<FestgeldkontoDO> liste = new ArrayList<>();
        festgeldkontoRepository.findAll().forEach(liste::add);
        return liste;
    }

    private boolean withdrawMoney(BigDecimal amount, Long id, int pin) {

        if (amount.doubleValue() < 0) {
            throw new IllegalArgumentException("Beträge <= 0 können  nicht abgehoben werden");
        }

        Optional<FestgeldkontoDO> festgeldkonto = findAll()
                .stream()
                .filter(konto -> konto.getPin() == pin)
                .filter(konto -> Objects.equals(konto.getKontonummer(), id)).findFirst();

        if (festgeldkonto.isPresent()) {
            FestgeldkontoDO konto = festgeldkonto.get();
            BigDecimal guthaben = konto.getGuthaben();
            BigDecimal subtract = guthaben.subtract(amount);
            if (subtract.doubleValue() < 0) {
                throw new IllegalArgumentException("Konto ist nicht genügend gedeckt");
            } else {
                konto.setGuthaben(subtract);
                // TODO: Obj. noch persisten
                return true;
            }
        }

        return false;
    }


    private FestgeldkontoDO depositMoney(BigDecimal amount, Long id, int pin) {
        Optional<FestgeldkontoDO> first = findAll()
                .stream()
                .filter(konto -> konto.getPin() == pin)
                .filter(konto -> Objects.equals(konto.getKontonummer(), id)).findFirst();

        if (first.isPresent() && amount.compareTo(BigDecimal.ZERO) != 0) {
            FestgeldkontoDO festgeldkontoDO = first.get();
            festgeldkontoDO.setGuthaben(amount);
        }
        throw new IllegalArgumentException("Einzahlung nicht möglich");
    }

    private FestgeldkontoDO setDispoLimit(Long id, BigDecimal limit, int pin) {
        Optional<FestgeldkontoDO> first = findAll()
                .stream()
                .filter(konto -> konto.getPin() == pin)
                .filter(konto -> Objects.equals(konto.getKontonummer(), id)).findFirst();

        if (first.isPresent()) {
            FestgeldkontoDO festgeldkontoDO = first.get();
            festgeldkontoDO.setDispolimit(limit);
        }
        throw new IllegalArgumentException("Limit konnte nicht gesetzt werden");
    }

}
