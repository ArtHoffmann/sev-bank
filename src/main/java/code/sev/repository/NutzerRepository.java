package code.sev.repository;

import code.sev.model.BankNutzerDO;
import code.sev.model.NutzerKey;
import org.springframework.data.repository.CrudRepository;

public interface NutzerRepository extends CrudRepository<BankNutzerDO, NutzerKey> {

}


