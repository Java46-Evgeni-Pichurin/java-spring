package telran.monitoring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import telran.monitoring.entities.jpa.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}