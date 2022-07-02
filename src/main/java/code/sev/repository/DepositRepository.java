package code.sev.repository;

import code.sev.model.DepositDO;
import org.springframework.data.repository.CrudRepository;

public interface DepositRepository extends CrudRepository<DepositDO, Long> {
}
