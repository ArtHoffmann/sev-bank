package code.sev.service;

import code.sev.exception.TransferException;
import code.sev.model.GirokontoDO;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.Optional;

@ApplicationScoped
public class TransferService {

    private GirokontoService girokontoService;

    public TransferService(GirokontoService girokontoService) {
        this.girokontoService = girokontoService;
    }

    public GirokontoDO transferMoney(Long vonKonto, Long aufkonto, BigDecimal amount, int pin) throws TransferException {

        if (amount.doubleValue() < 0) {
            throw new TransferException("Negative Beträge können nicht überwiesen werden");
        }
        girokontoService.withdrawMoney(amount, vonKonto, pin);
        GirokontoDO girokontoDO = depositMoney(amount, aufkonto);
        return girokontoDO;
    }

    public GirokontoDO depositMoney(BigDecimal amount, Long id) {
        Optional<GirokontoDO> first = girokontoService.findAll()
                .stream()
                .filter(konto -> konto.getKontonummer().equals(id)).findFirst();

        if (first.isPresent() && amount.doubleValue() > 0) {
            GirokontoDO gk = first.get();
            BigDecimal guthaben = gk.getGuthaben();
            BigDecimal neuesguthaben = guthaben.add(amount);
            gk.setGuthaben(neuesguthaben);
            girokontoService.updateGirokonto(gk);
            return gk;
        }
        throw new IllegalArgumentException("Einzahlung nicht möglich");
    }
}
