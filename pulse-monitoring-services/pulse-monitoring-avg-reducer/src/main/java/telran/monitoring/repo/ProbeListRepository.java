package telran.monitoring.repo;

import org.springframework.data.repository.CrudRepository;
import telran.monitoring.entities.ProbesList;

public interface ProbeListRepository extends CrudRepository<ProbesList, Long> {
}
