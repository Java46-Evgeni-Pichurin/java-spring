package telran.monitoring.repo;

import org.springframework.data.repository.CrudRepository;
import telran.monitoring.entities.LastProbe;

public interface AnalyzerRepository extends CrudRepository<LastProbe, Long> {
}