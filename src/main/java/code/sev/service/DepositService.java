package code.sev.service;

import code.sev.model.DepositDO;
import code.sev.repository.DepositRepository;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DepositService {

    private DepositRepository depositRepository;

    public DepositService(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    public void deposit(BigDecimal betrag, Long kontonummer, String type) {
        DepositDO depositDO = new DepositDO();
        depositDO.setDepositDate(LocalDate.now());
        depositDO.setWithdrawelDate(LocalDate.now().plusMonths(1));
        depositDO.setType(type);
        depositDO.setKontonummer(kontonummer);
        depositDO.setBetrag(betrag);
        depositRepository.save(depositDO);
    }

    public List<DepositDO> findAll() {
        List<DepositDO> liste = new ArrayList<>();
        depositRepository.findAll().forEach(liste::add);
        return liste;
    }

    public void editDeposit(DepositDO deposit) {
        depositRepository.save(deposit);
    }

    public void removeDeposit(DepositDO deposit) {
        depositRepository.delete(deposit);
    }

    public List<DepositDO> deposits(Long kontonummer) {
        List<DepositDO> depositDOList = new ArrayList<>();
        depositRepository.findAll().forEach(depositDOList::add);

        ArrayList<DepositDO> items = depositDOList
                .stream()
                .filter(depositDO -> depositDO.getKontonummer().equals(kontonummer))
                .collect(Collectors.toCollection(ArrayList::new));

        return items.stream().sorted((o1, o2) -> o1.getDepositDate().compareTo(o2.getDepositDate())).collect(Collectors.toList());
    }
}
