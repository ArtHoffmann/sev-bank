package code.sev.service;

import code.sev.model.BankNutzerDO;
import code.sev.model.BankNutzerTO;
import code.sev.repository.NutzerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class NutzerService {


    private final NutzerRepository repository;

    public NutzerService(NutzerRepository repository) {
        this.repository = repository;
    }

    public List<BankNutzerDO> findAllNutzer() {
        List<BankNutzerDO> nutzer = new ArrayList<>();
        repository.findAll().forEach(nutzer::add);
        return nutzer;
    }

    public BankNutzerDO findNutzerById(Long id) throws Exception {
        List<BankNutzerDO> nutzer = new ArrayList<>();
        repository.findAll().forEach(nutzer::add);
        return nutzer.stream().filter(n -> n.getNutzerKey().getId().equals(id)).findFirst().orElseThrow(Exception::new);
    }


    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void addNutzer(BankNutzerDO nutzer) {
        repository.save(nutzer);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public BankNutzerDO editNutzer(Long kdnr, BankNutzerTO nutzerTO) {
        List<BankNutzerDO> allNutzer = findAllNutzer();
        Optional<BankNutzerDO> first = allNutzer.stream().filter(nutzer -> nutzer.getNutzerKey().getKundennummer().equals(kdnr)).findFirst();
        if (first.isPresent()) {
            setUserData(first.get(), nutzerTO);
            return first.get();
        }
        throw new IllegalArgumentException("Edit fehlgeschlagen");
    }

    private void setUserData(BankNutzerDO bankNutzer, BankNutzerTO nutzerTO) {
        bankNutzer.setVorname(nutzerTO.getVorname());
        bankNutzer.setNachname(nutzerTO.getNachname());
        bankNutzer.setGeschlecht(nutzerTO.getGeschlecht());
        bankNutzer.setGeburtsdatum(nutzerTO.getGeburtsdatum());
    }
}
