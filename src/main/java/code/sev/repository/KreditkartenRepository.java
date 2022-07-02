package code.sev.repository;

import code.sev.model.KreditkartenDO;
import org.springframework.data.repository.CrudRepository;

public interface KreditkartenRepository  extends CrudRepository<KreditkartenDO, Long> {
}
