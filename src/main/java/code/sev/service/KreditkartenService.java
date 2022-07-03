package code.sev.service;

import code.sev.model.GirokontoKreditkarteDO;
import code.sev.model.KreditkartenDO;
import code.sev.repository.KreditkartenRepository;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class KreditkartenService {

    private KreditkartenRepository kreditkartenRepository;
    private Girokonto_KreditkartenService girokonto_kreditkartenService;

    public KreditkartenService(KreditkartenRepository kreditkartenRepository, Girokonto_KreditkartenService girokonto_kreditkartenService) {
        this.kreditkartenRepository = kreditkartenRepository;
        this.girokonto_kreditkartenService = girokonto_kreditkartenService;
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

    public void withdrawMoney(Long id, BigDecimal amount) {

    }
}
