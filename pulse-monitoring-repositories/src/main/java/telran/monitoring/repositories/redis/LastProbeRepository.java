package telran.monitoring.repositories.redis;

import org.springframework.data.repository.CrudRepository;
import telran.monitoring.entities.redis.LastProbe;

public interface LastProbeRepository extends CrudRepository<LastProbe, Long> {
}