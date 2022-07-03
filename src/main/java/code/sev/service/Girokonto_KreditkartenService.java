package code.sev.service;

import code.sev.model.GirokontoKreditkarteDO;
import code.sev.model.KreditkartenDO;
import code.sev.repository.Girokonto_KreditkartenRepository;
import code.sev.repository.KreditkartenRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@ApplicationScoped
public class Girokonto_KreditkartenService {

    private Girokonto_KreditkartenRepository girokonto_kreditkartenRepository;
    private KreditkartenRepository kreditkartenRepository;


    public Girokonto_KreditkartenService(Girokonto_KreditkartenRepository girokonto_kreditkartenRepository, KreditkartenRepository kreditkartenRepository) {
        this.girokonto_kreditkartenRepository = girokonto_kreditkartenRepository;
        this.kreditkartenRepository = kreditkartenRepository;
    }

    @Transactional
    public GirokontoKreditkarteDO addGkKKEintrag(Long kontonummer) {
        KreditkartenDO kk = new KreditkartenDO();
        kk.setKreditlimit(BigDecimal.ZERO);
        Long kkNumber = kk.generateKreditkartennummer();
        kk.setKreditkartennummer(kkNumber);
        kreditkartenRepository.save(kk);

        GirokontoKreditkarteDO girokonto_kreditkarteDO = new GirokontoKreditkarteDO();

        girokonto_kreditkarteDO.setKreditkartenId(BigDecimal.valueOf(kkNumber));
        girokonto_kreditkarteDO.setGirokontoId(BigDecimal.valueOf(kontonummer));
        girokonto_kreditkartenRepository.save(girokonto_kreditkarteDO);

        return girokonto_kreditkarteDO;
    }
}
