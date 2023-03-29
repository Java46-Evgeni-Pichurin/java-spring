package telran.monitoring.repositories.redis;

import org.springframework.data.repository.CrudRepository;
import telran.monitoring.entities.redis.ProbesList;

public interface ProbesListRepository extends CrudRepository<ProbesList, Long> {
}