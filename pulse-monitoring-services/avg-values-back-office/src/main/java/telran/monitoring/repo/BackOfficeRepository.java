package telran.monitoring.repo;

import org.springframework.stereotype.Repository;
import telran.monitoring.repositories.mongodb.AvgPulseRepository;

@Repository
public interface BackOfficeRepository extends AvgPulseRepository, BackOfficeCustomRepository {
}
