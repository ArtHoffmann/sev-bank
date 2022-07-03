package code.sev.service;

import code.sev.exception.WithdrawException;
import code.sev.model.GirokontoDO;
import code.sev.model.GirokontoKreditkarteDO;
import code.sev.model.KreditkartenDO;
import code.sev.repository.KreditkartenRepository;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class KreditkartenService {

    private KreditkartenRepository kreditkartenRepository;
    private Girokonto_KreditkartenService girokonto_kreditkartenService;
    private GirokontoService girokontoService;
    private KreditkartenService kreditkartenService;

    public KreditkartenService(KreditkartenRepository kreditkartenRepository, Girokonto_KreditkartenService girokonto_kreditkartenService, GirokontoService girokontoService, KreditkartenService kreditkartenService) {
        this.kreditkartenRepository = kreditkartenRepository;
        this.girokonto_kreditkartenService = girokonto_kreditkartenService;
        this.girokontoService = girokontoService;
        this.kreditkartenService = kreditkartenService;
    }

    public KreditkartenDO findKreditkarteById(Long id) {
        return kreditkartenRepository.findById(id).stream().findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public List<KreditkartenDO> findAll() {
        List<KreditkartenDO> kreditkarten = new ArrayList<>();
        kreditkartenRepository.findAll().forEach(kreditkarten::add);
        return kreditkarten;
    }

    public GirokontoKreditkarteDO addKreditkarteToGirokonto(Long gkId) {
        return girokonto_kreditkartenService.addGkKKEintrag(gkId);
    }

    public KreditkartenDO editKK(Long id, Long limit) {
        KreditkartenDO kreditkarteById = findKreditkarteById(id);
        kreditkarteById.setKreditlimit(BigDecimal.valueOf(limit));
        return kreditkartenRepository.save(kreditkarteById);
    }

    public boolean withdrawMoney(Long id, BigDecimal amount) throws WithdrawException {
        GirokontoKreditkarteDO verbund = girokonto_kreditkartenService.findAll()
                .stream()
                .filter(item -> item.getKreditkartenId().equals(BigDecimal.valueOf(id)))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        GirokontoDO girokontoById = girokontoService.findGirokontoById(verbund.getGirokontoId().longValue());
        KreditkartenDO kreditkarteById = kreditkartenService.findKreditkarteById(verbund.getKreditkartenId().longValue());

        BigDecimal limitKK = kreditkarteById.getKreditlimit();
        BigDecimal guthabenGK = girokontoById.getGuthaben();

        BigDecimal maximalesLimit = guthabenGK.add(limitKK);
        BigDecimal withdraw = maximalesLimit.subtract(amount);
        if (withdraw.doubleValue() < 0) {
            throw new WithdrawException("Auszahlung nicht möglich - Limit überschritten");
        }
        return true;
    }
}
