package code.sev.service;

import code.sev.model.BankNutzerDO;
import code.sev.model.GirokontoDO;
import code.sev.model.NutzerGirokontoDO;
import code.sev.repository.NutzerGirokontoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class NutzerGirokontoService {

    private NutzerGirokontoRepository nutzerGirokontoRepository;
    private GirokontoService girokontoService;
    private NutzerService nutzerService;

    public NutzerGirokontoService(NutzerGirokontoRepository nutzerGirokontoRepository, GirokontoService girokontoService, NutzerService nutzerService) {
        this.nutzerGirokontoRepository = nutzerGirokontoRepository;
        this.girokontoService = girokontoService;
        this.nutzerService = nutzerService;
    }

    public List<NutzerGirokontoDO> findAll() {
        List<NutzerGirokontoDO> liste = new ArrayList<>();
        nutzerGirokontoRepository.findAll().forEach(liste::add);
        return liste;
    }

    @Transactional
    public NutzerGirokontoDO addNutzerGKVerbindung(Long girokontoId, Long nutzerId) throws Exception {
        boolean gkFound = validateGirokontoId(girokontoId);
        boolean nutzerFound = validateNutzerId(nutzerId);
        boolean exists = checkEntity(girokontoId, nutzerId);
        if (gkFound && nutzerFound && !exists) {
            NutzerGirokontoDO nutzerGirokontoDO = new NutzerGirokontoDO();
            nutzerGirokontoDO.setGirokontoId(girokontoId);
            nutzerGirokontoDO.setNutzerId(nutzerId);
            return nutzerGirokontoRepository.save(nutzerGirokontoDO);
        }
        throw new Exception("Nutzer konnte nicht dem Girokonto hinzugefügt werden");
    }

    private boolean checkEntity(Long girokontoId, Long nutzerId) {
        return findAll().stream().anyMatch(item -> item.getGirokontoId().equals(girokontoId) && item.getNutzerId().equals(nutzerId));
    }

    private boolean validateNutzerId(Long nutzerId) {
        try {
            BankNutzerDO nutzerById = nutzerService.findAllNutzer().stream().filter(nutzer -> Objects.equals(nutzer.getNutzerKey().getKundennummer(), nutzerId)).findFirst().orElseThrow(IllegalArgumentException::new);
            return nutzerById != null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Nutzer mit der ID " + nutzerId + " konnte nicht gefunden werden");
        }
    }

    private boolean validateGirokontoId(Long girokontoId) {
        GirokontoDO girokontoById = girokontoService.findGirokontoById(girokontoId);
        return girokontoById != null;
    }

}
