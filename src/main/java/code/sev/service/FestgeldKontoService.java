package code.sev.service;

import code.sev.model.DepositDO;
import code.sev.model.FestgeldkontoDO;
import code.sev.repository.FestgeldkontoRepository;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class FestgeldKontoService {

    private FestgeldkontoRepository festgeldkontoRepository;
    private DepositService depositService;

    public FestgeldKontoService(FestgeldkontoRepository festgeldkontoRepository, DepositService depositService) {
        this.festgeldkontoRepository = festgeldkontoRepository;
        this.depositService = depositService;
    }

    public FestgeldkontoDO addFestgeldkonto(FestgeldkontoDO festgeldkontoDO) {
        return festgeldkontoRepository.save(festgeldkontoDO);
    }

    public FestgeldkontoDO updateFestgeldkonto(FestgeldkontoDO festgeldkontoDO) {
        return festgeldkontoRepository.save(festgeldkontoDO);
    }

    public void deleteFestgeldkontoById(Long kdnr) {
        FestgeldkontoDO byId = festgeldkontoRepository.findById(kdnr).orElseThrow(IllegalArgumentException::new);
        List<DepositDO> deposits = depositService.deposits(kdnr);
        if (!deposits.isEmpty()) {
            throw new IllegalArgumentException("Das Festgeldkonto beinhaltet noch deposits " + deposits.size());
        }
        festgeldkontoRepository.delete(byId);
    }

    public FestgeldkontoDO findFestgeldkontoById(Long id) {
        Optional<FestgeldkontoDO> first = festgeldkontoRepository.findById(id).stream().findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        throw new IllegalArgumentException("Festgeldkonto mit der ID " + id + " konne nicht gefunden werden");
    }

    public List<FestgeldkontoDO> findAll() {
        List<FestgeldkontoDO> liste = new ArrayList<>();
        festgeldkontoRepository.findAll().forEach(liste::add);
        return liste;
    }

    public boolean withdrawMoney(BigDecimal amount, Long id, int pin) throws Exception {

        if (amount.doubleValue() < 0) {
            throw new IllegalArgumentException("Beträge <= 0 können  nicht abgehoben werden");
        }

        Optional<FestgeldkontoDO> festgeldkonto = findAll()
                .stream()
                .filter(konto -> konto.getPin() == pin)
                .filter(konto -> Objects.equals(konto.getKontonummer(), id)).findFirst();

        Optional<DepositDO> auszahlbahrerBetrag = checkDeposits(id);

        if (festgeldkonto.isPresent() && auszahlbahrerBetrag.isPresent()) {

            FestgeldkontoDO konto = festgeldkonto.get();
            DepositDO depositDO = auszahlbahrerBetrag.get();
            BigDecimal auszahlbarerBetrag = depositDO.getBetrag();
            BigDecimal subtract = auszahlbarerBetrag.subtract(amount);

            if (subtract.doubleValue() < 0) {
                throw new IllegalArgumentException("Es kann nicht mehr Geld abgehoben werden als auf dem Festgeldkonto ist!");
            }

            konto.setGuthaben(subtract);
            depositDO.setBetrag(subtract);
            updateFestgeldkonto(konto);
            depositService.editDeposit(depositDO);
            return true;
        }
        return false;
    }

    private Optional<DepositDO> checkDeposits(Long id) {
        List<DepositDO> aktivDeposits = depositService.deposits(id);

        List<DepositDO> auszahlbareBetraege = aktivDeposits.stream()
                .filter(x -> x.getDepositDate().plusMonths(1).isBefore(x.getWithdrawelDate()))
                .collect(Collectors.toList());

        Optional<DepositDO> auszahlbahrerBetrag;
        if (auszahlbareBetraege.size() > 1) {
            auszahlbahrerBetrag = checkAuszahlbarebetraege(auszahlbareBetraege);
        } else {
            auszahlbahrerBetrag = auszahlbareBetraege.stream().findFirst();
        }
        return auszahlbahrerBetrag;
    }

    private Optional<DepositDO> checkAuszahlbarebetraege(List<DepositDO> auszahlbareBetraege) {
        DepositDO newDeposit = new DepositDO();
        LocalDate depositDate = null;
        LocalDate withdrawel = null;
        double sum = 0;
        for (DepositDO depositDO : auszahlbareBetraege) {
            depositDate = depositDO.getDepositDate();
            withdrawel = depositDO.getWithdrawelDate();
            sum += depositDO.getBetrag().doubleValue();
        }
        newDeposit.setType("FESTGELDKONTO");
        newDeposit.setDepositDate(depositDate);
        newDeposit.setWithdrawelDate(withdrawel);
        newDeposit.setBetrag(BigDecimal.valueOf(sum));
        return Optional.of(newDeposit);
    }


    public FestgeldkontoDO depositMoney(BigDecimal amount, Long id, int pin) {
        Optional<FestgeldkontoDO> first = findAll()
                .stream()
                .filter(konto -> konto.getPin() == pin)
                .filter(konto -> konto.getKontonummer().equals(id)).findFirst();


        if (first.isPresent() && amount.doubleValue() > 0) {
            FestgeldkontoDO festgeldkontoDO = first.get();
            BigDecimal guthaben = festgeldkontoDO.getGuthaben();
            BigDecimal neuesguthaben = guthaben.add(amount);
            festgeldkontoDO.setGuthaben(neuesguthaben);
            depositService.deposit(amount, festgeldkontoDO.getKontonummer(), "FESTGELDKONTO");
            updateFestgeldkonto(festgeldkontoDO);
            return festgeldkontoDO;
        }
        throw new IllegalArgumentException("Einzahlung nicht möglich");
    }

    public FestgeldkontoDO setDispoLimit(Long id, BigDecimal limit, int pin) {
        Optional<FestgeldkontoDO> first = findAll()
                .stream()
                .filter(konto -> konto.getPin() == pin)
                .filter(konto -> Objects.equals(konto.getKontonummer(), id)).findFirst();

        if (first.isPresent()) {
            FestgeldkontoDO festgeldkontoDO = first.get();
            festgeldkontoDO.setDispolimit(limit);
            updateFestgeldkonto(festgeldkontoDO);
            return festgeldkontoDO;
        }
        throw new IllegalArgumentException("Limit konnte nicht gesetzt werden");
    }

    public boolean changePing(Long id, int newPin, int oldPin) {
        Optional<FestgeldkontoDO> first = findAll()
                .stream()
                .filter(konto -> konto.getPin() == oldPin)
                .filter(konto -> Objects.equals(konto.getKontonummer(), id)).findFirst();
        if (newPin == oldPin) {
            throw new IllegalArgumentException("Der neue Pin stimmt mit dem alten Pin überein");
        }

        if (first.isPresent()) {
            FestgeldkontoDO festgeldkontoDO = first.get();
            festgeldkontoDO.setPin(newPin);
            updateFestgeldkonto(festgeldkontoDO);
            return true;
        }
        throw new IllegalArgumentException("Limit konnte nicht gesetzt werden");
    }

}
